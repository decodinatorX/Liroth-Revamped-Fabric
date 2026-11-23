package com.decodinator.liroth.core.blocks;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SilkTouchGrassBlock extends GrassBlock {
    public SilkTouchGrassBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        ItemStack tool = builder.getOptionalParameter(LootContextParams.TOOL);
        boolean hasSilkTouch = tool != null && tool.getEnchantments().keySet().stream().anyMatch(enchantment -> enchantment.is(Enchantments.SILK_TOUCH));
        if (!hasSilkTouch) {
            return List.of();
        }
        return super.getDrops(state, builder);
    }
}