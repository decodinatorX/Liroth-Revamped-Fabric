package com.decodinator.liroth.core.registry;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.core.recipetypes.AbstractQuantumRecipe;
import com.decodinator.liroth.core.recipetypes.AbstractSplitterRecipe;
import com.decodinator.liroth.core.recipetypes.QuantumRecipe;
import com.decodinator.liroth.core.recipetypes.SimpleQuantumSerializer;
import com.decodinator.liroth.core.recipetypes.SimpleSplitterSerializer;
import com.decodinator.liroth.core.recipetypes.SplitterRecipe;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;

public class LirothRecipeTypes {
	public static RecipeType<AbstractQuantumRecipe> QUANTUM_EXTRACTION;
	public static SimpleQuantumSerializer<AbstractQuantumRecipe> QUANTUM_EXTRACTION_S;
	
	public static RecipeType<AbstractSplitterRecipe> LIROTH_SPLITTING;
	public static SimpleSplitterSerializer<AbstractSplitterRecipe> LIROTH_SPLITTING_S;
	
	public static void init() {
		QUANTUM_EXTRACTION = Registry.register(BuiltInRegistries.RECIPE_TYPE, ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "quantum_extraction"), new RecipeType<AbstractQuantumRecipe>() {});
		QUANTUM_EXTRACTION_S = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "quantum_extraction"), new SimpleQuantumSerializer<>(QuantumRecipe::new, 200));
		LIROTH_SPLITTING = Registry.register(BuiltInRegistries.RECIPE_TYPE, ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "liroth_splitting"), new RecipeType<AbstractSplitterRecipe>() {});
		LIROTH_SPLITTING_S = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "liroth_splitting"), new SimpleSplitterSerializer<>(SplitterRecipe::new, 200));
		}
}
