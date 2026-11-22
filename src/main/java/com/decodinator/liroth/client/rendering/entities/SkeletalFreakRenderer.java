package com.decodinator.liroth.client.rendering.entities;


import com.decodinator.liroth.client.models.entities.SkeletalFreakModel;
import com.decodinator.liroth.core.entities.SkeletalFreakEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SkeletalFreakRenderer extends GeoEntityRenderer<SkeletalFreakEntity> {
    public SkeletalFreakRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SkeletalFreakModel<SkeletalFreakEntity>());
        this.addRenderLayer(new SkeletalFreakGlowLayer(this));
    }
}
