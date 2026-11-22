package com.decodinator.liroth.client.rendering.entities;


import com.decodinator.liroth.client.models.entities.ProwlerModel;
import com.decodinator.liroth.core.entities.ProwlerEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ProwlerRenderer extends GeoEntityRenderer<ProwlerEntity> {
    public ProwlerRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ProwlerModel<ProwlerEntity>());
        this.addRenderLayer(new ProwlerGlowLayer(this));
    }
}
