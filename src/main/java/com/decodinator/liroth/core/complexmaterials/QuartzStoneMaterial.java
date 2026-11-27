package com.decodinator.liroth.core.complexmaterials;

import com.decodinator.liroth.core.registry.LirothBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import org.betterx.bclib.blocks.*;

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
        polished = LirothBlocks.registerBlock("chiseled_"+name+"_bricks", () -> (new BaseBlock.Stone(material)));
        pillar = LirothBlocks.registerBlock(name + "_pillar_block", () -> (new BaseRotatedPillarBlock.Stone(material)));
        bricks = LirothBlocks.registerBlock(name + "_bricks", () -> (new BaseBlock.Stone(material)));
        brickStairs = LirothBlocks.registerBlock(name + "_brick_stairs", () -> (new BaseStairsBlock.Stone(bricks)));
        brickSlab = LirothBlocks.registerBlock(name + "_brick_slab", () -> (new BaseSlabBlock.Stone(bricks)));
        brickWall = LirothBlocks.registerBlock(name + "_brick_wall", () -> (new BaseWallBlock.Stone(bricks)));


        MaterialManager.register(this);
    }

}