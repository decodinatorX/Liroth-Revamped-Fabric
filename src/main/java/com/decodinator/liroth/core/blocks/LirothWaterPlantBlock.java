package com.decodinator.liroth.core.blocks;

import com.decodinator.liroth.core.registry.LirothFluids;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
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
import org.jetbrains.annotations.Nullable;

public class LirothWaterPlantBlock extends GrowingPlantBodyBlock implements LiquidBlockContainer {
    public Block tipBlock;
    public LirothWaterPlantBlock(BlockBehaviour.Properties p_54323_, Block tip) {
        super(p_54323_, Direction.UP, Shapes.block(), true);
        this.tipBlock = tip;
    }

    protected GrowingPlantHeadBlock getHeadBlock() {
        return (GrowingPlantHeadBlock)tipBlock;
    }

    public FluidState getFluidState(BlockState p_54336_) {
        return LirothFluids.LIROTH_FLUID_STILL.getSource().defaultFluidState();
    }

    protected boolean canAttachTo(BlockState p_153457_) {
        return true;
    }

    public boolean canPlaceLiquid(BlockGetter p_54325_, BlockPos p_54326_, BlockState p_54327_, Fluid p_54328_) {
        return false;
    }

    @Override
    public boolean canPlaceLiquid(@Nullable Player player, BlockGetter blockGetter, BlockPos blockPos, BlockState blockState, Fluid fluid) {
        return false;
    }

    public boolean placeLiquid(LevelAccessor p_54330_, BlockPos p_54331_, BlockState p_54332_, FluidState p_54333_) {
        return false;
    }

    @Override
    protected MapCodec<? extends GrowingPlantBodyBlock> codec() {
        return null;
    }
}