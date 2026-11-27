package com.decodinator.liroth.core.blocks;

import com.decodinator.liroth.core.registry.LirothFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.Shapes;
import org.jspecify.annotations.Nullable;

public class LirothWaterPlantBlock extends GrowingPlantBodyBlock implements LiquidBlockContainer {
    private final Block tipBlock;
    public LirothWaterPlantBlock(BlockBehaviour.Properties p_54323_, Block tip) {
        super(p_54323_, Direction.UP, Shapes.block(), true);
        this.tipBlock = tip;
    }

    @Override
    protected GrowingPlantHeadBlock getHeadBlock() {
        return (GrowingPlantHeadBlock)tipBlock;
    }

    @Override
    public FluidState getFluidState(BlockState p_54336_) {
        return LirothFluids.LIROTH_FLUID_STILL.getSource().defaultFluidState();
    }

    @Override
    public boolean canPlaceLiquid(@Nullable LivingEntity entity, BlockGetter blockGetter, BlockPos blockPos, BlockState blockState, Fluid fluid) {
        return false;
    }

    @Override
    public boolean placeLiquid(LevelAccessor p_54330_, BlockPos p_54331_, BlockState p_54332_, FluidState p_54333_) {
        return false;
    }
}