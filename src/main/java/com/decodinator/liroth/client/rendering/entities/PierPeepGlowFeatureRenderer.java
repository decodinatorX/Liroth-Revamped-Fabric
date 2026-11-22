package com.decodinator.liroth.client.rendering.entities;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.client.models.entities.PierPeepModel;
import com.decodinator.liroth.core.entities.PierPeepEntity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;

@Environment(value=EnvType.CLIENT)
public class PierPeepGlowFeatureRenderer<T extends PierPeepEntity>
extends EyesLayer<T, PierPeepModel<T>> {
    private static final RenderType SKIN = RenderType.eyes(ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "textures/entity/pier_peep/pier_peep_glow.png"));

    public PierPeepGlowFeatureRenderer(RenderLayerParent<T, PierPeepModel<T>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public RenderType renderType() {
        return SKIN;
    }
}

