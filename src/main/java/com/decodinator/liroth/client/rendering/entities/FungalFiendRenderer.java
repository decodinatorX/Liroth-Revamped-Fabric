package com.decodinator.liroth.client.rendering.entities;

import com.decodinator.liroth.client.models.entities.FungalFiendModel;
import com.decodinator.liroth.core.entities.FungalFiendEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import com.geckolib.renderer.GeoEntityRenderer;

public class FungalFiendRenderer extends GeoEntityRenderer<FungalFiendEntity, GeoLivingEntityRenderState> {
    public FungalFiendRenderer(EntityRendererProvider.Context render) {
        super(render, new FungalFiendModel());
        withRenderLayer(new FungalFiendFaceGlowLayer(this));
        withRenderLayer(new FungalFiendFuseFlashLayer(this));
    }

}