package com.decodinator.liroth.core.complexmaterials;

import com.decodinator.liroth.core.registry.LirothBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import org.betterx.bclib.blocks.*;

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
        stone = LirothBlocks.registerBlock(name, () -> (new BaseBlock.Stone(material)));
        carved = LirothBlocks.registerBlock("carved_" + name, () -> (new BaseBlock.Stone(material)));
        stairs = LirothBlocks.registerBlock(name + "_stairs", () -> (new BaseStairsBlock.Stone(stone)));
        slab = LirothBlocks.registerBlock(name + "_slab", () -> (new BaseSlabBlock.Stone(stone)));
        wall = LirothBlocks.registerBlock(name + "_wall", () -> (new BaseWallBlock.Stone(stone)));
        smooth = LirothBlocks.registerBlock("smooth_" + name, () -> (new BaseBlock.Stone(material)));
        smoothStairs = LirothBlocks.registerBlock("smooth_" + name + "_stairs", () -> (new BaseStairsBlock.Stone(smooth)));
        smoothSlab = LirothBlocks.registerBlock("smooth_" + name + "_slab", () -> (new BaseSlabBlock.Stone(smooth)));
        smoothWall = LirothBlocks.registerBlock("smooth_" + name + "_wall", () -> (new BaseWallBlock.Stone(smooth)));


        MaterialManager.register(this);
    }

}