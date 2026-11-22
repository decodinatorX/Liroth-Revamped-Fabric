package com.decodinator.liroth.util;

import com.decodinator.liroth.core.entities.FungalFiendEntity;
import com.decodinator.liroth.core.registry.LirothBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;

import java.util.HashSet;
import java.util.Set;

public final class FungalFiendExplosionHandler {
    private FungalFiendExplosionHandler() {}

    public static void onFungalFiendExplosion(Level level, Explosion explosion) {
        if (level.isClientSide || !(explosion.getDirectSourceEntity() instanceof FungalFiendEntity)) {
            return;
        }

        Set<BlockPos> blastBlocks = new HashSet<>(explosion.getToBlow());
        Set<BlockPos> exposedBlocks = new HashSet<>();
        for (BlockPos blastPos : blastBlocks) {
            for (Direction direction : Direction.values()) {
                BlockPos neighbor = blastPos.relative(direction);
                if (!blastBlocks.contains(neighbor)) {
                    exposedBlocks.add(neighbor);
                }
            }
        }

        for (BlockPos pos : exposedBlocks) {
            var state = level.getBlockState(pos);
            if (!state.isAir() && state.isSolid()) {
                level.setBlock(pos, LirothBlocks.DAMNATION_SOIL.defaultBlockState(), 3);
            }
        }
    }
}
