package com.decodinator.liroth.core.blocks;

import java.util.function.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.betterx.bclib.behaviours.interfaces.BehaviourStone;

public abstract class BaseFurnaceBlock extends org.betterx.bclib.blocks.BaseFurnaceBlock {
    private final Supplier<SimpleParticleType> particle;

    public BaseFurnaceBlock(Block source, Supplier<SimpleParticleType> particle) {
        this(Properties.ofFullCopy(source).lightLevel((state) -> (Boolean)state.getValue(LIT) ? 13 : 0), particle);
    }

    public BaseFurnaceBlock(BlockBehaviour.Properties properties, Supplier<SimpleParticleType> particle) {
        super(properties);
        this.particle = particle;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (state.getValue(LIT)) {
            double d0 = (double)pos.getX() + 0.5;
            double d1 = (double)pos.getY();
            double d2 = (double)pos.getZ() + 0.5;
            if (random.nextDouble() < 0.1) {
                level.playLocalSound(d0, d1, d2, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            }

            Direction direction = state.getValue(FACING);
            Direction.Axis direction$axis = direction.getAxis();
            double d3 = 0.52;
            double d4 = random.nextDouble() * 0.6 - 0.3;
            double d5 = direction$axis == Direction.Axis.X ? (double)direction.getStepX() * 0.52 : d4;
            double d6 = random.nextDouble() * 6.0 / 16.0;
            double d7 = direction$axis == Direction.Axis.Z ? (double)direction.getStepZ() * 0.52 : d4;
            level.addParticle(ParticleTypes.SMOKE, d0 + d5, d1 + d6, d2 + d7, 0.0, 0.0, 0.0);
            level.addParticle(particle.get(), d0 + d5, d1 + d6, d2 + d7, 0.0, 0.0, 0.0);
        }
    }

    public static class Stone extends BaseFurnaceBlock implements BehaviourStone {
        public Stone(Block source, Supplier<SimpleParticleType> rodParticle) {
            super(source, rodParticle);
        }

        public Stone(BlockBehaviour.Properties properties, Supplier<SimpleParticleType> rodParticle) {
            super(properties, rodParticle);
        }
    }
}