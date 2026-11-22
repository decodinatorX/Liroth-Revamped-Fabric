package com.decodinator.liroth.core.registry;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.client.screens.LirothSplitterScreenHandler;
import com.decodinator.liroth.client.screens.QuantumExtractorScreenHandler;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

import java.util.function.BiFunction;

public class LirothScreenHandlers {

    public static MenuType<LirothSplitterScreenHandler> LIROTH_SPLITTER_SCREEN_HANDLER;
    public static MenuType<QuantumExtractorScreenHandler> QUANTUM_EXTRACTOR_SCREEN_HANDLER;

    static <T extends AbstractContainerMenu> MenuType<T> register(ResourceLocation id, BiFunction<Integer, Inventory, T> factory) {
        MenuType<T> type = new MenuType<>(factory::apply, FeatureFlags.DEFAULT_FLAGS);
        return Registry.register(BuiltInRegistries.MENU, id, type);
    }
    
    public static void init() {
        LIROTH_SPLITTER_SCREEN_HANDLER = LirothScreenHandlers.register(ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "liroth_splitter"), LirothSplitterScreenHandler::new);
        QUANTUM_EXTRACTOR_SCREEN_HANDLER = LirothScreenHandlers.register(ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "quantum_extractor"), QuantumExtractorScreenHandler::new);
    }
}
