package com.decodinator.liroth.core.registry;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.core.features.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.RandomPatchFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.PointedDripstoneConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;

public class LirothFeatures {

    public static Feature<NoneFeatureConfiguration> OBSIDIAN_SPIKE = new ObsidianSpikeFeature(NoneFeatureConfiguration.CODEC);
    public static Feature<NoneFeatureConfiguration> VILE_TENTACLE = new VileTentacleFeature(NoneFeatureConfiguration.CODEC);
    public static Feature<NoneFeatureConfiguration> LIROTH_BONE_CLAW = new LirothBoneClawFeature(NoneFeatureConfiguration.CODEC);
    public static Feature<NoneFeatureConfiguration> LIROTH_BONE_MUSHROOM = new LirothBoneMushroomFeature(NoneFeatureConfiguration.CODEC);
    public static Feature<NoneFeatureConfiguration> LIROTH_BONE_TREE = new LirothBoneTreeFeature(NoneFeatureConfiguration.CODEC);
    public static Feature<PointedDripstoneConfiguration> JALSPHIRE_CRYSTAL_CLUSTER = new JalsphireCrystalClusterFeature(PointedDripstoneConfiguration.CODEC);
    public static Feature<PointedDripstoneConfiguration> PETRIFIED_CRYSTAL_CLUSTER = new PetrifiedCrystalClusterFeature(PointedDripstoneConfiguration.CODEC);
    public static Feature<NoneFeatureConfiguration> DAMNATION_VINES = new DamnationVinesFeature(NoneFeatureConfiguration.CODEC);
    public static Feature<RandomPatchConfiguration> WILTING_LIROTH_ROSES = new RandomPatchFeature(RandomPatchConfiguration.CODEC);
    public static Feature<PointedDripstoneConfiguration> GLEEMSTONE_SHARD_CLUSTER = new JalsphireCrystalClusterFeature(PointedDripstoneConfiguration.CODEC, LirothBlocks.POINTED_GLEEMSTONE_SHARD, LirothBlocks.GLEEMSTONE);
    public static Feature<NoneFeatureConfiguration> GLEEMSTONE_PILLAR = new GleemstonePillarFeature(NoneFeatureConfiguration.CODEC);

    public static void register() {
        RegisterFeature("obsidian_spike", OBSIDIAN_SPIKE);
        RegisterFeature("vile_tentacle", VILE_TENTACLE);
        RegisterFeature("liroth_bone_claw", LIROTH_BONE_CLAW);
        RegisterFeature("liroth_bone_mushroom", LIROTH_BONE_MUSHROOM);
        RegisterFeature("liroth_bone_tree", LIROTH_BONE_TREE);
        RegisterFeature("pointed_jalsphire_crystal", JALSPHIRE_CRYSTAL_CLUSTER);
        RegisterFeature("pointed_petrified_crystal", PETRIFIED_CRYSTAL_CLUSTER);
        RegisterFeature("damnation_vines", DAMNATION_VINES);
        RegisterFeature("wilting_liroth_roses", WILTING_LIROTH_ROSES);
        RegisterFeature("pointed_gleemstone_shard", GLEEMSTONE_SHARD_CLUSTER);
        RegisterFeature("gleemstone_pillar", GLEEMSTONE_PILLAR);
    }

    public static void RegisterFeature(String name, Feature<?> feature) {
        Registry.register(BuiltInRegistries.FEATURE, Liroth.C.mk(name), feature);
    }
}