package com.decodinator.liroth.compat.jei;

import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.category.IRecipeCategory;

public abstract class FurnaceVariantCategory<T> implements IRecipeCategory<T> {
	protected final IDrawableStatic staticFlame;
	protected final IDrawableAnimated animatedFlame;

	public FurnaceVariantCategory(IGuiHelper guiHelper) {
		staticFlame = guiHelper.createDrawable(Constants.RECIPE_GUI_VANILLA, 82, 114, 14, 14);
		animatedFlame = guiHelper.createAnimatedDrawable(staticFlame, 300, IDrawableAnimated.StartDirection.TOP, true);
	}
}
