package com.decodinator.liroth.client.rendering.entities;

import com.decodinator.liroth.client.models.entities.ProwlerModel;
import com.decodinator.liroth.core.entities.ProwlerEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import com.geckolib.renderer.GeoEntityRenderer;

public class ProwlerRenderer extends GeoEntityRenderer<ProwlerEntity, GeoLivingEntityRenderState> {
    public ProwlerRenderer(EntityRendererProvider.Context render) {
        super(render, new ProwlerModel());
        withRenderLayer(new ProwlerFaceGlowLayer(this));
    }

}