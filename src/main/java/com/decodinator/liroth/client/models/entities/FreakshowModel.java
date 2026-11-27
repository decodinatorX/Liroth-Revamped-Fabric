package com.decodinator.liroth.client.models.entities;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import com.decodinator.liroth.client.rendering.entities.FreakshowRenderState;
import net.minecraft.util.Mth;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class FreakshowModel extends EntityModel<FreakshowRenderState> {
	private final ModelPart leftLeg;
	private final ModelPart rightLeg;
	private final ModelPart head;
	private final ModelPart leftArm;
	private final ModelPart rightArm;
	private final ModelPart body;

	public FreakshowModel(ModelPart root) {
		super(root);
		this.leftLeg = root.getChild("left_leg");
		this.rightLeg = root.getChild("right_leg");
		this.head = root.getChild("head");
		this.leftArm = root.getChild("left_arm");
		this.rightArm = root.getChild("right_arm");
		this.body = root.getChild("body");
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 57).addBox(-1.0F, 25.0F, -1.0F, 2.0F, 5.0F, 2.0F).texOffs(0, 34).addBox(-1.0F, 5.0F, 0.0F, 2.0F, 20.0F, 2.0F).texOffs(0, 34).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F), PartPose.offset(-3.0F, -6.0F, 0.0F));
		partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 57).addBox(-1.0F, 25.0F, -1.0F, 2.0F, 5.0F, 2.0F).texOffs(0, 34).addBox(-1.0F, 5.0F, 0.0F, 2.0F, 20.0F, 2.0F).texOffs(0, 34).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F), PartPose.offset(3.0F, -6.0F, 0.0F));
		partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(128, 147).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F).texOffs(32, 12).addBox(4.0F, -3.0F, 0.0F, 4.0F, 1.0F, 1.0F).texOffs(32, 12).addBox(4.0F, -5.0F, 0.0F, 4.0F, 1.0F, 1.0F).texOffs(40, 0).addBox(4.0F, -4.0F, 0.0F, 5.0F, 1.0F, 1.0F).texOffs(0, 0).addBox(3.0F, -16.0F, -1.0F, 2.0F, 2.0F, 2.0F).texOffs(0, 4).addBox(5.0F, -16.0F, -1.0F, 2.0F, 2.0F, 2.0F).texOffs(0, 18).addBox(5.0F, -10.0F, -1.0F, 2.0F, 2.0F, 2.0F).texOffs(0, 22).addBox(6.0F, -14.0F, -1.0F, 2.0F, 2.0F, 2.0F).texOffs(32, 8).addBox(5.0F, -12.0F, -1.0F, 2.0F, 2.0F, 2.0F).texOffs(52, 28).addBox(2.0F, -10.0F, -1.0F, 2.0F, 2.0F, 2.0F).texOffs(24, 34).addBox(3.0F, -12.0F, -1.0F, 2.0F, 2.0F, 2.0F).texOffs(114, 27).addBox(-4.0F, -6.0F, 1.0F, -5.0F, 5.0F, 0.0F), PartPose.offset(0.0F, -18.0F, 0.0F));
		partdefinition.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 4).addBox(5.0F, -18.0F, -1.0F, 2.0F, 2.0F, 2.0F).texOffs(0, 18).addBox(5.0F, -12.0F, -1.0F, 2.0F, 2.0F, 2.0F).texOffs(0, 22).addBox(6.0F, -16.0F, -1.0F, 2.0F, 2.0F, 2.0F).texOffs(32, 8).addBox(5.0F, -14.0F, -1.0F, 2.0F, 2.0F, 2.0F).texOffs(24, 34).addBox(3.0F, -14.0F, -1.0F, 2.0F, 2.0F, 2.0F).texOffs(52, 28).addBox(2.0F, -12.0F, -1.0F, 2.0F, 2.0F, 2.0F).texOffs(0, 0).addBox(3.0F, -18.0F, -1.0F, 2.0F, 2.0F, 2.0F), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, -3.1416F, 0.0F, 3.1416F));
		partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(20, 42).addBox(-2.0F, -0.5F, -1.0F, 2.0F, 17.0F, 2.0F).texOffs(10, 43).mirror().addBox(-2.0F, 16.5F, -0.5F, 2.0F, 13.0F, 2.0F).mirror(false), PartPose.offset(-5.0F, -17.5F, 0.0F));
		partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(20, 42).addBox(0.0F, -0.5F, -1.0F, 2.0F, 17.0F, 2.0F).texOffs(10, 43).addBox(0.0F, 16.5F, -0.5F, 2.0F, 13.0F, 2.0F), PartPose.offset(5.0F, -17.5F, 0.0F));
		partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(21, 74).addBox(-3.0F, 6.0F, -2.0F, 6.0F, 1.0F, 1.0F).texOffs(14, 76).addBox(-4.0F, 2.0F, -2.0F, 8.0F, 3.0F, 1.0F).texOffs(17, 78).addBox(-4.0F, 10.0F, -2.0F, 8.0F, 2.0F, 1.0F).texOffs(21, 76).addBox(1.0F, 1.0F, -2.0F, 3.0F, 1.0F, 1.0F).texOffs(21, 76).addBox(1.0F, 0.0F, 2.0F, 3.0F, 1.0F, 1.0F).texOffs(21, 76).addBox(1.0F, 2.0F, 2.0F, 3.0F, 1.0F, 1.0F).texOffs(21, 76).addBox(1.0F, 4.0F, 2.0F, 3.0F, 1.0F, 1.0F).texOffs(21, 76).addBox(-4.0F, 4.0F, 2.0F, 3.0F, 1.0F, 1.0F).texOffs(21, 76).addBox(-4.0F, 2.0F, 2.0F, 3.0F, 1.0F, 1.0F).texOffs(21, 76).addBox(-4.0F, 0.0F, 2.0F, 3.0F, 1.0F, 1.0F).texOffs(21, 76).addBox(-4.0F, 1.0F, -2.0F, 3.0F, 1.0F, 1.0F).texOffs(21, 76).addBox(-4.0F, 0.0F, -2.0F, 2.0F, 1.0F, 1.0F).texOffs(21, 76).addBox(-1.0F, 9.0F, 1.0F, 2.0F, 1.0F, 1.0F).texOffs(21, 76).addBox(2.0F, 0.0F, -2.0F, 2.0F, 1.0F, 1.0F).texOffs(21, 76).addBox(-1.0F, 5.0F, -2.0F, 2.0F, 1.0F, 1.0F).texOffs(21, 76).addBox(-1.0F, 7.0F, -2.0F, 2.0F, 3.0F, 1.0F).texOffs(27, 72).addBox(-1.0F, 0.0F, 2.0F, 2.0F, 10.0F, 1.0F).texOffs(15, 79).addBox(-4.0F, 10.0F, 1.0F, 8.0F, 2.0F, 1.0F).texOffs(10, 66).addBox(4.0F, 10.0F, -1.0F, 1.0F, 2.0F, 2.0F).texOffs(10, 66).addBox(-5.0F, 10.0F, -1.0F, 1.0F, 2.0F, 2.0F).texOffs(1, 69).addBox(-5.0F, 4.0F, -1.0F, 1.0F, 1.0F, 3.0F).texOffs(46, 74).addBox(-5.0F, 2.0F, -1.0F, 1.0F, 1.0F, 3.0F).texOffs(18, 86).addBox(-5.0F, 0.0F, -1.0F, 1.0F, 1.0F, 3.0F).texOffs(8, 83).addBox(4.0F, 0.0F, -1.0F, 1.0F, 1.0F, 3.0F).texOffs(21, 73).addBox(4.0F, 4.0F, -1.0F, 1.0F, 1.0F, 3.0F).texOffs(41, 84).addBox(4.0F, 2.0F, -1.0F, 1.0F, 1.0F, 3.0F).texOffs(58, 83).addBox(0.0F, 0.0F, 3.0F, 0.0F, 10.0F, 3.0F), PartPose.offset(0.0F, -18.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 256, 256);
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
	public void setupAnim(FreakshowRenderState state) {
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