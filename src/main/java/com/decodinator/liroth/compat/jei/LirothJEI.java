package com.decodinator.liroth.compat.jei;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.client.screens.LirothSplitterScreen;
import com.decodinator.liroth.client.screens.QuantumExtractorScreen;
import com.decodinator.liroth.core.recipetypes.QuantumRecipe;
import com.decodinator.liroth.core.recipetypes.SplitterRecipe;
import com.decodinator.liroth.core.registry.LirothBlocks;
import com.decodinator.liroth.core.registry.LirothRecipeTypes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.fabricmc.fabric.api.recipe.v1.FabricRecipeManager;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class LirothJEI implements IModPlugin {
    public final static Identifier PLUGIN_ID = Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "jei_plugin");

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new QuantumCategory(guiHelper));
        registration.addRecipeCategories(new SplitterCategory(guiHelper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        IModPlugin.super.registerRecipes(registration);
        MinecraftServer server = Minecraft.getInstance().getSingleplayerServer();
        if (server == null) {
            return;
        }
        RecipeManager recipeManager = server.getRecipeManager();

        registration.addRecipes(QuantumCategory.TYPE, recipeManager.getAllOfType(LirothRecipeTypes.QUANTUM_EXTRACTION).stream().map(holder -> (QuantumRecipe) holder.value()).toList());
        registration.addRecipes(SplitterCategory.TYPE, recipeManager.getAllOfType(LirothRecipeTypes.LIROTH_SPLITTING).stream().map(holder -> (SplitterRecipe) holder.value()).toList());
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addCraftingStation(QuantumCategory.TYPE, LirothBlocks.QUANTUM_EXTRACTOR);
        registration.addCraftingStation(SplitterCategory.TYPE, LirothBlocks.LIROTH_SPLITTER);
    }

    @Override
    public Identifier getPluginUid() {
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