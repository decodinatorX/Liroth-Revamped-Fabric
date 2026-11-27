package com.decodinator.liroth.client.rendering.entities;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.client.models.entities.VileSharkModel;
import com.decodinator.liroth.core.entities.VileSharkEntity;

import com.decodinator.liroth.core.registry.LirothModelLayers;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class VileSharkEntityRenderer extends MobRenderer<VileSharkEntity, VileSharkRenderState, EntityModel<VileSharkRenderState>> {

    public VileSharkEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new VileSharkModel(context.bakeLayer(LirothModelLayers.VILE_SHARK)), 0.5f);
        this.addLayer(new VileSharkGlowFeatureRenderer(this));
    }

    @Override
    public Identifier getTextureLocation(VileSharkRenderState state) {
        return Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "textures/entity/vile_shark/vile_shark.png");
    }

    @Override
    public VileSharkRenderState createRenderState() {
        return new VileSharkRenderState();
    }

    @Override
    public void extractRenderState(VileSharkEntity entity, VileSharkRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.isMoving = entity.getDeltaMovement().horizontalDistanceSqr() > 1.0E-7D;
    }
}