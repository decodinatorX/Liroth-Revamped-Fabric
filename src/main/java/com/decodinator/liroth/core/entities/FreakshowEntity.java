package com.decodinator.liroth.core.entities;

import com.decodinator.liroth.core.registry.LirothSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
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
import org.jetbrains.annotations.Nullable;

public class FreakshowEntity extends Zombie {

    public FreakshowEntity(EntityType<? extends Zombie> entityType, Level world) {
        super(entityType, world);
    }

    public static AttributeSupplier.Builder createFreakshowAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.FOLLOW_RANGE, 256.0)
                .add(Attributes.MOVEMENT_SPEED, 0.15f)
                .add(Attributes.ATTACK_DAMAGE, 20.0)
                .add(Attributes.ARMOR, 2.0)
                .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnType, @Nullable SpawnGroupData spawnGroupData) {
        return super.finalizeSpawn(level, difficulty, spawnType, new Zombie.ZombieGroupData(false, false));
    }

    @Override
    protected boolean convertsInWater() {
        return false;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return LirothSounds.WARP_IDLE_SOUND_EVENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return LirothSounds.WARP_HURT_SOUND_EVENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return LirothSounds.WARP_DEATH_SOUND_EVENT;
    }
}