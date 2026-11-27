package com.decodinator.liroth.core.blocks;

import com.decodinator.liroth.core.registry.LirothFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public class LirothWaterPlant extends LirothDeadPlantBlock {
    private final Block deadCoralBlock;

    public LirothWaterPlant(Block deadCoralBlock, Block.Properties settings) {
        super(settings);
        this.deadCoralBlock = deadCoralBlock;
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (!LirothWaterPlant.scanForWater(state, world, pos)) {
            world.setBlock(pos, this.deadCoralBlock.defaultBlockState().setValue(WATERLOGGED, false), Block.UPDATE_NEIGHBORS);
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext p_54302_) {
        FluidState fluidstate = p_54302_.getLevel().getFluidState(p_54302_.getClickedPos());
        boolean isFullWater = fluidstate.is(FluidTags.WATER) && fluidstate.getAmount() == 8;
        boolean isFullLirothFluid = fluidstate.is(LirothFluids.LIROTH_FLUID_STILL) && fluidstate.getAmount() == 8;
        return isFullWater || isFullLirothFluid ? super.getStateForPlacement(p_54302_) : null;
    }
}