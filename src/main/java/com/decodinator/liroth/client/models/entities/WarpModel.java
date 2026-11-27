package com.decodinator.liroth.client.models.entities;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.core.entities.WarpEntity;
import net.minecraft.resources.Identifier;
import com.geckolib.model.DefaultedEntityGeoModel;

public class WarpModel extends DefaultedEntityGeoModel<WarpEntity> {
    public WarpModel() {
        super(Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "warp/warp"));
        withAltTexture(Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "warp/warp"));
        withAltAnimations(Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "warp/warp"));
    }
}