package com.decodinator.liroth.core.features;

import java.util.function.Consumer;

import com.decodinator.liroth.core.blocks.CustomPointedCrystal;
import com.decodinator.liroth.core.registry.LirothBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;

public class JalsphireCrystalHelper {
	protected static double getDripstoneHeight(double p_159624_, double p_159625_, double p_159626_, double p_159627_) {
		if (p_159624_ < p_159627_) {
			p_159624_ = p_159627_;
		}

		double d1 = p_159624_ / p_159625_ * 0.384D;
		double d2 = 0.75D * Math.pow(d1, 1.3333333333333333D);
		double d3 = Math.pow(d1, 0.6666666666666666D);
		double d4 = 0.3333333333333333D * Math.log(d1);
		double d5 = p_159626_ * (d2 - d3 - d4);
		d5 = Math.max(d5, 0.0D);
		return d5 / 0.384D * p_159625_;
	}

	protected static boolean isCircleMostlyEmbeddedInStone(WorldGenLevel p_159640_, BlockPos p_159641_, int p_159642_) {
		if (isEmptyOrWaterOrLava(p_159640_, p_159641_)) {
			return false;
		} else {
			float f1 = 6.0F / (float)p_159642_;

			for(float f2 = 0.0F; f2 < ((float)Math.PI * 2F); f2 += f1) {
				int i = (int)(Mth.cos(f2) * (float)p_159642_);
				int j = (int)(Mth.sin(f2) * (float)p_159642_);
				if (isEmptyOrWaterOrLava(p_159640_, p_159641_.offset(i, 0, j))) {
					return false;
				}
			}

			return true;
		}
	}

	protected static boolean isEmptyOrWater(LevelAccessor p_159629_, BlockPos p_159630_) {
		return p_159629_.isStateAtPosition(p_159630_, JalsphireCrystalHelper::isEmptyOrWater);
	}

	protected static boolean isEmptyOrWaterOrLava(LevelAccessor p_159660_, BlockPos p_159661_) {
		return p_159660_.isStateAtPosition(p_159661_, JalsphireCrystalHelper::isEmptyOrWaterOrLava);
	}

	protected static void buildBaseToTipColumn(Direction direction, int length, boolean mergeTip, Block pointedBlock,
											   Consumer<BlockState> stateConsumer) {
		if (length >= 3) {
			stateConsumer.accept(createPointedDripstone(direction, DripstoneThickness.BASE, pointedBlock));

			for (int i = 0; i < length - 3; ++i) {
				stateConsumer.accept(createPointedDripstone(direction, DripstoneThickness.MIDDLE, pointedBlock));
			}
		}

		if (length >= 2) {
			stateConsumer.accept(createPointedDripstone(direction, DripstoneThickness.FRUSTUM, pointedBlock));
		}

		if (length >= 1) {
			stateConsumer.accept(createPointedDripstone(direction,
					mergeTip ? DripstoneThickness.TIP_MERGE : DripstoneThickness.TIP, pointedBlock));
		}

	}

	protected static void growPointedDripstone(LevelAccessor level, BlockPos pos, Direction direction, int length,
											   boolean mergeTip, Block pointedBlock, Block baseBlock) {
		if (isDripstoneBase(level.getBlockState(pos.relative(direction.getOpposite())), baseBlock)) {
			BlockPos.MutableBlockPos mutablePos = pos.mutable();
			buildBaseToTipColumn(direction, length, mergeTip, pointedBlock, state -> {
				if (state.is(pointedBlock)) {
					state = state.setValue(CustomPointedCrystal.WATERLOGGED, level.isWaterAt(mutablePos));
				}

				level.setBlock(mutablePos, state, 2);
				mutablePos.move(direction);
			});
		}
	}

	protected static boolean placeDripstoneBlockIfPossible(LevelAccessor level, BlockPos pos, Block baseBlock) {
		BlockState blockstate = level.getBlockState(pos);
		if (blockstate.is(BlockTags.DRIPSTONE_REPLACEABLE)) {
			level.setBlock(pos, baseBlock.defaultBlockState(), 2);
			return true;
		} else {
			return false;
		}
	}

	private static BlockState createPointedDripstone(Direction direction, DripstoneThickness thickness, Block pointedBlock) {
		return pointedBlock.defaultBlockState().setValue(CustomPointedCrystal.TIP_DIRECTION, direction)
				.setValue(CustomPointedCrystal.THICKNESS, thickness);
	}

	public static boolean isDripstoneBaseOrLava(BlockState p_159650_) {
		return isDripstoneBase(p_159650_) || p_159650_.is(Blocks.LAVA);
	}

	public static boolean isDripstoneBase(BlockState state) {
		return isDripstoneBase(state, LirothBlocks.JALSPHIRE_CRYSTAL_BLOCK);
	}

	public static boolean isDripstoneBase(BlockState state, Block baseBlock) {
		return state.is(baseBlock) || state.is(BlockTags.DRIPSTONE_REPLACEABLE);
	}

	public static boolean isEmptyOrWater(BlockState p_159665_) {
		return p_159665_.isAir() || p_159665_.is(Blocks.WATER);
	}

	public static boolean isNeitherEmptyNorWater(BlockState p_203131_) {
		return !p_203131_.isAir() && !p_203131_.is(Blocks.WATER);
	}

	public static boolean isEmptyOrWaterOrLava(BlockState p_159667_) {
		return p_159667_.isAir() || p_159667_.is(Blocks.WATER) || p_159667_.is(Blocks.LAVA);
	}
}