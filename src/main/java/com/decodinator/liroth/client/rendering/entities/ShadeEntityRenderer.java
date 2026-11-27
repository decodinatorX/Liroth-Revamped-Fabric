package com.decodinator.liroth.client.rendering.entities;

import com.decodinator.liroth.client.models.entities.ShadeModel;
import com.decodinator.liroth.core.entities.ShadeEntity;
import com.decodinator.liroth.core.registry.LirothModelLayers;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
public class ShadeEntityRenderer extends MobRenderer<ShadeEntity, ShadeRenderState, EntityModel<ShadeRenderState>> {
	public ShadeEntityRenderer(EntityRendererProvider.Context context) {
		super(context, new ShadeModel(context.bakeLayer(LirothModelLayers.SHADE)), 0.5f);
		this.addLayer(new ShadeShadedFeatureRenderer(this));
	}

	@Override
	public ShadeRenderState createRenderState() {
		return new ShadeRenderState();
	}

	@Override
	public void extractRenderState(ShadeEntity entity, ShadeRenderState state, float partialTick) {
		super.extractRenderState(entity, state, partialTick);
		state.isFallFlying = entity.getFallFlyingTicks() > 4;
		state.isVisuallySwimming = entity.isVisuallySwimming();
		state.isPassenger = entity.isPassenger();
		state.swimAmount = entity.getSwimAmount(partialTick);
		state.velocityLengthSqr = (float) entity.getDeltaMovement().lengthSqr();

		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft.level == null) {
			state.texture = DefaultPlayerSkin.getDefaultTexture();
			return;
		}

		List<? extends AbstractClientPlayer> players = minecraft.level.players();
		if (players.isEmpty()) {
			state.texture = DefaultPlayerSkin.getDefaultTexture();
			return;
		}

		int playerIndex = Math.floorMod(entity.getId(), players.size());
		state.texture = players.get(playerIndex).getSkin().body().texturePath();
	}

	@Override
	public Identifier getTextureLocation(ShadeRenderState state) {
		return state.texture;
	}
}