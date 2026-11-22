package com.decodinator.liroth.core.registry;

import java.util.IdentityHashMap;
import java.util.Map;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.mixin.ShovelItemAccess;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class LirothFlattenables {

    public static void addFlatfuckfridayevent() {
        Liroth.LOGGER.warning("Liroth: Registering flattenables...");
        Map<Block, BlockState> flattenables = new IdentityHashMap<>(ShovelItemAccess.getFlattenables());
        flattenables.put(LirothBlocks.LIROTH_GRASS_BLOCK, LirothBlocks.LIROTH_PATH_BLOCK.defaultBlockState());
        flattenables.put(LirothBlocks.LIROTH_DIRT, LirothBlocks.LIROTH_PATH_BLOCK.defaultBlockState());
        flattenables.put(LirothBlocks.PIER_GRASS_BLOCK, LirothBlocks.PIER_PATH_BLOCK.defaultBlockState());
        flattenables.put(LirothBlocks.PIER_DIRT, LirothBlocks.PIER_PATH_BLOCK.defaultBlockState());
        flattenables.put(LirothBlocks.SPINERIOS_GRASS_BLOCK, LirothBlocks.SPINERIOS_PATH_BLOCK.defaultBlockState());
        flattenables.put(LirothBlocks.SPINERIOS_DIRT, LirothBlocks.SPINERIOS_PATH_BLOCK.defaultBlockState());
        ShovelItemAccess.setFlattenables(flattenables);
        Liroth.LOGGER.info("Liroth: Flattenables registered!");
    }
}