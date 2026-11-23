package com.decodinator.liroth.core.features;

import com.decodinator.liroth.core.registry.LirothBlocks;
import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public abstract class LirothSkeletonFeature
extends Feature<NoneFeatureConfiguration> {
    public LirothSkeletonFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        RandomSource random = context.random();
        WorldGenLevel structureWorldAccess = context.level();
        BlockPos blockPos = context.origin();
        BlockState blockState = LirothBlocks.LIROTH_BONE_BLOCK.defaultBlockState();
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
            world.setBlock(blockPos, LirothBlocks.LIROTH_BONE_BLOCK.defaultBlockState(), Block.UPDATE_NEIGHBORS);
        } else if (random.nextFloat() < 0.05f) {
            world.setBlock(blockPos, LirothBlocks.SEA_EYE.defaultBlockState(), Block.UPDATE_NEIGHBORS);
        }
        return true;
    }
}