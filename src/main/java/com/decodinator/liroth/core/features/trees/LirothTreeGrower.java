package com.decodinator.liroth.core.features.trees;

import com.decodinator.liroth.core.registry.LirothConfiguredFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.Optional;

public class LirothTreeGrower extends TreeGrower {
    public LirothTreeGrower(String name, Optional<ResourceKey<ConfiguredFeature<?, ?>>> megaTree, Optional<ResourceKey<ConfiguredFeature<?, ?>>> tree, Optional<ResourceKey<ConfiguredFeature<?, ?>>> flowers) {
        super(name, megaTree, tree, flowers);
    }

    public static final TreeGrower LIROTH = new TreeGrower(
            "liroth",
            Optional.empty(),
            Optional.of(LirothConfiguredFeatures.LIROTH),
            Optional.empty()
    );

    public static final TreeGrower DAMNATION = new TreeGrower(
            "damnation",
            Optional.empty(),
            Optional.of(LirothConfiguredFeatures.DAMNATION),
            Optional.empty()
    );

    public static final TreeGrower JAPZ = new TreeGrower(
            "japz",
            Optional.empty(),
            Optional.of(LirothConfiguredFeatures.JAPZ),
            Optional.empty()
    );

    public static final TreeGrower KOOLAW = new TreeGrower(
            "koolaw",
            Optional.empty(),
            Optional.of(LirothConfiguredFeatures.KOOLAW),
            Optional.empty()
    );

    public static final TreeGrower PETRIFIED = new TreeGrower(
            "petrified_damnation",
            Optional.empty(),
            Optional.of(LirothConfiguredFeatures.PETRIFIED),
            Optional.empty()
    );

    public static final TreeGrower PIER = new TreeGrower(
            "pier",
            Optional.empty(),
            Optional.of(LirothConfiguredFeatures.TALLPIER),
            Optional.empty()
    );

    public static final TreeGrower SPICED = new TreeGrower(
            "spiced",
            Optional.empty(),
            Optional.of(LirothConfiguredFeatures.SPICED),
            Optional.empty()
    );
}