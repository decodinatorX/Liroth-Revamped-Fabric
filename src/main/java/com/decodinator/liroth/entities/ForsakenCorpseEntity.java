package com.decodinator.liroth.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.world.World;

public class ForsakenCorpseEntity extends ZombieEntity {

	public ForsakenCorpseEntity(EntityType<? extends ZombieEntity> entityType, World world) {
		super(entityType, world);
		this.experiencePoints = 20;
	}

    public static DefaultAttributeContainer.Builder createForsakenCorpseAttributes() {
        return HostileEntity.createHostileAttributes()
        		.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0)
        		.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.23f)
        		.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0)
        		.add(EntityAttributes.GENERIC_ARMOR, 4.0)
        		.add(EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS);
    }
}