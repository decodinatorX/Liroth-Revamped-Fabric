package com.decodinator.liroth.core.blocks;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

final class SilkTouchLoot {
    private SilkTouchLoot() {}

    static boolean hasSilkTouch(LootParams.Builder params) {
        ItemInstance tool = params.getOptionalParameter(LootContextParams.TOOL);
        return tool != null && tool.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY).keySet().stream().anyMatch(enchantment -> enchantment.is(Enchantments.SILK_TOUCH));
    }
}