package com.decodinator.liroth.core.blocks;

import com.decodinator.liroth.core.registry.LirothBlocks;
import com.decodinator.liroth.core.registry.LirothItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealSource;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.CaveVines;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

import org.jspecify.annotations.Nullable;

public class CustomCaveVinesPlantBlock extends GrowingPlantBodyBlock implements BonemealableBlock, CaveVines {
    public CustomCaveVinesPlantBlock(BlockBehaviour.Properties p_153000_) {
        super(p_153000_, Direction.DOWN, SHAPE, false);
        this.registerDefaultState(this.stateDefinition.any().setValue(BERRIES, false));
    }

    @Override
    protected GrowingPlantHeadBlock getHeadBlock() {
        return (GrowingPlantHeadBlock) LirothBlocks.JAPZ_VINES;
    }

    @Override
    protected Block getBodyBlock() {
        return LirothBlocks.JAPZ_VINES_PLANT;
    }

    @Override
    protected BlockState updateHeadAfterConvertedFromBody(BlockState p_153028_, BlockState p_153029_) {
        return p_153029_.setValue(BERRIES, p_153028_.getValue(BERRIES));
    }

    @Override
    protected ItemStack getCloneItemStack(LevelReader p_153007_, BlockPos p_153008_, BlockState p_153009_, boolean includeData) {
        return new ItemStack(Items.GLOW_BERRIES);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        return use(player, state, level, pos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_153031_) {
        super.createBlockStateDefinition(p_153031_);
        p_153031_.add(BERRIES);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, BonemealSource source) {
        return !state.getValue(BERRIES);
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state, BonemealSource source) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state, BonemealSource source) {
        level.setBlock(pos, state.setValue(BERRIES, true), Block.UPDATE_NEIGHBORS);
    }

    private static InteractionResult use(@Nullable Entity entity, BlockState state, Level level, BlockPos pos) {
        if (state.getValue(BERRIES)) {
            if (level instanceof ServerLevel serverLevel) {
                Block.popResource(serverLevel, pos, new ItemStack(LirothItems.LIROTH_BERRY));
                float pitch = Mth.randomBetween(serverLevel.getRandom(), 0.8F, 1.2F);
                serverLevel.playSound(null, pos, SoundEvents.CAVE_VINES_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, pitch);
                BlockState newState = state.setValue(BERRIES, false);
                serverLevel.setBlock(pos, newState, Block.UPDATE_NEIGHBORS);
                serverLevel.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(entity, newState));
            }
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.PASS;
        }
    }
}