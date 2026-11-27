package com.decodinator.liroth.core.recipetypes;

import com.decodinator.liroth.core.registry.LirothRecipeTypes;
import com.decodinator.liroth.util.RandomizedOutputIngredient;
import com.decodinator.liroth.util.RandomizedOutputIngredient2;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;

public abstract class AbstractSplitterRecipe implements Recipe<RecipeInput> {
    protected final RecipeType<? extends Recipe<RecipeInput>> type;
    protected final Identifier id;
    public final String group;
    public final Ingredient ingredient;
    public final ItemStackTemplate result;
    public final RandomizedOutputIngredient bonus;
    public final RandomizedOutputIngredient2 bonus2;
    public final float experience;
    public final int cookingTime;

    public AbstractSplitterRecipe(RecipeType<? extends Recipe<RecipeInput>> p_250197_, Identifier p_249379_, String p_249518_, Ingredient p_251354_, ItemStackTemplate p_252185_, RandomizedOutputIngredient bonus, RandomizedOutputIngredient2 bonus2, float p_252165_, int p_250256_) {
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


    public ItemStack assemble(RecipeInput input) {
        return this.result.create();
    }

    @Override
    public boolean matches(RecipeInput input, net.minecraft.world.level.Level level) {
        return input.size() > 0 && this.ingredient.test(input.getItem(0));
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

    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();
        nonnulllist.add(this.ingredient);
        return nonnulllist;
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

    public RandomizedOutputIngredient2 getBonusItem2() {
        return this.bonus2;
    }

    @Override
    public String group() {
        return this.group;
    }

    public int getCookingTime() {
        return this.cookingTime;
    }

    public Identifier getId() {
        return this.id;
    }

    public RecipeType<? extends Recipe<RecipeInput>> getType() {
        return this.type;
    }

    @Override
    public RecipeSerializer<? extends Recipe<RecipeInput>> getSerializer() {
        return LirothRecipeTypes.LIROTH_SPLITTING_S.serializer();
    }

    public ItemStack createBonus(RandomSource rand) {
        int count = this.bonus.bonus.orElseThrow().count();
        return this.bonus.bonus.orElseThrow().withCount(count > 1 ? 1 + rand.nextInt(count) : count).create();
    }

    public ItemStack createBonus2(RandomSource rand) {
        int count = this.bonus2.bonus.orElseThrow().count();
        return this.bonus2.bonus.orElseThrow().withCount(count > 1 ? 1 + rand.nextInt(count) : count).create();
    }

    public Ingredient at(int slot) {
        return getIngredients().get(slot);
    }

    public abstract ItemStack assemble(Container p_44001_, RegistryAccess p_267165_);

}