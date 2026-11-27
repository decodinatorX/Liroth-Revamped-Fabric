package com.decodinator.liroth.core.blocks;

import java.util.function.Supplier;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CustomVinesBody extends GrowingPlantBodyBlock {
    public static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);
    private final Supplier<GrowingPlantHeadBlock> headBlock;

    public CustomVinesBody(BlockBehaviour.Properties arg, Supplier<GrowingPlantHeadBlock> head) {
        super(arg, Direction.DOWN, SHAPE, false);
        this.headBlock = head;
    }

    @Override
    protected GrowingPlantHeadBlock getHeadBlock() {
        return headBlock.get();
    }
}