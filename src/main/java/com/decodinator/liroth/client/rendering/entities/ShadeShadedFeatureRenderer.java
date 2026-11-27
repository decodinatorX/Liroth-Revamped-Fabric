package com.decodinator.liroth.client.rendering.entities;

import com.decodinator.liroth.Liroth;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
public class ShadeShadedFeatureRenderer extends EyesLayer<ShadeRenderState, EntityModel<ShadeRenderState>> {
    private static final RenderType SKIN = net.minecraft.client.renderer.rendertype.RenderTypes.entityTranslucent(Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "textures/entity/shade/shade_shaded.png"));

    public ShadeShadedFeatureRenderer(RenderLayerParent<ShadeRenderState, EntityModel<ShadeRenderState>> parent) {
        super(parent);
    }

    @Override
    public RenderType renderType() {
        return SKIN;
    }
}