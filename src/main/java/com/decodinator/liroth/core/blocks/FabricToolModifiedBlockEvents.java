package com.decodinator.liroth.core.blocks;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public final class FabricToolModifiedBlockEvents {
    private FabricToolModifiedBlockEvents() {}

    public static void register() {
        UseBlockCallback.EVENT.register((player, world, hand, hit) -> {
            if (!hit.getDirection().getAxis().isVertical() || hit.getDirection().getStepY() != 1) {
                return InteractionResult.PASS;
            }

            ItemStack tool = player.getItemInHand(hand);
            BlockState state = world.getBlockState(hit.getBlockPos());
            if (!(state.getBlock() instanceof FabricToolModifiedBlock soil)) {
                return InteractionResult.PASS;
            }

            boolean hoe = tool.is(ItemTags.HOES);
            boolean shovel = tool.is(ItemTags.SHOVELS);
            if (!hoe && !shovel) {
                return InteractionResult.PASS;
            }

            BlockState modified = soil.getToolModifiedState(state, tool);
            if (modified == null || modified == state) {
                return InteractionResult.PASS;
            }

            if (!world.isClientSide) {
                BlockPos pos = hit.getBlockPos();
                world.setBlock(pos, modified, 11);
                world.playSound(null, pos, hoe ? SoundEvents.HOE_TILL : SoundEvents.SHOVEL_FLATTEN,
                        player.getSoundSource(), 1.0F, 1.0F);
                EquipmentSlot slot = hand == InteractionHand.MAIN_HAND
                        ? EquipmentSlot.MAINHAND
                        : EquipmentSlot.OFFHAND;
                tool.hurtAndBreak(1, player, slot);
            }
            return InteractionResult.SUCCESS;
        });
    }
}
