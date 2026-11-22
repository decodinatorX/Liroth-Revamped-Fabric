package com.decodinator.liroth.client.rendering.entities;

import com.decodinator.liroth.client.models.entities.LirothianMimicModel;
import com.decodinator.liroth.core.entities.LirothianMimicEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class LirothianMimicRenderer extends GeoEntityRenderer<LirothianMimicEntity> {
    public LirothianMimicRenderer(EntityRendererProvider.Context render) {
        super(render, new LirothianMimicModel());
    }
}