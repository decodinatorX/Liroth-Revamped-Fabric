package com.decodinator.liroth.core.entities;

import com.decodinator.liroth.core.registry.LirothSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.RawAnimation;
import com.geckolib.animation.object.PlayState;
import com.geckolib.animation.state.AnimationTest;
import com.geckolib.util.GeckoLibUtil;

public class FungalFiendEntity extends Creeper implements GeoAnimatable {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public FungalFiendEntity(EntityType<? extends Creeper> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 20;
    }

    public static AttributeSupplier.Builder createFungalFiendAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return LirothSounds.FUNGAL_FIEND_HURT_SOUND_EVENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return LirothSounds.FUNGAL_FIEND_DEATH_SOUND_EVENT;
    }

    private static final RawAnimation FUNGAL_WALK = RawAnimation.begin().thenPlay("fungal_walk");
    private static final RawAnimation FUNGAL_IDLE = RawAnimation.begin().thenPlay("fungal_idle");

    private PlayState predicate(AnimationTest<FungalFiendEntity> event) {
        if (this.getCurrentSwing() != null) {
            event.setAnimation(FUNGAL_WALK);
            return PlayState.CONTINUE;
        }

        if (event.isMoving()) {
            event.setAnimation(FUNGAL_WALK);
            return PlayState.CONTINUE;
        }

        event.setAnimation(FUNGAL_IDLE);
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