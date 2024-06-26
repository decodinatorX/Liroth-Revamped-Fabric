package com.decodinator.liroth.entities;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.core.LirothSounds;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class WarpEntity extends EnderMan implements GeoAnimatable{
    private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    public WarpEntity(EntityType<? extends EnderMan> entityType, Level world) {
        super(entityType, world);
//        this.maxUpStep = 1.0f;
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0f);
    }

    public static AttributeSupplier.Builder createWarpAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 40.0).add(Attributes.MOVEMENT_SPEED, 0.3f).add(Attributes.ATTACK_DAMAGE, 7.0).add(Attributes.FOLLOW_RANGE, 64.0);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.isAngry() ? SoundEvents.ENDERMAN_SCREAM : LirothSounds.WARP_IDLE_SOUND_EVENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return LirothSounds.WARP_HURT_SOUND_EVENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return LirothSounds.WARP_DEATH_SOUND_EVENT;
    }

    private static final RawAnimation WARP_WALK = RawAnimation.begin().thenPlay("warp_walk");
    private static final RawAnimation WARP_CHASE = RawAnimation.begin().thenPlay("warp_chase");
    private static final RawAnimation WARP_IDLE = RawAnimation.begin().thenPlay("warp_idle");

    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> event) {
        if (!(swingTime > -0.15F && swingTime < 0.15F) && !this.isAggressive()) {
            event.getController().setAnimation(WARP_WALK);
            return PlayState.CONTINUE;
        }

        if (event.isMoving() && !this.isAggressive()) {
            event.getController().setAnimation(WARP_WALK);
            return PlayState.CONTINUE;
        }

        if (!(swingTime > -0.15F && swingTime < 0.15F) && this.isAggressive()) {
            event.getController().setAnimation(WARP_CHASE);
            return PlayState.CONTINUE;
        }

        if (event.isMoving() && this.isAggressive()) {
            event.getController().setAnimation(WARP_CHASE);
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(WARP_IDLE);
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<WarpEntity>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.factory;
    }

    @Override
    public double getTick(Object age) {
        return ((Entity) age).tickCount;
    }
}
