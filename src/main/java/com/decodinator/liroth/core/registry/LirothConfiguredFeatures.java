package com.decodinator.liroth.core.registry;

import com.decodinator.liroth.Liroth;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.Feature;

public final class LirothConfiguredFeatures {
    public static final ResourceKey<Feature> LIROTH = featureKey("liroth_tree");
    public static final ResourceKey<Feature> SPICED = featureKey("spiced_tree");
    public static final ResourceKey<Feature> TALLPIER = featureKey("tallpier_tree");
    public static final ResourceKey<Feature> DAMNATION = featureKey("damnation_tree");
    public static final ResourceKey<Feature> JAPZ = featureKey("japz_tree");
    public static final ResourceKey<Feature> KOOLAW = featureKey("koolaw_tree");
    public static final ResourceKey<Feature> PETRIFIED = featureKey("petrified_tree");
    public static final ResourceKey<Feature> DAMNATION_FUNGUS = featureKey("damnation_fungus");

    private LirothConfiguredFeatures() {
    }

    private static ResourceKey<Feature> featureKey(String name) {
        return ResourceKey.create(Registries.FEATURE, Liroth.C.mk(name));
    }
}