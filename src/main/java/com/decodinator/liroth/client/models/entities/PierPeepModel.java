package com.decodinator.liroth.client.models.entities;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class PierPeepModel extends EntityModel<LivingEntityRenderState> {
	private final ModelPart head;

	public PierPeepModel(ModelPart root) {
		super(root);
		this.head = root.getChild("head");
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F).mirror(false).texOffs(0, 14).addBox(0.0F, -6.0F, 4.0F, 0.01F, 4.0F, 8.0F), PartPose.offset(0.0F, 24.0F, 0.0F));

		head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 14).addBox(-4.0F, -2.0F, 4.0F, 0.01F, 4.0F, 8.0F), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(LivingEntityRenderState state) {
		super.setupAnim(state);
		this.head.yRot = state.yRot * ((float)Math.PI / 180);
		this.head.xRot = state.xRot * ((float)Math.PI / 180);
	}
}