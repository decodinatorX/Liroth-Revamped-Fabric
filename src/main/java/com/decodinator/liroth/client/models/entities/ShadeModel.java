package com.decodinator.liroth.client.models.entities;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import com.decodinator.liroth.client.rendering.entities.ShadeRenderState;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class ShadeModel extends EntityModel<ShadeRenderState> {
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart leftArm;
    private final ModelPart rightArm;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;

    public ShadeModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.leftArm = root.getChild("leftArm");
        this.rightArm = root.getChild("rightArm");
        this.leftLeg = root.getChild("leftLeg");
        this.rightLeg = root.getChild("rightLeg");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F), PartPose.offset(0.0F, 0.0F, 0.0F));

        partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F), PartPose.offset(0.0F, 0.0F, 0.0F));

        partdefinition.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(16, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(-5.0F, 2.0F, 0.0F));

        partdefinition.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(32, 0).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(5.0F, 2.0F, 0.0F));

        partdefinition.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(-1.9F, 12.0F, 0.0F));

        partdefinition.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(24, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(1.9F, 12.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    protected float lerpAngle(float angleOne, float angleTwo, float magnitude) {
        float f = (magnitude - angleTwo) % ((float)Math.PI * 2);
        if (f < (float)(-Math.PI)) {
            f += (float)Math.PI * 2;
        }
        if (f >= (float)Math.PI) {
            f -= (float)Math.PI * 2;
        }
        return angleTwo + angleOne * f;
    }

    @Override
    public void setupAnim(ShadeRenderState state) {
        super.setupAnim(state);
        float limbSwing = state.walkAnimationPos;
        float limbSwingAmount = state.walkAnimationSpeed;
        boolean fallFlying = state.isFallFlying;
        this.head.yRot = state.yRot * ((float)Math.PI / 180);
        this.head.xRot = fallFlying ? -0.7853982f : (state.swimAmount > 0.0f ? (state.isVisuallySwimming ? this.lerpAngle(state.swimAmount, this.head.xRot, -0.7853982f) : this.lerpAngle(state.swimAmount, this.head.xRot, state.xRot * ((float)Math.PI / 180))) : state.xRot * ((float)Math.PI / 180));
        this.body.yRot = 0.0f;
        this.rightArm.z = 0.0f;
        this.leftArm.z = 0.0f;
        float speedScale = 1.0f;
        if (fallFlying) {
            speedScale = state.velocityLengthSqr / 0.2f;
            speedScale *= speedScale * speedScale;
        }
        if (speedScale < 1.0f) {
            speedScale = 1.0f;
        }
        this.rightArm.xRot = Mth.cos(limbSwing * 0.6662f + (float)Math.PI) * 2.0f * limbSwingAmount * 0.5f / speedScale;
        this.leftArm.xRot = Mth.cos(limbSwing * 0.6662f) * 2.0f * limbSwingAmount * 0.5f / speedScale;
        this.rightArm.zRot = 0.0f;
        this.leftArm.zRot = 0.0f;
        this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount / speedScale;
        this.leftLeg.xRot = Mth.cos(limbSwing * 0.6662f + (float)Math.PI) * 1.4f * limbSwingAmount / speedScale;
        this.rightLeg.yRot = 0.0f;
        this.leftLeg.yRot = 0.0f;
        this.rightLeg.zRot = 0.0f;
        this.leftLeg.zRot = 0.0f;
        if (state.isPassenger) {
            this.rightArm.xRot -= 0.62831855f;
            this.leftArm.xRot -= 0.62831855f;
            this.rightLeg.xRot = -1.4137167f;
            this.rightLeg.yRot = 0.31415927f;
            this.rightLeg.zRot = 0.07853982f;
            this.leftLeg.xRot = -1.4137167f;
            this.leftLeg.yRot = -0.31415927f;
            this.leftLeg.zRot = -0.07853982f;
        }
        this.rightArm.yRot = 0.0f;
        this.leftArm.yRot = 0.0f;
        this.leftLeg.xRot = Mth.lerp(state.swimAmount, this.leftLeg.xRot, 0.3f * Mth.cos(limbSwing * 0.33333334f + (float)Math.PI));
        this.rightLeg.xRot = Mth.lerp(state.swimAmount, this.rightLeg.xRot, 0.3f * Mth.cos(limbSwing * 0.33333334f));
    }
}