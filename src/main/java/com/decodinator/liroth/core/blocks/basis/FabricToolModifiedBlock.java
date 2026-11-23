package com.decodinator.liroth.core.blocks.basis;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public interface FabricToolModifiedBlock {
    BlockState getToolModifiedState(BlockState state, ItemStack tool);
}
