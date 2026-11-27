package com.decodinator.liroth.core.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FarmlandBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class CustomFarmBlock extends FarmlandBlock {
    public CustomFarmBlock(Properties properties, Supplier<Block> dirtBlock) {
        super(dirtBlock.get(), properties);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        level.setBlock(pos, state.setValue(MOISTURE, 7), Block.UPDATE_CLIENTS);
    }
}