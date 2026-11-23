package com.decodinator.liroth.core.features;

import java.util.function.Consumer;

import com.decodinator.liroth.core.blocks.CustomPointedCrystal;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;

public class JalsphireCrystalHelper {

	protected static void buildBaseToTipColumn(Direction direction, int length, Block pointedBlock, Consumer<BlockState> stateConsumer) {
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
			stateConsumer.accept(createPointedDripstone(direction, DripstoneThickness.TIP, pointedBlock));
		}

	}

	protected static void growPointedDripstone(LevelAccessor level, BlockPos pos, Direction direction, int length, Block pointedBlock, Block baseBlock) {
		if (isDripstoneBase(level.getBlockState(pos.relative(direction.getOpposite())), baseBlock)) {
			BlockPos.MutableBlockPos mutablePos = pos.mutable();
			buildBaseToTipColumn(direction, length, pointedBlock, state -> {
				if (state.is(pointedBlock)) {
					state = state.setValue(CustomPointedCrystal.WATERLOGGED, level.isWaterAt(mutablePos));
				}

				level.setBlock(mutablePos, state, 2);
				mutablePos.move(direction);
			});
		}
	}

	protected static void placeDripstoneBlockIfPossible(LevelAccessor level, BlockPos pos, Block baseBlock) {
		BlockState blockstate = level.getBlockState(pos);
		if (blockstate.is(BlockTags.DRIPSTONE_REPLACEABLE)) {
			level.setBlock(pos, baseBlock.defaultBlockState(), 2);
		}
	}

	private static BlockState createPointedDripstone(Direction direction, DripstoneThickness thickness, Block pointedBlock) {
		return pointedBlock.defaultBlockState().setValue(CustomPointedCrystal.TIP_DIRECTION, direction)
				.setValue(CustomPointedCrystal.THICKNESS, thickness);
	}

	public static boolean isDripstoneBase(BlockState state, Block baseBlock) {
		return state.is(baseBlock) || state.is(BlockTags.DRIPSTONE_REPLACEABLE);
	}

	public static boolean isEmptyOrWater(BlockState p_159665_) {
		return p_159665_.isAir() || p_159665_.is(Blocks.WATER);
	}
}