package com.decodinator.liroth.client.rendering.entities;

import com.decodinator.liroth.Liroth;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
public class PierPeepFaceGlowLayer extends EyesLayer<LivingEntityRenderState, EntityModel<LivingEntityRenderState>> {
    private static final RenderType SKIN = net.minecraft.client.renderer.rendertype.RenderTypes.eyes(Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "textures/entity/pier_peep/pier_peep_glow.png"));

    public PierPeepFaceGlowLayer(RenderLayerParent<LivingEntityRenderState, EntityModel<LivingEntityRenderState>> parent) {
        super(parent);
    }

    @Override
    public RenderType renderType() {
        return SKIN;
    }
}