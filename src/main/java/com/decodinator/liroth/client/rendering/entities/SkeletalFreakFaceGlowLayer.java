package com.decodinator.liroth.client.rendering.entities;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.core.entities.SkeletalFreakEntity;
import com.geckolib.renderer.base.RenderPassInfo;
import com.geckolib.renderer.layer.GeoRenderLayer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

public class SkeletalFreakFaceGlowLayer extends GeoRenderLayer<SkeletalFreakEntity, Void, GeoLivingEntityRenderState> {
    private static final Identifier FACE_TEXTURE = Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "textures/entity/skeletal_freak/skeletal_freak_face.png");

    public SkeletalFreakFaceGlowLayer(SkeletalFreakRenderer renderer) {
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