package com.decodinator.liroth.client.rendering.entities.projectiles;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.core.entities.projectiles.LevoArrow;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class LevoArrowRenderer extends ArrowRenderer<LevoArrow> {
    public static final ResourceLocation SPECTRAL_ARROW_LOCATION = ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "textures/entity/projectiles/levo_arrow.png");

    public LevoArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    public ResourceLocation getTextureLocation(LevoArrow entity) {
        return SPECTRAL_ARROW_LOCATION;
    }
}