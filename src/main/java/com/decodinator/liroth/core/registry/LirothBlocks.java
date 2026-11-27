package com.decodinator.liroth.core.registry;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.core.blocks.*;
import com.decodinator.liroth.core.complexmaterials.*;
import com.decodinator.liroth.core.features.trees.LirothTreeGrower;
import com.decodinator.liroth.core.items.material.LirothArmorMaterial;
import com.decodinator.liroth.core.items.material.LirothArmorTier;
import com.decodinator.liroth.core.items.material.LirothToolMaterial;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import org.betterx.bclib.blocks.*;
import org.betterx.wover.block.api.BlockRegistry;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

import java.util.ArrayList;
import java.util.List;

public class LirothBlocks {
    public static List<Block> BLOCKS = new ArrayList<>();
    public static List<Block> PLANT_BLOCKS = new ArrayList<>();

    private static BlockRegistry BLOCKS_REGISTRY;
    private static boolean blocksRegistered;

    public static Block ANOMALY;
    public static Block ANOMALY_BLOCK;

    public static Block BLUE_SAND;
    public static SandStoneMaterial BLUE_SANDSTONE_MATERIAL;

    public static Block CHARRED_ACCESSWAY_BLOCK;

    public static CobbleStoneMaterial CHARRED_LIROTH;

    public static Block CHARRED_LIROTH_GEM_ORE;
    public static Block CHARRED_LIROTHIAN_COBALT_ORE;
    public static Block CHARRED_LIROTHIAN_PETROLEUM_ORE;

    public static StoneMaterial CHARRED_LIROTH_STONE;

    public static Block CHARRED_LIROTH_STONE_BRICK_LOCK;

    public static Block DAMNATION_FUNGAL_CLUSTER;
    public static Block POTTED_DAMNATION_FUNGAL_CLUSTER;
    public static Block DAMNATION_FUNGUS;
    public static Block POTTED_DAMNATION_FUNGUS;
    public static Block DAMNATION_FUNGAL_CARPET;
    public static Block DAMNATION_FUNGAL_HEAP;
    public static Block DAMNATION_FUNGAL_PATCH;
    public static Block DAMNATION_FUNGAL_FUSS;

    public static CustomSapling DAMNATION_SAPLING;
    public static Block POTTED_DAMNATION_SAPLING;

    public static Block DAMNATION_LEAVES;

    public static WoodMaterial DAMNATION_WOOD;

    public static LirothPortalBlock DAMNATION_DIMENSION_PORTAL;
    public static PortalChargeFrameBlock DAMNATION_PORTAL_FRAME;
    public static Block DAMNATION_SOIL;
    public static Block DAMNATION_WART_BLOCK;
    public static Block DAMNATION_VINES;
    public static Block DAMNATION_VINES_PLANT;

    public static Block DAMNED_PASSAGEWAY;

    public static Block DEAD_SEA_EYE;

    public static Block DEEPSLATE_LIROTH_ORE;
    public static Block DEEPSLATE_TOURMALINE_ORE;

    public static QuartzStoneMaterial DEVASTATED;

    public static LirothPortalBlock DEVASTATED_DIMENSION_PORTAL;
    public static PortalChargeFrameBlock DEVASTATED_PORTAL_FRAME;

    public static Block DIMENSIONAL_COMMUNICATOR;

    public static Block END_LIROTH_GEM_ORE;

    public static Block FORCEFIELD;

    public static Block FUNGAL_CAMPFIRE;
    public static Block FUNGAL_LANTERN;
    public static Block FUNGAL_TORCH;
    public static Block FUNGAL_LIGHT;

    public static Block GATEWAY_BLOCK;

    public static Block GLEEMSTONE;
    public static Block POINTED_GLEEMSTONE_SHARD;

    public static Block HAUNTED_THROUGHFARE_BLOCK;

    public static Block HILIGHT;

    public static Block JALSPHIRE_CRYSTAL_BLOCK;

    public static Block JALSPHIRE_ORE;

    public static LirothPortalBlock JALSPHIRE_DIMENSION_PORTAL;
    public static PortalChargeFrameBlock JALSPHIRE_PORTAL_FRAME;

    public static Block JALSPHIRE_GEM_BLOCK;
    public static Block JALSPHIRE_ORE_STONE;
    public static Block JALSPHIRE_ORE_DEEPSLATE;

    public static Block JALSPHIRE_TRACKWAY;
    public static LirothPortalBlock JANTIRO_DIMENSION_PORTAL;
    public static PortalChargeFrameBlock JANTIRO_PORTAL_FRAME;

    public static Block JAPZ_BLOSSOM;

    public static CustomSapling JAPZ_SAPLING;
    public static Block POTTED_JAPZ_SAPLING;

    public static WoodMaterial JAPZ_WOOD;

    public static Block JAPZ_LEAVES;
    public static Block JAPZ_MINI_TREE;
    public static Block POTTED_JAPZ_MINI_TREE;
    public static Block JAPZ_MOSS;
    public static Block JAPZ_MOSS_CARPET;
    public static Block JAPZ_VINES;
    public static Block JAPZ_VINES_PLANT;

    public static CustomSapling KOOLAW_SAPLING;
    public static Block POTTED_KOOLAW_SAPLING;

    public static WoodMaterial KOOLAW_WOOD;

    public static Block KOOLAW_LEAVES;

    public static Block NETHER_LIROTH_GEM_ORE;

    public static GemMaterial LIROTH_GEM;
    public static GemFragMaterial LIROTH_GEM_ANSALUM;
    public static GemFragMaterial LIROTH_GEM_LUX;
    public static GemFragMaterial LIROTH_GEM_SALEM;

    public static CustomSapling LIROTH_SAPLING;
    public static Block POTTED_LIROTH_SAPLING;

    public static WoodMaterial LIROTH_WOOD;

    public static Block LIROTH_BONE_BLOCK;

    public static CobbleStoneMaterial LIROTH_COBBLESTONE;

    public static Block LIROTH_DIRT;

    public static Block LIROTH_GEM_BLOCK;

    public static Block LIROTH_GLASS_BLOCK;

    public static Block LIROTH_GRASS_BLOCK;
    public static Block LIROTH_FARMLAND_BLOCK;
    public static Block LIROTH_PATH_BLOCK;

    public static Block LIROTH_LEAVES;

    public static Block LIROTHIAN_LIROTH_ORE;

    public static LirothPortalBlock LIROTH_DIMENSION_PORTAL;
    public static PortalChargeFrameBlock LIROTH_PORTAL_FRAME;

    public static Block LIROTH_ROSE;
    public static Block POTTED_LIROTH_ROSE;
    public static Block LIROTH_SOUL_SAND;

    public static Block LIROTH_SPLITTER;

    public static StoneMaterial LIROTH_STONE;

    public static MetalMaterial LIROTHIAN_COBALT;

    public static Block LIROTHIAN_PETROLEUM_BLOCK;
    public static Block LIROTHIAN_PETROLEUM_ORE;
    public static Block LIROTHIAN_PETROLEUM_TORCH;
    public static Block LIROTHIAN_MIMIC_BLOCK;
    public static Block LIROTHIAN_PETROLEUM_LANTERN;
    public static Block LIROTHIAN_PETROLEUM_CAMPFIRE;

    public static Block OLDEN_LIROTH_GEM_BLOCK;

    public static WoodMaterial PETRIFIED_DAMNATION_WOOD;

    public static Block PETRIFIED_DAMNATION_BRICKS;
    public static Block PETRIFIED_DAMNATION_BRICK_STAIRS;
    public static Block PETRIFIED_DAMNATION_BRICK_SLAB;
    public static Block PETRIFIED_DAMNATION_BRICK_WALL;

    public static Block PETRIFIED_CRYSTAL_BLOCK;
    public static Block PETRIFIED_MOSS_BLOCK;
    public static Block PETRIFIED_MOSS_CARPET;
    public static Block PETRIFIED_VINES;
    public static Block PETRIFIED_VINES_PLANT;

    public static Block PIER_DIRT;
    public static Block PIER_GRASS_BLOCK;
    public static Block PIER_FARMLAND_BLOCK;
    public static Block PIER_PATH_BLOCK;

    public static Block POINTED_JALSPHIRE_CRYSTAL;

    public static Block POINTED_PETRIFIED_CRYSTAL;

    public static Block POTESTIUM_ORE;
    public static Block POTESTIUM_BLOCK;
    public static Block POTESTIUM_PLATE_BLOCK;

    public static Block QUANTUM_EXTRACTOR;

    public static GemMaterial RUBY;

    public static Block QUANTUM_DIAMOND_ORE;
    public static Block QUANTUM_PLATE_BLOCK;

    public static Block SEA_EYE;

    public static Block SOULLESS_SOIL;

    public static Block SOUL_ROD;

    public static CustomSapling SPICED_SAPLING;
    public static Block POTTED_SPICED_SAPLING;

    public static WoodMaterial SPICED_WOOD;

    public static Block SPICED_LEAVES;

    public static Block SPINERIOS_COAL_ORE;

    public static CobbleStoneMaterial SPINERIOS_COBBLESTONE;

    public static Block SPINERIOS_COPPER_ORE;
    public static Block SPINERIOS_DIAMOND_ORE;
    public static Block SPINERIOS_DIRT;
    public static Block SPINERIOS_EMERALD_ORE;
    public static Block SPINERIOS_GOLD_ORE;

    public static Block SPINERIOS_GRASS_BLOCK;
    public static Block SPINERIOS_FARMLAND_BLOCK;
    public static Block SPINERIOS_PATH_BLOCK;

    public static Block SPINERIOS_IRON_ORE;
    public static Block SPINERIOS_LAPIS_ORE;
    public static Block SPINERIOS_LIROTH_ORE;
    public static Block SPINERIOS_QUARTZ_ORE;
    public static Block SPINERIOS_REDSTONE_ORE;
    public static Block SPINERIOS_ANCIENT_DEBRIS;

    public static StoneMaterial SPINERIOS_STONE;

    public static Block SPINERIOS_STONE_ORE;

    public static Block SPINERIOS_STONE_TOURMALINE_ORE;
    public static Block SPINERIOS_TOURMALINE_ORE;

    public static CustomSapling TALLPIER_SAPLING;
    public static Block POTTED_TALLPIER_SAPLING;

    public static WoodMaterial TALLPIER_WOOD;

    public static Block TALLPIER_LEAVES;

    public static GemMaterial TOURMALINE;

    public static Block TOURMALINE_ORE;

    public static Block VILE_TENTACLE_TIP;
    public static Block VILE_TENTACLE;

    public static Block WALL_FUNGAL_TORCH;
    public static Block WALL_LIROTHIAN_PETROLEUM_TORCH;

    public static Block WILITING_LIROTH_ROSE;
    public static Block POTTED_WILITING_LIROTH_ROSE;

    public static LiquidBlock LIROTH_FLUID_BLOCK;
    public static LiquidBlock MOLTEN_SPINERIOS_BLOCK;


    static {
        getBlockRegistry();
    }

    public static void ensureRegistered() {
        getBlockRegistry();
    }

    public static void registerBlocks() {
        if (blocksRegistered) return;
        blocksRegistered = true;
        ANOMALY = registerBlock("anomaly", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK).sound(SoundType.AMETHYST).mapColor(MapColor.COLOR_PINK).noOcclusion())));
        ANOMALY_BLOCK = registerBlock("anomaly_block", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK).sound(SoundType.AMETHYST).mapColor(MapColor.COLOR_PINK))));
        BLUE_SAND = registerBlock("blue_sand", () -> (new ColoredFallingBlock(new ColorRGBA(0) ,BlockBehaviour.Properties.ofFullCopy(Blocks.SAND).mapColor(MapColor.COLOR_BLUE))));
        BLUE_SANDSTONE_MATERIAL = new SandStoneMaterial("blue_sandstone", MapColor.COLOR_BLUE);
        CHARRED_ACCESSWAY_BLOCK = registerBlock("charred_accessway_block", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE))));
        CHARRED_LIROTH = new CobbleStoneMaterial("charred_liroth_cobblestone", MapColor.COLOR_BLACK, () -> LirothParticles.PURPLE_FLAME);
        CHARRED_LIROTH_GEM_ORE = registerBlock("charred_lirothian_liroth_gem_ore", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_DIAMOND_ORE))));
        CHARRED_LIROTHIAN_COBALT_ORE = registerBlock("charred_lirothian_cobalt_ore", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_DIAMOND_ORE))));
        CHARRED_LIROTHIAN_PETROLEUM_ORE = registerBlock("charred_lirothian_petroleum_ore", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_DIAMOND_ORE))));
        CHARRED_LIROTH_STONE = new StoneMaterial("charred_liroth_stone", MapColor.COLOR_GRAY);
        CHARRED_LIROTH_STONE_BRICK_LOCK = registerBlock("charred_liroth_stone_brick_lock", LockBlock::new);
        FUNGAL_TORCH = registerBlockOnly("fungal_torch", () -> (new CustomTorch(LirothParticles.GREEN_FLAME)));
        WALL_FUNGAL_TORCH = registerBlockOnly("wall_fungal_torch", () -> (new CustomWallTorch(LirothParticles.GREEN_FLAME)));
        LIROTHIAN_PETROLEUM_TORCH = registerBlockOnly("lirothian_petroleum_torch", () -> (new CustomTorch(LirothParticles.PURPLE_FLAME)));
        LIROTHIAN_MIMIC_BLOCK = registerBlock("lirothian_mimic_block", LirothianMimicBlock::new);
        WALL_LIROTHIAN_PETROLEUM_TORCH = registerBlockOnly("wall_lirothian_petroleum_torch", () -> (new CustomWallTorch(LirothParticles.PURPLE_FLAME)));
        DAMNATION_WOOD = new WoodMaterial("damnation", MapColor.COLOR_GRAY, MapColor.COLOR_LIGHT_GRAY).init();
        DAMNATION_FUNGAL_CLUSTER = registerPlantBlock("damnation_fungal_cluster", () -> (new DoublePlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_FUNGUS).noCollision().lightLevel(state -> 15).offsetType(BlockBehaviour.OffsetType.XZ))));
        DAMNATION_FUNGUS = registerPlantBlock("damnation_fungus", () -> (new CustomFungalPlant(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_FUNGUS).noCollision().lightLevel(state -> 15), null, null, DAMNATION_SOIL)));
        DAMNATION_FUNGAL_CARPET = registerBlock("damnation_fungal_carpet", () -> (new NonStackingCarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_CARPET))));
        DAMNATION_FUNGAL_HEAP = registerPlantBlock("damnation_fungal_heap", () -> (new SilkTouchGrassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_ROOTS).noCollision().lightLevel(state -> 15))));
        DAMNATION_FUNGAL_PATCH = registerPlantBlock("damnation_fungal_patch", () -> (new SilkTouchGrassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_ROOTS).noCollision().lightLevel(state -> 15))));
        DAMNATION_FUNGAL_FUSS = registerPlantBlock("damnation_fungal_fuss", () -> (new SilkTouchGrassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_ROOTS).noCollision().lightLevel(state -> 15))));
        DAMNATION_SAPLING = registerPlantBlock("damnation_sapling", () -> (new CustomSapling(LirothTreeGrower.DAMNATION)));
        DAMNATION_LEAVES = registerBlock("damnation_leaves", () -> (new CustomLeavesBlock(0.01f, 0x222222 ,BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isSuffocating((state, world, pos) -> false).isSuffocating(((state, level, pos) -> false)))));
        DAMNATION_DIMENSION_PORTAL = registerBlockOnly("damnation_portal", () -> (new LirothPortalBlock(BlockBehaviour.Properties.of().noCollision().randomTicks().strength(-1.0F).sound(SoundType.GLASS).lightLevel(state -> 11).pushReaction(PushReaction.IMMOVEABLE), () -> LirothParticles.DAMNATION_PORTAL, "damnation", () -> CHARRED_ACCESSWAY_BLOCK)));
        DAMNATION_SOIL = registerBlock("damnation_soil", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT).sound(SoundType.SOUL_SAND).strength(0.2F))));
        DAMNATION_WART_BLOCK = registerBlock("damnation_wart_block", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_WART_BLOCK))));
        DAMNATION_VINES = registerPlantBlock("damnation_vines", () -> (new CustomVinesHead(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).randomTicks().noCollision().instabreak().sound(SoundType.WEEPING_VINES).pushReaction(PushReaction.POPPED).lightLevel(state -> 8), () -> DAMNATION_VINES_PLANT)));
        DAMNATION_VINES_PLANT = registerBlockOnly("damnation_vines_plant", () -> (new CustomVinesBody(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).randomTicks().noCollision().instabreak().sound(SoundType.WEEPING_VINES).pushReaction(PushReaction.POPPED).lightLevel(state -> 8), () -> (GrowingPlantHeadBlock) DAMNATION_VINES)));
        DAMNED_PASSAGEWAY = registerBlock("damned_passageway", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE))));
        DEAD_SEA_EYE = registerPlantBlock("dead_sea_eye", () -> (new LirothDeadPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_FAN))));
        DEEPSLATE_LIROTH_ORE = registerBlock("deepslate_liroth_gem_ore", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_DIAMOND_ORE))));
        DEEPSLATE_TOURMALINE_ORE = registerBlock("deepslate_tourmaline_ore", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_GOLD_ORE))));
        DEVASTATED = new QuartzStoneMaterial("devastated", MapColor.COLOR_BLACK);
        DEVASTATED_DIMENSION_PORTAL = registerBlockOnly("devastated_plains_portal", () -> (new LirothPortalBlock(BlockBehaviour.Properties.of().noCollision().randomTicks().strength(-1.0F).sound(SoundType.GLASS).lightLevel(state -> 11).pushReaction(PushReaction.IMMOVEABLE), () -> LirothParticles.DEVASTATED_PORTAL, "devastated_plains", () -> JALSPHIRE_TRACKWAY)));
        DIMENSIONAL_COMMUNICATOR = registerBlock("dimensional_communicator", () -> (new BaseRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)) {}));
        END_LIROTH_GEM_ORE = registerBlock("end_liroth_gem_ore", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.END_STONE))));
        FORCEFIELD = registerBlock("forcefield", () -> (new IronBarsBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK).noOcclusion().sound(SoundType.GLASS))));
        FUNGAL_CAMPFIRE = registerBlock("fungal_campfire", () -> (new FungalCampfireBlock(false, 1, BlockBehaviour.Properties.ofFullCopy(Blocks.CAMPFIRE).lightLevel(state -> 15).noOcclusion())));
        FUNGAL_LANTERN = registerBlock("fungal_lantern", () -> (new LanternBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN))));
        FUNGAL_LIGHT = registerBlock("fungallight", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_WART_BLOCK).strength(1.0F).sound(SoundType.SHROOMLIGHT).lightLevel(state -> 15))));
        GATEWAY_BLOCK = registerBlock("gateway_block", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_BLOCK))));
        GLEEMSTONE = registerBlock("gleemstone", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLOWSTONE).sound(SoundType.GLASS).lightLevel(state -> 15))));
        POINTED_GLEEMSTONE_SHARD = registerBlock("pointed_gleemstone_shard", () -> (new CustomPointedCrystal(BlockBehaviour.Properties.of().strength(0.3F).sound(SoundType.GLASS).dynamicShape().lightLevel(state -> 8), GLEEMSTONE)));
        HAUNTED_THROUGHFARE_BLOCK = registerBlock("haunted_throughfare_block", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE))));
        HILIGHT = registerBlock("hilight", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_WART_BLOCK).strength(1.0F).sound(SoundType.SHROOMLIGHT).lightLevel(state -> 15))));
        JALSPHIRE_CRYSTAL_BLOCK = registerBlock("jalsphire_crystal_block", () -> (new BaseBlock(BlockBehaviour.Properties.of().strength(0.3F).sound(SoundType.GLASS).lightLevel(state -> 8))));
        JALSPHIRE_ORE = registerBlock("jalsphire_ore", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT))));
        JALSPHIRE_DIMENSION_PORTAL = registerBlockOnly("jalsphire_plains_dimension_portal", () -> (new LirothPortalBlock(BlockBehaviour.Properties.of().noCollision().randomTicks().strength(-1.0F).sound(SoundType.GLASS).lightLevel(state -> 11).pushReaction(PushReaction.IMMOVEABLE), () -> LirothParticles.JALSPHIRE_PORTAL, "jalsphire_plains", () -> HAUNTED_THROUGHFARE_BLOCK)));
        JALSPHIRE_GEM_BLOCK = registerBlock("jalsphire_gem_block", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK))));
        JALSPHIRE_ORE_STONE = registerBlock("jalsphire_stone_ore", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE))));
        JALSPHIRE_ORE_DEEPSLATE = registerBlock("jalsphire_deepslate_ore", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_DIAMOND_ORE))));
        JALSPHIRE_TRACKWAY = registerBlock("jalsphire_trackway", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(1.5F, 6.0F))));
        JANTIRO_DIMENSION_PORTAL = registerBlockOnly("jantiro_escape_dimension_portal", () -> (new LirothPortalBlock(BlockBehaviour.Properties.of().noCollision().randomTicks().strength(-1.0F).sound(SoundType.GLASS).lightLevel(state -> 11).pushReaction(PushReaction.IMMOVEABLE), () -> LirothParticles.JANTIRO_PORTAL, "jantiros_escape_dimension", () -> DAMNED_PASSAGEWAY)));
        JAPZ_BLOSSOM = registerPlantBlock("japz_blossom", () -> (new SporeBlossomBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SPORE_BLOSSOM).lightLevel(state -> 8))));
        JAPZ_WOOD = new WoodMaterial("japz", MapColor.COLOR_BLACK, MapColor.COLOR_LIGHT_BLUE).init();
        JAPZ_SAPLING = registerPlantBlock("japz_sapling", () -> (new CustomSapling(LirothTreeGrower.JAPZ)));
        JAPZ_LEAVES = registerBlock("japz_leaves", () -> (new CustomLeavesBlock(0.01f, 0x006868, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isSuffocating((state, world, pos) -> false).isSuffocating((state, world, pos) -> false))));
        JAPZ_MINI_TREE = registerPlantBlock("japz_mini_tree", () -> (new CustomMiniTree(BlockBehaviour.Properties.ofFullCopy(Blocks.AZALEA), SPINERIOS_DIRT)));
        JAPZ_MOSS = registerBlock("japz_moss", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK))));
        JAPZ_MOSS_CARPET = registerBlock("japz_moss_carpet", () -> (new NonStackingCarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_CARPET))));
        JAPZ_VINES_PLANT = registerBlockOnly("japz_vines_plant", () -> (new CustomCaveVinesPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAVE_VINES_PLANT))));
        JAPZ_VINES = registerPlantBlock("japz_vines", () -> (new CustomCaveVinesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAVE_VINES))));
        KOOLAW_SAPLING = registerPlantBlock("koolaw_sapling", () -> (new CustomSapling(LirothTreeGrower.KOOLAW)));
        KOOLAW_WOOD = new WoodMaterial("koolaw", MapColor.TERRACOTTA_WHITE, MapColor.TERRACOTTA_WHITE).init();
        KOOLAW_LEAVES = registerBlock("koolaw_leaves", () -> (new CustomLeavesBlock(0.01f, 0x601AFF, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES))));
        NETHER_LIROTH_GEM_ORE = registerBlock("nether_liroth_gem_ore", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE).sound(SoundType.NETHERRACK))));
        LIROTH_BONE_BLOCK = registerBlock("liroth_bone_block", () -> (new BaseRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BONE_BLOCK)) {}));
        LIROTH_COBBLESTONE = new CobbleStoneMaterial("liroth_cobblestone", MapColor.COLOR_GRAY, () -> LirothParticles.PURPLE_FLAME);
        LIROTH_DIRT = registerBlock("liroth_dirt", () -> (new CustomDirtBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT), () -> LIROTH_FARMLAND_BLOCK, () -> LIROTH_PATH_BLOCK)));
        LIROTH_GEM_BLOCK = registerBlock("liroth_gem_block", () -> (new BaseRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERITE_BLOCK).mapColor(MapColor.COLOR_LIGHT_BLUE).strength(5.0F, 1200.0F)) {}));
        LIROTH_GEM = GemMaterial.makeLiroth("liroth_gem", MapColor.COLOR_LIGHT_BLUE, LirothToolMaterial.LIROTH, LirothArmorMaterial.LIROTH.value(), LirothArmorTier.LIROTH, LirothToolMaterial.LIROTH.getLevel());
        LIROTH_GEM_ANSALUM = GemFragMaterial.makeAnsalum("liroth_gem_ansalum", MapColor.COLOR_RED, LirothToolMaterial.LIROTH, LirothArmorMaterial.LIROTH_ANSALUM.value(), LirothArmorTier.LIROTH_ANSALUM, LirothToolMaterial.LIROTH.getLevel());
        LIROTH_GEM_LUX = GemFragMaterial.makeNormal("liroth_gem_lux", MapColor.COLOR_PURPLE, LirothToolMaterial.LIROTH, LirothArmorMaterial.LIROTH_LUX.value(), LirothArmorTier.LIROTH_LUX, LirothToolMaterial.LIROTH.getLevel());
        LIROTH_GEM_SALEM = GemFragMaterial.makeNormal("liroth_gem_salem", MapColor.COLOR_BLUE, LirothToolMaterial.LIROTH, LirothArmorMaterial.LIROTH_SALEM.value(), LirothArmorTier.LIROTH_SALEM, LirothToolMaterial.LIROTH.getLevel());
        LIROTH_GLASS_BLOCK = registerBlock("liroth_glass_block", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS))));
        LIROTH_GRASS_BLOCK = registerBlock("liroth_grass_block", () -> (new CustomGrassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK), LIROTH_DIRT, () ->  LIROTH_FARMLAND_BLOCK, () ->  LIROTH_PATH_BLOCK)));
        LIROTH_FARMLAND_BLOCK = registerBlock("liroth_farmland", () -> (new CustomFarmBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FARMLAND), () -> LIROTH_DIRT)));
        LIROTH_PATH_BLOCK = registerBlock("liroth_path_block", () -> (new CustomPathBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT_PATH), () -> LIROTH_DIRT, () -> LIROTH_FARMLAND_BLOCK)));
        LIROTH_SAPLING = registerPlantBlock("liroth_sapling", () -> (new CustomSapling(LirothTreeGrower.LIROTH)));
        LIROTH_LEAVES = registerBlock("liroth_leaves", () -> (new CustomLeavesBlock(0.01f, 0x006B6D, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isSuffocating((state, world, pos) -> false).isSuffocating((state, world, pos) -> false))));
        LIROTH_WOOD = new WoodMaterial("liroth", MapColor.COLOR_BLUE, MapColor.COLOR_CYAN).init();
        LIROTHIAN_LIROTH_ORE = registerBlock("lirothian_liroth_ore", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE))));
        LIROTH_DIMENSION_PORTAL = registerBlockOnly("liroth_dimension_portal", () -> (new LirothPortalBlock(BlockBehaviour.Properties.of().noCollision().randomTicks().strength(-1.0F).sound(SoundType.GLASS).lightLevel(state -> 11).pushReaction(PushReaction.IMMOVEABLE), () -> LirothParticles.LIROTH_PORTAL, "liroth_dimension", () -> DIMENSIONAL_COMMUNICATOR)));
        LIROTH_ROSE = registerPlantBlock("liroth_rose", () -> (new LirothRosePlant(MapColor.COLOR_PURPLE)));
        LIROTH_SOUL_SAND = registerBlock("liroth_soul_sand", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SOUL_SAND))));
        LIROTH_SPLITTER = registerBlock("liroth_splitter", () -> (new LirothSplitterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FURNACE))));
        LIROTH_STONE = new StoneMaterial("liroth_stone", MapColor.COLOR_LIGHT_BLUE);
        LIROTHIAN_COBALT = MetalMaterial.makeNormal("lirothian_cobalt", MapColor.COLOR_LIGHT_BLUE, LirothToolMaterial.LIROTHIAN_COBALT, LirothArmorMaterial.LIROTHIAN_COBALT.value(), LirothArmorTier.LIROTHIAN_COBALT, LirothToolMaterial.LIROTHIAN_COBALT.getLevel());
        LIROTHIAN_PETROLEUM_BLOCK = registerBlock("lirothian_petroleum_block", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COAL_BLOCK).strength(2.0F, 6.0F))));
        LIROTHIAN_PETROLEUM_ORE = registerBlock("lirothian_petroleum_ore", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COAL_ORE))));
        LIROTHIAN_PETROLEUM_LANTERN = registerBlock("lirothian_petroleum_lantern", () -> (new LanternBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN))));
        LIROTHIAN_PETROLEUM_CAMPFIRE = registerBlock("lirothian_petroleum_campfire", () -> (new LirothianPetroleumCampfireBlock(false, 1, BlockBehaviour.Properties.ofFullCopy(Blocks.CAMPFIRE).lightLevel(state -> 15).noOcclusion())));
        OLDEN_LIROTH_GEM_BLOCK = registerBlock("olden_liroth_gem_block", () -> (new BaseRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERITE_BLOCK).strength(5.0F, 1200.0F)) {}));
        PETRIFIED_DAMNATION_WOOD = new WoodMaterial("petrified_damnation", MapColor.COLOR_BLACK, MapColor.COLOR_GREEN).init();
        PETRIFIED_DAMNATION_BRICKS = registerBlock("petrified_damnation_bricks", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF_BRICKS))));
        PETRIFIED_DAMNATION_BRICK_SLAB = registerBlock("petrified_damnation_brick_slab", () -> (new BaseSlabBlock.Stone(PETRIFIED_DAMNATION_BRICKS, true)));
        PETRIFIED_DAMNATION_BRICK_STAIRS = registerBlock("petrified_damnation_brick_stairs", () -> (new BaseStairsBlock.Stone(PETRIFIED_DAMNATION_BRICKS, true)));
        PETRIFIED_DAMNATION_BRICK_WALL = registerBlock("petrified_damnation_brick_wall", () -> (new BaseWallBlock.Stone(PETRIFIED_DAMNATION_BRICKS)));
        PETRIFIED_CRYSTAL_BLOCK = registerBlock("petrified_crystal_block", () -> (new BaseBlock(BlockBehaviour.Properties.of().strength(0.3F).sound(SoundType.GLASS).lightLevel(state -> 8))));
        PETRIFIED_MOSS_BLOCK = registerBlock("petrified_moss_block", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK))));
        PETRIFIED_MOSS_CARPET = registerBlock("petrified_moss_carpet", () -> (new NonStackingCarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_CARPET))));
        PETRIFIED_VINES = registerPlantBlock("petrified_vines", () -> (new PetrifiedCaveVinesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAVE_VINES))));
        PETRIFIED_VINES_PLANT = registerBlockOnly("petrified_vines_plant", () -> (new PetrifiedCaveVinesPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAVE_VINES_PLANT))));
        PIER_DIRT = registerBlock("pier_dirt", () -> (new CustomDirtBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT), () -> PIER_FARMLAND_BLOCK, () -> PIER_PATH_BLOCK)));
        PIER_GRASS_BLOCK = registerBlock("pier_grass_block", () -> (new CustomGrassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK), PIER_DIRT, () ->  PIER_FARMLAND_BLOCK, () ->  PIER_PATH_BLOCK)));
        PIER_FARMLAND_BLOCK = registerBlock("pier_farmland", () -> (new CustomFarmBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FARMLAND), () -> PIER_DIRT)));
        PIER_PATH_BLOCK = registerBlock("pier_path_block", () -> (new CustomPathBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT_PATH), () -> PIER_DIRT, () -> PIER_FARMLAND_BLOCK)));
        POINTED_JALSPHIRE_CRYSTAL = registerBlock("pointed_jalsphire_crystal", () -> (new CustomPointedCrystal(BlockBehaviour.Properties.of().strength(0.3F).sound(SoundType.GLASS).dynamicShape().lightLevel(state -> (8)), JALSPHIRE_CRYSTAL_BLOCK)));
        POINTED_PETRIFIED_CRYSTAL = registerBlock("pointed_petrified_crystal", () -> (new CustomPointedCrystal(BlockBehaviour.Properties.of().strength(0.3F).sound(SoundType.GLASS).dynamicShape().lightLevel(state -> (8)), PETRIFIED_CRYSTAL_BLOCK)));
        POTESTIUM_ORE = registerBlock("potestium_ore", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SOUL_SOIL))));
        POTESTIUM_BLOCK = registerBlock("potestium_block", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK))));
        POTESTIUM_PLATE_BLOCK = registerBlock("potestium_plate_block", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK))));
        QUANTUM_EXTRACTOR = registerBlock("quantum_extractor", () -> (new QuantumExtractorBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FURNACE).noOcclusion().lightLevel(state -> state.getValue(QuantumExtractorBlock.LIT) ? 13 : 0))));
        RUBY = GemMaterial.makeNormal("ruby", MapColor.COLOR_RED, LirothToolMaterial.TOURMALINE, LirothArmorMaterial.RUBY.value(), LirothArmorTier.RUBY, 3, SoundType.SOUL_SOIL);
        QUANTUM_DIAMOND_ORE = registerBlock("quantum_diamond_ore", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SOUL_SOIL))));
        QUANTUM_PLATE_BLOCK = registerBlock("quantum_plate_block", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK))));
        SEA_EYE = registerPlantBlock("sea_eye", () -> (new LirothWaterPlant(DEAD_SEA_EYE, BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_FIRE_CORAL_FAN))));
        SOULLESS_SOIL = registerBlock("soulless_soil", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SOUL_SOIL))));
        SOUL_ROD = registerBlock("soul_rod", () -> (new CustomRod(BlockBehaviour.Properties.ofFullCopy(Blocks.END_ROD), () -> LirothParticles.CLOAK)));
        SPICED_WOOD = new WoodMaterial("spiced", MapColor.COLOR_ORANGE, MapColor.COLOR_ORANGE).init();
        SPICED_SAPLING = registerPlantBlock("spiced_sapling", () -> (new CustomSapling(LirothTreeGrower.SPICED)));
        SPICED_LEAVES = registerBlock("spiced_leaves", () -> (new CustomLeavesBlock(0.01f, 0xDE4700, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES))));
        SPINERIOS_ANCIENT_DEBRIS = registerBlock("spinerios_ancient_debris", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ANCIENT_DEBRIS).strength(5.0F, 6.0F))));
        SPINERIOS_COAL_ORE = registerBlock("spinerios_coal_ore", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT))));
        SPINERIOS_COPPER_ORE = registerBlock("spinerios_copper_ore", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT))));
        SPINERIOS_DIAMOND_ORE = registerBlock("spinerios_diamond_ore", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT))));
        SPINERIOS_EMERALD_ORE = registerBlock("spinerios_emerald_ore", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT))));
        SPINERIOS_GOLD_ORE = registerBlock("spinerios_gold_ore", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT))));
        SPINERIOS_IRON_ORE = registerBlock("spinerios_iron_ore", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT))));
        SPINERIOS_LAPIS_ORE = registerBlock("spinerios_lapis_ore", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT))));
        SPINERIOS_LIROTH_ORE = registerBlock("spinerios_liroth_ore", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT))));
        SPINERIOS_QUARTZ_ORE = registerBlock("spinerios_quartz_ore", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT))));
        SPINERIOS_REDSTONE_ORE = registerBlock("spinerios_redstone_ore", () -> (new RedStoneOreBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT).lightLevel(state -> state.getValue(RedStoneOreBlock.LIT) ? 9 : 0))));
        SPINERIOS_TOURMALINE_ORE = registerBlock("spinerios_tourmaline_ore", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT))));
        SPINERIOS_STONE_TOURMALINE_ORE = registerBlock("spinerios_stone_tourmaline_ore", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).sound(SoundType.STONE))));
        SPINERIOS_STONE_ORE = registerBlock("spinerios_stone_ore", () -> (new BaseBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT))));
        SPINERIOS_STONE = new StoneMaterial("spinerios_stone", MapColor.COLOR_PURPLE);
        SPINERIOS_COBBLESTONE = new CobbleStoneMaterial("spinerios_cobblestone", MapColor.COLOR_GRAY, () -> LirothParticles.GREEN_FLAME);
        SPINERIOS_DIRT = registerBlock("spinerios_dirt", () -> (new CustomDirtBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT), () -> SPINERIOS_FARMLAND_BLOCK, () -> SPINERIOS_PATH_BLOCK)));
        SPINERIOS_GRASS_BLOCK = registerBlock("spinerios_grass_block", () -> (new CustomGrassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK), SPINERIOS_DIRT, () -> SPINERIOS_FARMLAND_BLOCK,() -> SPINERIOS_PATH_BLOCK)));
        SPINERIOS_FARMLAND_BLOCK = registerBlock("spinerios_farmland", () -> (new CustomFarmBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FARMLAND), () -> SPINERIOS_DIRT)));
        SPINERIOS_PATH_BLOCK = registerBlock("spinerios_path_block", () -> (new CustomPathBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT_PATH), () -> SPINERIOS_DIRT, () -> SPINERIOS_FARMLAND_BLOCK)));
        TALLPIER_SAPLING = registerPlantBlock("tallpier_sapling", () -> (new CustomSapling(LirothTreeGrower.PIER)));
        TALLPIER_WOOD = new WoodMaterial("tallpier", MapColor.COLOR_PURPLE, MapColor.COLOR_PURPLE).init();
        TALLPIER_LEAVES = registerBlock("tallpier_leaves", () -> (new CustomLeavesBlock(0.01f, 0x570606, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES))));
        TOURMALINE = GemMaterial.makeNormal("tourmaline", MapColor.COLOR_RED, LirothToolMaterial.TOURMALINE, LirothArmorMaterial.TOURMALINE.value(), LirothArmorTier.TOURMALINE, LirothToolMaterial.TOURMALINE.getLevel());
        VILE_TENTACLE_TIP = registerPlantBlock("vile_tentacle_tip", () -> (new LirothWaterTipBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.KELP), () -> VILE_TENTACLE)));
        VILE_TENTACLE = registerBlockOnly("vile_tentacle", () -> (new LirothWaterPlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.KELP_PLANT), VILE_TENTACLE_TIP)));
        WILITING_LIROTH_ROSE = registerPlantBlock("wilting_liroth_rose", () -> (new LirothRosePlant(MapColor.COLOR_LIGHT_GRAY)));

        POTTED_DAMNATION_FUNGAL_CLUSTER = registerBlockOnly("potted_damnation_fungal_cluster", () -> (new FlowerPotBlock(DAMNATION_FUNGAL_CLUSTER, BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT))));
        POTTED_DAMNATION_FUNGUS = registerBlockOnly("potted_damnation_fungus", () -> (new FlowerPotBlock(DAMNATION_FUNGUS, BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT))));
        POTTED_DAMNATION_SAPLING = registerBlockOnly("potted_damnation_sapling", () -> (new FlowerPotBlock(DAMNATION_SAPLING, BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT))));
        POTTED_JAPZ_MINI_TREE = registerBlockOnly("potted_japz_mini_tree", () -> (new FlowerPotBlock(JAPZ_MINI_TREE, BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT))));
        POTTED_JAPZ_SAPLING = registerBlockOnly("potted_japz_sapling", () -> (new FlowerPotBlock(JAPZ_SAPLING, BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT))));
        POTTED_KOOLAW_SAPLING = registerBlockOnly("potted_koolaw_sapling", () -> (new FlowerPotBlock(KOOLAW_SAPLING, BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT))));
        POTTED_LIROTH_ROSE = registerBlockOnly("potted_liroth_rose", () -> (new FlowerPotBlock(LIROTH_ROSE, BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT))));
        POTTED_LIROTH_SAPLING = registerBlockOnly("potted_liroth_sapling", () -> (new FlowerPotBlock(LIROTH_SAPLING, BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT))));
        POTTED_SPICED_SAPLING = registerBlockOnly("potted_spiced_sapling", () -> (new FlowerPotBlock(SPICED_SAPLING, BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT))));
        POTTED_TALLPIER_SAPLING = registerBlockOnly("potted_tallpier_sapling", () -> (new FlowerPotBlock(TALLPIER_SAPLING, BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT))));
        POTTED_WILITING_LIROTH_ROSE = registerBlockOnly("potted_wilting_liroth_rose", () -> (new FlowerPotBlock(WILITING_LIROTH_ROSE, BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT))));

        LIROTH_FLUID_BLOCK = registerBlockOnly("liroth_fluid", () -> (new LiquidBlock(LirothFluids.LIROTH_FLUID_STILL, BlockBehaviour.Properties.ofFullCopy(Blocks.WATER))));
        MOLTEN_SPINERIOS_BLOCK = registerBlockOnly("molten_spinerios", () -> (new LiquidBlock(LirothFluids.MOLTEN_SPINERIOS_STILL, BlockBehaviour.Properties.ofFullCopy(Blocks.WATER))));

        LIROTH_PORTAL_FRAME = registerBlock("liroth_portal_frame", () -> (new PortalChargeFrameBlock(LirothItems.LIROTH_CHARGE, () -> DIMENSIONAL_COMMUNICATOR, () -> LIROTH_DIMENSION_PORTAL)));
        DAMNATION_PORTAL_FRAME = registerBlock("damnation_portal_frame", () -> (new PortalChargeFrameBlock(LirothItems.DAMNATION_CHARGE, () -> CHARRED_ACCESSWAY_BLOCK, () -> DAMNATION_DIMENSION_PORTAL)));
        JANTIRO_PORTAL_FRAME = registerBlock("jantiro_portal_frame", () -> (new PortalChargeFrameBlock(LirothItems.JANTIRO_CHARGE, () -> DAMNED_PASSAGEWAY, () -> JANTIRO_DIMENSION_PORTAL)));
        JALSPHIRE_PORTAL_FRAME = registerBlock("jalsphire_portal_frame", () -> (new PortalChargeFrameBlock(LirothItems.JALSPHIRE_CHARGE, () -> HAUNTED_THROUGHFARE_BLOCK, () -> JALSPHIRE_DIMENSION_PORTAL)));
        DEVASTATED_PORTAL_FRAME = registerBlock("devastated_portal_frame", () -> (new PortalChargeFrameBlock(LirothItems.ANOMALY_CHARGE, () -> JALSPHIRE_TRACKWAY, () -> DEVASTATED_DIMENSION_PORTAL)));

    }

    @SafeVarargs
    public static <T extends Block> T registerBlock(String name, T block, TagKey<Block>... tags) {
        T registeredBlock = getBlockRegistry().register(name, block, tags);
        BLOCKS.add(registeredBlock);
        return registeredBlock;
    }

    public static <T extends Block> T registerBlock(String name, Supplier<T> blockFactory) {
        T block = BlockRegistry.withConstructionId(Liroth.C.mk(name), blockFactory);
        return registerBlock(name, block);
    }

    @SafeVarargs
    public static <T extends Block> T registerPlantBlock(String name, T block, TagKey<Block>... tags) {
        T registeredBlock = getBlockRegistry().register(name, block, tags);
        PLANT_BLOCKS.add(registeredBlock);
        return registeredBlock;
    }

    public static <T extends Block> T registerPlantBlock(String name, Supplier<T> blockFactory) {
        T block = BlockRegistry.withConstructionId(Liroth.C.mk(name), blockFactory);
        return registerPlantBlock(name, block);
    }

    public static <T extends Block> T registerBlockOnly(String name, Block block) {
        return (T) getBlockRegistry().registerBlockOnly(name, block);
    }

    public static <T extends Block> T registerBlockOnly(String name, Supplier<T> blockFactory) {
        T block = BlockRegistry.withConstructionId(Liroth.C.mk(name), blockFactory);
        return registerBlockOnly(name, block);
    }

    @NotNull
    public static BlockRegistry getBlockRegistry() {
        if (BLOCKS_REGISTRY == null) {
            BLOCKS_REGISTRY = BlockRegistry.forMod(Liroth.C);
        }
        return BLOCKS_REGISTRY;
    }

    public static List<Block> getBlocks() {
        return BLOCKS;
    }

    public static List<Block> getPlantBlocks() {
        return PLANT_BLOCKS;
    }

    @ApiStatus.Internal
    public static void ensureStaticallyLoaded() {
        ensureRegistered();
    }
}
