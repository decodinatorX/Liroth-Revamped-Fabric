package com.decodinator.liroth.client.rendering.entities;

import com.decodinator.liroth.client.models.entities.SoulArachnidModel;
import com.decodinator.liroth.core.entities.SoulArachnidEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import com.geckolib.renderer.GeoEntityRenderer;

public class SoulArachnidRenderer extends GeoEntityRenderer<SoulArachnidEntity, GeoLivingEntityRenderState> {
    public SoulArachnidRenderer(EntityRendererProvider.Context render) {
        super(render, new SoulArachnidModel());
        withRenderLayer(new SoulArachnidFaceGlowLayer(this));
    }
}