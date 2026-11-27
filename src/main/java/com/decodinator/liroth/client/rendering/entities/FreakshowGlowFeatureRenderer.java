package com.decodinator.liroth.client.rendering.entities;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.client.models.entities.FreakshowModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class FreakshowGlowFeatureRenderer extends EyesLayer<FreakshowRenderState, FreakshowModel> {
    private static final RenderType SKIN = RenderTypes.eyes(Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "textures/entity/freakshow/freakshow_glow.png"));

    public FreakshowGlowFeatureRenderer(RenderLayerParent<FreakshowRenderState, FreakshowModel> parent) {
        super(parent);
    }

    @Override
    public RenderType renderType() {
        return SKIN;
    }
}