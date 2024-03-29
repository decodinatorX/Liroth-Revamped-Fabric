package com.decodinator.liroth.entities.renderers;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.core.LirothModelLayers;
import com.decodinator.liroth.entities.PierPeepEntity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(value=EnvType.CLIENT)
public class PierPeepEntityRenderer extends MobRenderer<PierPeepEntity, PierPeepModel<PierPeepEntity>> {

    public PierPeepEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new PierPeepModel(context.bakeLayer(LirothModelLayers.PIER_PEEP)), 0.5f);
        this.addLayer(new PierPeepGlowFeatureRenderer<PierPeepEntity>(this));
    }
 
    @Override
    public ResourceLocation getTextureLocation(PierPeepEntity entity) {
        return new ResourceLocation("liroth", "textures/entity/pier_peep/pier_peep.png");
        
    }
}
