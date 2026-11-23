package com.decodinator.liroth.core.worldgen;

import com.decodinator.liroth.mixin.RandomStateAccessor;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;
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
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.RandomSupport;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.jetbrains.annotations.NotNull;

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
        HolderGetter<NormalNoise.NoiseParameters> noises = ((RandomStateAccessor) (Object) randomState).liroth$getNoises();
        return RandomState.create(this.generatorSettings().value(), noises, this.getDimensionSeed(seed));
    }

    private RandomState withStoredDimensionSeed(RandomState randomState) {
        return this.withDimensionSeed(randomState, this.worldSeed);
    }

    @Override
    public @NotNull ChunkGeneratorStructureState createState(HolderLookup<StructureSet> structureSetLookup, RandomState randomState, long seed) {
        this.worldSeed = seed;
        long dimensionSeed = this.getDimensionSeed(seed);
        return ChunkGeneratorStructureState.createForNormal(
                this.withDimensionSeed(randomState, seed), dimensionSeed, this.getBiomeSource(), structureSetLookup
        );
    }

    @Override
    public @NotNull CompletableFuture<ChunkAccess> createBiomes(RandomState randomState, Blender blender, StructureManager structureManager, ChunkAccess chunk) {
        return super.createBiomes(this.withStoredDimensionSeed(randomState), blender, structureManager, chunk);
    }

    @Override
    public void applyCarvers(WorldGenRegion level, long seed, RandomState randomState, BiomeManager biomeManager, StructureManager structureManager, ChunkAccess chunk, GenerationStep.Carving step) {
        long dimensionSeed = this.getDimensionSeed(seed);
        super.applyCarvers(level, dimensionSeed, this.withDimensionSeed(randomState, seed), biomeManager,
                structureManager, chunk, step);
    }

    @Override
    public void buildSurface(WorldGenRegion level, StructureManager structureManager, RandomState randomState, ChunkAccess chunk) {
        super.buildSurface(level, structureManager, this.withDimensionSeed(randomState, level.getSeed()), chunk);
    }

    @Override
    public @NotNull CompletableFuture<ChunkAccess> fillFromNoise(Blender blender, RandomState randomState, StructureManager structureManager, ChunkAccess chunk) {
        return super.fillFromNoise(blender, this.withStoredDimensionSeed(randomState), structureManager, chunk);
    }

    @Override
    public int getBaseHeight(int x, int z, Heightmap.Types type, LevelHeightAccessor level, RandomState randomState) {
        return super.getBaseHeight(x, z, type, level, this.withStoredDimensionSeed(randomState));
    }

    @Override
    public @NotNull NoiseColumn getBaseColumn(int x, int z, LevelHeightAccessor level, RandomState randomState) {
        return super.getBaseColumn(x, z, level, this.withStoredDimensionSeed(randomState));
    }

    @Override
    public void addDebugScreenInfo(List<String> info, RandomState randomState, BlockPos pos) {
        super.addDebugScreenInfo(info, this.withStoredDimensionSeed(randomState), pos);
    }

    @Override
    public void spawnOriginalMobs(WorldGenRegion level) {
        if (!this.generatorSettings().value().disableMobGeneration()) {
            ChunkPos chunkPos = level.getCenter();
            Holder<Biome> biome = level.getBiome(chunkPos.getWorldPosition().atY(level.getMaxBuildHeight() - 1));
            WorldgenRandom random = new WorldgenRandom(new LegacyRandomSource(RandomSupport.generateUniqueSeed()));
            random.setDecorationSeed(this.getDimensionSeed(level.getSeed()), chunkPos.getMinBlockX(), chunkPos.getMinBlockZ());
            NaturalSpawner.spawnMobsForChunkGeneration(level, biome, chunkPos, random);
        }
    }

    @Override
    protected @NotNull MapCodec<? extends ChunkGenerator> codec() {
        return CODEC;
    }
}