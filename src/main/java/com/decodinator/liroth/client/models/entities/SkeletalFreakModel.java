package com.decodinator.liroth.client.models.entities;

import com.decodinator.liroth.Liroth;

import com.decodinator.liroth.core.entities.SkeletalFreakEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class SkeletalFreakModel<T extends SkeletalFreakEntity> extends DefaultedEntityGeoModel<SkeletalFreakEntity> {
	public SkeletalFreakModel() {
		super(ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "skeletal_freak/skeletal"));
	}

	@Override
	public ResourceLocation getModelResource(SkeletalFreakEntity object) {
		return ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "geo/skeletal_freak/skeletal.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SkeletalFreakEntity object) {
		return ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "textures/entity/skeletal_freak/skeletal_freak.png");
	}

	@Override
	public ResourceLocation getAnimationResource(SkeletalFreakEntity animatable) {
		return ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "animations/skeletal_freak/skeletal.animation.json");
	}
}
