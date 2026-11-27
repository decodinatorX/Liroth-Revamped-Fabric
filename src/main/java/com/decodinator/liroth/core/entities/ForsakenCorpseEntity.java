package com.decodinator.liroth.core.entities;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.DifficultyInstance;
import org.jetbrains.annotations.Nullable;
import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.RawAnimation;
import com.geckolib.animation.object.PlayState;
import com.geckolib.animation.state.AnimationTest;
import com.geckolib.util.GeckoLibUtil;

public class ForsakenCorpseEntity extends Zombie implements GeoAnimatable {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public ForsakenCorpseEntity(EntityType<? extends Zombie> entityType, Level world) {
        super(entityType, world);
        this.xpReward = 20;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnType, @Nullable SpawnGroupData spawnGroupData) {
        return super.finalizeSpawn(level, difficulty, spawnType, new Zombie.ZombieGroupData(false, false));
    }

    public static AttributeSupplier.Builder createForsakenCorpseAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.FOLLOW_RANGE, 35.0)
                .add(Attributes.MOVEMENT_SPEED, 0.23f)
                .add(Attributes.ATTACK_DAMAGE, 6.0)
                .add(Attributes.ARMOR, 4.0)
                .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
    }

    @Override
    protected boolean convertsInWater() {
        return false;
    }


    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.DROWNED_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.DROWNED_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.DROWNED_DEATH;
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.DROWNED_STEP;
    }

    private static final RawAnimation CORPSE_WALK = RawAnimation.begin().thenPlay("corpse_walk");
    private static final RawAnimation CORPSE_CHASE = RawAnimation.begin().thenPlay("corpse_chase");
    private static final RawAnimation CORPSE_IDLE = RawAnimation.begin().thenPlay("corpse_idle");

    private PlayState predicate(AnimationTest<ForsakenCorpseEntity> event) {
        if (this.getCurrentSwing() != null && !this.isAggressive()) {
            event.setAnimation(CORPSE_WALK);
            return PlayState.CONTINUE;
        }

        if (event.isMoving() && !this.isAggressive()) {
            event.setAnimation(CORPSE_WALK);
            return PlayState.CONTINUE;
        }

        if (this.getCurrentSwing() != null && this.isAggressive()) {
            event.setAnimation(CORPSE_CHASE);
            return PlayState.CONTINUE;
        }

        if (event.isMoving() && this.isAggressive()) {
            event.setAnimation(CORPSE_CHASE);
            return PlayState.CONTINUE;
        }

        event.setAnimation(CORPSE_IDLE);
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(com.geckolib.animatable.manager.AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>("controller", this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}