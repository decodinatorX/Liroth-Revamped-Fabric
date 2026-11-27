package com.decodinator.liroth.client.models.entities;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.core.entities.ProwlerEntity;
import net.minecraft.resources.Identifier;
import com.geckolib.model.DefaultedEntityGeoModel;

public class ProwlerModel extends DefaultedEntityGeoModel<ProwlerEntity> {
    public ProwlerModel() {
        super(Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "prowler/prowler"));
        withAltTexture(Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "prowler/prowler"));
        withAltAnimations(Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "prowler/prowler"));
    }
}