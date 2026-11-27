package com.decodinator.liroth.client.rendering.entities;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.client.models.entities.PierPeepModel;
import com.decodinator.liroth.core.entities.PierPeepEntity;
import com.decodinator.liroth.core.registry.LirothModelLayers;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class PierPeepEntityRenderer extends MobRenderer<PierPeepEntity, LivingEntityRenderState, EntityModel<LivingEntityRenderState>> {

    public PierPeepEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new PierPeepModel(context.bakeLayer(LirothModelLayers.PIER_PEEP)), 0.5f);
        this.addLayer(new PierPeepFaceGlowLayer(this));
    }

    @Override
    public Identifier getTextureLocation(LivingEntityRenderState state) {
        return Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "textures/entity/pier_peep/pier_peep.png");
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }
}