package com.decodinator.liroth.core.blocks;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class CustomWallTorch extends WallTorchBlock {

	public CustomWallTorch(SimpleParticleType particleOptions) {
		super(particleOptions, Properties.ofFullCopy(Blocks.WALL_TORCH));
	}
}
