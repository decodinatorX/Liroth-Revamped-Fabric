package com.decodinator.liroth.entities.renderers;

import com.decodinator.liroth.Liroth;

import com.decodinator.liroth.entities.ForsakenCorpseEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class ForsakenCorpseGlowLayer extends GeoRenderLayer<ForsakenCorpseEntity> {
    private static final ResourceLocation SKIN = new ResourceLocation(Liroth.MOD_ID + ":textures/entity/forsaken_corpse/forsaken_corpse_face.png");

    public ForsakenCorpseGlowLayer(GeoRenderer<ForsakenCorpseEntity> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(PoseStack poseStack, ForsakenCorpseEntity animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        RenderType armorRenderType = RenderType.eyes(SKIN);
        if (animatable.isInvisible()) {
            return;
        }
        getRenderer().reRender(getDefaultBakedModel(animatable), poseStack, bufferSource, animatable, armorRenderType,
                bufferSource.getBuffer(armorRenderType), partialTick, packedLight, OverlayTexture.NO_OVERLAY,
                1, 1, 1, 1);
    }

}
