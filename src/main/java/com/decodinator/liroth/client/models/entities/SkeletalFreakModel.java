package com.decodinator.liroth.client.models.entities;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.core.entities.SkeletalFreakEntity;
import net.minecraft.resources.Identifier;
import com.geckolib.model.DefaultedEntityGeoModel;

public class SkeletalFreakModel extends DefaultedEntityGeoModel<SkeletalFreakEntity> {
	public SkeletalFreakModel() {
		super(Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "skeletal_freak/skeletal"));
		withAltTexture(Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "skeletal_freak/skeletal_freak"));
		withAltAnimations(Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "skeletal_freak/skeletal"));
	}
}