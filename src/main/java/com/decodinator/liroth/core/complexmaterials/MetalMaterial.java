package com.decodinator.liroth.core.complexmaterials;

import com.decodinator.liroth.core.blocks.basis.LirothAnvilBlock;
import com.decodinator.liroth.core.registry.LirothBlocks;
import com.decodinator.liroth.core.registry.LirothItems;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import org.betterx.bclib.blocks.*;
import org.betterx.bclib.items.ModelProviderItem;
import org.betterx.bclib.items.tool.*;
import org.betterx.wover.complex.api.equipment.ArmorSlot;
import org.betterx.wover.complex.api.equipment.ArmorTier;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Supplier;

public class MetalMaterial implements MaterialManager.Material {
    public final Block ore;
    public final Block block;
    public final Block rawBlock;
    public final Block bars;
    public final Block pressurePlate;
    public final Block door;
    public final Block trapdoor;
    public final Block chain;

    public final Block anvilBlock;

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
    public final String name;
    public final int anvilLevel;
    private final Supplier<Properties> itemSettings;

    public static MetalMaterial makeNormal(
            String name,
            MapColor color,
            Tier material,
            Holder<ArmorMaterial> armor,
            ArmorTier tier,
            int anvilLevel
    ) {
        return new MetalMaterial(
                name,
                true,
                true,
                () -> BlockBehaviour.Properties
                        .ofFullCopy(Blocks.IRON_BLOCK)
                        .mapColor(color),
                LirothItems::makeItemSettings,
                material,
                armor,
                tier,
                anvilLevel
        );
    }

    private MetalMaterial(
            String name,
            boolean hasOre,
            boolean hasIngotBlock,
            Supplier<BlockBehaviour.Properties> settingsSupplier,
            Supplier<Properties> itemSettings,
            Tier material,
            Holder<ArmorMaterial> armor,
            ArmorTier tier,
            int anvilLevel
    ) {
        final BlockBehaviour.Properties settings = settingsSupplier.get();

        this.anvilLevel = anvilLevel;
        this.hasOre = hasOre;
        this.hasIngotBlock = hasIngotBlock;
        this.name = name;
        this.itemSettings = itemSettings;
        rawOre = hasOre ? LirothItems.registerItem(name + "_raw", new ModelProviderItem(newItemSettings())) : null;
        ore = hasOre ? LirothBlocks.registerBlock(name + "_ore", new BaseOreBlock(() -> rawOre, 1, 3, 1)) : null;

        rawBlock = LirothBlocks.registerBlock("raw_" + name, new BaseBlock.Metal(settings));

        block = LirothBlocks.registerBlock(name + "_block", new BaseBlock.Metal(settings));

        door = LirothBlocks.registerBlock(name + "_door", new BaseDoorBlock.Metal(block, BlockSetType.IRON));
        trapdoor = LirothBlocks.registerBlock(name + "_trapdoor", new BaseTrapdoorBlock.Metal(block, BlockSetType.IRON));
        bars = LirothBlocks.registerBlock(name + "_bars", new BaseBarsBlock.Metal(block));
        chain = LirothBlocks.registerBlock(name + "_chain", new BaseChainBlock.Metal(block.defaultMapColor()));
        pressurePlate = LirothBlocks.registerBlock(
                name + "_plate",
                new BasePressurePlateBlock.Wood(block, BlockSetType.IRON)
        );

        nugget = LirothItems.registerItem(name + "_nugget", new ModelProviderItem(newItemSettings()));
        ingot = LirothItems.registerItem(name + "_ingot", new ModelProviderItem(newItemSettings()));

        shovel = LirothItems.registerTool(name + "_shovel", new BaseShovelItem(material, 1.5F, -3.0F, newItemSettings()));
        sword = LirothItems.registerTool(name + "_sword", new BaseSwordItem(material, 3, -2.4F, newItemSettings()));
        pickaxe = LirothItems.registerTool(name + "_pickaxe", new BasePickaxeItem(material, 1, -2.8F, newItemSettings()));
        axe = LirothItems.registerTool(name + "_axe", new BaseAxeItem(material, 6.0F, -3.0F, newItemSettings()));
        hoe = LirothItems.registerTool(name + "_hoe", new BaseHoeItem(material, -3, 0.0F, newItemSettings()));

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

        anvilBlock = LirothBlocks.registerBlock(
                name + "_anvil",
                new LirothAnvilBlock(this, block.defaultMapColor(), anvilLevel)
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
