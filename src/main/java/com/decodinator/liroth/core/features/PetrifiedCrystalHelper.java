package com.decodinator.liroth.core.features;

import java.util.function.Consumer;

import com.decodinator.liroth.core.registry.LirothBlocks;
import com.decodinator.liroth.core.blocks.PointedPetrifiedCrystal;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;

public class PetrifiedCrystalHelper {

	protected static void buildBaseToTipColumn(Direction p_159652_, int p_159653_, Consumer<BlockState> p_159655_) {
		      if (p_159653_ >= 3) {
		         p_159655_.accept(createPointedDripstone(p_159652_, DripstoneThickness.BASE));

		         for(int i = 0; i < p_159653_ - 3; ++i) {
		            p_159655_.accept(createPointedDripstone(p_159652_, DripstoneThickness.MIDDLE));
		         }
		      }

		      if (p_159653_ >= 2) {
		         p_159655_.accept(createPointedDripstone(p_159652_, DripstoneThickness.FRUSTUM));
		      }

		      if (p_159653_ >= 1) {
		         p_159655_.accept(createPointedDripstone(p_159652_, DripstoneThickness.TIP));
		      }

		   }

		   protected static void growPointedDripstone(LevelAccessor p_190848_, BlockPos p_190849_, Direction p_190850_, int p_190851_) {
		      if (isDripstoneBase(p_190848_.getBlockState(p_190849_.relative(p_190850_.getOpposite())))) {
		         BlockPos.MutableBlockPos blockishit = p_190849_.mutable();
		         buildBaseToTipColumn(p_190850_, p_190851_, (p_190846_) -> {
		            if (p_190846_.is(LirothBlocks.POINTED_PETRIFIED_CRYSTAL)) {
		               p_190846_ = p_190846_.setValue(PointedPetrifiedCrystal.WATERLOGGED, p_190848_.isWaterAt(blockishit));
		            }

		            p_190848_.setBlock(blockishit, p_190846_, 2);
					 blockishit.move(p_190850_);
		         });
		      }
		   }

		   protected static void placeDripstoneBlockIfPossible(LevelAccessor p_190854_, BlockPos p_190855_) {
		      BlockState blockstate = p_190854_.getBlockState(p_190855_);
		      if (blockstate.is(BlockTags.DRIPSTONE_REPLACEABLE)) {
		         p_190854_.setBlock(p_190855_, LirothBlocks.PETRIFIED_CRYSTAL_BLOCK.defaultBlockState(), 2);
			  }
		   }

		   private static BlockState createPointedDripstone(Direction p_159657_, DripstoneThickness p_159658_) {
		      return LirothBlocks.POINTED_PETRIFIED_CRYSTAL.defaultBlockState().setValue(PointedPetrifiedCrystal.TIP_DIRECTION, p_159657_).setValue(PointedPetrifiedCrystal.THICKNESS, p_159658_);
		   }

	public static boolean isDripstoneBase(BlockState p_159663_) {
		      return p_159663_.is(LirothBlocks.PETRIFIED_CRYSTAL_BLOCK) || p_159663_.is(BlockTags.DRIPSTONE_REPLACEABLE);
		   }

		   public static boolean isEmptyOrWater(BlockState p_159665_) {
		      return p_159665_.isAir() || p_159665_.is(Blocks.WATER);
		   }

}