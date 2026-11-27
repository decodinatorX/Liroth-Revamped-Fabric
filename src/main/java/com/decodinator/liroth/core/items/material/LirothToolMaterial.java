package com.decodinator.liroth.core.items.material;

import com.decodinator.liroth.core.registry.LirothBlocks;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.component.Weapon;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.List;
import java.util.function.Supplier;

public enum LirothToolMaterial {
	LIROTHIAN_COBALT(BlockTags.INCORRECT_FOR_IRON_TOOL, 320, 7.0F, 1.5F, 12, 2, () -> LirothBlocks.LIROTHIAN_COBALT.ingot),
	TOURMALINE(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 320, 12.0F, 4.0F, 20, 5, () -> LirothBlocks.TOURMALINE.ingot),
	RUBY(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1561, 8.0F, 3.0F, 10, 3, () -> LirothBlocks.RUBY.ingot),
	LIROTH(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 2531, 10.0F, 5.0F, 15, 5, () -> LirothBlocks.LIROTH_GEM.ingot),
	ANSALUM_LIROTH(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 2031, 7.0F, 8.0F, 5, 5, () -> LirothBlocks.LIROTH_GEM.ingot),
	LUX_LIROTH(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 2531, 13.0F, 3.0F, 15, 5, () -> LirothBlocks.LIROTH_GEM.ingot),
	SALEM_LIROTH(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 3031, 10.0F, 5.0F, 20, 5, () -> LirothBlocks.LIROTH_GEM.ingot),
	QUANTUM_LIROTH(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 3031, 12.0F, 8.0F, 15, 5, () -> LirothBlocks.LIROTH_GEM.ingot),
	POTESTIUM_LIROTH(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 2531, 16.0F, 6.0F, 30, 5, () -> LirothBlocks.LIROTH_GEM.ingot);

	private final TagKey<Block> incorrectBlocksForDrops;
	private final int durability;
	private final float speed;
	private final float attackDamageBonus;
	private final int enchantmentValue;
	private final int level;
	private final Supplier<Item> repairItem;

	LirothToolMaterial(
			TagKey<Block> incorrectBlocksForDrops,
			int durability,
			float speed,
			float attackDamageBonus,
			int enchantmentValue,
			int level,
			Supplier<Item> repairItem
	) {
		this.incorrectBlocksForDrops = incorrectBlocksForDrops;
		this.durability = durability;
		this.speed = speed;
		this.attackDamageBonus = attackDamageBonus;
		this.enchantmentValue = enchantmentValue;
		this.level = level;
		this.repairItem = repairItem;
	}

	public int level() {
		return level;
	}

	public Item repairItem() {
		return repairItem.get();
	}

	public int getUses() {
		return durability;
	}

	public float getSpeed() {
		return speed;
	}

	public float getAttackDamageBonus() {
		return attackDamageBonus;
	}

	public TagKey<Block> getIncorrectBlocksForDrops() {
		return incorrectBlocksForDrops;
	}

	public int getEnchantmentValue() {
		return enchantmentValue;
	}

	public Item.Properties applyToolProperties(
			Properties properties,
			TagKey<Block> minesEfficiently,
			float attackDamageBaseline,
			float attackSpeedBaseline,
			float disableBlockingSeconds
	) {
		return applyToolProperties(properties, minesEfficiently, attackDamageBaseline, attackSpeedBaseline,
				disableBlockingSeconds, repairItem.get());
	}

	public Item.Properties applyToolProperties(
			Properties properties,
			TagKey<Block> minesEfficiently,
			float attackDamageBaseline,
			float attackSpeedBaseline,
			float disableBlockingSeconds,
			Item repairItem
	) {
		HolderGetter<Block> lookup = BuiltInRegistries.acquireBootstrapRegistrationLookup(BuiltInRegistries.BLOCK);
		return applyCommonProperties(properties, repairItem)
				.component(
						DataComponents.TOOL,
						new Tool(
								List.of(
										Tool.Rule.deniesDrops(lookup.getOrThrow(incorrectBlocksForDrops)),
										Tool.Rule.minesAndDrops(lookup.getOrThrow(minesEfficiently), speed)
								),
								1.0F,
								1,
								true
						)
				)
				.attributes(createToolAttributes(attackDamageBaseline, attackSpeedBaseline))
				.component(DataComponents.WEAPON, new Weapon(2, disableBlockingSeconds));
	}

	public Item.Properties applySwordProperties(
			Properties properties,
			float attackDamageBaseline,
			float attackSpeedBaseline
	) {
		return applySwordProperties(properties, attackDamageBaseline, attackSpeedBaseline, repairItem.get());
	}

	public Item.Properties applySwordProperties(
			Properties properties,
			float attackDamageBaseline,
			float attackSpeedBaseline,
			Item repairItem
	) {
		HolderGetter<Block> lookup = BuiltInRegistries.acquireBootstrapRegistrationLookup(BuiltInRegistries.BLOCK);
		return applyCommonProperties(properties, repairItem)
				.component(
						DataComponents.TOOL,
						new Tool(
								List.of(
										Tool.Rule.minesAndDrops(HolderSet.direct(Blocks.COBWEB.builtInRegistryHolder()), 15.0F),
										Tool.Rule.overrideSpeed(lookup.getOrThrow(BlockTags.SWORD_INSTANTLY_MINES), Float.MAX_VALUE),
										Tool.Rule.overrideSpeed(lookup.getOrThrow(BlockTags.SWORD_EFFICIENT), 1.5F)
								),
								1.0F,
								2,
								false
						)
				)
				.attributes(createToolAttributes(attackDamageBaseline, attackSpeedBaseline))
				.component(DataComponents.WEAPON, new Weapon(1));
	}

	private Properties applyCommonProperties(Properties properties, Item repairItem) {
		return properties
				.durability(durability)
				.repairable(repairItem)
				.enchantable(enchantmentValue);
	}

	private ItemAttributeModifiers createToolAttributes(float attackDamageBaseline, float attackSpeedBaseline) {
		return ItemAttributeModifiers.builder()
				.add(
						Attributes.ATTACK_DAMAGE,
						new AttributeModifier(
								Item.BASE_ATTACK_DAMAGE_ID,
								attackDamageBaseline + attackDamageBonus,
								AttributeModifier.Operation.ADD_VALUE
						),
						EquipmentSlotGroup.MAINHAND
				)
				.add(
						Attributes.ATTACK_SPEED,
						new AttributeModifier(
								Item.BASE_ATTACK_SPEED_ID,
								attackSpeedBaseline,
								AttributeModifier.Operation.ADD_VALUE
						),
						EquipmentSlotGroup.MAINHAND
				)
				.build();
	}

	public int getLevel() {
		return level;
	}
}