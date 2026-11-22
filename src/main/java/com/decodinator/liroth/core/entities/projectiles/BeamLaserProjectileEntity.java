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
import net.minecraft.world.entity.projectile.AbstractArrow;
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

	protected void onHitEntity(EntityHitResult entityHitResult) { // called on entity hit.
		super.onHitEntity(entityHitResult);
		Entity entity = entityHitResult.getEntity(); // sets a new Entity instance as the EntityHitResult (victim)
		int i = entity instanceof Blaze ? 3 : 0; // sets i to 3 if the Entity instance is an instance of BlazeEntity
		entity.hurt(this.damageSources().arrow(this, this.getOwner()), (float)i); // deals damage

		if (entity instanceof LivingEntity livingEntity) { // checks if entity is an instance of LivingEntity (meaning it is not a boat or minecart)
			livingEntity.addEffect((new MobEffectInstance(MobEffects.BLINDNESS, 20 * 3, 0))); // applies a status effect
			livingEntity.playSound(SoundEvents.AMBIENT_CAVE.value()); // plays a sound for the entity hit only
		}
	}

	protected void onHitBlock(BlockHitResult hitResult) { // called on collision with a block
		super.onHitBlock(hitResult);
		if (!this.level().isClientSide) { // checks if the world is client
			this.level().broadcastEntityEvent(this, (byte)3); // particle?
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