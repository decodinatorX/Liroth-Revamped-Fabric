package com.decodinator.liroth.client.models.entities;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.core.entities.WarpEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class WarpModel<T extends WarpEntity> extends DefaultedEntityGeoModel<WarpEntity> {
    public WarpModel() {
        super(ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "warp/warp"));
    }

    @Override
    public ResourceLocation getModelResource(WarpEntity object) {
        return ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "geo/warp/warp.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(WarpEntity object) {
        return ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "textures/entity/warp/warp.png");
    }

    @Override
    public ResourceLocation getAnimationResource(WarpEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "animations/warp/warp.animation.json");
    }
}
