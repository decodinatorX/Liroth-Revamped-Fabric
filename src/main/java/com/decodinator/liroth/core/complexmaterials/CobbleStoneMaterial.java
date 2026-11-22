package com.decodinator.liroth.core.complexmaterials;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.core.blocks.BaseFurnaceBlock;
import com.decodinator.liroth.core.registry.LirothBlocks;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import org.betterx.bclib.blocks.*;
import org.betterx.wover.recipe.api.RecipeBuilder;
import org.betterx.wover.tag.api.event.context.ItemTagBootstrapContext;
import org.betterx.wover.tag.api.event.context.TagBootstrapContext;
import org.betterx.wover.tag.api.predefined.CommonItemTags;

import java.util.function.Supplier;

public class CobbleStoneMaterial implements MaterialManager.Material {
    public Supplier<SimpleParticleType> furnaceFireParticle;
    public final Block stone;
    public final String name;

    public final Block stairs;
    public final Block slab;
    public final Block wall;
    public final Block furnace;

    public static BlockBehaviour.Properties createMaterial(MapColor color) {
        return BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(color);
    }

    public CobbleStoneMaterial(String name, MapColor color, Supplier<SimpleParticleType> fireParticle) {
        BlockBehaviour.Properties material = createMaterial(color);

        this.furnaceFireParticle = fireParticle;

        this.name = name;
        stone = LirothBlocks.registerBlock(name, new BaseBlock.Stone(material));
        stairs = LirothBlocks.registerBlock(name + "_stairs", new BaseStairsBlock.Stone(stone));
        slab = LirothBlocks.registerBlock(name + "_slab", new BaseSlabBlock.Stone(stone));
        wall = LirothBlocks.registerBlock(name + "_wall", new BaseWallBlock.Stone(stone));
        furnace = LirothBlocks.registerBlock(name + "_furnace", new BaseFurnaceBlock.Stone(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(color), fireParticle));

        MaterialManager.register(this);

    }

    public static void recipesForStoneMaterial(RecipeOutput context, CobbleStoneMaterial mat) {
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
    }

    @Override
    public void registerRecipes(RecipeOutput context) {

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

        RecipeBuilder.crafting(Liroth.C.mk(name + "_wall"), wall)
                .outputCount(6)
                .shape("###", "###")
                .addMaterial('#', stone)
                .group("liroth_wall")
                .build(context);
        RecipeBuilder.crafting(Liroth.C.mk(name + "_furnace"), furnace)
                .shape("###", "# #", "###")
                .addMaterial('#', stone)
                .group("liroth_stone_ITEM_FURNACES")
                .build(context);

        CobbleStoneMaterial.recipesForStoneMaterial(context, this);
    }

    @Override
    public void registerBlockTags(TagBootstrapContext<Block> context) {
    }

    @Override
    public void registerItemTags(ItemTagBootstrapContext context) {
        context.add(ItemTags.STONE_CRAFTING_MATERIALS, stone.asItem());
        context.add(ItemTags.STONE_TOOL_MATERIALS, stone.asItem());
        context.add(CommonItemTags.FURNACES, furnace.asItem());
    }
}