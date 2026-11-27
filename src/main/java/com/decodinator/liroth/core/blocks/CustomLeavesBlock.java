package com.decodinator.liroth.core.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TintedParticleLeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class CustomLeavesBlock extends TintedParticleLeavesBlock {
    private final int particleColor;

    public CustomLeavesBlock(float leafParticleChance, int particleColor, BlockBehaviour.Properties properties) {
        super(leafParticleChance, properties);
        this.particleColor = particleColor;
    }

    @Override
    protected void spawnFallingLeavesParticle(Level level, BlockPos pos, RandomSource random) {
        if (!level.isClientSide()) {
            return;
        }

        level.addParticle(ColorParticleOption.create(ParticleTypes.TINTED_LEAVES, particleColor),
                pos.getX() + random.nextDouble(),
                pos.getY() - 0.05,
                pos.getZ() + random.nextDouble(),
                0.0,
                0.0,
                0.0
        );
    }
}