package com.decodinator.liroth.core.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.NetherVines;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.Supplier;

public class CustomVinesHead extends GrowingPlantHeadBlock {
    protected static final VoxelShape SHAPE = Block.box(4.0, 9.0, 4.0, 12.0, 16.0, 12.0);
    private final Supplier<Block> plantBlock;

    public CustomVinesHead(BlockBehaviour.Properties arg, Supplier<Block> plant) {
        super(arg, Direction.DOWN, SHAPE, false, 0.1);
        this.plantBlock = plant;
    }

    @Override
    protected MapCodec<? extends GrowingPlantHeadBlock> codec() {
        return null;
    }

    protected int getBlocksToGrowWhenBonemealed(RandomSource arg) {
        return NetherVines.getBlocksToGrowWhenBonemealed(arg);
    }


    protected Block getBodyBlock() {
        return plantBlock.get();
    }

    protected boolean canGrowInto(BlockState arg) {
        return arg.isAir();
    }

    @Override
    protected List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        return SilkTouchLoot.hasSilkTouch(params) ? super.getDrops(state, params) : List.of();
    }
}