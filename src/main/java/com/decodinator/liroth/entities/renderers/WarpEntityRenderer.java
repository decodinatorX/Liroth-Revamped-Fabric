package com.decodinator.liroth.entities.renderers;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.core.fluids.LirothFluidRenderingModClient;
import com.decodinator.liroth.entities.FungalFiendEntity;
import com.decodinator.liroth.entities.WarpEntity;
import com.decodinator.liroth.entities.renderers.FungalFiendModel;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.DrownedOverlayFeatureRenderer;
import net.minecraft.client.render.entity.feature.EndermanEyesFeatureRenderer;
import net.minecraft.client.render.entity.model.CreeperEntityModel;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.util.Identifier;

public class WarpEntityRenderer extends MobEntityRenderer<WarpEntity, WarpModel<WarpEntity>> {

    public WarpEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new WarpModel(context.getPart(LirothFluidRenderingModClient.MODEL_WARP_LAYER)), 0.5f);
        this.addFeature(new WarpGlowFeatureRenderer<WarpEntity>(this));
    }
 
    @Override
    public Identifier getTexture(WarpEntity entity) {
        return new Identifier("liroth", "textures/entity/warp/warp.png");
        
    }
}
