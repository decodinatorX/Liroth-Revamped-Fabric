package com.decodinator.liroth.core.complexmaterials;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.core.registry.LirothBlocks;
import org.betterx.bclib.blocks.*;
import org.betterx.wover.recipe.api.RecipeBuilder;
import org.betterx.wover.tag.api.event.context.ItemTagBootstrapContext;
import org.betterx.wover.tag.api.event.context.TagBootstrapContext;

import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;

public class StoneMaterial implements MaterialManager.Material {
    public final Block stone;
    public final String name;

    public final Block stairs;
    public final Block slab;
    public final Block wall;
    public final Block button;
    public final Block pressurePlate;
    public final Block bricks;
    public final Block brickStairs;
    public final Block brickSlab;
    public final Block brickWall;

    public static BlockBehaviour.Properties createMaterial(MapColor color) {
        return BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(color);
    }

    public StoneMaterial(String name, MapColor color) {
        BlockBehaviour.Properties material = createMaterial(color);

        this.name = name;
        stone = LirothBlocks.registerBlock(name, new BaseBlock.Stone(material));
        stairs = LirothBlocks.registerBlock(name + "_stairs", new BaseStairsBlock.Stone(stone));
        slab = LirothBlocks.registerBlock(name + "_slab", new BaseSlabBlock.Stone(stone));
        wall = LirothBlocks.registerBlock(name + "_wall", new BaseWallBlock.Stone(stone));
        button = LirothBlocks.registerBlock(name + "_button", new BaseButtonBlock.Stone(stone, BlockSetType.STONE));
        pressurePlate = LirothBlocks.registerBlock(
                name + "_plate",
                new BasePressurePlateBlock.Stone(stone, BlockSetType.STONE)
        );
        bricks = LirothBlocks.registerBlock(name + "_bricks", new BaseBlock.Stone(material));
        brickStairs = LirothBlocks.registerBlock(name + "_bricks_stairs", new BaseStairsBlock.Stone(bricks));
        brickSlab = LirothBlocks.registerBlock(name + "_bricks_slab", new BaseSlabBlock.Stone(bricks));
        brickWall = LirothBlocks.registerBlock(name + "_bricks_wall", new BaseWallBlock.Stone(bricks));


        MaterialManager.register(this);
    }

    public static void recipesForStoneMaterial(RecipeOutput context, StoneMaterial mat) {
        RecipeBuilder
                .stonecutting(
                        Liroth.C.mk(mat.name + "_bricks_stonecutting"),
                        mat.bricks
                )
                .input(mat.stone)
                .group(mat.name + "_stonecutting")
                .build(context);

        RecipeBuilder
                .stonecutting(
                        Liroth.C.mk(mat.name + "_wall_stonecutting"),
                        mat.wall
                )
                .input(mat.stone)
                .group(mat.name + "_stonecutting")
                .build(context);

        RecipeBuilder
                .stonecutting(
                        Liroth.C.mk(mat.name + "_slab_stonecutting"),
                        mat.slab
                )
                .input(mat.stone)
                .group(mat.name + "_stonecutting")
                .build(context);

        RecipeBuilder
                .stonecutting(
                        Liroth.C.mk(mat.name + "_stairs_stonecutting"),
                        mat.stairs
                )
                .input(mat.stone)
                .group(mat.name + "_stonecutting")
                .build(context);

        RecipeBuilder
                .stonecutting(
                        Liroth.C.mk(mat.name + "_brick_slab_stonecutting"),
                        mat.brickSlab
                )
                .outputCount(2)
                .input(mat.stone)
                .group(mat.name + "_stonecutting")
                .build(context);

        RecipeBuilder
                .stonecutting(
                        Liroth.C.mk(mat.name + "_brick_stair_stonecutting"),
                        mat.brickStairs
                )
                .input(mat.stone)
                .group(mat.name + "_stonecutting")
                .build(context);

        RecipeBuilder
                .stonecutting(
                        Liroth.C.mk(mat.name + "_brick_wall_stonecutting"),
                        mat.brickWall
                )
                .input(mat.stone)
                .group(mat.name + "_stonecutting")
                .build(context);

        RecipeBuilder
                .stonecutting(
                        Liroth.C.mk(mat.name + "_brick_slabs_from_" + mat.name + "_brick_stonecutting"),
                        mat.brickSlab
                )
                .outputCount(2)
                .input(mat.bricks)
                .group(mat.name + "_stonecutting")
                .build(context);

        RecipeBuilder
                .stonecutting(
                        Liroth.C.mk(mat.name + "_brick_stair_from_" + mat.name + "_brick_stonecutting"),
                        mat.brickStairs
                )
                .input(mat.bricks)
                .group(mat.name + "_stonecutting")
                .build(context);

        RecipeBuilder
                .stonecutting(
                        Liroth.C.mk(mat.name + "_brick_wall_from_" + mat.name + "_brick_stonecutting"),
                        mat.brickWall
                )
                .input(mat.bricks)
                .group(mat.name + "_stonecutting")
                .build(context);
    }

    @Override
    public void registerRecipes(RecipeOutput context) {
        RecipeBuilder.crafting(Liroth.C.mk(name + "_bricks"), bricks)
                .outputCount(4)
                .shape("##", "##")
                .addMaterial('#', stone)
                .group("liroth_bricks")
                .build(context);

        RecipeBuilder.crafting(Liroth.C.mk(name + "_stairs"), stairs)
                .outputCount(4)
                .shape("#  ", "## ", "###")
                .addMaterial('#', stone)
                .group("liroth_stone_stairs")
                .build(context);
        RecipeBuilder.crafting(Liroth.C.mk(name + "_slab"), slab)
                .outputCount(6)
                .shape("###")
                .addMaterial('#', stone)
                .group("liroth_stone_slabs")
                .build(context);
        RecipeBuilder.crafting(Liroth.C.mk(name + "_bricks_stairs"), brickStairs)
                .outputCount(4)
                .shape("#  ", "## ", "###")
                .addMaterial('#', bricks)
                .group("liroth_stone_stairs")
                .build(context);
        RecipeBuilder.crafting(Liroth.C.mk(name + "_bricks_slab"), brickSlab)
                .outputCount(6)
                .shape("###")
                .addMaterial('#', bricks)
                .group("liroth_stone_slabs")
                .build(context);

        RecipeBuilder.crafting(Liroth.C.mk(name + "_wall"), wall)
                .outputCount(6)
                .shape("###", "###")
                .addMaterial('#', stone)
                .group("liroth_wall")
                .build(context);
        RecipeBuilder.crafting(Liroth.C.mk(name + "_bricks_wall"), brickWall)
                .outputCount(6)
                .shape("###", "###")
                .addMaterial('#', bricks)
                .group("liroth_wall")
                .build(context);

        RecipeBuilder.crafting(Liroth.C.mk(name + "_button"), button)
                .shapeless()
                .addMaterial('#', stone)
                .group("liroth_stone_buttons")
                .build(context);
        RecipeBuilder.crafting(Liroth.C.mk(name + "_pressure_plate"), pressurePlate)
                .shape("##")
                .addMaterial('#', stone)
                .group("liroth_stone_plates")
                .build(context);

        StoneMaterial.recipesForStoneMaterial(context, this);
    }

    @Override
    public void registerBlockTags(TagBootstrapContext<Block> context) {
        context.add(BlockTags.STONE_BRICKS, bricks);
        context.add(BlockTags.WALLS, wall, brickWall);
        context.add(BlockTags.SLABS, slab, brickSlab);
        context.add(pressurePlate, BlockTags.PRESSURE_PLATES, BlockTags.STONE_PRESSURE_PLATES);
    }

    @Override
    public void registerItemTags(ItemTagBootstrapContext context) {
        context.add(ItemTags.SLABS, slab.asItem(), brickSlab.asItem());
        context.add(ItemTags.STONE_BRICKS, bricks.asItem());
        context.add(ItemTags.STONE_CRAFTING_MATERIALS, stone.asItem());
        context.add(ItemTags.STONE_TOOL_MATERIALS, stone.asItem());
    }
}