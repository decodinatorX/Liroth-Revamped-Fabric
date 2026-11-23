package com.decodinator.liroth.core.fluids;

import com.decodinator.liroth.core.registry.LirothBlocks;
import com.decodinator.liroth.core.registry.LirothFluids;
import com.decodinator.liroth.core.registry.LirothItems;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.NotNull;

public abstract class LirothFluid extends WaterTypeFluid {
	@Override
	public @NotNull Fluid getSource() {
		return LirothFluids.LIROTH_FLUID_STILL;
	}
 
	@Override
	public @NotNull Fluid getFlowing() {
		return LirothFluids.LIROTH_FLUID_FLOWING;
	}
 
	@Override
	public @NotNull Item getBucket() {
		return LirothItems.LIROTH_FLUID_BUCKET;
	}
 
	@Override
	protected @NotNull BlockState createLegacyBlock(FluidState fluidState) {
		return LirothBlocks.LIROTH_FLUID_BLOCK.defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(fluidState));
	}
 
	public static class Flowing extends LirothFluid {
		@Override
		protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
			super.createFluidStateDefinition(builder);
			builder.add(LEVEL);
		}
 
		@Override
		public int getAmount(FluidState fluidState) {
			return fluidState.getValue(LEVEL);
		}
 
		@Override
		public boolean isSource(FluidState fluidState) {
			return false;
		}

		@Override
		protected boolean canConvertToSource(Level var1) {
			return true;
		}
	}
 
	public static class Still extends LirothFluid {
		@Override
		public int getAmount(FluidState fluidState) {
			return 8;
		}

		@Override
		protected boolean canConvertToSource(Level var1) {
			return false;
		}

		@Override
		public boolean isSource(FluidState var1) {
			return true;
		}
	}
}