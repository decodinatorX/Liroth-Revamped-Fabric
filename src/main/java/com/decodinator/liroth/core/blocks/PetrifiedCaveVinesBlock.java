package com.decodinator.liroth.core.blocks;

import com.decodinator.liroth.core.registry.LirothBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealSource;
import net.minecraft.world.level.block.CaveVines;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;

public class PetrifiedCaveVinesBlock extends GrowingPlantHeadBlock implements CaveVines {
    public PetrifiedCaveVinesBlock(Properties pos) {
        super(pos, Direction.DOWN, SHAPE, false, 0.1D);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0).setValue(BERRIES, false));
    }

    @Override
    protected int getBlocksToGrowWhenBonemealed(RandomSource source) {
        return 1;
    }

    @Override
    protected boolean canGrowInto(BlockState state) {
        return state.isAir();
    }

    @Override
    protected Block getBodyBlock() {
        return LirothBlocks.PETRIFIED_VINES_PLANT;
    }

    @Override
    protected BlockState updateBodyAfterConvertedFromHead(BlockState state, BlockState blockState) {
        return blockState.setValue(BERRIES, state.getValue(BERRIES));
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        if (direction == this.growthDirection.getOpposite() && !state.canSurvive(level, pos)) {
            ticks.scheduleTick(pos, this, 1);
        }

        if (direction != this.growthDirection
                || !neighborState.is(this) && !neighborState.is(LirothBlocks.PETRIFIED_VINES_PLANT)) {
            if (this.scheduleFluidTicks) {
                ticks.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
            }
            return super.updateShape(state, level, ticks, pos, direction, neighborPos, neighborState, random);
        }
        return this.updateBodyAfterConvertedFromHead(state, LirothBlocks.PETRIFIED_VINES_PLANT.defaultBlockState());
    }

    @Override
    protected BlockState getGrowIntoState(BlockState state, RandomSource source) {
        return super.getGrowIntoState(state, source).setValue(BERRIES, source.nextFloat() < 0.11F);
    }

    @Override
    protected ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData) {
        return new ItemStack(Items.GLOW_BERRIES);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        return CaveVines.use(player, state, level, pos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BERRIES);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, BonemealSource source) {
        return !state.getValue(BERRIES);
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state, BonemealSource source) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state, BonemealSource source) {
        level.setBlock(pos, state.setValue(BERRIES, true), Block.UPDATE_NEIGHBORS);
    }

    @Override
    protected List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        return SilkTouchLoot.hasSilkTouch(params) ? super.getDrops(state, params) : List.of();
    }
}