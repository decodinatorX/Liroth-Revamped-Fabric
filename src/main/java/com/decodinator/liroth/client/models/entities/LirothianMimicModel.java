package com.decodinator.liroth.client.models.entities;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.core.entities.FungalFiendEntity;
import com.decodinator.liroth.core.entities.LirothianMimicEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class LirothianMimicModel extends DefaultedEntityGeoModel<LirothianMimicEntity> {
	public LirothianMimicModel() {
		super(ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "lirothian_mimic/mimic"), true);
		withAltTexture(ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "lirothian_mimic/lirothian_mimic"));
		withAltAnimations(ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "lirothian_mimic/mimic"));
	}
}