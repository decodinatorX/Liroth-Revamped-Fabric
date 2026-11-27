package com.decodinator.liroth.core.entities.projectiles;

import com.decodinator.liroth.core.registry.LirothEntities;
import com.decodinator.liroth.core.registry.LirothItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class BeamLaserProjectileEntity extends AbstractArrow {
	public BeamLaserProjectileEntity(EntityType<? extends AbstractArrow> entityType, Level world) {
		super(entityType, world);
		this.setPickupItemStack(new ItemStack(LirothItems.COOL_BEAM));
		this.pickup = Pickup.ALLOWED;
	}

	protected void onHitEntity(EntityHitResult entityHitResult) {
		super.onHitEntity(entityHitResult);
		Entity entity = entityHitResult.getEntity();
		int i = entity instanceof Blaze ? 3 : 0;
		entity.hurt(this.damageSources().arrow(this, this.getOwner()), (float)i);

		if (entity instanceof LivingEntity livingEntity) {
			livingEntity.addEffect((new MobEffectInstance(MobEffects.BLINDNESS, 20 * 3, 0)));
			livingEntity.playSound(SoundEvents.AMBIENT_CAVE.value());
		}
	}

	protected void onHitBlock(BlockHitResult hitResult) {
		super.onHitBlock(hitResult);
		if (!this.level().isClientSide()) {
			this.level().broadcastEntityEvent(this, (byte)3);
		}

	}

	@Override
	protected ItemStack getPickupItem() {
		return new ItemStack(LirothItems.COOL_BEAM);
	}

	@Override
	protected ItemStack getDefaultPickupItem() {
		return new ItemStack(LirothItems.COOL_BEAM);
	}

	public static BeamLaserProjectileEntity createBeamProjectile(LivingEntity shooter) {
		BeamLaserProjectileEntity projectile = new BeamLaserProjectileEntity(LirothEntities.BEAM_LASER_PROJECTILE_ENTITY, shooter.level());
		projectile.setOwner(shooter);
		projectile.setPos(shooter.getX(), shooter.getEyeY() - 0.1F, shooter.getZ());
		return projectile;
	}
}