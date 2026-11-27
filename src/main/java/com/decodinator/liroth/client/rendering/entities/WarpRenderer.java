package com.decodinator.liroth.client.rendering.entities;

import com.decodinator.liroth.client.models.entities.WarpModel;
import com.decodinator.liroth.core.entities.WarpEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import com.geckolib.renderer.GeoEntityRenderer;

public class WarpRenderer extends GeoEntityRenderer<WarpEntity, GeoLivingEntityRenderState> {
    public WarpRenderer(EntityRendererProvider.Context render) {
        super(render, new WarpModel());
        withRenderLayer(new WarpFaceGlowLayer(this));
    }

}