package com.decodinator.liroth.client.rendering.entities;

import com.decodinator.liroth.client.models.entities.ShadeModel;
import com.decodinator.liroth.core.registry.LirothModelLayers;
import com.decodinator.liroth.core.entities.ShadeEntity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

@Environment(value=EnvType.CLIENT)
public class ShadeEntityRenderer extends MobRenderer<ShadeEntity, ShadeModel<ShadeEntity>>{

	public ShadeEntityRenderer(EntityRendererProvider.Context context) {
		super(context, new ShadeModel(context.bakeLayer(LirothModelLayers.SHADE)), 0.5f);
		this.addLayer(new ShadeShadedFeatureRenderer<ShadeEntity>(this));
	}

	@Override
	public ResourceLocation getTextureLocation(ShadeEntity entity) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.level == null) {
			return DefaultPlayerSkin.getDefaultTexture();
		}
		List<AbstractClientPlayer> players = mc.level.players().stream().filter(AbstractClientPlayer.class::isInstance).map(AbstractClientPlayer.class::cast).toList();

		if (players.isEmpty()) {
			return DefaultPlayerSkin.getDefaultTexture();
		}
		int playerIndex = Math.floorMod(entity.getId(), players.size());
		return players.get(playerIndex).getSkin().texture();
	}
}
