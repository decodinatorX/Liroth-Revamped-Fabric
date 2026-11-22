package com.decodinator.liroth.client.rendering.entities;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.client.models.entities.VileSharkModel;
import com.decodinator.liroth.core.registry.LirothModelLayers;
import com.decodinator.liroth.core.entities.VileSharkEntity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(value=EnvType.CLIENT)
public class VileSharkEntityRenderer extends MobRenderer<VileSharkEntity, VileSharkModel<VileSharkEntity>> {

    public VileSharkEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new VileSharkModel(context.bakeLayer(LirothModelLayers.VILE_SHARK)), 0.5f);
        this.addLayer(new VileSharkGlowFeatureRenderer<VileSharkEntity>(this));
    }
 
    @Override
    public ResourceLocation getTextureLocation(VileSharkEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "textures/entity/vile_shark/vile_shark.png");
        
    }
}
