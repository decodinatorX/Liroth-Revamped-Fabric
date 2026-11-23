package com.decodinator.liroth.core.recipetypes;

import com.decodinator.liroth.core.registry.LirothRecipeTypes;
import com.decodinator.liroth.util.RandomizedOutputIngredient;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractQuantumRecipe implements Recipe<RecipeInput> {
   protected final RecipeType<?> type;
   public final String group;
   public final Ingredient ingredient;
   public final ItemStack result;
   public final RandomizedOutputIngredient bonus;
   public final float experience;
   public final int cookingTime;

   public AbstractQuantumRecipe(RecipeType<?> p_250197_, String p_249518_, Ingredient p_251354_, ItemStack p_252185_, RandomizedOutputIngredient bonus, float p_252165_, int p_250256_) {
      this.type = p_250197_;
      this.group = p_249518_;
      this.ingredient = p_251354_;
      this.result = p_252185_;
      this.bonus = bonus;
      this.experience = p_252165_;
      this.cookingTime = p_250256_;
   }

   public boolean canCraftInDimensions(int p_43743_, int p_43744_) {
      return true;
   }

   public @NotNull NonNullList<Ingredient> getIngredients() {
      NonNullList<Ingredient> nonnulllist = NonNullList.create();
      nonnulllist.add(this.ingredient);
      return nonnulllist;
   }

   public float getExperience() {
      return this.experience;
   }

   public ItemStack getResultItem() {
      return this.result;
   }

   public RandomizedOutputIngredient getBonusItem() {
      return this.bonus;
   }

   public @NotNull String getGroup() {
      return this.group;
   }

   public int getCookingTime() {
      return this.cookingTime;
   }

   public @NotNull RecipeType<?> getType() {
      return this.type;
   }

   @Override
   public @NotNull RecipeSerializer<?> getSerializer() {
      return LirothRecipeTypes.QUANTUM_EXTRACTION_S;
   }

   public ItemStack createBonus(RandomSource rand) {
      int chance = Math.clamp(this.bonus.percent, 0, 100);
      if (this.bonus.bonus.isEmpty() || rand.nextInt(100) >= chance) {
         return ItemStack.EMPTY;
      }

      ItemStack getBonus = this.bonus.bonus.copy();
      if (this.bonus.bonus.getCount() > 1) {
         getBonus.setCount(1 + rand.nextInt(this.bonus.bonus.getCount()));
      }
      return getBonus;
   }

   public Ingredient at(int slot) {
      return getIngredients().get(slot);
   }

   public abstract ItemStack assemble(Container p_44001_, RegistryAccess p_267165_);
}