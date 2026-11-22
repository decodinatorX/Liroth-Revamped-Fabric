package com.decodinator.liroth.client.rendering.entities;

import com.decodinator.liroth.client.models.entities.FungalFiendModel;
import com.decodinator.liroth.core.entities.FungalFiendEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FungalFiendRenderer extends GeoEntityRenderer<FungalFiendEntity> {
    public FungalFiendRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new FungalFiendModel<FungalFiendEntity>());
        this.addRenderLayer(new FungalFiendGlowLayer(this));
    }
}
