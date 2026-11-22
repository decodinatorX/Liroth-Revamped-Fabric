package com.decodinator.liroth.core.recipetypes;

import com.decodinator.liroth.core.registry.LirothBlocks;
import com.decodinator.liroth.core.registry.LirothRecipeTypes;

import com.decodinator.liroth.util.RandomizedOutputIngredient;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.Level;

public class QuantumRecipe extends AbstractQuantumRecipe {
   public QuantumRecipe(String p_250200_, Ingredient p_250340_, ItemStack p_250306_, RandomizedOutputIngredient bonus, float p_249577_, int p_250030_) {
      super(LirothRecipeTypes.QUANTUM_EXTRACTION, p_250200_, p_250340_, p_250306_, bonus, p_249577_, p_250030_);
   }

   @Override
   public ItemStack assemble(Container container, RegistryAccess registryAccess) {
      return this.result.copy();
   }

   @Override
   public ItemStack getResultItem(RegistryAccess registryAccess) {
      return this.result;
   }

   @Override
   public boolean matches(RecipeInput recipeInput, Level level) {
      return recipeInput.size() > 0 && this.ingredient.test(recipeInput.getItem(0));
   }

   @Override
   public ItemStack assemble(RecipeInput recipeInput, HolderLookup.Provider provider) {
      return this.result.copy();
   }

   @Override
   public ItemStack getResultItem(HolderLookup.Provider provider) {
      return this.result;
   }

   public ItemStack getToastSymbol() {
      return new ItemStack(LirothBlocks.QUANTUM_EXTRACTOR);
   }
}
