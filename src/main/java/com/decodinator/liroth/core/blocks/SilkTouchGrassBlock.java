package com.decodinator.liroth.core.blocks;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;

import java.util.List;

public class SilkTouchGrassBlock extends GrassBlock {
    public SilkTouchGrassBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        if (!SilkTouchLoot.hasSilkTouch(builder)) {
            return List.of();
        }
        return super.getDrops(state, builder);
    }
}