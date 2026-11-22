package com.decodinator.liroth.client.rendering.items;

import com.decodinator.liroth.client.LirothClient;
import com.decodinator.liroth.client.models.items.PotestiumHelmetModel;
import com.mojang.blaze3d.vertex.PoseStack;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class PotestiumHelmetRenderer implements ArmorRenderer {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("liroth", "textures/models/armor/potestium_armor_helm.png");
    private static PotestiumHelmetModel potestiumHelmetModel;
    public PotestiumHelmetRenderer() {

    }
    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, HumanoidModel<LivingEntity> contextModel) {
        if (potestiumHelmetModel == null) {
        	potestiumHelmetModel = new PotestiumHelmetModel(Minecraft.getInstance().getEntityModels().bakeLayer(LirothClient.MODEL_POTESTIUM_HELMET_LAYER));;
        }

        contextModel.copyPropertiesTo(potestiumHelmetModel);
        potestiumHelmetModel.setAllVisible(false);
        potestiumHelmetModel.head.visible = slot == EquipmentSlot.HEAD;
            ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, potestiumHelmetModel, TEXTURE);
    }
}