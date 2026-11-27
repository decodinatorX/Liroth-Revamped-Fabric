package com.decodinator.liroth.client.rendering.entities;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.core.entities.FungalFiendEntity;
import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.base.RenderPassInfo;
import com.geckolib.renderer.layer.GeoRenderLayer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public class FungalFiendFaceGlowLayer extends GeoRenderLayer<FungalFiendEntity, Void, GeoLivingEntityRenderState> {
    private static final Identifier FACE_TEXTURE = Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "textures/entity/fungal_fiend/fungal_fiend_face.png");

    public FungalFiendFaceGlowLayer(FungalFiendRenderer renderer) {
        super(renderer);
    }

    @Override
    public void submitRenderTask(RenderPassInfo renderPassInfo, SubmitNodeCollector renderTasks) {
        GeoRenderState state = renderPassInfo.renderState();
        float swell = Mth.clamp(state.getOrDefaultGeckolibData(FungalFiendFuseFlashLayer.SWELLING, 0.0F), 0.0F, 1.0F);
        int color = renderPassInfo.renderColor();
        int alpha = Mth.floor((0.35F + swell * 0.35F) * 255.0F);
        color = (color & 0x00FFFFFF) | (alpha << 24);
        int faceColor = color;

        renderTasks.submitCustomGeometry(renderPassInfo.poseStack(), RenderTypes.entityTranslucentEmissive(FACE_TEXTURE), (pose, vertexConsumer) -> {
            var poseStack = renderPassInfo.poseStack();
            poseStack.pushPose();
            poseStack.last().set(pose);
            renderPassInfo.renderPosed(() -> renderPassInfo.model().render(renderPassInfo, vertexConsumer, 15728640, renderPassInfo.packedOverlay(), faceColor));poseStack.popPose();
        });
    }
}