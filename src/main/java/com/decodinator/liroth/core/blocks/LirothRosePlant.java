package com.decodinator.liroth.core.blocks;

import com.decodinator.liroth.core.registry.LirothTags;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import org.betterx.bclib.behaviours.interfaces.BehaviourPlant;

public class LirothRosePlant extends BlockCommonPlant implements BehaviourPlant {
    public LirothRosePlant(MapColor color) {
        super(color);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        BlockPos blockpos = pos.below();
        BlockState belowBlockState = world.getBlockState(blockpos);
        return this.mayPlaceOn(belowBlockState, world, blockpos);
    }

    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(BlockTags.DIRT) || state.is(LirothTags.LIROTH_FARMLANDS);
    }
}
