package com.decodinator.liroth.core.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EndRodBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class CustomRod extends EndRodBlock {
    private final Supplier<SimpleParticleType> particle;

    public CustomRod(Properties properties, Supplier<SimpleParticleType> rodParticle) {
        super(properties);
        this.particle = rodParticle;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource source) {
        Direction direction = state.getValue(FACING);
        double d0 = (double)pos.getX() + 0.55D - (double)(source.nextFloat() * 0.1F);
        double d1 = (double)pos.getY() + 0.55D - (double)(source.nextFloat() * 0.1F);
        double d2 = (double)pos.getZ() + 0.55D - (double)(source.nextFloat() * 0.1F);
        double d3 = (double)(0.4F - (source.nextFloat() + source.nextFloat()) * 0.4F);
        if (source.nextInt(5) == 0) {
            level.addParticle(particle.get(), d0 + (double)direction.getStepX() * d3, d1 + (double)direction.getStepY() * d3, d2 + (double)direction.getStepZ() * d3, source.nextGaussian() * 0.005D, source.nextGaussian() * 0.005D, source.nextGaussian() * 0.005D);
        }
    }
}