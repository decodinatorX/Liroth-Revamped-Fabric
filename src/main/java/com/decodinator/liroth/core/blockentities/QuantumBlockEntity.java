package com.decodinator.liroth.core.blockentities;

import com.decodinator.liroth.core.registry.LirothRecipeTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class QuantumBlockEntity extends QuantumExtractorBlockEntity {
	public QuantumBlockEntity(BlockPos pos, BlockState state) {
		super(pos, state, LirothRecipeTypes.QUANTUM_EXTRACTION);
	}
}