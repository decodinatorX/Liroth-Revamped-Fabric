package com.decodinator.liroth.client.rendering.entities.projectiles;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.client.models.entities.projectiles.BeamLaserProjectileModel;
import com.decodinator.liroth.core.entities.projectiles.BeamLaserProjectileEntity;

import com.decodinator.liroth.core.registry.LirothModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

@Environment(value=EnvType.CLIENT)
public class BeamLaserProjectileEntityRenderer extends EntityRenderer<BeamLaserProjectileEntity> {
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "textures/entity/beam.png");
    private final BeamLaserProjectileModel model;

    public BeamLaserProjectileEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new BeamLaserProjectileModel<>(context.bakeLayer(LirothModelLayers.BEAM_LASER_PROJECTILE));
    }

    public void render(BeamLaserProjectileEntity entity, float yaw, float ticks, PoseStack pose, MultiBufferSource buffer, int light) {
        pose.pushPose();
        pose.mulPose(Axis.YP.rotationDegrees(Mth.lerp(ticks, entity.yRotO, entity.getYRot()) - 90.0f));
        pose.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(ticks, entity.xRotO, entity.getXRot())));
        VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(buffer, this.model.renderType(this.getTextureLocation(entity)), false, false);
        this.model.renderToBuffer(pose, vertexConsumer, light, OverlayTexture.NO_OVERLAY);
        pose.popPose();
        super.render(entity, yaw, ticks, pose, buffer, light);
    }

    @Override
    public ResourceLocation getTextureLocation(BeamLaserProjectileEntity arrowEntity) {
        return TEXTURE;
    }
}