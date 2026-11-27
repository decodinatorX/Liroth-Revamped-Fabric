package com.decodinator.liroth.core.features;

import com.decodinator.liroth.core.registry.LirothBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;

public abstract class LirothSkeletonFeature implements Feature {
    @Override
    public boolean place(WorldGenLevel structureWorldAccess, ChunkGenerator chunkGenerator, RandomSource random, BlockPos blockPos) {
        BlockState blockState = (BlockState)LirothBlocks.LIROTH_BONE_BLOCK.defaultBlockState();
        return this.generateCoral(structureWorldAccess, random, blockPos, blockState);
    }

    protected abstract boolean generateCoral(LevelAccessor var1, RandomSource var2, BlockPos var3, BlockState var4);

    protected boolean generateCoralPiece(LevelAccessor world, RandomSource random, BlockPos pos, BlockState state) {
        BlockPos blockPos = pos.above();
        BlockState blockState = world.getBlockState(pos);
        if (!blockState.is(LirothBlocks.LIROTH_FLUID_BLOCK) && !blockState.is(LirothBlocks.LIROTH_BONE_BLOCK) || !world.getBlockState(blockPos).is(LirothBlocks.LIROTH_FLUID_BLOCK)) {
            return false;
        }
        world.setBlock(pos, state, Block.UPDATE_NEIGHBORS);
        if (random.nextFloat() < 0.25f) {
            world.setBlock(blockPos, (BlockState)LirothBlocks.LIROTH_BONE_BLOCK.defaultBlockState(), Block.UPDATE_NEIGHBORS);
        } else if (random.nextFloat() < 0.05f) {
            world.setBlock(blockPos, (BlockState)LirothBlocks.SEA_EYE.defaultBlockState(), Block.UPDATE_NEIGHBORS);
        }
        return true;
    }
}