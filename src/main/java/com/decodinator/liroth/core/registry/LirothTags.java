package com.decodinator.liroth.core.registry;

import com.decodinator.liroth.Liroth;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class LirothTags {
	  public static final TagKey<Block> DIRT_ORE_REPLACEABLES = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "dirt_ore_replaceables"));
	  public static final TagKey<Block> LIROTH_FARMLANDS = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "liroth_farmlands"));
	  public static final TagKey<Biome> LIROTH_BIOMES = TagKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "liroth_biomes"));
	  public static final TagKey<Fluid> STICKY_FLUIDS = TagKey.create(Registries.FLUID, Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "sticky_fluids"));
	  public static final TagKey<Item> TORCHES = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "torches"));
	  public static final TagKey<Item> CHEST = TagKey.create(Registries.ITEM, Identifier.withDefaultNamespace("chest"));
}
