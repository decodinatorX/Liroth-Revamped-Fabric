package com.decodinator.liroth.client.models.entities;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.core.entities.ForsakenCorpseEntity;
import net.minecraft.resources.Identifier;
import com.geckolib.model.DefaultedEntityGeoModel;

public class ForsakenCorpseModel extends DefaultedEntityGeoModel<ForsakenCorpseEntity> {
    public ForsakenCorpseModel() {
        super(Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "forsaken_corpse/corpse"));
        withAltTexture(Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "forsaken_corpse/forsaken_corpse"));
        withAltAnimations(Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "forsaken_corpse/forsaken"));
    }
}