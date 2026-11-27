package com.decodinator.liroth.core.registry;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.core.features.*;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;

public class LirothFeatures {

    public static void register() {
        registerFeature("obsidian_spike", ObsidianSpikeFeature.CODEC);
        registerFeature("vile_tentacle", VileTentacleFeature.CODEC);
        registerFeature("liroth_bone_claw", LirothBoneClawFeature.CODEC);
        registerFeature("liroth_bone_mushroom", LirothBoneMushroomFeature.CODEC);
        registerFeature("liroth_bone_tree", LirothBoneTreeFeature.CODEC);
        registerFeature("pointed_jalsphire_crystal", JalsphireCrystalClusterFeature.CODEC);
        registerFeature("pointed_petrified_crystal", PetrifiedCrystalClusterFeature.CODEC);
        registerFeature("damnation_vines", DamnationVinesFeature.CODEC);
        registerFeature("pointed_gleemstone_shard", JalsphireCrystalClusterFeature.createCodec(LirothBlocks.POINTED_GLEEMSTONE_SHARD, LirothBlocks.GLEEMSTONE));
        registerFeature("gleemstone_pillar", GleemstonePillarFeature.CODEC);
    }

    private static void registerFeature(String name, MapCodec<? extends Feature> codec) {
        Registry.register(BuiltInRegistries.FEATURE_TYPE, Liroth.C.mk(name), codec);
    }
}