package com.decodinator.liroth.core.recipetypes;

import com.decodinator.liroth.core.registry.LirothRecipeTypes;
import com.decodinator.liroth.util.RandomizedOutputIngredient;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.crafting.*;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.Level;

public abstract class AbstractQuantumRecipe implements Recipe<RecipeInput> {
   protected final RecipeType<? extends Recipe<RecipeInput>> type;
   public final String group;
   public final Ingredient ingredient;
   public final ItemStackTemplate result;
   public final RandomizedOutputIngredient bonus;
   public final float experience;
   public final int cookingTime;

   public AbstractQuantumRecipe(RecipeType<? extends Recipe<RecipeInput>> p_250197_, String p_249518_, Ingredient p_251354_, ItemStackTemplate p_252185_, RandomizedOutputIngredient bonus, float p_252165_, int p_250256_) {
      this.type = p_250197_;
      this.group = p_249518_;
      this.ingredient = p_251354_;
      this.result = p_252185_;
      this.bonus = bonus;
      this.experience = p_252165_;
      this.cookingTime = p_250256_;
   }

   public boolean matches(RecipeInput input, Level level) {
      return input.size() > 0 && this.ingredient.test(input.getItem(0));
   }

   public ItemStack assemble(RecipeInput input) {
      return this.result.create();
   }

   public NonNullList<Ingredient> getIngredients() {
      NonNullList<Ingredient> nonnulllist = NonNullList.create();
      nonnulllist.add(this.ingredient);
      return nonnulllist;
   }

   @Override
   public boolean showNotification() {
      return true;
   }

   @Override
   public PlacementInfo placementInfo() {
      return PlacementInfo.create(this.ingredient);
   }

   @Override
   public RecipeBookCategory recipeBookCategory() {
      return RecipeBookCategories.FURNACE_MISC;
   }

   public float getExperience() {
      return this.experience;
   }

   public ItemStack getResultItem() {
      return this.result.create();
   }

   public RandomizedOutputIngredient getBonusItem() {
      return this.bonus;
   }

   @Override
   public String group() {
      return this.group;
   }

   public int getCookingTime() {
      return this.cookingTime;
   }

   public RecipeType<? extends Recipe<RecipeInput>> getType() {
      return this.type;
   }

   @Override
   public RecipeSerializer<? extends Recipe<RecipeInput>> getSerializer() {
      return LirothRecipeTypes.QUANTUM_EXTRACTION_S.serializer();
   }

   public ItemStack createBonus(RandomSource rand) {
      int chance = Math.clamp(this.bonus.percent, 0, 100);
      if (this.bonus.bonus.isEmpty() || rand.nextInt(100) >= chance) {
         return ItemStack.EMPTY;
      }

      int count = this.bonus.bonus.get().count();
      return this.bonus.bonus.get().withCount(count > 1 ? 1 + rand.nextInt(count) : count).create();
   }

   public Ingredient at(int slot) {
      return getIngredients().get(slot);
   }

   public abstract ItemStack assemble(Container p_44001_, RegistryAccess p_267165_);

}