package com.decodinator.liroth.core.complexmaterials;

import com.decodinator.liroth.core.registry.LirothBlocks;
import org.betterx.bclib.blocks.*;

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

}