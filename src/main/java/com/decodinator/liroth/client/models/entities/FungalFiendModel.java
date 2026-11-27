package com.decodinator.liroth.client.models.entities;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.core.entities.FungalFiendEntity;
import net.minecraft.resources.Identifier;
import com.geckolib.model.DefaultedEntityGeoModel;

public class FungalFiendModel extends DefaultedEntityGeoModel<FungalFiendEntity> {
	public FungalFiendModel() {
		super(Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "fungal_fiend/fungal"));
		withAltTexture(Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "fungal_fiend/fungal_fiend"));
		withAltAnimations(Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "fungal_fiend/fungal"));
	}
}