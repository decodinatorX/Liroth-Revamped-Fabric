package com.decodinator.liroth.client.rendering.entities;

import com.decodinator.liroth.Liroth;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class VileSharkGlowFeatureRenderer extends EyesLayer<VileSharkRenderState, EntityModel<VileSharkRenderState>> {
    private static final RenderType SKIN = net.minecraft.client.renderer.rendertype.RenderTypes.eyes(Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "textures/entity/vile_shark/vile_shark_glow.png"));

    public VileSharkGlowFeatureRenderer(RenderLayerParent<VileSharkRenderState, EntityModel<VileSharkRenderState>> parent) {
        super(parent);
    }

    @Override
    public RenderType renderType() {
        return SKIN;
    }
}