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

public abstract class MoltenSpinerios extends WaterTypeFluid {
	@Override
	public Fluid getSource() {
		return LirothFluids.MOLTEN_SPINERIOS_STILL;
	}
 
	@Override
	public Fluid getFlowing() {
		return LirothFluids.MOLTEN_SPINERIOS_FLOWING;
	}
 
	@Override
	public Item getBucket() {
		return LirothItems.MOLTEN_SPINERIOS_BUCKET;
	}
 
	@Override
	protected BlockState createLegacyBlock(FluidState fluidState) {
		return LirothBlocks.MOLTEN_SPINERIOS_BLOCK.defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(fluidState));
	}
 
	public static class Flowing extends MoltenSpinerios {
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
			return false;
		}
	}
 
	public static class Still extends MoltenSpinerios {
		@Override
		public int getAmount(FluidState fluidState) {
			return 8;
		}
 
		@Override
		public boolean isSource(FluidState fluidState) {
			return true;
		}

		@Override
		protected boolean canConvertToSource(Level var1) {
			return true;
		}
	}
}