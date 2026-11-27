package com.decodinator.liroth.core.complexmaterials;

import com.decodinator.liroth.core.items.LirothArmorItem;
import com.decodinator.liroth.core.items.material.LirothToolMaterial;
import com.decodinator.liroth.core.registry.LirothBlocks;
import com.decodinator.liroth.core.registry.LirothItems;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import org.betterx.bclib.behaviours.BehaviourBuilders;
import org.betterx.bclib.blocks.*;
import org.betterx.bclib.items.ModelProviderItem;
import org.betterx.bclib.items.tool.*;
import org.betterx.wover.complex.api.equipment.ArmorSlot;
import org.betterx.wover.complex.api.equipment.ArmorTier;

import java.util.function.Supplier;

public class GemMaterial implements MaterialManager.Material {
    public final Block ore;
    public final Block block;

    public final Item rawOre;
    public final Item nugget;
    public final Item ingot;

    public final Item shovel;
    public final Item sword;
    public final Item pickaxe;
    public final Item axe;
    public final Item hoe;

    public final Item helmet;
    public final Item chestplate;
    public final Item leggings;
    public final Item boots;

    public final boolean hasOre;
    public final boolean hasIngotBlock;
    public final boolean hasNugget;
    public final boolean hasRaw;
    public final String name;
    public final int anvilLevel;
    private final Supplier<Properties> itemSettings;

    public static GemMaterial makeNormal(
            String name,
            MapColor color,
            LirothToolMaterial material,
            ArmorMaterial armMat,
            ArmorTier armor,
            int anvilLevel
    ) {
        return makeNormal(name, color, material, armMat, armor, anvilLevel, SoundType.STONE);
    }

    public static GemMaterial makeNormal(
            String name,
            MapColor color,
            LirothToolMaterial material,
            ArmorMaterial armMat,
            ArmorTier armor,
            int anvilLevel,
            SoundType oreSound
    ) {
        return new GemMaterial(
                name,
                true,
                true,
                false,
                false,
                () -> BlockBehaviour.Properties
                        .ofFullCopy(Blocks.IRON_BLOCK)
                        .mapColor(color),
                LirothItems::makeItemSettings,
                material,
                armMat,
                armor,
                anvilLevel,
                oreSound
        );
    }

    public static GemMaterial makeLiroth(
            String name,
            MapColor color,
            LirothToolMaterial material,
            ArmorMaterial armMat,
            ArmorTier armor,
            int anvilLevel
    ) {
        return new GemMaterial(
                name,
                true,
                false,
                true,
                true,
                () -> BlockBehaviour.Properties
                        .ofFullCopy(Blocks.NETHERITE_BLOCK)
                        .mapColor(color),
                LirothItems::makeItemSettings,
                material,
                armMat,
                armor,
                anvilLevel,
                SoundType.STONE
        );
    }

    public static GemMaterial makeOreless(
            String name,
            MapColor color,
            float hardness,
            float resistance,
            LirothToolMaterial material,
            ArmorMaterial armMat,
            ArmorTier armor,
            int anvilLevel
    ) {
        return new GemMaterial(
                name,
                false,
                true,
                true,
                false,
                () -> BlockBehaviour.Properties
                        .ofFullCopy(Blocks.IRON_BLOCK)
                        .mapColor(color)
                        .destroyTime(hardness)
                        .explosionResistance(resistance),
                LirothItems::makeItemSettings,
                material,
                armMat,
                armor,
                anvilLevel,
                SoundType.STONE
        );
    }

    private GemMaterial(
            String name,
            boolean hasOre,
            boolean hasIngotBlock,
            boolean hasNugget,
            boolean hasRaw,
            Supplier<BlockBehaviour.Properties> settingsSupplier,
            Supplier<Properties> itemSettings,
            LirothToolMaterial material,
            ArmorMaterial armMat,
            ArmorTier armor,
            int anvilLevel,
            SoundType oreSound
    ) {
        final BlockBehaviour.Properties settings = settingsSupplier.get();

        this.anvilLevel = anvilLevel;
        this.hasOre = hasOre;
        this.hasIngotBlock = hasIngotBlock;
        this.name = name;
        this.itemSettings = itemSettings;
        this.hasRaw = hasRaw;
        this.hasNugget = hasNugget;
        ingot = LirothItems.registerItem(name + "_ingot", () -> new ModelProviderItem(newItemSettings()));

        rawOre = hasRaw ? LirothItems.registerItem(name + "_raw", () -> new ModelProviderItem(newItemSettings())) : null;
        ore = hasOre ? LirothBlocks.registerBlock(name + "_ore", () -> (new BaseOreBlock(BehaviourBuilders.createStone(MapColor.COLOR_BLACK).requiresCorrectToolForDrops().destroyTime(3.0F).explosionResistance(9.0F).sound(oreSound), () -> ingot, 1, 3, 1))) : null;

        block = hasIngotBlock ? LirothBlocks.registerBlock(name + "_block", () -> (new BaseBlock.Metal(settings))) : LirothBlocks.LIROTH_GEM_BLOCK;

        nugget = hasNugget ? LirothItems.registerItem(name + "_nugget", () -> new ModelProviderItem(newItemSettings())) : null;

        shovel = LirothItems.registerTool(name + "_shovel", material, BlockTags.MINEABLE_WITH_SHOVEL, 1.5F, -3.0F, newItemSettings(), ingot);
        sword = LirothItems.registerSwordTool(name + "_sword", material, 3.0F, -2.4F, newItemSettings(), ingot);
        pickaxe = LirothItems.registerTool(name + "_pickaxe", material, BlockTags.MINEABLE_WITH_PICKAXE, 1.0F, -2.8F, newItemSettings(), ingot);
        axe = LirothItems.registerTool(name + "_axe", material, BlockTags.MINEABLE_WITH_AXE, 6.0F, -3.0F, newItemSettings(), ingot);
        hoe = LirothItems.registerTool(name + "_hoe", material, BlockTags.MINEABLE_WITH_HOE, -3.0F, 0.0F, newItemSettings(), ingot);

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
