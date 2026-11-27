package com.decodinator.liroth.core.features.trees;

import com.decodinator.liroth.core.registry.LirothConfiguredFeatures;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.levelgen.feature.Feature;

public final class LirothTreeGrower {
    public static final TreeGrower LIROTH = create("liroth", LirothConfiguredFeatures.LIROTH);
    public static final TreeGrower DAMNATION = create("damnation", LirothConfiguredFeatures.DAMNATION);
    public static final TreeGrower JAPZ = create("japz", LirothConfiguredFeatures.JAPZ);
    public static final TreeGrower KOOLAW = create("koolaw", LirothConfiguredFeatures.KOOLAW);
    public static final TreeGrower PETRIFIED = create("petrified_damnation", LirothConfiguredFeatures.PETRIFIED);
    public static final TreeGrower PIER = create("pier", LirothConfiguredFeatures.TALLPIER);
    public static final TreeGrower SPICED = create("spiced", LirothConfiguredFeatures.SPICED);

    private LirothTreeGrower() {
    }

    private static TreeGrower create(String name, net.minecraft.resources.ResourceKey<Feature> feature) {
        return new TreeGrower(name, WeightedList.of(feature), WeightedList.of(), WeightedList.of(), feature);
    }
}