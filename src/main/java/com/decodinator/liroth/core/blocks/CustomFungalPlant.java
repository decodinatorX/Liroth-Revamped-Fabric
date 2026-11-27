package com.decodinator.liroth.core.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.NetherFungusBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;

public class CustomFungalPlant extends NetherFungusBlock {
    private final Block floorBlock;

    public CustomFungalPlant(BlockBehaviour.Properties settings, ResourceKey<Feature> feature, Block requiredBlock, Block floor) {
        super(feature, requiredBlock, BlockTags.SUPPORTS_CRIMSON_FUNGUS, settings);
        this.floorBlock = floor;
    }

    @Override
    protected boolean mayPlaceOn(BlockState floor, BlockGetter world, BlockPos pos) {
        return floor.is(floorBlock) || super.mayPlaceOn(floor, world, pos);
    }

}