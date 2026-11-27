package com.decodinator.liroth.client.rendering.entities;

import com.decodinator.liroth.client.models.entities.ForsakenCorpseModel;
import com.decodinator.liroth.core.entities.ForsakenCorpseEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import com.geckolib.renderer.GeoEntityRenderer;

public class ForsakenCorpseRenderer extends GeoEntityRenderer<ForsakenCorpseEntity, GeoLivingEntityRenderState> {
    public ForsakenCorpseRenderer(EntityRendererProvider.Context render) {
        super(render, new ForsakenCorpseModel());
        withRenderLayer(new ForsakenCorpseFaceGlowLayer(this));
    }
}