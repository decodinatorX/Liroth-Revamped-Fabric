
package com.decodinator.liroth.core.complexmaterials;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.core.items.LirothArmorItem;
import com.decodinator.liroth.core.registry.LirothBlocks;
import com.decodinator.liroth.core.registry.LirothItems;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import org.betterx.bclib.blocks.BaseBlock;
import org.betterx.bclib.blocks.BaseOreBlock;
import org.betterx.bclib.items.ModelProviderItem;
import org.betterx.bclib.items.tool.*;
import org.betterx.wover.complex.api.equipment.ArmorSlot;
import org.betterx.wover.complex.api.equipment.ArmorTier;
import org.betterx.wover.recipe.api.RecipeBuilder;
import org.betterx.wover.tag.api.event.context.ItemTagBootstrapContext;
import org.betterx.wover.tag.api.event.context.TagBootstrapContext;

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
            Tier material,
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
                armor,
                anvilLevel
        );
    }

    public static GemFragMaterial makeAnsalum(
            String name,
            MapColor color,
            Tier material,
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
                armor,
                anvilLevel
        );
    }

    public static GemFragMaterial makeOreless(
            String name,
            MapColor color,
            float hardness,
            float resistance,
            Tier material,
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
            Tier material,
            ArmorTier armor,
            int anvilLevel
    ) {
        final BlockBehaviour.Properties settings = settingsSupplier.get();

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
                new LirothArmorItem(armor, ArmorSlot.HELMET_SLOT, newArmorSettings(ArmorSlot.HELMET_SLOT, armor))
        );
        chestplate = LirothItems.registerArmorItem(
                name + "_chestplate",
                new LirothArmorItem(armor, ArmorSlot.CHESTPLATE_SLOT, newArmorSettings(ArmorSlot.CHESTPLATE_SLOT, armor))
        );
        leggings = LirothItems.registerArmorItem(
                name + "_leggings",
                new LirothArmorItem(armor, ArmorSlot.LEGGINGS_SLOT, newArmorSettings(ArmorSlot.LEGGINGS_SLOT, armor))
        );
        boots = LirothItems.registerArmorItem(
                name + "_boots",
                new LirothArmorItem(armor, ArmorSlot.BOOTS_SLOT, newArmorSettings(ArmorSlot.BOOTS_SLOT, armor))
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

    @Override
    public void registerRecipes(RecipeOutput context) {

        // Tool crafting
        if (hasSword) {
            RecipeBuilder.crafting(Liroth.C.mk(name + "_sword"), sword)
                    .shape(" # ", " # ", " ! ")
                    .addMaterial('#', ingot)
                    .addMaterial('!', Items.STICK)
                    .group("liroth_swords")
                    .build(context);
        }
        if (hasAxe) {
            RecipeBuilder.crafting(Liroth.C.mk(name + "_axe"), axe)
                    .shape("## ", "#! ", " ! ")
                    .addMaterial('#', ingot)
                    .addMaterial('!', Items.STICK)
                    .group("liroth_axes")
                    .build(context);
        }

        // Armor crafting
        RecipeBuilder.crafting(Liroth.C.mk(name + "_helmet"), helmet)
                .shape("###", "# #")
                .addMaterial('#', ingot)
                .group("liroth_helmets")
                .build(context);
        RecipeBuilder.crafting(Liroth.C.mk(name + "_chestplate"), chestplate)
                .shape("# #", "###", "###")
                .addMaterial('#', ingot)
                .group("liroth_chestplates")
                .build(context);
        RecipeBuilder.crafting(Liroth.C.mk(name + "_leggings"), leggings)
                .shape("###", "# #", "# #")
                .addMaterial('#', ingot)
                .group("liroth_leggings")
                .build(context);
        RecipeBuilder.crafting(Liroth.C.mk(name + "_boots"), boots)
                .shape("# #", "# #")
                .addMaterial('#', ingot)
                .group("liroth_boots")
                .build(context);

    }

    @Override
    public void registerBlockTags(TagBootstrapContext<Block> context) {
    }

    @Override
    public void registerItemTags(ItemTagBootstrapContext context) {
    }
}
