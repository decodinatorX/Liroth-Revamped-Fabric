package com.decodinator.liroth.core.items.material;

import net.minecraft.world.item.ArmorMaterials;
import org.betterx.wover.complex.api.equipment.ArmorTier;
import org.betterx.wover.complex.api.equipment.ArmorTiers;

public class LirothArmorTier {
    public static ArmorTier LIROTH = ArmorTier
            .builder("liroth")
            .armorMaterial(LirothArmorMaterial.LIROTH)
            .armorValuesWithOffset(ArmorTiers.NETHERITE_ARMOR, new ArmorTier.ArmorValues(37))
            .build();
    public static ArmorTier LIROTHIAN_COBALT = ArmorTier
            .builder("lirothian_cobalt")
            .armorMaterial(LirothArmorMaterial.LIROTHIAN_COBALT)
            .armorValuesWithOffset(ArmorTiers.IRON_ARMOR, new ArmorTier.ArmorValues(15))
            .build();
    public static ArmorTier TOURMALINE = ArmorTier
            .builder("tourmaline")
            .armorMaterial(LirothArmorMaterial.TOURMALINE)
            .armorValuesWithOffset(ArmorTiers.NETHERITE_ARMOR, new ArmorTier.ArmorValues(15))
            .build();
    public static ArmorTier RUBY = ArmorTier
            .builder("ruby")
            .armorMaterial(LirothArmorMaterial.RUBY)
            .armorValuesWithOffset(ArmorTiers.DIAMOND_ARMOR, new ArmorTier.ArmorValues(33))
            .build();
    public static ArmorTier LIROTH_ANSALUM = ArmorTier
            .builder("ansalum_liroth")
            .armorMaterial(LirothArmorMaterial.LIROTH_ANSALUM)
            .armorValuesWithOffset(ArmorTiers.NETHERITE_ARMOR, new ArmorTier.ArmorValues(37))
            .build();
    public static ArmorTier LIROTH_LUX = ArmorTier
            .builder("lux_liroth")
            .armorMaterial(LirothArmorMaterial.LIROTH_LUX)
            .armorValuesWithOffset(ArmorTiers.NETHERITE_ARMOR, new ArmorTier.ArmorValues(37))
            .build();
    public static ArmorTier LIROTH_SALEM = ArmorTier
            .builder("salem_liroth")
            .armorMaterial(LirothArmorMaterial.LIROTH_SALEM)
            .armorValuesWithOffset(ArmorTiers.NETHERITE_ARMOR, new ArmorTier.ArmorValues(37))
            .build();
    public static ArmorTier QUANTUM_LIROTH_ARMOR = ArmorTier
            .builder("quantum_liroth")
            .armorMaterial(LirothArmorMaterial.LIROTH)
            .armorValuesWithOffset(ArmorTiers.NETHERITE_ARMOR, new ArmorTier.ArmorValues(50))
            .build();
    public static ArmorTier POTESTIUM_LIROTH_ARMOR = ArmorTier
            .builder("potestium_liroth")
            .armorMaterial(LirothArmorMaterial.LIROTH)
            .armorValuesWithOffset(ArmorTiers.NETHERITE_ARMOR, new ArmorTier.ArmorValues(50))
            .build();
}