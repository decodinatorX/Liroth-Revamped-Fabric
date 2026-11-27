package com.decodinator.liroth.client.rendering.entities;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.core.entities.ProwlerEntity;
import com.geckolib.renderer.base.RenderPassInfo;
import com.geckolib.renderer.layer.GeoRenderLayer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

public class ProwlerFaceGlowLayer extends GeoRenderLayer<ProwlerEntity, Void, GeoLivingEntityRenderState> {
    private static final Identifier FACE_TEXTURE = Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "textures/entity/prowler/prowler_glow.png");

    public ProwlerFaceGlowLayer(ProwlerRenderer renderer) {
        super(renderer);
    }

    @Override
    public void submitRenderTask(RenderPassInfo<GeoLivingEntityRenderState> renderPassInfo, SubmitNodeCollector renderTasks) {
        renderTasks.submitCustomGeometry(renderPassInfo.poseStack(), RenderTypes.entityTranslucentEmissive(FACE_TEXTURE), (pose, vertexConsumer) -> {
            var poseStack = renderPassInfo.poseStack();
            poseStack.pushPose();
            poseStack.last().set(pose);
            renderPassInfo.renderPosed(() -> renderPassInfo.model().render(renderPassInfo, vertexConsumer, 15728640, renderPassInfo.packedOverlay(), renderPassInfo.renderColor()));
            poseStack.popPose();
        });
    }
}