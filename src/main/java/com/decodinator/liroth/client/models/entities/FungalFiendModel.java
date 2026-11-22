package com.decodinator.liroth.client.models.entities;

import com.decodinator.liroth.Liroth;

import com.decodinator.liroth.core.entities.FungalFiendEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class FungalFiendModel<T extends FungalFiendEntity> extends DefaultedEntityGeoModel<FungalFiendEntity> {
	public FungalFiendModel() {
		super(ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "fungal_fiend/fungal"));
	}

	@Override
	public ResourceLocation getModelResource(FungalFiendEntity object) {
		return ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "geo/fungal_fiend/fungal.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(FungalFiendEntity object) {
		return ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "textures/entity/fungal_fiend/fungal_fiend.png");
	}

	@Override
	public ResourceLocation getAnimationResource(FungalFiendEntity animatable) {
		return ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "animations/fungal_fiend/fungal.animation.json");
	}
}
