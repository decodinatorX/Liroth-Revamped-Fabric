package com.decodinator.liroth.core.entities;

import com.decodinator.liroth.core.registry.LirothSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Enderman;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathType;
import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.RawAnimation;
import com.geckolib.animation.object.PlayState;
import com.geckolib.animation.state.AnimationTest;
import com.geckolib.util.GeckoLibUtil;

public class ProwlerEntity extends Enderman implements GeoAnimatable {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public ProwlerEntity(EntityType<? extends Enderman> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(PathType.WATER, -1.0f);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.removeAllGoals(goal -> {
            String goalClass = goal.getClass().getName();
            // WHO ADDED THIS???
            return goalClass.endsWith("$EndermanTakeBlockGoal") || goalClass.endsWith("$EndermanLeaveBlockGoal");
        });
    }

    public static AttributeSupplier.Builder createProwlerAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 40)
                .add(Attributes.MOVEMENT_SPEED, 0.4f)
                .add(Attributes.ATTACK_DAMAGE, 12)
                .add(Attributes.FOLLOW_RANGE, 48);
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

    private PlayState predicate(AnimationTest<ProwlerEntity> event) {
        if (this.getCurrentSwing() != null && !this.isAggressive()) {
            event.setAnimation(WARP_WALK);
            return PlayState.CONTINUE;
        }

        if (event.isMoving() && !this.isAggressive()) {
            event.setAnimation(WARP_WALK);
            return PlayState.CONTINUE;
        }

        if (this.getCurrentSwing() != null && this.isAggressive()) {
            event.setAnimation(WARP_CHASE);
            return PlayState.CONTINUE;
        }

        if (event.isMoving() && this.isAggressive()) {
            event.setAnimation(WARP_CHASE);
            return PlayState.CONTINUE;
        }

        event.setAnimation(WARP_IDLE);
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