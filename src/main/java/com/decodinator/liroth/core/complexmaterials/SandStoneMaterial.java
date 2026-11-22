package com.decodinator.liroth.core.complexmaterials;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.core.registry.LirothBlocks;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;
import org.betterx.bclib.blocks.*;
import org.betterx.wover.recipe.api.RecipeBuilder;
import org.betterx.wover.tag.api.event.context.ItemTagBootstrapContext;
import org.betterx.wover.tag.api.event.context.TagBootstrapContext;
import org.betterx.wover.tag.api.predefined.CommonItemTags;

public class SandStoneMaterial implements MaterialManager.Material {
    public final Block stone;
    public final String name;

    public final Block carved;
    public final Block stairs;
    public final Block slab;
    public final Block wall;
    public final Block smooth;
    public final Block smoothStairs;
    public final Block smoothSlab;
    public final Block smoothWall;

    public static BlockBehaviour.Properties createMaterial(MapColor color) {
        return BlockBehaviour.Properties.ofFullCopy(Blocks.SANDSTONE).mapColor(color);
    }

    public SandStoneMaterial(String name, MapColor color) {
        BlockBehaviour.Properties material = createMaterial(color);

        this.name = name;
        stone = LirothBlocks.registerBlock(name, new BaseBlock.Stone(material));
        carved = LirothBlocks.registerBlock("carved_" + name, new BaseBlock.Stone(material));
        stairs = LirothBlocks.registerBlock(name + "_stairs", new BaseStairsBlock.Stone(stone));
        slab = LirothBlocks.registerBlock(name + "_slab", new BaseSlabBlock.Stone(stone));
        wall = LirothBlocks.registerBlock(name + "_wall", new BaseWallBlock.Stone(stone));
        smooth = LirothBlocks.registerBlock("smooth_" + name, new BaseBlock.Stone(material));
        smoothStairs = LirothBlocks.registerBlock("smooth_" + name + "_stairs", new BaseStairsBlock.Stone(smooth));
        smoothSlab = LirothBlocks.registerBlock("smooth_" + name + "_slab", new BaseSlabBlock.Stone(smooth));
        smoothWall = LirothBlocks.registerBlock("smooth_" + name + "_wall", new BaseWallBlock.Stone(smooth));


        MaterialManager.register(this);
    }

    public static void recipesForStoneMaterial(RecipeOutput context, SandStoneMaterial mat) {
        RecipeBuilder
                .stonecutting(
                        Liroth.C.mk(mat.name + "_smooth_stonecutting"),
                        mat.smooth
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
                        Liroth.C.mk(mat.name + "_smooth_slab_stonecutting"),
                        mat.smoothSlab
                )
                .outputCount(2)
                .input(mat.stone)
                .group(mat.name + "_stonecutting")
                .build(context);

        RecipeBuilder
                .stonecutting(
                        Liroth.C.mk(mat.name + "_smooth_stair_stonecutting"),
                        mat.smoothStairs
                )
                .input(mat.stone)
                .group(mat.name + "_stonecutting")
                .build(context);

        RecipeBuilder
                .stonecutting(
                        Liroth.C.mk(mat.name + "_smooth_wall_stonecutting"),
                        mat.smoothWall
                )
                .input(mat.stone)
                .group(mat.name + "_stonecutting")
                .build(context);

        RecipeBuilder
                .stonecutting(
                        Liroth.C.mk(mat.name + "_carved_stonecutting"),
                        mat.carved
                )
                .input(mat.stone)
                .group(mat.name + "_stonecutting")
                .build(context);

        RecipeBuilder
                .stonecutting(
                        Liroth.C.mk(mat.name + "_smooth_slabs_from_" + mat.name + "_brick_stonecutting"),
                        mat.smoothSlab
                )
                .outputCount(2)
                .input(mat.smooth)
                .group(mat.name + "_stonecutting")
                .build(context);

        RecipeBuilder
                .stonecutting(
                        Liroth.C.mk(mat.name + "_smooth_stair_from_" + mat.name + "_brick_stonecutting"),
                        mat.smoothStairs
                )
                .input(mat.smooth)
                .group(mat.name + "_stonecutting")
                .build(context);

        RecipeBuilder
                .stonecutting(
                        Liroth.C.mk(mat.name + "_smooth_wall_from_" + mat.name + "_brick_stonecutting"),
                        mat.smoothWall
                )
                .input(mat.smooth)
                .group(mat.name + "_stonecutting")
                .build(context);
    }

    @Override
    public void registerRecipes(RecipeOutput context) {
        RecipeBuilder.crafting(Liroth.C.mk(name + "_bricks"), smooth)
                .outputCount(4)
                .shape("##", "##")
                .addMaterial('#', stone)
                .group("liroth_bricks")
                .build(context);
        RecipeBuilder.crafting(Liroth.C.mk(name + "_polished"), carved)
                .outputCount(4)
                .shape("##", "##")
                .addMaterial('#', smooth)
                .group("liroth_tile")
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
        RecipeBuilder.crafting(Liroth.C.mk(name + "_bricks_stairs"), smoothStairs)
                .outputCount(4)
                .shape("#  ", "## ", "###")
                .addMaterial('#', smooth)
                .group("liroth_stone_stairs")
                .build(context);
        RecipeBuilder.crafting(Liroth.C.mk(name + "_bricks_slab"), smoothSlab)
                .outputCount(6)
                .shape("###")
                .addMaterial('#', smooth)
                .group("liroth_stone_slabs")
                .build(context);

        RecipeBuilder.crafting(Liroth.C.mk(name + "_wall"), wall)
                .outputCount(6)
                .shape("###", "###")
                .addMaterial('#', stone)
                .group("liroth_wall")
                .build(context);
        RecipeBuilder.crafting(Liroth.C.mk(name + "_bricks_wall"), smoothWall)
                .outputCount(6)
                .shape("###", "###")
                .addMaterial('#', smooth)
                .group("liroth_wall")
                .build(context);

        SandStoneMaterial.recipesForStoneMaterial(context, this);
    }

    @Override
    public void registerBlockTags(TagBootstrapContext<Block> context) {
        context.add(BlockTags.STONE_BRICKS, smooth);
        context.add(BlockTags.WALLS, wall, smoothWall);
        context.add(BlockTags.SLABS, slab, smoothSlab);
    }

    @Override
    public void registerItemTags(ItemTagBootstrapContext context) {
        context.add(ItemTags.SLABS, slab.asItem(), smoothSlab.asItem());
        context.add(ItemTags.STONE_CRAFTING_MATERIALS, stone.asItem());
        context.add(ItemTags.STONE_TOOL_MATERIALS, stone.asItem());
    }
}