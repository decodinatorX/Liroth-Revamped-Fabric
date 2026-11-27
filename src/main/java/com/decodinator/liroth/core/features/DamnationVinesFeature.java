package com.decodinator.liroth.core.features;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;

public class DamnationVinesFeature implements Feature {
    public static final MapCodec<DamnationVinesFeature> CODEC = MapCodec.unit(DamnationVinesFeature::new);
    private static final Direction[] DIRECTIONS = Direction.values();

    public DamnationVinesFeature() {
    }

    @Override
    public MapCodec<DamnationVinesFeature> codec() {
        return CODEC;
    }

    @Override
    public boolean place(WorldGenLevel structureWorldAccess, ChunkGenerator chunkGenerator, RandomSource random, BlockPos blockPos) {
        if (!structureWorldAccess.isEmptyBlock(blockPos)) {
            return false;
        }
        BlockState blockState = structureWorldAccess.getBlockState(blockPos.above());
        if (!blockState.is(Blocks.NETHERRACK) && !blockState.is(Blocks.NETHER_WART_BLOCK)) {
            return false;
        }
        this.generateNetherWartBlocksInArea(structureWorldAccess, random, blockPos);
        this.generateVinesInArea(structureWorldAccess, random, blockPos);
        return true;
    }

    private void generateNetherWartBlocksInArea(LevelAccessor world, RandomSource random, BlockPos pos) {
        world.setBlock(pos, Blocks.NETHER_WART_BLOCK.defaultBlockState(), Block.UPDATE_NEIGHBORS);
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        BlockPos.MutableBlockPos mutable2 = new BlockPos.MutableBlockPos();
        for (int i = 0; i < 200; ++i) {
            mutable.setWithOffset(pos, random.nextInt(6) - random.nextInt(6), random.nextInt(2) - random.nextInt(5), random.nextInt(6) - random.nextInt(6));
            if (!world.isEmptyBlock(mutable)) continue;
            int j = 0;
            for (Direction direction : DIRECTIONS) {
                BlockState blockState = world.getBlockState(mutable2.setWithOffset(mutable, direction));
                if (blockState.is(Blocks.NETHERRACK) || blockState.is(Blocks.NETHER_WART_BLOCK)) {
                    ++j;
                }
                if (j > 1) break;
            }
            world.setBlock(mutable, Blocks.NETHER_WART_BLOCK.defaultBlockState(), Block.UPDATE_NEIGHBORS);
        }
    }

    private void generateVinesInArea(LevelAccessor world, RandomSource random, BlockPos pos) {
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        for (int i = 0; i < 100; ++i) {
            BlockState blockState;
            mutable.setWithOffset(pos, random.nextInt(8) - random.nextInt(8), random.nextInt(2) - random.nextInt(7), random.nextInt(8) - random.nextInt(8));
            if (!world.isEmptyBlock(mutable) || !(blockState = world.getBlockState(mutable.above())).is(Blocks.NETHERRACK) && !blockState.is(Blocks.NETHER_WART_BLOCK)) continue;
            int j = Mth.nextInt(random, 1, 8);
            if (random.nextInt(6) == 0) {
                j *= 2;
            }
            if (random.nextInt(5) == 0) {
                j = 1;
            }
            DamnationVinesFeature.generateVineColumn(world, random, mutable, j, 17, 25);
        }
    }

    public static void generateVineColumn(LevelAccessor world, RandomSource random, BlockPos.MutableBlockPos pos, int length, int minAge, int maxAge) {
        for (int i = 0; i <= length; ++i) {
            if (world.isEmptyBlock(pos)) {
                if (i == length || !world.isEmptyBlock(pos.below())) {
                    world.setBlock(pos, Blocks.WEEPING_VINES.defaultBlockState().setValue(GrowingPlantHeadBlock.AGE, Mth.nextInt(random, minAge, maxAge)), Block.UPDATE_NEIGHBORS);
                    break;
                }
                world.setBlock(pos, Blocks.WEEPING_VINES_PLANT.defaultBlockState(), Block.UPDATE_NEIGHBORS);
            }
            pos.move(Direction.DOWN);
        }
    }
}