package com.decodinator.liroth.client.rendering.items;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.core.items.PotestiumHelmItem;

import com.geckolib.renderer.base.GeoRenderState;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import com.geckolib.model.DefaultedItemGeoModel;
import com.geckolib.renderer.GeoArmorRenderer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.Item;

import java.util.Set;

// this one was weird cause i am not the surest of what the wornArmor thing does but I also don't wanna fuck anything up lmao

public final class PotestiumHelmetRenderer<R extends HumanoidRenderState & GeoRenderState> extends GeoArmorRenderer<PotestiumHelmItem, R> {
    public PotestiumHelmetRenderer() {
        super(new DefaultedItemGeoModel<>(Liroth.C.mk("armor/potestium_armor_helm")));
    }

    @Override
    public void addRenderData(PotestiumHelmItem animatable, RenderData relatedObject, R renderState, float partialTick) {
        Set<Item> wornArmor = new ObjectOpenHashSet<>(4);
        if (!(relatedObject.entity() instanceof ArmorStand)) {
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                if (slot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR)
                    wornArmor.add(relatedObject.entity().getItemBySlot(slot).getItem());
            }
        }
    }
}