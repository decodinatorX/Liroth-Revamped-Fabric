package com.decodinator.liroth.client.models.entities;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.core.entities.ProwlerEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class ProwlerModel<T extends ProwlerEntity> extends DefaultedEntityGeoModel<ProwlerEntity> {
    public ProwlerModel() {
        super(ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "prowler/prowler"));
    }

    @Override
    public ResourceLocation getModelResource(ProwlerEntity object) {
        return ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "geo/prowler/prowler.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ProwlerEntity object) {
        return ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "textures/entity/prowler/prowler.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ProwlerEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "animations/prowler/prowler.animation.json");
    }
}
