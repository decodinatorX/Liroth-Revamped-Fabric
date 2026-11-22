package com.decodinator.liroth.core.blocks;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TorchBlock;

public class CustomTorch extends TorchBlock {

	public CustomTorch(SimpleParticleType particle) {
		super(particle, Properties.ofFullCopy(Blocks.TORCH));
	}
}
