package com.decodinator.liroth.client.models.entities;

import com.decodinator.liroth.client.rendering.entities.VileSharkRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class VileSharkModel extends EntityModel<VileSharkRenderState> {
	private final ModelPart tail;
	private final ModelPart tail_fin;

	public VileSharkModel(ModelPart root) {
		super(root);
		this.tail = root.getChild("tail");
		this.tail_fin = root.getChild("tail_fin");
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(26, 28).addBox(-5.0F, -4.0F, -8.0F, 10.0F, 5.0F, 8.0F).texOffs(33, 0).addBox(-5.0F, 2.0F, -6.0F, 10.0F, 1.0F, 6.0F), PartPose.offset(0.0F, 20.0F, -3.0F));

		partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 42).addBox(-6.0F, -5.0F, 0.0F, 12.0F, 9.0F, 13.0F), PartPose.offset(0.0F, 20.0F, -3.0F));

		partdefinition.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 20).addBox(-3.0F, -2.5F, 0.0F, 6.0F, 5.0F, 11.0F), PartPose.offset(0.0F, 20.5F, 10.0F));

		partdefinition.addOrReplaceChild("tail_fin", CubeListBuilder.create().texOffs(23, 20).addBox(-6.0F, -0.5F, -1.0F, 12.0F, 1.0F, 6.0F), PartPose.offset(0.0F, 20.5F, 20.0F));

		PartDefinition back_fin = partdefinition.addOrReplaceChild("back_fin", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 5.0F));

		back_fin.addOrReplaceChild("back_fin_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -12.0F, 3.0F, 1.0F, 5.0F, 4.0F), PartPose.offsetAndRotation(0.0F, 7.0F, -5.0F, -0.1745F, 0.0F, 0.0F));

		partdefinition.addOrReplaceChild("left_fin", CubeListBuilder.create().texOffs(0, 36).addBox(-8.0F, -1.0F, -1.0F, 8.0F, 1.0F, 4.0F), PartPose.offset(-6.0F, 22.0F, -1.0F));

		partdefinition.addOrReplaceChild("right_fin", CubeListBuilder.create().texOffs(33, 7).addBox(0.0F, -1.0F, -1.0F, 8.0F, 1.0F, 4.0F), PartPose.offset(6.0F, 22.0F, -1.0F));

		partdefinition.addOrReplaceChild("nose", CubeListBuilder.create(), PartPose.offset(0.0F, 20.0F, -11.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(VileSharkRenderState state) {
		super.setupAnim(state);
		if (state.isMoving) {
			this.tail.xRot = -0.1F * Mth.cos(state.ageInTicks * 0.3F);
			this.tail_fin.xRot = -0.2F * Mth.cos(state.ageInTicks * 0.3F);
		}
	}
}