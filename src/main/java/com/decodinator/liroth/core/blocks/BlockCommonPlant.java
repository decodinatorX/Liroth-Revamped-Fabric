package com.decodinator.liroth.core.blocks;

import net.minecraft.world.level.block.BonemealSource;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FarmlandBlock;
import net.minecraft.world.level.block.SoundType;
import org.betterx.bclib.blocks.BlockProperties;
import org.betterx.bclib.behaviours.BehaviourBuilders;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MapColor;

public abstract class BlockCommonPlant extends Block implements BonemealableBlock {
    public static final IntegerProperty AGE = BlockProperties.AGE;

    public BlockCommonPlant(MapColor color) {
        super(BehaviourBuilders.createPlant(color).sound(SoundType.CROP).noOcclusion().noCollision());
    }

    public BlockCommonPlant(Properties settings) {
        super(settings.randomTicks());
    }

    public int getMaxAge() {
        return 3;
    }

    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
        stateManager.add(AGE);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess ticks, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        return state.canSurvive(world, pos) ? state : Blocks.AIR.defaultBlockState();
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state, BonemealSource source) {
        return state.getValue(AGE) < this.getMaxAge();
    }

    @Override
    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state, BonemealSource source) {
        int age = state.getValue(AGE);
        if (age < this.getMaxAge())
            return isFertile(world.getBlockState(pos.below())) ? (random.nextBoolean()) : (random.nextInt(4) == 0);
        else
            return false;
    }

    protected boolean canGrowTerrain(Level world, RandomSource random, BlockPos pos, BlockState state) {
        int age = state.getValue(AGE);
        if (age < this.getMaxAge())
            return isFertile(world.getBlockState(pos.below()))
                    ? (random.nextInt(8) == 0)
                    : (random.nextInt(16) == 0);
        else
            return false;
    }

    public static boolean isFertile(BlockState state) {
        return state.getBlock() instanceof FarmlandBlock;
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state, BonemealSource source) {
        int age = state.getValue(AGE);
        world.setBlockAndUpdate(pos, state.setValue(AGE, Math.min(this.getMaxAge(), age + 1)));
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        super.randomTick(state, world, pos, random);
        if (canGrowTerrain(world, random, pos, state))
            performBonemeal(world, random, pos, state, BonemealSource.MOB);
    }
}