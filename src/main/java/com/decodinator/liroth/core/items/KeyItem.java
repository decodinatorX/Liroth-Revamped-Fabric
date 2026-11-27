package com.decodinator.liroth.core.items;

import com.decodinator.liroth.core.registry.LirothBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Set;

public class KeyItem extends Item {
    public KeyItem() {
        super(new Item.Properties().durability(1));
    }

    @Override
    public InteractionResult useOn(UseOnContext use) {
        BlockPos pos = use.getClickedPos();
        Player player = use.getPlayer();
        Level level = use.getLevel();
        BlockState state = level.getBlockState(pos);

        if (state.is(LirothBlocks.CHARRED_LIROTH_STONE_BRICK_LOCK)) {
            level.playSound(player, pos, SoundEvents.LODESTONE_COMPASS_LOCK, SoundSource.BLOCKS, 1.0f, level.getRandom().nextFloat() * 0.4f + 0.8f);
            destroyConnectedLocks(level, player, pos);
            if (player != null) {
                use.getItemInHand().hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
            }
            return level.isClientSide() ? InteractionResult.SUCCESS : InteractionResult.CONSUME;
        }
        return InteractionResult.FAIL;
    }

    private void destroyConnectedLocks(Level level, Player player, BlockPos start) {
        ArrayDeque<BlockPos> pending = new ArrayDeque<>();
        Set<BlockPos> visited = new HashSet<>();
        pending.add(start);

        while (!pending.isEmpty()) {
            BlockPos pos = pending.removeFirst();
            if (!visited.add(pos) || !level.getBlockState(pos).is(LirothBlocks.CHARRED_LIROTH_STONE_BRICK_LOCK)) {
                continue;
            }

            level.destroyBlock(pos, false);
            level.gameEvent(player, GameEvent.BLOCK_DESTROY, pos);

            for (Direction direction : Direction.values()) {
                BlockPos neighbor = pos.relative(direction);
                if (!visited.contains(neighbor)
                        && level.getBlockState(neighbor).is(LirothBlocks.CHARRED_LIROTH_STONE_BRICK_LOCK)) {
                    pending.addLast(neighbor);
                }
            }
        }
    }
}