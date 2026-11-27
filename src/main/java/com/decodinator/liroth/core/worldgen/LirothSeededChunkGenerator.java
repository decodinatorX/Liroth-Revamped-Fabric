package com.decodinator.liroth.core.worldgen;

import com.decodinator.liroth.mixin.RandomStateAccessor;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.RandomSupport;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.densityfunction.SamplerContext;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.jspecify.annotations.Nullable;

// "I DIDNT ASK TO BE RIPPED APART AND STITCHED BACK TOGETHER!!!"

public class LirothSeededChunkGenerator extends NoiseBasedChunkGenerator {
    public static final MapCodec<LirothSeededChunkGenerator> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            BiomeSource.CODEC.fieldOf("biome_source").forGetter(ChunkGenerator::getBiomeSource),
            NoiseGeneratorSettings.CODEC.fieldOf("settings").forGetter(LirothSeededChunkGenerator::generatorSettings),
            Codec.LONG.fieldOf("seed_salt").forGetter(generator -> generator.seedSalt)
    ).apply(instance, instance.stable(LirothSeededChunkGenerator::new)));

    private final long seedSalt;
    private volatile long worldSeed;

    public LirothSeededChunkGenerator(BiomeSource biomeSource, Holder<NoiseGeneratorSettings> settings, long seedSalt) {
        super(biomeSource, settings);
        this.seedSalt = seedSalt;
    }

    public long getDimensionSeed(long seed) {
        return seed ^ this.seedSalt;
    }

    private RandomState withDimensionSeed(RandomState randomState, long seed) {
        HolderGetter<NormalNoise> noises =
                ((RandomStateAccessor) (Object) randomState).liroth$getNoises();
        return RandomState.create(noises, this.getDimensionSeed(seed), this.generatorSettings().value());
    }

    private RandomState withStoredDimensionSeed(RandomState randomState) {
        return this.withDimensionSeed(randomState, this.worldSeed);
    }

    @Override
    public ChunkGeneratorStructureState createState(HolderLookup<StructureSet> structureSetLookup,
                                                    RandomState randomState, long seed) {
        this.worldSeed = seed;
        long dimensionSeed = this.getDimensionSeed(seed);
        RandomState dimensionRandomState = this.withDimensionSeed(randomState, seed);
        return ChunkGeneratorStructureState.createForNormal(
                dimensionRandomState, dimensionSeed, this.getOrigin(dimensionRandomState), this.getBiomeSource(),
                structureSetLookup
        );
    }

    @Override
    public CompletableFuture<ChunkAccess> createBiomes(RandomState randomState, Blender blender,
                                                       StructureManager structureManager, ChunkAccess chunk) {
        return super.createBiomes(this.withStoredDimensionSeed(randomState), blender, structureManager, chunk);
    }

    @Override
    public CompletableFuture<ChunkAccess> buildTerrain(ChunkAccess chunk, Blender blender, RandomState randomState,
                                                       StructureManager structureManager, BiomeManager biomeManager,
                                                       @Nullable WorldGenRegion carverBiomeRegion,
                                                       Set<Holder<Biome>> possibleBiomes) {
        return super.buildTerrain(chunk, blender, this.withStoredDimensionSeed(randomState), structureManager,
                biomeManager, carverBiomeRegion, possibleBiomes);
    }

    @Override
    public int getBaseHeight(int x, int z, Heightmap.Types type, LevelHeightAccessor level, RandomState randomState) {
        return super.getBaseHeight(x, z, type, level, this.withStoredDimensionSeed(randomState));
    }

    @Override
    public NoiseColumn getBaseColumn(int x, int z, LevelHeightAccessor level, RandomState randomState) {
        return super.getBaseColumn(x, z, level, this.withStoredDimensionSeed(randomState));
    }

    @Override
    public void addDebugScreenInfo(List<String> info, RandomState randomState, BlockPos pos,
                                   SamplerContext samplerContext) {
        super.addDebugScreenInfo(info, this.withStoredDimensionSeed(randomState), pos, samplerContext);
    }

    @Override
    public void spawnOriginalMobs(WorldGenRegion level) {
        if (!this.generatorSettings().value().disableMobGeneration()) {
            ChunkPos chunkPos = level.getCenter();
            BlockPos sourcePos = chunkPos.getWorldPosition().atY(level.getMaxY());
            WorldgenRandom random = new WorldgenRandom(new LegacyRandomSource(RandomSupport.generateUniqueSeed()));
            random.setDecorationSeed(this.getDimensionSeed(level.getSeed()), chunkPos.getMinBlockX(), chunkPos.getMinBlockZ());
            NaturalSpawner.spawnMobsForChunkGeneration(level, sourcePos, chunkPos, random);
        }
    }

    @Override
    protected MapCodec<? extends ChunkGenerator> codec() {
        return CODEC;
    }
}