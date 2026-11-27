package com.decodinator.liroth.core.blocks;

import com.decodinator.liroth.core.entities.LirothianMimicEntity;
import com.decodinator.liroth.core.registry.LirothEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LirothianMimicBlock extends Block {
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    protected static final VoxelShape AABB = Block.box(1.0, 0.0, 1.0, 15.0, 14.0, 15.0);
    public LirothianMimicBlock() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.CHEST).noOcclusion());
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        triggerMimic(state, level, pos, player);
        return InteractionResult.SUCCESS;
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        triggerMimic(state, level, pos, player);
        return InteractionResult.SUCCESS;
    }

    private void triggerMimic(BlockState state, Level level, BlockPos pos, Player player) {
        if (level instanceof ServerLevel serverLevel) {
            level.removeBlock(pos, false);
            level.levelEvent(2001, pos, Block.getId(state));
            spawnMimic(serverLevel, pos, player);
        }
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        BlockState destroyedState = super.playerWillDestroy(level, pos, state, player);
        if (level instanceof ServerLevel serverLevel) {
            spawnMimic(serverLevel, pos, player);
        }
        return destroyedState;
    }

    private void spawnMimic(ServerLevel level, BlockPos pos, Player player) {
        LirothianMimicEntity mimic = new LirothianMimicEntity(LirothEntities.LIROTHIAN_MIMIC, level);
        mimic.setPos(pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5);
        mimic.setYRot(player.getYRot());
        mimic.setXRot(0.0F);
        mimic.setTarget(player);
        level.addFreshEntity(mimic);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return AABB;
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}