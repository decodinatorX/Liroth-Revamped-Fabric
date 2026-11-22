package com.decodinator.liroth.core.registry;

import java.util.IdentityHashMap;
import java.util.Map;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.mixin.AxeItemAccess;

import net.minecraft.world.level.block.Block;

public class LirothStrippables {

    public static void strippableLogsLiroth() {
        Liroth.LOGGER.warning("Liroth: Registering strippable Blocks...");

        Map<Block, Block> stripables = new IdentityHashMap<>(AxeItemAccess.getStripables());

        stripables.put(LirothBlocks.LIROTH_WOOD.getLog(), LirothBlocks.LIROTH_WOOD.getBark());
        stripables.put(LirothBlocks.SPICED_WOOD.getLog(), LirothBlocks.SPICED_WOOD.getBark());
        stripables.put(LirothBlocks.TALLPIER_WOOD.getLog(), LirothBlocks.TALLPIER_WOOD.getBark());
        stripables.put(LirothBlocks.DAMNATION_WOOD.getLog(), LirothBlocks.DAMNATION_WOOD.getBark());
        stripables.put(LirothBlocks.JAPZ_WOOD.getLog(), LirothBlocks.JAPZ_WOOD.getBark());
        stripables.put(LirothBlocks.KOOLAW_WOOD.getLog(), LirothBlocks.KOOLAW_WOOD.getBark());
        stripables.put(LirothBlocks.PETRIFIED_DAMNATION_WOOD.getLog(), LirothBlocks.PETRIFIED_DAMNATION_WOOD.getBark());

        AxeItemAccess.setStripables(stripables);
        Liroth.LOGGER.info("Liroth: Strippables registered!");
    }
}