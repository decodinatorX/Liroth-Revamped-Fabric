package com.decodinator.liroth.core.items;

import java.util.function.Predicate;

import com.decodinator.liroth.core.registry.LirothItems;
import com.decodinator.liroth.core.registry.LirothSounds;
import com.decodinator.liroth.core.entities.projectiles.BeamLaserProjectileEntity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class LirothBlaster extends BowItem {

    public LirothBlaster(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        if (user.getAbilities().instabuild || !getCoolBeamAmmo(user).isEmpty()) {
            user.startUsingItem(hand);
            return InteractionResultHolder.consume(itemStack);
        }
        return InteractionResultHolder.fail(itemStack);
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return projectile -> projectile.is(LirothItems.COOL_BEAM);
    }

    @Override
    public int getDefaultProjectileRange() {
        return 50;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void releaseUsing(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks) {
        float f;
        if (!(user instanceof Player)) {
            return;
        }
        Player playerEntity = (Player)user;
        boolean bl = playerEntity.getAbilities().instabuild/* || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY, stack) > 0*/;
        ItemStack itemStack = getCoolBeamAmmo(playerEntity);
        if (itemStack.isEmpty() && !bl) {
            return;
        }
        if (itemStack.isEmpty()) {
            itemStack = new ItemStack(LirothItems.COOL_BEAM);
        }
        if ((double)(f = LirothBlaster.getPowerForTime(this.getUseDuration(stack, playerEntity) - remainingUseTicks)) < 0.1) {
            return;
        }
        if (!world.isClientSide) {
            AbstractArrow persistentProjectileEntity = BeamLaserProjectileEntity.createBeamProjectile(playerEntity);
            persistentProjectileEntity.shootFromRotation(playerEntity, playerEntity.getXRot(), playerEntity.getYRot(), 0.0f, f * 7.0f, 1.0f);
            if (f == 1.0f) {
                persistentProjectileEntity.setCritArrow(true);
            }
/*            if ((j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER, stack)) > 0) {
                persistentProjectileEntity.setBaseDamage(persistentProjectileEntity.getBaseDamage() + (double)j * 0.5 + 0.5);
            }
            if ((k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH, stack)) > 0) {
                persistentProjectileEntity.setKnockback(k);
            }
            if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, stack) > 0) {
                persistentProjectileEntity.setSecondsOnFire(100);
            }*/
            // stack.hurtAndBreak(1, playerEntity, p -> p.broadcastBreakEvent(playerEntity.getUsedItemHand()));
            if (bl) {
                persistentProjectileEntity.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
            }
            world.addFreshEntity(persistentProjectileEntity);
        }
        world.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvent.createVariableRangeEvent(LirothSounds.LIROTH_BLASTER_FIRING_SOUND_ID), SoundSource.PLAYERS, 1.0f, 1.0f / (world.getRandom().nextFloat() * 0.4f + 1.2f) + f * 0.5f);
        if (!bl) {
            itemStack.shrink(1);
        }
        playerEntity.awardStat(Stats.ITEM_USED.get(this));
    }

    private static ItemStack getCoolBeamAmmo(Player player) {
        if (player.getOffhandItem().is(LirothItems.COOL_BEAM)) {
            return player.getOffhandItem();
        }
        if (player.getMainHandItem().is(LirothItems.COOL_BEAM)) {
            return player.getMainHandItem();
        }
        for (ItemStack itemStack : player.getInventory().items) {
            if (itemStack.is(LirothItems.COOL_BEAM)) {
                return itemStack;
            }
        }
        return ItemStack.EMPTY;
    }

}