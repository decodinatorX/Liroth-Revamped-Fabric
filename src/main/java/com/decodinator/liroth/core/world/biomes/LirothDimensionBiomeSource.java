package com.decodinator.liroth.core.world.biomes;

import com.decodinator.liroth.Liroth;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.RegistryLookupCodec;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;

import java.util.Map;
import java.util.stream.Collectors;

public class LirothDimensionBiomeSource extends BiomeSource {
    public static final Codec<LirothDimensionBiomeSource> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(RegistryLookupCodec.of(Registry.BIOME_KEY).forGetter((biomeSource) ->
                    biomeSource.BIOME_REGISTRY), Codec.intRange(1, 20).fieldOf("biome_size").orElse(2).forGetter((biomeSource) ->
                    biomeSource.biomeSize), Codec.LONG.fieldOf("seed").stable().forGetter((biomeSource) ->
                    biomeSource.seed)).apply(instance, instance.stable(LirothDimensionBiomeSource::new)));

    public static final Identifier LIROTH_BIOME = new Identifier(Liroth.MOD_ID, "liroth_biome");
    public static final Identifier SPICED_DESERT = new Identifier(Liroth.MOD_ID, "spiced_desert");
    public static final Identifier TALLPIER = new Identifier(Liroth.MOD_ID, "tallpier");
    public static final Identifier VILE_SEA = new Identifier(Liroth.MOD_ID, "vile_sea");
    private final Registry<Biome> BIOME_REGISTRY;
    public static Registry<Biome> LAYERS_BIOME_REGISTRY;
    private final long seed;
    private final int biomeSize;

    protected LirothDimensionBiomeSource(Registry<Biome> biomeRegistry, int biomeSize, long seed) {
        super(biomeRegistry.getEntries().stream()
                .filter(entry -> entry.getKey().getValue().getNamespace().equals(Liroth.MOD_ID))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList()));
        this.BIOME_REGISTRY = biomeRegistry;
        LirothDimensionBiomeSource.LAYERS_BIOME_REGISTRY = biomeRegistry;
        this.biomeSize = biomeSize;
        this.seed = seed * 125;
    }

    @Override
    protected Codec<? extends BiomeSource> getCodec() {
        return CODEC;
    }

    @Override
    public BiomeSource withSeed(long seed) {
        return new LirothDimensionBiomeSource(this.BIOME_REGISTRY, this.biomeSize, seed);
    }

    @Override
    public Biome getBiome(int x, int y, int z, MultiNoiseUtil.MultiNoiseSampler noise) {
        if ((int) noise.sample(x, y, z).temperatureNoise() > 0.50 && noise.sample(x, y, z).temperatureNoise() < 0.50) {
            return BIOME_REGISTRY.get(LirothDimensionBiomeSource.TALLPIER);
        } else if ((int) noise.sample(x, y, z).temperatureNoise() > 0.50 && noise.sample(x, y, z).temperatureNoise() < 0.50) {
            return BIOME_REGISTRY.get(LirothDimensionBiomeSource.SPICED_DESERT);
        } else if ((int) noise.sample(x, y, z).temperatureNoise() > 0.50 && noise.sample(x, y, z).temperatureNoise() < 0.50) {
        	return BIOME_REGISTRY.get(LirothDimensionBiomeSource.LIROTH_BIOME);
        } else if ((int) noise.sample(x, y, z).temperatureNoise() > 0.50 && noise.sample(x, y, z).temperatureNoise() < 0.50) {
        	return BIOME_REGISTRY.get(LirothDimensionBiomeSource.VILE_SEA);
        } else {
            return BIOME_REGISTRY.get(LirothDimensionBiomeSource.LIROTH_BIOME);
        }
    }
}