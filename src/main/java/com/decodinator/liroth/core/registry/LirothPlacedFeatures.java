package com.decodinator.liroth.core.registry;

import java.util.List;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

public class LirothPlacedFeatures {

	   public static final ResourceKey<PlacedFeature> OVERWORLD_LIROTH_ORE = PlacementUtils.createKey("liroth_ore");
	   public static final ResourceKey<PlacedFeature> NETHER_LIROTH_ORE = PlacementUtils.createKey("nether_liroth_ore");
	   public static final ResourceKey<PlacedFeature> END_LIROTH_ORE = PlacementUtils.createKey("end_liroth_ore");

	   public static final ResourceKey<PlacedFeature> TOURMALINE_ORE = PlacementUtils.createKey("tourmaline_ore");

	   private static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_) {
		      return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
		   }

		   private static List<PlacementModifier> commonOrePlacement(int p_195344_, PlacementModifier p_195345_) {
		      return orePlacement(CountPlacement.of(p_195344_), p_195345_);
		   }

		   public static void bootstrap(BootstrapContext<PlacedFeature> p_256238_) {
			      HolderGetter<ConfiguredFeature<?, ?>> holdergetter = p_256238_.lookup(Registries.CONFIGURED_FEATURE);
			      Holder<ConfiguredFeature<?, ?>> liroth_ore_holder = holdergetter.getOrThrow(LirothConfiguredFeatures.OVERWORLD_LIROTH_ORE);
			      Holder<ConfiguredFeature<?, ?>> nether_liroth_ore_holder = holdergetter.getOrThrow(LirothConfiguredFeatures.NETHER_LIROTH_ORE);
			      Holder<ConfiguredFeature<?, ?>> end_liroth_ore_holder = holdergetter.getOrThrow(LirothConfiguredFeatures.END_LIROTH_ORE);
			      Holder<ConfiguredFeature<?, ?>> tourmaline_ore_holder = holdergetter.getOrThrow(LirothConfiguredFeatures.TOURMALINE_ORE);
			      PlacementUtils.register(p_256238_, OVERWORLD_LIROTH_ORE, liroth_ore_holder, commonOrePlacement(7, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))));
			      PlacementUtils.register(p_256238_, NETHER_LIROTH_ORE, nether_liroth_ore_holder, commonOrePlacement(10, PlacementUtils.RANGE_10_10));
			      PlacementUtils.register(p_256238_, END_LIROTH_ORE, end_liroth_ore_holder, commonOrePlacement(10, PlacementUtils.RANGE_10_10));
			      PlacementUtils.register(p_256238_, TOURMALINE_ORE, tourmaline_ore_holder, commonOrePlacement(7, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))));
		   }
}
