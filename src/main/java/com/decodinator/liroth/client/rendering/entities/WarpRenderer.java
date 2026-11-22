package com.decodinator.liroth.client.rendering.entities;


import com.decodinator.liroth.client.models.entities.WarpModel;
import com.decodinator.liroth.core.entities.WarpEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class WarpRenderer extends GeoEntityRenderer<WarpEntity> {
    public WarpRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new WarpModel<WarpEntity>());
        this.addRenderLayer(new WarpGlowLayer(this));
    }
}
