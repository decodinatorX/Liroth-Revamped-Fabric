package com.decodinator.liroth.core.registry;

import com.decodinator.liroth.Liroth;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

@Environment(value=EnvType.CLIENT)
public class LirothModelLayers {

	public static final ModelLayerLocation BEAM_LASER_PROJECTILE = make("beam_laser_projectile");

	public static final ModelLayerLocation FREAKSHOW = make("freakshow");
	public static final ModelLayerLocation PIER_PEEP = make("pier_peep");
	public static final ModelLayerLocation SHADE = make("shade");
	public static final ModelLayerLocation VILE_SHARK = make("vile_shark");

	private static ModelLayerLocation make(String name) {
		return make(name, "main");
	}

	private static ModelLayerLocation make(String name, String layer) {
		return new ModelLayerLocation(prefix(name), layer);
	}
	
	public static ResourceLocation prefix(String path) {
		return ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, path);
	}

	public static void init() {}
}