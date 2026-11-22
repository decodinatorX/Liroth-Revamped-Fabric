package com.decodinator.liroth.core.features;

import com.decodinator.liroth.core.registry.LirothBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class GleemstonePillarFeature extends Feature<NoneFeatureConfiguration> {
    public GleemstonePillarFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        LevelAccessor level = context.level();
        BlockPos origin = context.origin();
        if (!level.getBlockState(origin).isAir()) {
            return false;
        }

        BlockPos.MutableBlockPos cursor = origin.mutable();
        BlockPos floorAir = null;
        BlockPos ceilingAir = null;
        for (int distance = 0; distance < 64; distance++) {
            BlockPos below = cursor.below();
            if (level.isOutsideBuildHeight(below.getY())
                    || !level.getBlockState(below).isAir()) {
                if (!level.isOutsideBuildHeight(below.getY())
                        && level.getBlockState(below).isFaceSturdy(level, below, Direction.UP)) {
                    floorAir = cursor.immutable();
                }
                break;
            }
            cursor.move(Direction.DOWN);
        }

        cursor.set(origin);
        for (int distance = 0; distance < 64; distance++) {
            BlockPos above = cursor.above();
            if (level.isOutsideBuildHeight(above.getY())
                    || !level.getBlockState(above).isAir()) {
                if (!level.isOutsideBuildHeight(above.getY())
                        && level.getBlockState(above).isFaceSturdy(level, above, Direction.DOWN)) {
                    ceilingAir = cursor.immutable();
                }
                break;
            }
            cursor.move(Direction.UP);
        }

        if (floorAir == null || ceilingAir == null || floorAir.getY() > ceilingAir.getY()) {
            return false;
        }

        BlockState pillar = LirothBlocks.GLEEMSTONE.defaultBlockState();
        for (int y = floorAir.getY(); y <= ceilingAir.getY(); y++) {
            BlockPos center = new BlockPos(origin.getX(), y, origin.getZ());
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    BlockPos pos = center.offset(x, 0, z);
                    if (!level.isOutsideBuildHeight(pos.getY()) && level.getBlockState(pos).isAir()) {
                        level.setBlock(pos, pillar, 2);
                    }
                }
            }
        }

        level.setBlock(floorAir.below(), pillar, 2);
        level.setBlock(ceilingAir.above(), pillar, 2);
        return true;
    }
}