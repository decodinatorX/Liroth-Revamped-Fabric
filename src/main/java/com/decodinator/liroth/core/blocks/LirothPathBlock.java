package com.decodinator.liroth.core.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirtPathBlock;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LirothPathBlock
extends DirtPathBlock {
    protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 15.0, 16.0);

    public LirothPathBlock(Block.Properties settings) {
        super(settings);
    }
}

