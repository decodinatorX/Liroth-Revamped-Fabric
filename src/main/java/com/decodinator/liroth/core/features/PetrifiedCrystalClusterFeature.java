package com.decodinator.liroth.core.features;

import java.util.Optional;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;

public final class PetrifiedCrystalClusterFeature implements Feature {
	public static final MapCodec<PetrifiedCrystalClusterFeature> CODEC = RecordCodecBuilder.mapCodec(instance ->
			instance.group(
					Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_taller_dripstone")
							.forGetter(feature -> feature.chanceOfTallerDripstone),
					Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_directional_spread")
							.forGetter(feature -> feature.chanceOfDirectionalSpread),
					Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_spread_radius2")
							.forGetter(feature -> feature.chanceOfSpreadRadius2),
					Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_spread_radius3")
							.forGetter(feature -> feature.chanceOfSpreadRadius3)
			).apply(instance, PetrifiedCrystalClusterFeature::new));

	private final float chanceOfTallerDripstone;
	private final float chanceOfDirectionalSpread;
	private final float chanceOfSpreadRadius2;
	private final float chanceOfSpreadRadius3;

	public PetrifiedCrystalClusterFeature(float chanceOfTallerDripstone, float chanceOfDirectionalSpread,
										  float chanceOfSpreadRadius2, float chanceOfSpreadRadius3) {
		this.chanceOfTallerDripstone = chanceOfTallerDripstone;
		this.chanceOfDirectionalSpread = chanceOfDirectionalSpread;
		this.chanceOfSpreadRadius2 = chanceOfSpreadRadius2;
		this.chanceOfSpreadRadius3 = chanceOfSpreadRadius3;
	}

	@Override
	public MapCodec<PetrifiedCrystalClusterFeature> codec() {
		return CODEC;
	}

	@Override
	public boolean place(WorldGenLevel levelaccessor, ChunkGenerator chunkGenerator, RandomSource randomsource, BlockPos blockpos) {
		Optional<Direction> optional = getTipDirection(levelaccessor, blockpos, randomsource);
		if (optional.isEmpty()) {
			return false;
		} else {
			BlockPos blockpos1 = blockpos.relative(optional.get().getOpposite());
			createPatchOfDripstoneBlocks(levelaccessor, randomsource, blockpos1);
			int i = randomsource.nextFloat() < chanceOfTallerDripstone && PetrifiedCrystalHelper.isEmptyOrWater(levelaccessor.getBlockState(blockpos.relative(optional.get()))) ? 2 : 1;
			PetrifiedCrystalHelper.growPointedDripstone(levelaccessor, blockpos, optional.get(), i, false);
			return true;
		}
	}

	private static Optional<Direction> getTipDirection(LevelAccessor p_225199_, BlockPos p_225200_, RandomSource p_225201_) {
		boolean flag = PetrifiedCrystalHelper.isDripstoneBase(p_225199_.getBlockState(p_225200_.above()));
		boolean flag1 = PetrifiedCrystalHelper.isDripstoneBase(p_225199_.getBlockState(p_225200_.below()));
		if (flag && flag1) {
			return Optional.of(p_225201_.nextBoolean() ? Direction.DOWN : Direction.UP);
		} else if (flag) {
			return Optional.of(Direction.DOWN);
		} else {
			return flag1 ? Optional.of(Direction.UP) : Optional.empty();
		}
	}

	private void createPatchOfDripstoneBlocks(LevelAccessor p_225194_, RandomSource p_225195_, BlockPos p_225196_) {
		PetrifiedCrystalHelper.placeDripstoneBlockIfPossible(p_225194_, p_225196_);

		for(Direction direction : Direction.Plane.HORIZONTAL) {
			if (!(p_225195_.nextFloat() > chanceOfDirectionalSpread)) {
				BlockPos blockpos = p_225196_.relative(direction);
				PetrifiedCrystalHelper.placeDripstoneBlockIfPossible(p_225194_, blockpos);
				if (!(p_225195_.nextFloat() > chanceOfSpreadRadius2)) {
					BlockPos blockpos1 = blockpos.relative(Direction.getRandom(p_225195_));
					PetrifiedCrystalHelper.placeDripstoneBlockIfPossible(p_225194_, blockpos1);
					if (!(p_225195_.nextFloat() > chanceOfSpreadRadius3)) {
						BlockPos blockpos2 = blockpos1.relative(Direction.getRandom(p_225195_));
						PetrifiedCrystalHelper.placeDripstoneBlockIfPossible(p_225194_, blockpos2);
					}
				}
			}
		}

	}
}