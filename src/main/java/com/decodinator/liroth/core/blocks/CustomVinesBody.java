package com.decodinator.liroth.core.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Supplier;

public class CustomVinesBody extends GrowingPlantBodyBlock {
    public static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);
    private final Supplier<GrowingPlantHeadBlock> headBlock;

    public CustomVinesBody(BlockBehaviour.Properties arg, Supplier<GrowingPlantHeadBlock> head) {
        super(arg, Direction.DOWN, SHAPE, false);
        this.headBlock = head;
    }

    protected boolean canGrowInto(BlockState arg) {
        return arg.isAir();
    }

    protected GrowingPlantHeadBlock getHeadBlock() {
        return headBlock.get();
    }

    @Override
    protected MapCodec<? extends GrowingPlantBodyBlock> codec() {
        return null;
    }
}