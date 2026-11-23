
package com.decodinator.liroth.core.complexmaterials;

import com.decodinator.liroth.core.registry.LirothItems;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Tier;
import org.betterx.bclib.items.ModelProviderItem;
import org.betterx.bclib.items.tool.*;
import org.betterx.wover.complex.api.equipment.ArmorSlot;
import org.betterx.wover.complex.api.equipment.ArmorTier;

import java.util.function.Supplier;

public class GemFragMaterial implements MaterialManager.Material {
    public final Item ingot;

    public final Item sword;
    public final Item axe;

    public final Item helmet;
    public final Item chestplate;
    public final Item leggings;
    public final Item boots;

    public final boolean hasSword;
    public final boolean hasAxe;
    public final String name;
    public final int anvilLevel;
    private final Supplier<Properties> itemSettings;

    public static GemFragMaterial makeNormal(
            String name,
            Tier material,
            Holder<ArmorMaterial> armor,
            ArmorTier tier,
            int anvilLevel
    ) {
        return new GemFragMaterial(
                name,
                false,
                true,
                LirothItems::makeItemSettings,
                material,
                armor,
                tier,
                anvilLevel
        );
    }

    public static GemFragMaterial makeAnsalum(
            String name,
            Tier material,
            Holder<ArmorMaterial> armor,
            ArmorTier tier,
            int anvilLevel
    ) {
        return new GemFragMaterial(
                name,
                true,
                false,
                LirothItems::makeItemSettings,
                material,
                armor,
                tier,
                anvilLevel
        );
    }

    private GemFragMaterial(
            String name,
            boolean hasAxe,
            boolean hasSword,
            Supplier<Properties> itemSettings,
            Tier material,
            Holder<ArmorMaterial> armor,
            ArmorTier tier,
            int anvilLevel
    ) {

        this.anvilLevel = anvilLevel;
        this.hasAxe = hasAxe;
        this.hasSword = hasSword;
        this.name = name;
        this.itemSettings = itemSettings;

        ingot = LirothItems.registerItem(name + "_ingot", new ModelProviderItem(newItemSettings()));

        sword = hasSword ? LirothItems.registerTool(name + "_sword", new BaseSwordItem(material, 3, -2.4F, newItemSettings())) : null;
        axe = hasAxe ? LirothItems.registerTool(name + "_axe", new BaseAxeItem(material, 6.0F, -3.0F, newItemSettings())) : null;

        helmet = LirothItems.registerArmorItem(
                name + "_helmet",
                new ArmorItem(armor, ArmorItem.Type.HELMET, newArmorSettings(ArmorSlot.HELMET_SLOT, tier))
        );
        chestplate = LirothItems.registerArmorItem(
                name + "_chestplate",
                new ArmorItem(armor, ArmorItem.Type.CHESTPLATE, newArmorSettings(ArmorSlot.CHESTPLATE_SLOT, tier))
        );
        leggings = LirothItems.registerArmorItem(
                name + "_leggings",
                new ArmorItem(armor, ArmorItem.Type.LEGGINGS, newArmorSettings(ArmorSlot.LEGGINGS_SLOT, tier))
        );
        boots = LirothItems.registerArmorItem(
                name + "_boots",
                new ArmorItem(armor, ArmorItem.Type.BOOTS, newArmorSettings(ArmorSlot.BOOTS_SLOT, tier))
        );

        MaterialManager.register(this);
    }

    private Properties newItemSettings() {
        return itemSettings.get();
    }

    private Properties newArmorSettings(ArmorSlot slot, ArmorTier tier) {
        var values = tier.getValues(slot);
        if (values == null) {
            throw new IllegalArgumentException("Values for " + slot + " are not defined for " + tier);
        }
        return newItemSettings().durability(slot.armorType.getDurability(values.durability()));
    }

}
