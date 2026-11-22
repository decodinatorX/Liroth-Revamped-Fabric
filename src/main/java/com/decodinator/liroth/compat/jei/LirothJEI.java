package com.decodinator.liroth.compat.jei;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.core.recipetypes.QuantumRecipe;
import com.decodinator.liroth.core.recipetypes.SplitterRecipe;
import com.decodinator.liroth.core.registry.LirothBlocks;
import com.decodinator.liroth.core.registry.LirothRecipeTypes;
import com.decodinator.liroth.client.screens.LirothSplitterScreen;
import com.decodinator.liroth.client.screens.QuantumExtractorScreen;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class LirothJEI implements IModPlugin {
    public final static ResourceLocation PLUGIN_ID = ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "jei_plugin");

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IModPlugin.super.registerCategories(registration);

        IJeiHelpers jeiHelpers = registration.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        
        registration.addRecipeCategories(new QuantumCategory(guiHelper));
        registration.addRecipeCategories(new SplitterCategory(guiHelper));
	}
	
    @SuppressWarnings("unchecked")
	@Override
    public void registerRecipes(IRecipeRegistration registration) {
        Minecraft minecraft = Minecraft.getInstance();
        IModPlugin.super.registerRecipes(registration);
        ClientLevel world = minecraft.level;
        if (world == null) {
            return;
        }
        var recipeManager = world.getRecipeManager();
        registration.addRecipes(QuantumCategory.TYPE, recipeManager.getAllRecipesFor(LirothRecipeTypes.QUANTUM_EXTRACTION).stream().map(holder -> (QuantumRecipe) holder.value()).toList());
        registration.addRecipes(SplitterCategory.TYPE, recipeManager.getAllRecipesFor(LirothRecipeTypes.LIROTH_SPLITTING).stream().map(holder -> (SplitterRecipe) holder.value()).toList());
    }
    
    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        IModPlugin.super.registerRecipeCatalysts(registration);
        registration.addRecipeCatalyst(
                new ItemStack(LirothBlocks.QUANTUM_EXTRACTOR),
                QuantumCategory.TYPE
        );
        registration.addRecipeCatalyst(
                new ItemStack(LirothBlocks.LIROTH_SPLITTER),
                SplitterCategory.TYPE
        );
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        IModPlugin.super.registerRecipeTransferHandlers(registration);
    }

	@Override
	public ResourceLocation getPluginUid() {
		return PLUGIN_ID;
	}
	
	  @Override
	  public void registerGuiHandlers(IGuiHandlerRegistration registry) {
	    registry.addRecipeClickArea(LirothSplitterScreen.class,
	        73, 30,
	        30, 24, SplitterCategory.TYPE);
	    registry.addRecipeClickArea(QuantumExtractorScreen.class,
	        75, 22,
	        32, 21, QuantumCategory.TYPE);
	  }
}
