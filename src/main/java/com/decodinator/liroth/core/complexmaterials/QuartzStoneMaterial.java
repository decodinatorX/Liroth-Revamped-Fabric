package com.decodinator.liroth.core.complexmaterials;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.core.registry.LirothBlocks;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import org.betterx.bclib.blocks.*;
import org.betterx.wover.recipe.api.RecipeBuilder;
import org.betterx.wover.tag.api.event.context.ItemTagBootstrapContext;
import org.betterx.wover.tag.api.event.context.TagBootstrapContext;

public class QuartzStoneMaterial implements MaterialManager.Material {
    public final Block stone;
    public final String name;

    public final Block polished;
    public final Block pillar;
    public final Block bricks;
    public final Block brickStairs;
    public final Block brickSlab;
    public final Block brickWall;

    public static BlockBehaviour.Properties createMaterial(MapColor color) {
        return BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(color);
    }

    public QuartzStoneMaterial(String name, MapColor color) {
        BlockBehaviour.Properties material = createMaterial(color);

        this.name = name;
        stone = LirothBlocks.SOULLESS_SOIL;
        polished = LirothBlocks.registerBlock("chiseled_"+name+"_bricks", new BaseBlock.Stone(material));
        pillar = LirothBlocks.registerBlock(name + "_pillar_block", new BaseRotatedPillarBlock.Stone(material));
        bricks = LirothBlocks.registerBlock(name + "_bricks", new BaseBlock.Stone(material));
        brickStairs = LirothBlocks.registerBlock(name + "_brick_stairs", new BaseStairsBlock.Stone(bricks));
        brickSlab = LirothBlocks.registerBlock(name + "_brick_slab", new BaseSlabBlock.Stone(bricks));
        brickWall = LirothBlocks.registerBlock(name + "_brick_wall", new BaseWallBlock.Stone(bricks));


        MaterialManager.register(this);
    }

    public static void recipesForStoneMaterial(RecipeOutput context, QuartzStoneMaterial mat) {
        RecipeBuilder
                .stonecutting(
                        Liroth.C.mk(mat.name + "_brick_slab_stonecutting"),
                        mat.brickSlab
                )
                .outputCount(2)
                .input(mat.bricks)
                .group(mat.name + "_stonecutting")
                .build(context);

        RecipeBuilder
                .stonecutting(
                        Liroth.C.mk(mat.name + "_brick_stair_stonecutting"),
                        mat.brickStairs
                )
                .input(mat.bricks)
                .group(mat.name + "_stonecutting")
                .build(context);

        RecipeBuilder
                .stonecutting(
                        Liroth.C.mk(mat.name + "_brick_wall_stonecutting"),
                        mat.brickWall
                )
                .input(mat.bricks)
                .group(mat.name + "_stonecutting")
                .build(context);

        RecipeBuilder
                .stonecutting(
                        Liroth.C.mk(mat.name + "_pillar_stonecutting"),
                        mat.pillar
                )
                .input(mat.bricks)
                .group(mat.name + "_stonecutting")
                .build(context);

        RecipeBuilder
                .stonecutting(
                        Liroth.C.mk(mat.name + "_polished_stonecutting"),
                        mat.polished
                )
                .input(mat.bricks)
                .group(mat.name + "_stonecutting")
                .build(context);
    }

    @Override
    public void registerRecipes(RecipeOutput context) {
        RecipeBuilder.smelting(Liroth.C.mk(name + "_bricks"), bricks)
                .disableCampfire()
                .disableBlastFurnace()
                .disableSmoker()
                .cookingTime(16)
                .experience(10f)
                .input(stone)
                .build(context);
        RecipeBuilder.crafting(Liroth.C.mk(name + "_polished"), polished)
                .outputCount(4)
                .shape("##", "##")
                .addMaterial('#', bricks)
                .group("liroth_tile")
                .build(context);
        RecipeBuilder.crafting(Liroth.C.mk(name + "_pillar"), pillar)
                .shape("#", "#")
                .addMaterial('#', brickSlab)
                .group("liroth_pillar")
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

        RecipeBuilder.crafting(Liroth.C.mk(name + "_bricks_wall"), brickWall)
                .outputCount(6)
                .shape("###", "###")
                .addMaterial('#', bricks)
                .group("liroth_wall")
                .build(context);

        QuartzStoneMaterial.recipesForStoneMaterial(context, this);
    }

    @Override
    public void registerBlockTags(TagBootstrapContext<Block> context) {
        context.add(BlockTags.STONE_BRICKS, bricks);
        context.add(BlockTags.WALLS, brickWall);
        context.add(BlockTags.SLABS, brickSlab);
    }

    @Override
    public void registerItemTags(ItemTagBootstrapContext context) {
        context.add(ItemTags.SLABS, brickSlab.asItem());
        context.add(ItemTags.STONE_BRICKS, bricks.asItem());
        context.add(ItemTags.STONE_CRAFTING_MATERIALS, stone.asItem());
        context.add(ItemTags.STONE_TOOL_MATERIALS, stone.asItem());
    }
}