package com.decodinator.liroth.core.entities;

import com.decodinator.liroth.core.entities.projectiles.BeamLaserProjectileEntity;
import com.decodinator.liroth.core.registry.LirothSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.skeleton.Skeleton;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.RawAnimation;
import com.geckolib.animation.object.PlayState;
import com.geckolib.animation.state.AnimationTest;
import com.geckolib.util.GeckoLibUtil;

public class SkeletalFreakEntity extends Skeleton implements GeoAnimatable {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public SkeletalFreakEntity(EntityType<? extends Skeleton> entityType, Level level) {
        super(entityType, level);
        if (!level.isClientSide()) {
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
        }
    }

    public static AttributeSupplier.Builder createSkeletalFreakAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.25);
    }


    @Override
    public void performRangedAttack(LivingEntity target, float pullProgress) {
        AbstractArrow persistentProjectileEntity = BeamLaserProjectileEntity.createBeamProjectile(this);
        double d = target.getX() - this.getX();
        double e = target.getY(0.3333333333333333) - persistentProjectileEntity.getY();
        double f = target.getZ() - this.getZ();
        double g = Math.sqrt(d * d + f * f);
        persistentProjectileEntity.shoot(d, e + g * (double) 0.2f, f, 1.6f, 14 - this.level().getDifficulty().getId() * 4);
        this.playSound(LirothSounds.LIROTH_BLASTER_FIRING_SOUND_EVENT, 1.0f, 1.0f / (this.getRandom().nextFloat() * 0.4f + 0.8f));
        this.level().addFreshEntity(persistentProjectileEntity);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.STRAY_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.STRAY_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.STRAY_DEATH;
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.WITHER_SKELETON_STEP;
    }

    private static final RawAnimation SKELETAL_WALK = RawAnimation.begin().thenPlay("skeletal_walk");
    private static final RawAnimation SKELETAL_IDLE = RawAnimation.begin().thenPlay("skeletal_idle");

    private PlayState predicate(AnimationTest<SkeletalFreakEntity> event) {
        if (this.getCurrentSwing() != null) {
            event.setAnimation(SKELETAL_WALK);
            return PlayState.CONTINUE;
        }

        if (event.isMoving()) {
            event.setAnimation(SKELETAL_WALK);
            return PlayState.CONTINUE;
        }

        event.setAnimation(SKELETAL_IDLE);
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(com.geckolib.animatable.manager.AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>("controller", this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}