package com.decodinator.liroth.core.recipetypes;

import com.decodinator.liroth.core.registry.LirothRecipeTypes;
import com.decodinator.liroth.util.RandomizedOutputIngredient;
import com.decodinator.liroth.util.RandomizedOutputIngredient2;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;

public abstract class AbstractSplitterRecipe implements Recipe<RecipeInput> {
    protected final RecipeType<?> type;
    protected final ResourceLocation id;
    public final String group;
    public final Ingredient ingredient;
    public final ItemStack result;
    public final RandomizedOutputIngredient bonus;
    public final RandomizedOutputIngredient2 bonus2;
    public final float experience;
    public final int cookingTime;

    public AbstractSplitterRecipe(RecipeType<?> p_250197_, ResourceLocation p_249379_, String p_249518_, Ingredient p_251354_, ItemStack p_252185_, RandomizedOutputIngredient bonus, RandomizedOutputIngredient2 bonus2, float p_252165_, int p_250256_) {
        this.type = p_250197_;
        this.id = p_249379_;
        this.group = p_249518_;
        this.ingredient = p_251354_;
        this.result = p_252185_;
        this.bonus = bonus;
        this.bonus2 = bonus2;
        this.experience = p_252165_;
        this.cookingTime = p_250256_;
    }


    public ItemStack assemble(Container p_43746_) {
        return this.result.copy();
    }

    public boolean canCraftInDimensions(int p_43743_, int p_43744_) {
        return true;
    }

    public NonNullList<Ingredient> getIngredients() {
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

    public RandomizedOutputIngredient2 getBonusItem2() {
        return this.bonus2;
    }

    public String getGroup() {
        return this.group;
    }

    public int getCookingTime() {
        return this.cookingTime;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public RecipeType<?> getType() {
        return this.type;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return LirothRecipeTypes.LIROTH_SPLITTING_S;
    }

    public ItemStack createBonus(RandomSource rand) {
        ItemStack getBonus = this.bonus.bonus.copy();
        if (this.bonus.bonus.getCount() > 1) {
            getBonus.setCount(1 + rand.nextInt(this.bonus.bonus.getCount()));
        }
        return getBonus;
    }

    public ItemStack createBonus2(RandomSource rand) {
        ItemStack getBonus = this.bonus2.bonus.copy();
        if (this.bonus2.bonus.getCount() > 1) {
            getBonus.setCount(1 + rand.nextInt(this.bonus2.bonus.getCount()));
        }
        return getBonus;
    }

    public ItemStack[] ingredientAt(int slot) {
        Ingredient ing = at(slot);
        return ing.getItems();
    }

    public Ingredient at(int slot) {
        return getIngredients().get(slot);
    }

    public abstract ItemStack assemble(Container p_44001_, RegistryAccess p_267165_);

    public abstract ItemStack getResultItem(RegistryAccess p_267052_);
}
