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
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import org.betterx.bclib.behaviours.BehaviourBuilders;
import org.betterx.bclib.blocks.*;
import org.betterx.bclib.items.ModelProviderItem;
import org.betterx.bclib.items.tool.*;
import org.betterx.wover.complex.api.equipment.ArmorSlot;
import org.betterx.wover.complex.api.equipment.ArmorTier;
import org.betterx.wover.recipe.api.RecipeBuilder;
import org.betterx.wover.tag.api.event.context.ItemTagBootstrapContext;
import org.betterx.wover.tag.api.event.context.TagBootstrapContext;

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
            Tier material,
            ArmorTier armor,
            int anvilLevel
    ) {
        return makeNormal(name, color, material, armor, anvilLevel, SoundType.STONE);
    }

    public static GemMaterial makeNormal(
            String name,
            MapColor color,
            Tier material,
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
                armor,
                anvilLevel,
                oreSound
        );
    }

    public static GemMaterial makeLiroth(
            String name,
            MapColor color,
            Tier material,
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
            Tier material,
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
            Tier material,
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
        ingot = LirothItems.registerItem(name + "_ingot", new ModelProviderItem(newItemSettings()));

        rawOre = hasRaw ? LirothItems.registerItem(name + "_raw", new ModelProviderItem(newItemSettings())) : null;
        ore = hasOre ? LirothBlocks.registerBlock(name + "_ore", new BaseOreBlock(BehaviourBuilders.createStone(MapColor.COLOR_BLACK).requiresCorrectToolForDrops().destroyTime(3.0F).explosionResistance(9.0F).sound(oreSound), () -> ingot, 1, 3, 1)) : null;

        block = hasIngotBlock ? LirothBlocks.registerBlock(name + "_block", new BaseBlock.Metal(settings)) : LirothBlocks.LIROTH_GEM_BLOCK;

        nugget = hasNugget ? LirothItems.registerItem(name + "_nugget", new ModelProviderItem(newItemSettings())) : null;

        shovel = LirothItems.registerTool(name + "_shovel", new BaseShovelItem(material, 1.5F, -3.0F, newItemSettings()));
        sword = LirothItems.registerTool(name + "_sword", new BaseSwordItem(material, 3, -2.4F, newItemSettings()));
        pickaxe = LirothItems.registerTool(name + "_pickaxe", new BasePickaxeItem(material, 1, -2.8F, newItemSettings()));
        axe = LirothItems.registerTool(name + "_axe", new BaseAxeItem(material, 6.0F, -3.0F, newItemSettings()));
        hoe = LirothItems.registerTool(name + "_hoe", new BaseHoeItem(material, -3, 0.0F, newItemSettings()));

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
        if (hasOre) {
            RecipeBuilder.blasting(Liroth.C.mk(name + "_ingot_furnace_ore"), ingot)
                    .input(ore)
                    .build(context);
            RecipeBuilder.blasting(Liroth.C.mk(name + "_ingot_furnace_raw"), ingot)
                    .input(rawOre)
                    .build(context);
        }

        // Basic recipes
        RecipeBuilder.crafting(Liroth.C.mk(name + "_ingot_from_nuggets"), ingot)
                .shape("###", "###", "###")
                .addMaterial('#', nugget)
                .group("liroth_metal_ingots_nug")
                .build(context);
        RecipeBuilder.crafting(Liroth.C.mk(name + "_nuggets_from_ingot"), nugget)
                .outputCount(9)
                .shapeless()
                .addMaterial('#', ingot)
                .group("liroth_metal_nuggets_ing")
                .build(context);
        RecipeBuilder.crafting(Liroth.C.mk(name + "_block"), block)
                .shape("###", "###", "###")
                .addMaterial('#', ingot)
                .group("liroth_metal_blocks")
                .build(context);
        RecipeBuilder.crafting(Liroth.C.mk(name + "_ingot_from_block"), ingot)
                .outputCount(9)
                .shapeless()
                .addMaterial('#', block)
                .group("liroth_metal_ingots")
                .build(context);

        // Tools & armor into nuggets
        RecipeBuilder.blasting(Liroth.C.mk(name + "_axe_nugget"), nugget)
                .input(axe)
                .build(context);
        RecipeBuilder.blasting(Liroth.C.mk(name + "_hoe_nugget"), nugget)
                .input(hoe)
                .build(context);
        RecipeBuilder.blasting(Liroth.C.mk(name + "_pickaxe_nugget"), nugget)
                .input(pickaxe)
                .build(context);
        RecipeBuilder.blasting(Liroth.C.mk(name + "_sword_nugget"), nugget)
                .input(sword)
                .build(context);
        RecipeBuilder.blasting(Liroth.C.mk(name + "_helmet_nugget"), nugget)
                .input(helmet)
                .build(context);
        RecipeBuilder.blasting(Liroth.C.mk(name + "_chestplate_nugget"), nugget)
                .input(chestplate)
                .build(context);
        RecipeBuilder.blasting(Liroth.C.mk(name + "_leggings_nugget"), nugget)
                .input(leggings)
                .build(context);
        RecipeBuilder.blasting(Liroth.C.mk(name + "_boots_nugget"), nugget)
                .input(boots)
                .build(context);

        // Tool crafting
        RecipeBuilder.crafting(Liroth.C.mk(name + "_sword"), sword)
                .shape(" # ", " # ", " ! ")
                .addMaterial('#', ingot)
                .addMaterial('!', Items.STICK)
                .group("liroth_swords")
                .build(context);
        RecipeBuilder.crafting(Liroth.C.mk(name + "_shovel"), shovel)
                .shape(" # ", " ! ", " ! ")
                .addMaterial('#', ingot)
                .addMaterial('!', Items.STICK)
                .group("liroth_shovels")
                .build(context);
        RecipeBuilder.crafting(Liroth.C.mk(name + "_pickaxe"), pickaxe)
                .shape("###", " ! ", " ! ")
                .addMaterial('#', ingot)
                .addMaterial('!', Items.STICK)
                .group("liroth_pickaxes")
                .build(context);
        RecipeBuilder.crafting(Liroth.C.mk(name + "_axe"), axe)
                .shape("## ", "#! ", " ! ")
                .addMaterial('#', ingot)
                .addMaterial('!', Items.STICK)
                .group("liroth_axes")
                .build(context);
        RecipeBuilder.crafting(Liroth.C.mk(name + "_hoe"), hoe)
                .shape("## ", " ! ", " ! ")
                .addMaterial('#', ingot)
                .addMaterial('!', Items.STICK)
                .group("liroth_hoes")
                .build(context);

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
        context.add(BlockTags.BEACON_BASE_BLOCKS, block);
    }

    @Override
    public void registerItemTags(ItemTagBootstrapContext context) {
        context.add(ItemTags.BEACON_PAYMENT_ITEMS, ingot);
    }
}
