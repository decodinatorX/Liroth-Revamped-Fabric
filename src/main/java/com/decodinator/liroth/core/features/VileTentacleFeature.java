package com.decodinator.liroth.core.features;

import com.decodinator.liroth.core.blocks.LirothWaterTipBlock;
import com.decodinator.liroth.core.registry.LirothBlocks;
import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.chunk.ChunkGenerator;

public final class VileTentacleFeature implements Feature {
    public static final MapCodec<VileTentacleFeature> CODEC = MapCodec.unit(VileTentacleFeature::new);

    @Override
    public MapCodec<VileTentacleFeature> codec() {
        return CODEC;
    }

    @Override
    public boolean place(WorldGenLevel structureWorldAccess, ChunkGenerator chunkGenerator, RandomSource random, BlockPos blockPos) {
        int i = 0;
        int j = structureWorldAccess.getHeight(Heightmap.Types.OCEAN_FLOOR, blockPos.getX(), blockPos.getZ());
        BlockPos blockPos2 = new BlockPos(blockPos.getX(), j, blockPos.getZ());
        if (structureWorldAccess.getBlockState(blockPos2).is(LirothBlocks.LIROTH_FLUID_BLOCK)) {
            BlockState blockState = LirothBlocks.VILE_TENTACLE_TIP.defaultBlockState();
            BlockState blockState2 = LirothBlocks.VILE_TENTACLE.defaultBlockState();
            int k = 1 + random.nextInt(10);
            for (int l = 0; l <= k; ++l) {
                if (structureWorldAccess.getBlockState(blockPos2).is(LirothBlocks.LIROTH_FLUID_BLOCK) && structureWorldAccess.getBlockState(blockPos2.above()).is(LirothBlocks.LIROTH_FLUID_BLOCK) && blockState2.canSurvive(structureWorldAccess, blockPos2)) {
                    if (l == k) {
                        structureWorldAccess.setBlock(blockPos2, (BlockState)blockState.setValue(LirothWaterTipBlock.AGE, random.nextInt(4) + 20), Block.UPDATE_NEIGHBORS);
                        ++i;
                    } else {
                        structureWorldAccess.setBlock(blockPos2, blockState2, Block.UPDATE_NEIGHBORS);
                    }
                } else if (l > 0) {
                    BlockPos blockPos3 = blockPos2.below();
                    if (!blockState.canSurvive(structureWorldAccess, blockPos3) || structureWorldAccess.getBlockState(blockPos3.below()).is(LirothBlocks.VILE_TENTACLE_TIP)) break;
                    structureWorldAccess.setBlock(blockPos3, (BlockState)blockState.setValue(LirothWaterTipBlock.AGE, random.nextInt(4) + 20), Block.UPDATE_NEIGHBORS);
                    ++i;
                    break;
                }
                blockPos2 = blockPos2.above();
            }
        }
        return i > 0;
    }
}