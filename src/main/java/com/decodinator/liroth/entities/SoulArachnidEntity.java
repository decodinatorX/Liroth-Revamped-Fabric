package com.decodinator.liroth.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.world.World;

public class SoulArachnidEntity extends SpiderEntity {

	public SoulArachnidEntity(EntityType<? extends SpiderEntity> entityType, World world) {
		super(entityType, world);
	}
	
    public static DefaultAttributeContainer.Builder createSoulArachnidAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 16.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f);
    }
}
