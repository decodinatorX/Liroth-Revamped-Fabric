package com.decodinator.liroth.core.blocks;


import com.decodinator.liroth.core.LirothBlocks;
import com.decodinator.liroth.core.LirothFluids;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class CustomKelpBlock  extends GrowingPlantHeadBlock implements LiquidBlockContainer {
	   protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D);
	   private static final double GROW_PER_TICK_PROBABILITY = 0.14D;

	   public CustomKelpBlock(BlockBehaviour.Properties p_54300_) {
	      super(p_54300_, Direction.UP, SHAPE, true, 0.14D);
	   }

	   protected boolean canGrowInto(BlockState p_54321_) {
	      return p_54321_.is(LirothBlocks.LIROTH_FLUID);
	   }

	   protected Block getBodyBlock() {
	      return LirothBlocks.VILE_TENTACLE;
	   }

	   protected boolean canAttachTo(BlockState p_153455_) {
	      return !p_153455_.is(Blocks.MAGMA_BLOCK);
	   }

	   public boolean canPlaceLiquid(BlockGetter p_54304_, BlockPos p_54305_, BlockState p_54306_, Fluid p_54307_) {
	      return false;
	   }

	   public boolean placeLiquid(LevelAccessor p_54309_, BlockPos p_54310_, BlockState p_54311_, FluidState p_54312_) {
	      return false;
	   }

	   protected int getBlocksToGrowWhenBonemealed(RandomSource p_221366_) {
	      return 1;
	   }

	   @Nullable
	   public BlockState getStateForPlacement(BlockPlaceContext p_54302_) {
	      FluidState fluidstate = p_54302_.getLevel().getFluidState(p_54302_.getClickedPos());
	      return fluidstate.is(FluidTags.WATER) && fluidstate.getAmount() == 8 ? super.getStateForPlacement(p_54302_) : null;
	   }

	   public FluidState getFluidState(BlockState p_54319_) {
	      return LirothFluids.LIROTH_FLUID_STILL.getSource(false);
	   }
	}
