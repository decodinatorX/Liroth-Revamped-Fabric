package com.decodinator.liroth.client.rendering.entities;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.client.models.entities.FreakshowModel;
import com.decodinator.liroth.core.registry.LirothModelLayers;
import com.decodinator.liroth.core.entities.FreakshowEntity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(value=EnvType.CLIENT)
public class FreakshowEntityRenderer extends MobRenderer<FreakshowEntity, FreakshowModel<FreakshowEntity>> {

    public FreakshowEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new FreakshowModel(context.bakeLayer(LirothModelLayers.FREAKSHOW)), 0.5f);
        this.addLayer(new FreakshowGlowFeatureRenderer<FreakshowEntity>(this));
        this.addLayer(new FreakshowTranslucentFeatureRenderer<FreakshowEntity>(this));
    }
 
    @Override
    public ResourceLocation getTextureLocation(FreakshowEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "textures/entity/freakshow/nothing.png");
        
    }
}
