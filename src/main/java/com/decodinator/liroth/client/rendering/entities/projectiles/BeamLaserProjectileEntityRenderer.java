package com.decodinator.liroth.client.rendering.entities.projectiles;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.client.models.entities.projectiles.BeamLaserProjectileModel;
import com.decodinator.liroth.core.entities.projectiles.BeamLaserProjectileEntity;
import com.decodinator.liroth.core.registry.LirothModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class BeamLaserProjectileEntityRenderer extends EntityRenderer<BeamLaserProjectileEntity, ArrowRenderState> {
    public static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "textures/entity/beam.png");
    private final BeamLaserProjectileModel model;

    public BeamLaserProjectileEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new BeamLaserProjectileModel(context.bakeLayer(LirothModelLayers.BEAM_LASER_PROJECTILE));
    }

    @Override
    public ArrowRenderState createRenderState() {
        return new ArrowRenderState();
    }

    @Override
    public void extractRenderState(BeamLaserProjectileEntity entity, ArrowRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.yRot = Mth.lerp(partialTick, entity.yRotO, entity.getYRot()) - 90.0F;
        state.xRot = Mth.lerp(partialTick, entity.xRotO, entity.getXRot());
    }

    @Override
    public void submit(ArrowRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        poseStack.pushPose();
        poseStack.rotateDegrees(Axis.YP, state.yRot);
        poseStack.rotateDegrees(Axis.ZP, state.xRot);
        submitNodeCollector.submitModelPart(this.model.root(), poseStack, this.model.renderType(TEXTURE), state.lightCoords, OverlayTexture.NO_OVERLAY, null, -1, state.outlineColor);
        poseStack.popPose();
        super.submit(state, poseStack, submitNodeCollector, camera);
    }

}