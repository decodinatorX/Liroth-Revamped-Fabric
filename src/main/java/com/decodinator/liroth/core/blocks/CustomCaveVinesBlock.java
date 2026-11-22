package com.decodinator.liroth.core.blocks;

import com.decodinator.liroth.core.registry.LirothBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import net.minecraft.world.item.ItemStack;

public class CustomCaveVinesBlock extends GrowingPlantHeadBlock implements BonemealableBlock, CaveVines {
    public static final MapCodec<CustomCaveVinesBlock> CODEC = simpleCodec(CustomCaveVinesBlock::new);

    public CustomCaveVinesBlock(BlockBehaviour.Properties p_152959_) {
        super(p_152959_, Direction.DOWN, SHAPE, false, 0.1D);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0).setValue(BERRIES, Boolean.FALSE));
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        if (facing == this.growthDirection.getOpposite() && !state.canSurvive(level, currentPos)) {
            level.scheduleTick(currentPos, this, 1);
        }

        if (facing != this.growthDirection || !facingState.is(this) && !facingState.is(LirothBlocks.JAPZ_VINES_PLANT)) {
            if (this.scheduleFluidTicks) {
                level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
            }

            return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
        } else {
            return this.updateBodyAfterConvertedFromHead(state, LirothBlocks.JAPZ_VINES_PLANT.defaultBlockState());
        }
    }

    @Override
    protected int getBlocksToGrowWhenBonemealed(@NotNull RandomSource p_220928_) {
        return 1;
    }

    @Override
    protected boolean canGrowInto(BlockState p_152998_) {
        return p_152998_.isAir();
    }

    @Override
    protected Block getBodyBlock() {
        return LirothBlocks.JAPZ_VINES_PLANT;
    }

    @Override
    protected GrowingPlantHeadBlock getHeadBlock() {
        return (GrowingPlantHeadBlock) LirothBlocks.JAPZ_VINES;
    }

    @Override
    protected @NotNull BlockState updateBodyAfterConvertedFromHead(BlockState p_152987_, BlockState p_152988_) {
        return p_152988_.setValue(BERRIES, p_152987_.getValue(BERRIES));
    }

    @Override
    public @NotNull MapCodec<CustomCaveVinesBlock> codec() {
        return CODEC;
    }

    @Override
    protected @NotNull BlockState getGrowIntoState(@NotNull BlockState p_220935_, @NotNull RandomSource p_220936_) {
        return super.getGrowIntoState(p_220935_, p_220936_).setValue(BERRIES, p_220936_.nextFloat() < 0.11F);
    }

    @Override
    protected List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        return SilkTouchLoot.hasSilkTouch(params) ? super.getDrops(state, params) : List.of();
    }

    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> p_152993_) {
        super.createBlockStateDefinition(p_152993_);
        p_152993_.add(BERRIES);
    }

    @Override
    public boolean isBonemealSuccess(@NotNull Level p_220930_, @NotNull RandomSource p_220931_, @NotNull BlockPos p_220932_, @NotNull BlockState p_220933_) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel p_220923_, @NotNull RandomSource p_220924_, @NotNull BlockPos p_220925_, BlockState p_220926_) {
        p_220923_.setBlock(p_220925_, p_220926_.setValue(BERRIES, Boolean.TRUE), 2);
    }
}