package com.decodinator.liroth.core.blocks;

import net.kyrptonaught.customportalapi.CustomPortalBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Supplier;

public class LirothPortalBlock extends CustomPortalBlock {
	private final Supplier<SimpleParticleType> portalParticle;

	public LirothPortalBlock(BlockBehaviour.Properties properties, Supplier<SimpleParticleType> portalParticle) {
		super(properties);
		this.portalParticle = portalParticle;
	}

	@Override
	public Block getPortalBase(Level level, BlockPos pos) {
		Block closestFrame = Blocks.AIR;
		int closestDistance = Integer.MAX_VALUE;

		for (int x = -4; x <= 4; x++) {
			for (int z = -4; z <= 4; z++) {
				Block block = level.getBlockState(pos.offset(x, 0, z)).getBlock();
				if (block instanceof PortalChargeFrameBlock) {
					int distance = Math.abs(x) + Math.abs(z);
					if (distance < closestDistance) {
						closestFrame = block;
						closestDistance = distance;
					}
				}
			}
		}

		return closestFrame;
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		if (random.nextInt(100) == 0) {
			level.playLocalSound(
					pos.getX() + 0.5D,
					pos.getY() + 0.5D,
					pos.getZ() + 0.5D,
					SoundEvents.PORTAL_AMBIENT,
					SoundSource.BLOCKS,
					0.5F,
					0.8F + random.nextFloat() * 0.4F,
					false
			);
		}

		for (int i = 0; i < 4; i++) {
			double x = pos.getX() + random.nextDouble();
			double y = pos.getY() + random.nextDouble();
			double z = pos.getZ() + random.nextDouble();
			double velocityX = (random.nextFloat() - 0.5D) * 0.1D;
			double velocityY = (random.nextFloat() - 0.5D) * 0.1D;
			double velocityZ = (random.nextFloat() - 0.5D) * 0.1D;

			level.addParticle(portalParticle.get(), x, y, z, velocityX, velocityY, velocityZ);
		}
	}
}