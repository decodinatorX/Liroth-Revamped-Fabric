package com.decodinator.liroth.client.rendering.entities;

import com.decodinator.liroth.core.entities.FungalFiendEntity;
import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.constant.DataTickets;
import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.base.RenderPassInfo;
import com.geckolib.renderer.layer.GeoRenderLayer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.util.Mth;

public class FungalFiendFuseFlashLayer extends GeoRenderLayer {
    static final DataTicket<Float> SWELLING = DataTickets.create("liroth_fungal_fiend_swelling", Float.class);

    public FungalFiendFuseFlashLayer(FungalFiendRenderer renderer) {
        super(renderer);
    }

    @Override
    public void addRenderData(GeoAnimatable animatable, Object relatedObject, GeoRenderState renderState, float partialTick) {
        FungalFiendEntity fungalFiend = (FungalFiendEntity) animatable;
        renderState.addGeckolibData(SWELLING, Mth.clamp(fungalFiend.getSwelling(partialTick), 0.0F, 1.0F));
    }

    @Override
    public void submitRenderTask(RenderPassInfo renderPassInfo, SubmitNodeCollector renderTasks) {
        GeoRenderState state = renderPassInfo.renderState();
        float swell = state.getOrDefaultGeckolibData(SWELLING, 0.0F);
        float flash = (int)(swell * 10.0F) % 2 == 0 ? 0.0F : Mth.clamp(swell, 0.5F, 1.0F);
        if (flash <= 0.0F) {
            return;
        }

        renderTasks.submitCustomGeometry(renderPassInfo.poseStack(), RenderTypes.entityTranslucent(getRenderer().getTextureLocation(state)), (pose, vertexConsumer) -> {
            var poseStack = renderPassInfo.poseStack();
            poseStack.pushPose();
            poseStack.last().set(pose);
            renderPassInfo.renderPosed(() -> renderPassInfo.model().render(renderPassInfo, vertexConsumer, renderPassInfo.packedLight(), LivingEntityRenderer.getOverlayCoords((LivingEntityRenderState) state, flash), 0xFFFFFFFF));
            poseStack.popPose();
        });
    }
}