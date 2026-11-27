package com.decodinator.liroth.client.rendering.entities;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.client.models.entities.FreakshowModel;
import com.decodinator.liroth.core.entities.FreakshowEntity;
import com.decodinator.liroth.core.registry.LirothModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class FreakshowEntityRenderer extends MobRenderer<FreakshowEntity, FreakshowRenderState, FreakshowModel> {

    public FreakshowEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new FreakshowModel(context.bakeLayer(LirothModelLayers.FREAKSHOW)), 0.5f);
        this.addLayer(new FreakshowGlowFeatureRenderer(this));
        this.addLayer(new FreakshowTranslucentFeatureRenderer(this));
    }

    @Override
    public Identifier getTextureLocation(FreakshowRenderState state) {
        return Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "textures/entity/freakshow/nothing.png");
    }

    @Override
    public FreakshowRenderState createRenderState() {
        return new FreakshowRenderState();
    }

    @Override
    public void extractRenderState(FreakshowEntity entity, FreakshowRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.isFallFlying = entity.getFallFlyingTicks() > 4;
        state.isVisuallySwimming = entity.isVisuallySwimming();
        state.isPassenger = entity.isPassenger();
        state.swimAmount = entity.getSwimAmount(partialTick);
        state.velocityLengthSqr = (float) entity.getDeltaMovement().lengthSqr();
    }
}