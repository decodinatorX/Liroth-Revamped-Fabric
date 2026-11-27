package com.decodinator.liroth.core.blocks;

import com.decodinator.liroth.core.registry.LirothPortalBuilders;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Portal;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.dimension.DimensionType;

import java.util.function.Supplier;

public class LirothPortalBlock extends Block implements Portal {
	private final Supplier<SimpleParticleType> portalParticle;
	private final ResourceKey<Level> destination;
	private final Supplier<Block> portalFrame;

	public LirothPortalBlock(BlockBehaviour.Properties properties, Supplier<SimpleParticleType> portalParticle, String destination, Supplier<Block> portalFrame) {
		super(properties);
		this.portalParticle = portalParticle;
		this.destination = ResourceKey.create(Registries.DIMENSION, Identifier.fromNamespaceAndPath("liroth", destination));
		this.portalFrame = portalFrame;
	}

	public Block getPortalFrame() {
		return portalFrame.get();
	}

	@Override
	protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier, boolean isPrecise) {
		if (entity.canUsePortal(false)) {
			entity.setAsInsidePortal(this, pos);
		}
	}

	@Override
	public TeleportTransition getPortalDestination(ServerLevel currentLevel, Entity entity, BlockPos portalEntryPos) {
		ResourceKey<Level> destinationDimension = currentLevel.dimension().equals(destination) ? Level.OVERWORLD : destination;
		ServerLevel destinationLevel = currentLevel.getServer().getLevel(destinationDimension);
		if (destinationLevel == null) {
			return null;
		}

		double scale = DimensionType.getTeleportationScale(currentLevel.dimensionType(), destinationLevel.dimensionType());
		BlockPos target = destinationLevel.getWorldBorder().clampToBounds(entity.getX() * scale, entity.getY(), entity.getZ() * scale);
		BlockPos portalPos = LirothPortalBuilders.findOrCreateExit(destinationLevel, target, this);
		return new TeleportTransition(destinationLevel, new Vec3(portalPos.getX() + 1.5, portalPos.getY() + 1.0, portalPos.getZ() + 1.5), Vec3.ZERO, entity.getYRot(), entity.getXRot(), TeleportTransition.PLAY_PORTAL_SOUND.then(TeleportTransition.PLACE_PORTAL_TICKET));
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		if (random.nextInt(100) == 0) {
			level.playLocalSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.PORTAL_AMBIENT, SoundSource.BLOCKS, 0.5F, 0.8F + random.nextFloat() * 0.4F, false);
		}
		double x = pos.getX() + random.nextDouble();
		double y = pos.getY() + random.nextDouble();
		double z = pos.getZ() + random.nextDouble();
		level.addParticle(portalParticle.get(), x, y, z, (random.nextFloat() - 0.5D) * 0.1D, (random.nextFloat() - 0.5D) * 0.1D, (random.nextFloat() - 0.5D) * 0.1D);
	}
}