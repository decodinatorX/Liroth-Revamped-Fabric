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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import com.decodinator.liroth.core.registry.LirothBlocks;

public class JalsphireCrystalClusterFeature implements Feature {
	public static final MapCodec<JalsphireCrystalClusterFeature> CODEC =
			createCodec(LirothBlocks.POINTED_JALSPHIRE_CRYSTAL, LirothBlocks.JALSPHIRE_CRYSTAL_BLOCK);

	public static MapCodec<JalsphireCrystalClusterFeature> createCodec(Block pointedBlock, Block baseBlock) {
		return RecordCodecBuilder.mapCodec(instance ->
			instance.group(
					Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_taller_dripstone")
							.forGetter(feature -> feature.chanceOfTallerDripstone),
					Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_directional_spread")
							.forGetter(feature -> feature.chanceOfDirectionalSpread),
					Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_spread_radius2")
							.forGetter(feature -> feature.chanceOfSpreadRadius2),
					Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_spread_radius3")
							.forGetter(feature -> feature.chanceOfSpreadRadius3)
			).apply(instance, (taller, directional, radius2, radius3) ->
					new JalsphireCrystalClusterFeature(taller, directional, radius2, radius3, pointedBlock, baseBlock)));
	}

	private final Block pointedBlock;
	private final Block baseBlock;
	protected final float chanceOfTallerDripstone;
	protected final float chanceOfDirectionalSpread;
	protected final float chanceOfSpreadRadius2;
	protected final float chanceOfSpreadRadius3;

	public JalsphireCrystalClusterFeature(float chanceOfTallerDripstone, float chanceOfDirectionalSpread,
										  float chanceOfSpreadRadius2, float chanceOfSpreadRadius3) {
		this(chanceOfTallerDripstone, chanceOfDirectionalSpread, chanceOfSpreadRadius2, chanceOfSpreadRadius3,
				LirothBlocks.POINTED_JALSPHIRE_CRYSTAL, LirothBlocks.JALSPHIRE_CRYSTAL_BLOCK);
	}

	protected JalsphireCrystalClusterFeature(float chanceOfTallerDripstone, float chanceOfDirectionalSpread,
											 float chanceOfSpreadRadius2, float chanceOfSpreadRadius3,
											 Block pointedBlock, Block baseBlock) {
		this.pointedBlock = pointedBlock;
		this.baseBlock = baseBlock;
		this.chanceOfTallerDripstone = chanceOfTallerDripstone;
		this.chanceOfDirectionalSpread = chanceOfDirectionalSpread;
		this.chanceOfSpreadRadius2 = chanceOfSpreadRadius2;
		this.chanceOfSpreadRadius3 = chanceOfSpreadRadius3;
	}

	@Override
	public MapCodec<? extends Feature> codec() {
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
			int i = randomsource.nextFloat() < chanceOfTallerDripstone && JalsphireCrystalHelper.isEmptyOrWater(levelaccessor.getBlockState(blockpos.relative(optional.get()))) ? 2 : 1;
			JalsphireCrystalHelper.growPointedDripstone(levelaccessor, blockpos, optional.get(), i, false, pointedBlock, baseBlock);
			return true;
		}
	}

	private Optional<Direction> getTipDirection(LevelAccessor p_225199_, BlockPos p_225200_, RandomSource p_225201_) {
		boolean flag = JalsphireCrystalHelper.isDripstoneBase(p_225199_.getBlockState(p_225200_.above()), baseBlock);
		boolean flag1 = JalsphireCrystalHelper.isDripstoneBase(p_225199_.getBlockState(p_225200_.below()), baseBlock);
		if (flag && flag1) {
			return Optional.of(p_225201_.nextBoolean() ? Direction.DOWN : Direction.UP);
		} else if (flag) {
			return Optional.of(Direction.DOWN);
		} else {
			return flag1 ? Optional.of(Direction.UP) : Optional.empty();
		}
	}

	private void createPatchOfDripstoneBlocks(LevelAccessor p_225194_, RandomSource p_225195_, BlockPos p_225196_) {
		JalsphireCrystalHelper.placeDripstoneBlockIfPossible(p_225194_, p_225196_, baseBlock);

		for(Direction direction : Direction.Plane.HORIZONTAL) {
			if (!(p_225195_.nextFloat() > chanceOfDirectionalSpread)) {
				BlockPos blockpos = p_225196_.relative(direction);
				JalsphireCrystalHelper.placeDripstoneBlockIfPossible(p_225194_, blockpos, baseBlock);
				if (!(p_225195_.nextFloat() > chanceOfSpreadRadius2)) {
					BlockPos blockpos1 = blockpos.relative(Direction.getRandom(p_225195_));
					JalsphireCrystalHelper.placeDripstoneBlockIfPossible(p_225194_, blockpos1, baseBlock);
					if (!(p_225195_.nextFloat() > chanceOfSpreadRadius3)) {
						BlockPos blockpos2 = blockpos1.relative(Direction.getRandom(p_225195_));
						JalsphireCrystalHelper.placeDripstoneBlockIfPossible(p_225194_, blockpos2, baseBlock);
					}
				}
			}
		}

	}
}