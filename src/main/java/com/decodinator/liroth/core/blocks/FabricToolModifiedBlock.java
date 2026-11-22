package com.decodinator.liroth.core.blocks;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

/** Supplies Fabric's hoe and shovel block transformations for custom soil blocks. */
public interface FabricToolModifiedBlock {
    BlockState getToolModifiedState(BlockState state, ItemStack tool);
}
