package com.decodinator.liroth.client.rendering.entities;

import com.decodinator.liroth.client.models.entities.SkeletalFreakModel;
import com.decodinator.liroth.core.entities.SkeletalFreakEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import com.geckolib.renderer.GeoEntityRenderer;

public class SkeletalFreakRenderer extends GeoEntityRenderer<SkeletalFreakEntity, GeoLivingEntityRenderState> {
    public SkeletalFreakRenderer(EntityRendererProvider.Context render) {
        super(render, new SkeletalFreakModel());
        withRenderLayer(new SkeletalFreakFaceGlowLayer(this));
    }
}