package com.decodinator.liroth.client.rendering.entities;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.client.models.entities.ShadeModel;
import com.decodinator.liroth.core.entities.ShadeEntity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;

@Environment(value=EnvType.CLIENT)
public class ShadeShadedFeatureRenderer<T extends ShadeEntity>
extends EyesLayer<T, ShadeModel<T>> {
    private static final RenderType SKIN = RenderType.entityTranslucent(ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "textures/entity/shade/shade_shaded.png"));

    public ShadeShadedFeatureRenderer(RenderLayerParent<T, ShadeModel<T>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public RenderType renderType() {
        return SKIN;
    }
}

