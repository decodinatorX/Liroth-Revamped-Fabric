package com.decodinator.liroth.client.rendering.entities;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.client.models.entities.FreakshowModel;
import com.decodinator.liroth.core.entities.FreakshowEntity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;

@Environment(value=EnvType.CLIENT)
public class FreakshowTranslucentFeatureRenderer<T extends FreakshowEntity>
extends EyesLayer<T, FreakshowModel<T>> {
    private static final RenderType SKIN = RenderType.entityTranslucent(ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "textures/entity/freakshow/freakshow.png"));

    public FreakshowTranslucentFeatureRenderer(RenderLayerParent<T, FreakshowModel<T>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public RenderType renderType() {
        return SKIN;
    }
}

