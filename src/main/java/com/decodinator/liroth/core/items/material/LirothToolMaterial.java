package com.decodinator.liroth.core.items.material;

import com.decodinator.liroth.core.registry.LirothBlocks;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;

public enum LirothToolMaterial implements Tier {
	LIROTHIAN_COBALT(Tiers.IRON.getIncorrectBlocksForDrops(), 2, 320, 7.0F, 1.5F, 12, () -> LirothBlocks.LIROTHIAN_COBALT.ingot),
	TOURMALINE(Tiers.NETHERITE.getIncorrectBlocksForDrops(), 5, 320, 12.0F, 4.0F, 20, () -> LirothBlocks.TOURMALINE.ingot),
	LIROTH(Tiers.NETHERITE.getIncorrectBlocksForDrops(), 5, 2531, 10.0F, 5.0F, 15, () -> LirothBlocks.LIROTH_GEM.ingot),
	ANSALUM_LIROTH(Tiers.NETHERITE.getIncorrectBlocksForDrops(), 5, 2031, 7.0F, 8.0F, 5, () -> LirothBlocks.LIROTH_GEM.ingot),
	LUX_LIROTH(Tiers.NETHERITE.getIncorrectBlocksForDrops(), 5, 2531, 13.0F, 3.0F, 15, () -> LirothBlocks.LIROTH_GEM.ingot),
	SALEM_LIROTH(Tiers.NETHERITE.getIncorrectBlocksForDrops(), 5, 3031, 10.0F, 5.0F, 20, () -> LirothBlocks.LIROTH_GEM.ingot),
	QUANTUM_LIROTH(Tiers.NETHERITE.getIncorrectBlocksForDrops(), 5, 3031, 12.0F, 8.0F, 15, () -> LirothBlocks.LIROTH_GEM.ingot),
	POTESTIUM_LIROTH(Tiers.NETHERITE.getIncorrectBlocksForDrops(), 5, 2531, 16.0F, 6.0F, 30, () -> LirothBlocks.LIROTH_GEM.ingot);

	private final int uses;
	private final float speed;
	private final int level;
	private final int enchantibility;
	private final float damage;
	private final Supplier<ItemLike> reapair;
	public final TagKey<Block> incorrectBlocksForDrops;

	LirothToolMaterial(
			TagKey<Block> incorrectBlocksForDrops,
			int level,
			int uses,
			float speed,
			float damage,
			int enchantibility,
			Supplier<ItemLike> reapair
	) {

		this.incorrectBlocksForDrops = incorrectBlocksForDrops;
		this.uses = uses;
		this.speed = speed;
		this.level = level;
		this.enchantibility = enchantibility;
		this.damage = damage;
		this.reapair = reapair;
	}

	@Override
	public int getUses() {
		return uses;
	}

	@Override
	public float getSpeed() {
		return speed;
	}

	@Override
	public float getAttackDamageBonus() {
		return damage;
	}

	@Override
	public @NotNull TagKey<Block> getIncorrectBlocksForDrops() {
		return this.incorrectBlocksForDrops;
	}

	public int getLevel() {
		return level;
	}

	@Override
	public int getEnchantmentValue() {
		return enchantibility;
	}

	@Override
	public @NotNull Ingredient getRepairIngredient() {
		return Ingredient.of(reapair.get());
	}

}