package com.decodinator.liroth.core.items.material;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.core.registry.LirothBlocks;
import com.decodinator.liroth.core.registry.LirothItems;
import org.betterx.wover.item.api.armor.CustomArmorMaterial;

import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

public class LirothArmorMaterial {
	public static final Holder<ArmorMaterial> LIROTH = CustomArmorMaterial
			.start(Liroth.C.mk("liroth"))
			.defense(5, 8, 10, 5, 13)
			.enchantmentValue(15)
			.equipSound(SoundEvents.ARMOR_EQUIP_NETHERITE)
			.toughness(3.0f)
			.knockbackResistance(0.1f)
			.repairIngredientSupplier(() -> Ingredient.of(LirothBlocks.LIROTH_GEM.ingot))
			.buildAndRegister();
	public static final Holder<ArmorMaterial> LIROTHIAN_COBALT = CustomArmorMaterial
			.start(Liroth.C.mk("lirothian_cobalt"))
			.defense(2, 5, 6, 2, 5)
			.enchantmentValue(9)
			.equipSound(SoundEvents.ARMOR_EQUIP_IRON)
			.toughness(0.0f)
			.knockbackResistance(0f)
			.repairIngredientSupplier(() -> Ingredient.of(LirothBlocks.LIROTHIAN_COBALT.ingot))
			.buildAndRegister();
	public static final Holder<ArmorMaterial> LIROTH_ANSALUM = CustomArmorMaterial
			.start(Liroth.C.mk("liroth_gem_ansalum"))
			.defense(5, 8, 10, 5, 13)
			.enchantmentValue(5)
			.equipSound(SoundEvents.ARMOR_EQUIP_NETHERITE)
			.toughness(5.0f)
			.knockbackResistance(0.2f)
			.repairIngredientSupplier(() -> Ingredient.of(LirothBlocks.LIROTH_GEM.ingot))
			.buildAndRegister();
	public static final Holder<ArmorMaterial> LIROTH_LUX = CustomArmorMaterial
			.start(Liroth.C.mk("liroth_gem_lux"))
			.defense(4, 7, 9, 4, 10)
			.enchantmentValue(30)
			.equipSound(SoundEvents.ARMOR_EQUIP_NETHERITE)
			.toughness(1.0f)
			.knockbackResistance(0.05f)
			.repairIngredientSupplier(() -> Ingredient.of(LirothBlocks.LIROTH_GEM.ingot))
			.buildAndRegister();
	public static final Holder<ArmorMaterial> LIROTH_SALEM = CustomArmorMaterial
			.start(Liroth.C.mk("liroth_gem_salem"))
			.defense(5, 8, 10, 5, 14)
			.enchantmentValue(12)
			.equipSound(SoundEvents.ARMOR_EQUIP_NETHERITE)
			.toughness(2.5f)
			.knockbackResistance(0.075f)
			.repairIngredientSupplier(() -> Ingredient.of(LirothBlocks.LIROTH_GEM.ingot))
			.buildAndRegister();
	public static final Holder<ArmorMaterial> TOURMALINE = CustomArmorMaterial
			.start(Liroth.C.mk("tourmaline"))
			.defense(4, 7, 9, 4, 12)
			.enchantmentValue(18)
			.equipSound(SoundEvents.ARMOR_EQUIP_GOLD)
			.toughness(2.0f)
			.knockbackResistance(0.1f)
			.repairIngredientSupplier(() -> Ingredient.of(LirothBlocks.TOURMALINE.ingot))
			.buildAndRegister();
	public static final Holder<ArmorMaterial> RUBY = CustomArmorMaterial
			.start(Liroth.C.mk("ruby"))
			.defense(4, 7, 9, 4, 12)
			.enchantmentValue(15)
			.equipSound(SoundEvents.ARMOR_EQUIP_DIAMOND)
			.toughness(1.0f)
			.knockbackResistance(0.0f)
			.repairIngredientSupplier(() -> Ingredient.of(LirothBlocks.RUBY.ingot))
			.buildAndRegister();
	public static final Holder<ArmorMaterial> QUANTUM_LIROTH = CustomArmorMaterial
			.start(Liroth.C.mk("quantum_liroth"))
			.defense(7, 10, 12, 7, 15)
			.enchantmentValue(11)
			.equipSound(SoundEvents.ARMOR_EQUIP_NETHERITE)
			.toughness(2.0f)
			.knockbackResistance(0.0f)
			.repairIngredientSupplier(() -> Ingredient.of(LirothItems.QUANTUM_PLATE))
			.buildAndRegister();
	public static final Holder<ArmorMaterial> POTESTIUM_LIROTH = CustomArmorMaterial
			.start(Liroth.C.mk("potestium_liroth"))
			.defense(6, 9, 11, 6, 14)
			.enchantmentValue(21)
			.equipSound(SoundEvents.ARMOR_EQUIP_NETHERITE)
			.toughness(4.0f)
			.knockbackResistance(0.3f)
			.repairIngredientSupplier(() -> Ingredient.of(LirothItems.POTESTIUM_PLATE))
			.buildAndRegister();
}