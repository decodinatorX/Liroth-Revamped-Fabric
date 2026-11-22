package com.decodinator.liroth.core.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FungusBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class CustomFungalPlant extends FungusBlock {
    public Block floorBlock;

    public CustomFungalPlant(BlockBehaviour.Properties settings, ResourceKey<ConfiguredFeature<?, ?>> feature, Block requiredBlock, Block floor) {
        super(feature, requiredBlock, settings);
        this.floorBlock = floor;
    }

    @Override
    protected boolean mayPlaceOn(BlockState floor, BlockGetter world, BlockPos pos) {
        return floor.is(floorBlock) || super.mayPlaceOn(floor, world, pos);
    }

}