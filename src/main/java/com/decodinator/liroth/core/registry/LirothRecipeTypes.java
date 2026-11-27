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
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeType;

public class LirothRecipeTypes {
	public static RecipeType<AbstractQuantumRecipe> QUANTUM_EXTRACTION;
	public static SimpleQuantumSerializer<AbstractQuantumRecipe> QUANTUM_EXTRACTION_S;
	
	public static RecipeType<AbstractSplitterRecipe> LIROTH_SPLITTING;
	public static SimpleSplitterSerializer<AbstractSplitterRecipe> LIROTH_SPLITTING_S;
	
	public static void init() {
		QUANTUM_EXTRACTION = Registry.register(BuiltInRegistries.RECIPE_TYPE, Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "quantum_extraction"), new RecipeType<AbstractQuantumRecipe>() {});
		QUANTUM_EXTRACTION_S = new SimpleQuantumSerializer<>(QuantumRecipe::new, 200);
		Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "quantum_extraction"), QUANTUM_EXTRACTION_S.serializer());
		LIROTH_SPLITTING = Registry.register(BuiltInRegistries.RECIPE_TYPE, Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "liroth_splitting"), new RecipeType<AbstractSplitterRecipe>() {});
		LIROTH_SPLITTING_S = new SimpleSplitterSerializer<>(SplitterRecipe::new, 200);
		Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "liroth_splitting"), LIROTH_SPLITTING_S.serializer());
		}
}
