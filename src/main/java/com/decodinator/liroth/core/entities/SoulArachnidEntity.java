package com.decodinator.liroth.core.entities;

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

public class SoulArachnidEntity extends Spider implements GeoAnimatable {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public SoulArachnidEntity(EntityType<? extends Spider> entity, Level level) {
        super(entity, level);
    }

    public static AttributeSupplier.Builder createSoulArachnidAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 16)
                .add(Attributes.MOVEMENT_SPEED, 0.3f);
    }

    private static final RawAnimation WALK = RawAnimation.begin().thenPlay("animation.soul_arachnid.walk");
    private static final RawAnimation IDLE = RawAnimation.begin().thenPlay("animation.soul_arachnid.idle");

    private PlayState predicate(AnimationTest<SoulArachnidEntity> event) {
        if (this.getCurrentSwing() != null) {
            event.setAnimation(WALK);
            return PlayState.CONTINUE;
        }

        if (event.isMoving()) {
            event.setAnimation(WALK);
            return PlayState.CONTINUE;
        }

        event.setAnimation(IDLE);
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