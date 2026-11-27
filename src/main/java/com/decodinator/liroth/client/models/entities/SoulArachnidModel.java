package com.decodinator.liroth.client.models.entities;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.core.entities.SoulArachnidEntity;
import net.minecraft.resources.Identifier;
import com.geckolib.model.DefaultedEntityGeoModel;

public class SoulArachnidModel extends DefaultedEntityGeoModel<SoulArachnidEntity> {
	public SoulArachnidModel() {
		super(Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "soul_arachnid/soul_arachnid"));
		withAltTexture(Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "soul_arachnid/soul_arachnid"));
		withAltAnimations(Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "soul_arachnid/soul_arachnid"));
	}
}