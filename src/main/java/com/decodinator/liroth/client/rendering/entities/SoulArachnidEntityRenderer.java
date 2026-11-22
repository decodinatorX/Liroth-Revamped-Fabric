package com.decodinator.liroth.client.rendering.entities;

import com.decodinator.liroth.client.models.entities.SoulArachnidModel;
import com.decodinator.liroth.core.entities.SoulArachnidEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SoulArachnidEntityRenderer extends GeoEntityRenderer<SoulArachnidEntity> {
    public SoulArachnidEntityRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SoulArachnidModel<SoulArachnidEntity>());
        this.addRenderLayer(new SoulArachnidGlowFeatureRenderer(this));
    }
}