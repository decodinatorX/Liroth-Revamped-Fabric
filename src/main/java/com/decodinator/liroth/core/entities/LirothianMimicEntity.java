package com.decodinator.liroth.core.entities;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.spider.Spider;
import net.minecraft.world.level.Level;
import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.RawAnimation;
import com.geckolib.animation.object.PlayState;
import com.geckolib.animation.state.AnimationTest;
import com.geckolib.util.GeckoLibUtil;

public class LirothianMimicEntity extends Spider implements GeoAnimatable {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public LirothianMimicEntity(EntityType<? extends Spider> entity, Level level) {
        super(entity, level);
    }

    public static AttributeSupplier.Builder createLirothianMimicAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 16)
                .add(Attributes.MOVEMENT_SPEED, 0.3f);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.HOGLIN_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.WOOD_BREAK;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.WOOD_HIT;
    }

    private static final RawAnimation MIMIC_WALK = RawAnimation.begin().thenPlay("walk");
    private static final RawAnimation MIMIC_IDLE = RawAnimation.begin().thenPlay("idle");

    private PlayState predicate(AnimationTest<LirothianMimicEntity> event) {
        if (this.getCurrentSwing() != null) {
            event.setAnimation(MIMIC_WALK);
            return PlayState.CONTINUE;
        }

        if (event.isMoving()) {
            event.setAnimation(MIMIC_WALK);
            return PlayState.CONTINUE;
        }

        event.setAnimation(MIMIC_IDLE);
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