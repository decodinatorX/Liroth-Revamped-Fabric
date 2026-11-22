package com.decodinator.liroth.client.models.entities;

import com.decodinator.liroth.Liroth;

import com.decodinator.liroth.core.entities.ForsakenCorpseEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class ForsakenCorpseModel<T extends ForsakenCorpseEntity> extends DefaultedEntityGeoModel<ForsakenCorpseEntity> {
    public ForsakenCorpseModel() {
        super(ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "forsaken_corpse/corpse"));
    }

    @Override
    public ResourceLocation getModelResource(ForsakenCorpseEntity object) {
        return ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "geo/forsaken_corpse/corpse.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ForsakenCorpseEntity object) {
        return ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "textures/entity/forsaken_corpse/forsaken_corpse.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ForsakenCorpseEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "animations/forsaken_corpse/forsaken.animation.json");
    }
}
