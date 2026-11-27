
package com.decodinator.liroth.core.complexmaterials;

import com.decodinator.liroth.core.items.LirothArmorItem;
import com.decodinator.liroth.core.items.material.LirothToolMaterial;
import com.decodinator.liroth.core.registry.LirothItems;
import net.minecraft.core.Holder;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import org.betterx.bclib.items.ModelProviderItem;
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
            MapColor color,
            LirothToolMaterial material,
            ArmorMaterial armMat,
            ArmorTier armor,
            int anvilLevel
    ) {
        return new GemFragMaterial(
                name,
                false,
                true,
                () -> BlockBehaviour.Properties
                        .ofFullCopy(Blocks.IRON_BLOCK)
                        .mapColor(color),
                LirothItems::makeItemSettings,
                material,
                armMat,
                armor,
                anvilLevel
        );
    }

    public static GemFragMaterial makeAnsalum(
            String name,
            MapColor color,
            LirothToolMaterial material,
            ArmorMaterial armMat,
            ArmorTier armor,
            int anvilLevel
    ) {
        return new GemFragMaterial(
                name,
                true,
                false,
                () -> BlockBehaviour.Properties
                        .ofFullCopy(Blocks.NETHERITE_BLOCK)
                        .mapColor(color),
                LirothItems::makeItemSettings,
                material,
                armMat,
                armor,
                anvilLevel
        );
    }

    public static GemFragMaterial makeOreless(
            String name,
            MapColor color,
            float hardness,
            float resistance,
            LirothToolMaterial material,
            ArmorMaterial armMat,
            ArmorTier armor,
            int anvilLevel
    ) {
        return new GemFragMaterial(
                name,
                false,
                true,
                () -> BlockBehaviour.Properties
                        .ofFullCopy(Blocks.IRON_BLOCK)
                        .mapColor(color)
                        .destroyTime(hardness)
                        .explosionResistance(resistance),
                LirothItems::makeItemSettings,
                material,
                armMat,
                armor,
                anvilLevel
        );
    }

    private GemFragMaterial(
            String name,
            boolean hasAxe,
            boolean hasSword,
            Supplier<BlockBehaviour.Properties> settingsSupplier,
            Supplier<Properties> itemSettings,
            LirothToolMaterial material,
            ArmorMaterial armMat,
            ArmorTier armor,
            int anvilLevel
    ) {

        this.anvilLevel = anvilLevel;
        this.hasAxe = hasAxe;
        this.hasSword = hasSword;
        this.name = name;
        this.itemSettings = itemSettings;

        ingot = LirothItems.registerItem(name + "_ingot", () -> new ModelProviderItem(newItemSettings()));

        sword = hasSword ? LirothItems.registerSwordTool(name + "_sword", material, 3.0F, -2.4F, newItemSettings(), ingot) : null;
        axe = hasAxe ? LirothItems.registerTool(name + "_axe", material, BlockTags.MINEABLE_WITH_AXE, 6.0F, -3.0F, newItemSettings(), ingot) : null;

        helmet = LirothItems.registerArmorItem(name + "_helmet", () -> new LirothArmorItem(armor, ArmorSlot.HELMET_SLOT, newArmorSettings(ArmorSlot.HELMET_SLOT, armor).humanoidArmor(armMat, ArmorType.HELMET)));
        chestplate = LirothItems.registerArmorItem(name + "_chestplate", () -> new LirothArmorItem(armor, ArmorSlot.CHESTPLATE_SLOT, newArmorSettings(ArmorSlot.CHESTPLATE_SLOT, armor).humanoidArmor(armMat, ArmorType.CHESTPLATE)));
        leggings = LirothItems.registerArmorItem(name + "_leggings", () -> new LirothArmorItem(armor, ArmorSlot.LEGGINGS_SLOT, newArmorSettings(ArmorSlot.LEGGINGS_SLOT, armor).humanoidArmor(armMat, ArmorType.LEGGINGS)));
        boots = LirothItems.registerArmorItem(name + "_boots", () -> new LirothArmorItem(armor, ArmorSlot.BOOTS_SLOT, newArmorSettings(ArmorSlot.BOOTS_SLOT, armor).humanoidArmor(armMat, ArmorType.BOOTS)));

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
