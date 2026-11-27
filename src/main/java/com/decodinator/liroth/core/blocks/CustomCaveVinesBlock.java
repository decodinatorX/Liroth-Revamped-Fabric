package com.decodinator.liroth.core.blocks;

import com.decodinator.liroth.core.registry.LirothBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealSource;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.CaveVines;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;

import java.util.List;
import net.minecraft.world.item.ItemStack;

public class CustomCaveVinesBlock extends GrowingPlantHeadBlock implements BonemealableBlock, CaveVines {
    public CustomCaveVinesBlock(BlockBehaviour.Properties p_152959_) {
        super(p_152959_, Direction.DOWN, SHAPE, false, 0.1D);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0).setValue(BERRIES, Boolean.FALSE));
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos currentPos, Direction facing, BlockPos facingPos, BlockState facingState, RandomSource random) {
        if (facing == this.growthDirection.getOpposite() && !state.canSurvive(level, currentPos)) {
            ticks.scheduleTick(currentPos, this, 1);
        }

        if (facing != this.growthDirection || !facingState.is(this) && !facingState.is(LirothBlocks.JAPZ_VINES_PLANT)) {
            if (this.scheduleFluidTicks) {
                ticks.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
            }
            return super.updateShape(state, level, ticks, currentPos, facing, facingPos, facingState, random);
        }
        else {
            return this.updateBodyAfterConvertedFromHead(state, LirothBlocks.JAPZ_VINES_PLANT.defaultBlockState());
        }
    }

    @Override
    protected int getBlocksToGrowWhenBonemealed(RandomSource p_220928_) {
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
    protected BlockState updateBodyAfterConvertedFromHead(BlockState p_152987_, BlockState p_152988_) {
        return p_152988_.setValue(BERRIES, p_152987_.getValue(BERRIES));
    }

    @Override
    protected BlockState getGrowIntoState(BlockState p_220935_, RandomSource p_220936_) {
        return super.getGrowIntoState(p_220935_, p_220936_).setValue(BERRIES, p_220936_.nextFloat() < 0.11F);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        return CaveVines.use(player, state, level, pos);
    }

    @Override
    protected List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        return SilkTouchLoot.hasSilkTouch(params) ? super.getDrops(state, params) : List.of();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_152993_) {
        super.createBlockStateDefinition(p_152993_);
        p_152993_.add(BERRIES);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, BonemealSource source) {
        return !state.getValue(BERRIES);
    }

    @Override
    public boolean isBonemealSuccess(Level p_220930_, RandomSource p_220931_, BlockPos p_220932_, BlockState p_220933_, BonemealSource source) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel p_220923_, RandomSource p_220924_, BlockPos p_220925_, BlockState p_220926_, BonemealSource source) {
        p_220923_.setBlock(p_220925_, p_220926_.setValue(BERRIES, Boolean.TRUE), Block.UPDATE_NEIGHBORS);
    }
}