package com.decodinator.liroth.core.items;

import java.util.function.Predicate;

import com.decodinator.liroth.core.entities.projectiles.BeamLaserProjectileEntity;
import com.decodinator.liroth.core.registry.LirothItems;
import com.decodinator.liroth.core.registry.LirothSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class LirothBlaster extends BowItem {

    public LirothBlaster(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResult use(Level world, Player user, InteractionHand hand) {
        if (user.getAbilities().instabuild || !getCoolBeamAmmo(user).isEmpty()) {
            user.startUsingItem(hand);
            return InteractionResult.CONSUME;
        }
        return InteractionResult.FAIL;
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return projectile -> projectile.is(LirothItems.COOL_BEAM);
    }

    @Override
    public int getDefaultProjectileRange() {
        return 50;
    }

    @Override
    public boolean releaseUsing(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks) {
        if (!(user instanceof Player)) {
            return false;
        }
        Player playerEntity = (Player) user;
        boolean bl = playerEntity.getAbilities().instabuild;
        ItemStack itemStack = getCoolBeamAmmo(playerEntity);
        if (itemStack.isEmpty() && !bl) {
            return false;
        }
        if (itemStack.isEmpty()) {
            itemStack = new ItemStack(LirothItems.COOL_BEAM);
        }
        float power = BowItem.getPowerForTime(stack.getUseDuration(playerEntity) - remainingUseTicks);
        if (power < 0.1) {
            return false;
        }
        if (world instanceof ServerLevel serverLevel) {
            AbstractArrow persistentProjectileEntity = BeamLaserProjectileEntity.createBeamProjectile(playerEntity);
            persistentProjectileEntity.shootFromRotation(playerEntity, playerEntity.getXRot(), playerEntity.getYRot(), 0.0f, power * 7.0f, 1.0f);
            if (power == 1.0f) {
                persistentProjectileEntity.setCritArrow(true);
            }
            if (bl) {
                persistentProjectileEntity.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
            }
            serverLevel.addFreshEntity(persistentProjectileEntity);
        }
        world.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), LirothSounds.LIROTH_BLASTER_FIRING_SOUND_EVENT, SoundSource.PLAYERS, 1.0f, 1.0f / (world.getRandom().nextFloat() * 0.4f + 1.2f) + power * 0.5f);
        if (!bl) {
            itemStack.shrink(1);
        }
        playerEntity.awardStat(Stats.ITEM_USED.get(this));
        return true;
    }

    private static ItemStack getCoolBeamAmmo(Player player) {
        if (player.getOffhandItem().is(LirothItems.COOL_BEAM)) {
            return player.getOffhandItem();
        }
        if (player.getMainHandItem().is(LirothItems.COOL_BEAM)) {
            return player.getMainHandItem();
        }
        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            ItemStack itemStack = player.getInventory().getItem(slot);
            if (itemStack.is(LirothItems.COOL_BEAM)) {
                return itemStack;
            }
        }
        return ItemStack.EMPTY;
    }

}