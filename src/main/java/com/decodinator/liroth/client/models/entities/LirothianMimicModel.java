package com.decodinator.liroth.client.models.entities;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.core.entities.LirothianMimicEntity;
import net.minecraft.resources.Identifier;
import com.geckolib.model.DefaultedEntityGeoModel;

public class LirothianMimicModel extends DefaultedEntityGeoModel<LirothianMimicEntity> {
	public LirothianMimicModel() {
		super(Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "lirothian_mimic/mimic"));
		withAltTexture(Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "lirothian_mimic/lirothian_mimic"));
		withAltAnimations(Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "lirothian_mimic/mimic"));
	}
}