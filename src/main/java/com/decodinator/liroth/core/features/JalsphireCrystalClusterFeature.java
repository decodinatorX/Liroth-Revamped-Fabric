package com.decodinator.liroth.core.features;

import java.util.Optional;
import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.PointedDripstoneConfiguration;
import com.decodinator.liroth.core.registry.LirothBlocks;

public class JalsphireCrystalClusterFeature extends Feature<PointedDripstoneConfiguration> {
	private final Block pointedBlock;
	private final Block baseBlock;

	public JalsphireCrystalClusterFeature(Codec<PointedDripstoneConfiguration> p_191067_) {
		this(p_191067_, LirothBlocks.POINTED_JALSPHIRE_CRYSTAL, LirothBlocks.JALSPHIRE_CRYSTAL_BLOCK);
	}

	public JalsphireCrystalClusterFeature(Codec<PointedDripstoneConfiguration> codec, Block pointedBlock, Block baseBlock) {
		super(codec);
		this.pointedBlock = pointedBlock;
		this.baseBlock = baseBlock;
	}

	public boolean place(FeaturePlaceContext<PointedDripstoneConfiguration> p_191078_) {
		LevelAccessor levelaccessor = p_191078_.level();
		BlockPos blockpos = p_191078_.origin();
		RandomSource randomsource = p_191078_.random();
		PointedDripstoneConfiguration pointeddripstoneconfiguration = p_191078_.config();
		Optional<Direction> optional = getTipDirection(levelaccessor, blockpos, randomsource);
		if (optional.isEmpty()) {
			return false;
		} else {
			BlockPos blockpos1 = blockpos.relative(optional.get().getOpposite());
			createPatchOfDripstoneBlocks(levelaccessor, randomsource, blockpos1, pointeddripstoneconfiguration);
			int i = randomsource.nextFloat() < pointeddripstoneconfiguration.chanceOfTallerDripstone && JalsphireCrystalHelper.isEmptyOrWater(levelaccessor.getBlockState(blockpos.relative(optional.get()))) ? 2 : 1;
			JalsphireCrystalHelper.growPointedDripstone(levelaccessor, blockpos, optional.get(), i, pointedBlock, baseBlock);
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

	private void createPatchOfDripstoneBlocks(LevelAccessor p_225194_, RandomSource p_225195_, BlockPos p_225196_, PointedDripstoneConfiguration p_225197_) {
		JalsphireCrystalHelper.placeDripstoneBlockIfPossible(p_225194_, p_225196_, baseBlock);

		for(Direction direction : Direction.Plane.HORIZONTAL) {
			if (!(p_225195_.nextFloat() > p_225197_.chanceOfDirectionalSpread)) {
				BlockPos blockpos = p_225196_.relative(direction);
				JalsphireCrystalHelper.placeDripstoneBlockIfPossible(p_225194_, blockpos, baseBlock);
				if (!(p_225195_.nextFloat() > p_225197_.chanceOfSpreadRadius2)) {
					BlockPos blockpos1 = blockpos.relative(Direction.getRandom(p_225195_));
					JalsphireCrystalHelper.placeDripstoneBlockIfPossible(p_225194_, blockpos1, baseBlock);
					if (!(p_225195_.nextFloat() > p_225197_.chanceOfSpreadRadius3)) {
						BlockPos blockpos2 = blockpos1.relative(Direction.getRandom(p_225195_));
						JalsphireCrystalHelper.placeDripstoneBlockIfPossible(p_225194_, blockpos2, baseBlock);
					}
				}
			}
		}

	}
}