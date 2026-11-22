package com.decodinator.liroth.client.models.entities;

import com.decodinator.liroth.Liroth;

import com.decodinator.liroth.core.entities.SoulArachnidEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class SoulArachnidModel<T extends SoulArachnidEntity> extends DefaultedEntityGeoModel<SoulArachnidEntity> {
	public SoulArachnidModel() {
		super(ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "soul_arachnid/soul_arachnid"));
	}

	@Override
	public ResourceLocation getModelResource(SoulArachnidEntity object) {
		return ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "geo/soul_arachnid/soul_arachnid.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SoulArachnidEntity object) {
		return ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "textures/entity/soul_arachnid/soul_arachnid.png");
	}

	@Override
	public ResourceLocation getAnimationResource(SoulArachnidEntity animatable) {
		return ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "animations/soul_arachnid/soul_arachnid.animation.json");
	}
}
