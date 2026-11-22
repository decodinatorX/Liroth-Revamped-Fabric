package com.decodinator.liroth.core.blocks;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

final class SilkTouchLoot {
    private SilkTouchLoot() {}

    static boolean hasSilkTouch(LootParams.Builder params) {
        ItemStack tool = params.getOptionalParameter(LootContextParams.TOOL);
        return tool != null && tool.getEnchantments().keySet().stream()
                .anyMatch(enchantment -> enchantment.is(Enchantments.SILK_TOUCH));
    }
}