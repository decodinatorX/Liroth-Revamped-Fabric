package com.decodinator.liroth.compat.jei;

import com.decodinator.liroth.core.recipetypes.AbstractSplitterRecipe;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractSplitterCategory<T extends AbstractSplitterRecipe> extends FurnaceVariantCategory<T>{
	protected final IDrawableStatic staticFlame;
	protected final IDrawableAnimated animatedFlame;
	private final IDrawable background;
	private final IDrawable icon;
	private final Component title;
	private final int regularCookTime;
	private final LoadingCache<Integer, IDrawableAnimated> cachedArrows;


	public AbstractSplitterCategory(IGuiHelper guiHelper, Block iconBlock, String translationKey, int regularCookTime) {
		super(guiHelper);
		this.staticFlame = guiHelper.createDrawable(JEILirothConstants.RECIPE_GUI_LIROTH_MOD, 93, 184, 14, 14);
		this.animatedFlame = guiHelper.createAnimatedDrawable(staticFlame, 300, IDrawableAnimated.StartDirection.TOP, true);
		title = Component.translatable(translationKey);
		this.regularCookTime = regularCookTime;
		this.background = guiHelper.createDrawable(JEILirothConstants.RECIPE_GUI_LIROTH_MOD, 0, 0, 82, 78);
		this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(iconBlock));
		this.cachedArrows = CacheBuilder.newBuilder().maximumSize(25).build(new CacheLoader<>() {
			@Override
			public @NotNull IDrawableAnimated load(@NotNull Integer cookTime) {return guiHelper.drawableBuilder(JEILirothConstants.RECIPE_GUI_LIROTH_MOD, 103, 198, 34, 53)
					.buildAnimated(cookTime, IDrawableAnimated.StartDirection.LEFT, false);
			}
		});
	}

	@Override
	public IDrawable getIcon() {
		return icon;
	}

	@Override
	public int getWidth() {
		return background.getWidth();
	}

	@Override
	public int getHeight() {
		return background.getHeight();
	}

	@Override
	public void draw(T recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
		background.draw(guiGraphics, 0, 0);
		animatedFlame.draw(guiGraphics, 4, 31);
		getArrow(recipe).draw(guiGraphics, 24, 12);
	}

	@Override
	public @NotNull Component getTitle() {
		return title;
	}

	protected IDrawableAnimated getArrow(AbstractSplitterRecipe recipe) {
		int cookTime = recipe.getCookingTime();
		if (cookTime <= 0) {
			cookTime = regularCookTime;
		}
		return this.cachedArrows.getUnchecked(cookTime);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, AbstractSplitterRecipe recipe, @NotNull IFocusGroup focuses) {

		builder.addSlot(RecipeIngredientRole.INPUT, 1, 13).add(recipe.at(0));
		builder.addSlot(RecipeIngredientRole.OUTPUT, 61, 5).add(recipe.getResultItem());
		if (!recipe.bonus.getStack().isEmpty()) {
			builder.addSlot(RecipeIngredientRole.OUTPUT, 61, 31).add(recipe.bonus.getStack());
		}
		if (!recipe.bonus2.getStack().isEmpty()) {
			builder.addSlot(RecipeIngredientRole.OUTPUT, 61, 57).add(recipe.bonus2.getStack());
		}
	}
}