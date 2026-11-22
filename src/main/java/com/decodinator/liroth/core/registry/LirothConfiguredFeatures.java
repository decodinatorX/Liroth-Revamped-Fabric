package com.decodinator.liroth.core.registry;

import java.util.List;
import java.util.OptionalInt;

import com.decodinator.liroth.Liroth;
import com.google.common.collect.ImmutableList;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.ThreeLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.DarkOakFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaJungleFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.SpruceFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.LeaveVineDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TrunkVineDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.DarkOakTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.MegaJungleTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

public class LirothConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> LIROTH = treeKey("liroth_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SPICED = treeKey("spiced_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TALLPIER = treeKey("tallpier_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DAMNATION = treeKey("damnation_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> JAPZ = treeKey("japz_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> KOOLAW = treeKey("koolaw_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PETRIFIED = treeKey("petrified_tree");

    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_LIROTH_ORE = FeatureUtils.createKey("liroth_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_LIROTH_ORE = FeatureUtils.createKey("nether_liroth_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> END_LIROTH_ORE = FeatureUtils.createKey("end_liroth_ore");

    public static final ResourceKey<ConfiguredFeature<?, ?>> TOURMALINE_ORE = FeatureUtils.createKey("tourmaline_ore");

    private static ResourceKey<ConfiguredFeature<?, ?>> treeKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, Liroth.C.mk(name));
    }

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> p_256319_) {
        FeatureUtils.register(p_256319_, LIROTH, Feature.TREE, (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(LirothBlocks.LIROTH_WOOD.getLog()), new FancyTrunkPlacer(3, 11, 0), BlockStateProvider.simple(LirothBlocks.LIROTH_LEAVES), new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4), new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))).ignoreVines().build());
        FeatureUtils.register(p_256319_, SPICED, Feature.TREE, (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(LirothBlocks.SPICED_WOOD.getLog()), new ForkingTrunkPlacer(5, 2, 2), BlockStateProvider.simple(LirothBlocks.SPICED_LEAVES), new AcaciaFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)), new TwoLayersFeatureSize(1, 0, 2))).ignoreVines().build());
        FeatureUtils.register(p_256319_, TALLPIER, Feature.TREE, (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(LirothBlocks.TALLPIER_WOOD.getLog()), new MegaJungleTrunkPlacer(10, 2, 19), BlockStateProvider.simple(LirothBlocks.TALLPIER_LEAVES), new MegaJungleFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 2), new TwoLayersFeatureSize(1, 1, 2))).decorators(ImmutableList.of(TrunkVineDecorator.INSTANCE, new LeaveVineDecorator(0.25F))).build());
        FeatureUtils.register(p_256319_, DAMNATION, Feature.TREE, (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(LirothBlocks.DAMNATION_WOOD.getLog()), new DarkOakTrunkPlacer(6, 2, 1), BlockStateProvider.simple(LirothBlocks.DAMNATION_LEAVES), new DarkOakFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0)), new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty()))).ignoreVines().build());
        FeatureUtils.register(p_256319_, JAPZ, Feature.TREE, (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(LirothBlocks.JAPZ_WOOD.getLog()), new StraightTrunkPlacer(5, 2, 1), BlockStateProvider.simple(LirothBlocks.JAPZ_LEAVES), new SpruceFoliagePlacer(UniformInt.of(2, 3), UniformInt.of(0, 2), UniformInt.of(1, 2)), new TwoLayersFeatureSize(2, 0, 2))).ignoreVines().build());
        FeatureUtils.register(p_256319_, KOOLAW, Feature.TREE, (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(LirothBlocks.KOOLAW_WOOD.getLog()), new StraightTrunkPlacer(4, 2, 0), BlockStateProvider.simple(LirothBlocks.KOOLAW_LEAVES), new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3), new TwoLayersFeatureSize(1, 0, 1)).build()));
        FeatureUtils.register(p_256319_, PETRIFIED, Feature.TREE, (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(LirothBlocks.PETRIFIED_DAMNATION_WOOD.getLog()), new ForkingTrunkPlacer(5, 2, 2), BlockStateProvider.simple(LirothBlocks.DAMNATION_LEAVES), new AcaciaFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)), new TwoLayersFeatureSize(1, 0, 2))).ignoreVines().build());

        RuleTest stoneTest = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepSlateTest = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherTest = new BlockMatchTest(Blocks.NETHERRACK);
        RuleTest endTest = new BlockMatchTest(Blocks.END_STONE);

        List<OreConfiguration.TargetBlockState> overworld_liroth_list = List.of(OreConfiguration.target(stoneTest, LirothBlocks.LIROTH_GEM.ore.defaultBlockState()), OreConfiguration.target(deepSlateTest, LirothBlocks.DEEPSLATE_LIROTH_ORE.defaultBlockState()));
        List<OreConfiguration.TargetBlockState> tourmaline_list = List.of(OreConfiguration.target(stoneTest, LirothBlocks.TOURMALINE_ORE.defaultBlockState()), OreConfiguration.target(deepSlateTest, LirothBlocks.DEEPSLATE_TOURMALINE_ORE.defaultBlockState()));
        FeatureUtils.register(p_256319_, OVERWORLD_LIROTH_ORE, Feature.ORE, new OreConfiguration(overworld_liroth_list, 4, 0.5F));
        FeatureUtils.register(p_256319_, NETHER_LIROTH_ORE, Feature.ORE, new OreConfiguration(netherTest, LirothBlocks.NETHER_LIROTH_GEM_ORE.defaultBlockState(), 4));
        FeatureUtils.register(p_256319_, END_LIROTH_ORE, Feature.ORE, new OreConfiguration(endTest, LirothBlocks.END_LIROTH_GEM_ORE.defaultBlockState(), 6));
        FeatureUtils.register(p_256319_, TOURMALINE_ORE, Feature.ORE, new OreConfiguration(tourmaline_list, 4, 0.5F));
    }
}