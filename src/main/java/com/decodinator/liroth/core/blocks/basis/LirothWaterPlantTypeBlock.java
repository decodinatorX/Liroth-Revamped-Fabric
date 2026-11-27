package com.decodinator.liroth.core.blocks.basis;

import com.decodinator.liroth.core.registry.LirothFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class LirothWaterPlantTypeBlock extends Block implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape AABB = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D);

    public LirothWaterPlantTypeBlock(BlockBehaviour.Properties p_49161_) {
        super(p_49161_);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.valueOf(true)));
    }

    protected void tryScheduleDieTick(BlockState p_49165_, LevelAccessor p_49166_, BlockPos p_49167_) {
        if (!scanForWater(p_49165_, p_49166_, p_49167_)) {
            p_49166_.scheduleTick(p_49167_, this, 60 + p_49166_.getRandom().nextInt(40));
        }

    }

    protected static boolean scanForWater(BlockState p_49187_, BlockGetter p_49188_, BlockPos p_49189_) {
        if (p_49187_.getValue(WATERLOGGED)) {
            return true;
        } else {
            for(Direction direction : Direction.values()) {
                if (p_49188_.getFluidState(p_49189_.relative(direction)).is(FluidTags.WATER)) {
                    return true;
                }
            }

            return false;
        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext p_49163_) {
        FluidState fluidstate = p_49163_.getLevel().getFluidState(p_49163_.getClickedPos());
        return this.defaultBlockState().setValue(WATERLOGGED, Boolean.valueOf(fluidstate.is(FluidTags.WATER) && fluidstate.getAmount() == 8));
    }

    public VoxelShape getShape(BlockState p_49182_, BlockGetter p_49183_, BlockPos p_49184_, CollisionContext p_49185_) {
        return AABB;
    }

    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        if (state.getValue(WATERLOGGED)) {
            ticks.scheduleTick(pos, LirothFluids.LIROTH_FLUID_STILL, LirothFluids.LIROTH_FLUID_STILL.getTickDelay(level));
        }

        return direction == Direction.DOWN && !this.canSurvive(state, level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, level, ticks, pos, direction, neighborPos, neighborState, random);
    }

    public boolean canSurvive(BlockState p_49169_, LevelReader p_49170_, BlockPos p_49171_) {
        BlockPos blockpos = p_49171_.below();
        return p_49170_.getBlockState(blockpos).isFaceSturdy(p_49170_, blockpos, Direction.UP);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49180_) {
        p_49180_.add(WATERLOGGED);
    }

    public FluidState getFluidState(BlockState p_49191_) {
        return p_49191_.getValue(WATERLOGGED) ? LirothFluids.LIROTH_FLUID_STILL.defaultFluidState() : super.getFluidState(p_49191_);
    }
}