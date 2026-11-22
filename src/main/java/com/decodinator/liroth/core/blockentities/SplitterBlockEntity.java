package com.decodinator.liroth.core.blockentities;

import com.decodinator.liroth.core.registry.LirothRecipeTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class SplitterBlockEntity extends LirothSplitterBlockEntity {
	public SplitterBlockEntity(BlockPos pos, BlockState state) {
		super(pos, state, LirothRecipeTypes.LIROTH_SPLITTING);
	}
}