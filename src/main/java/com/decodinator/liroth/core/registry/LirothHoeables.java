package com.decodinator.liroth.core.registry;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.mixin.HoeItemAccess;
import com.mojang.datafixers.util.Pair;

import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;

public class LirothHoeables {
    public static void tillablesLiroth() {
        Liroth.LOGGER.warning("Liroth: Registering tillables...");
        Map<Block, Pair<Predicate<UseOnContext>, Consumer<UseOnContext>>> tillables = new IdentityHashMap<>(HoeItemAccess.getTillables());
        tillables.put(LirothBlocks.LIROTH_GRASS_BLOCK, Pair.of(HoeItem::onlyIfAirAbove, HoeItem.changeIntoState(LirothBlocks.LIROTH_FARMLAND_BLOCK.defaultBlockState())));
        tillables.put(LirothBlocks.LIROTH_DIRT, Pair.of(HoeItem::onlyIfAirAbove, HoeItem.changeIntoState(LirothBlocks.LIROTH_FARMLAND_BLOCK.defaultBlockState())));
        tillables.put(LirothBlocks.PIER_GRASS_BLOCK, Pair.of(HoeItem::onlyIfAirAbove, HoeItem.changeIntoState(LirothBlocks.PIER_FARMLAND_BLOCK.defaultBlockState())));
        tillables.put(LirothBlocks.PIER_DIRT, Pair.of(HoeItem::onlyIfAirAbove, HoeItem.changeIntoState(LirothBlocks.PIER_FARMLAND_BLOCK.defaultBlockState())));
        tillables.put(LirothBlocks.SPINERIOS_GRASS_BLOCK, Pair.of(HoeItem::onlyIfAirAbove, HoeItem.changeIntoState(LirothBlocks.SPINERIOS_FARMLAND_BLOCK.defaultBlockState())));
        tillables.put(LirothBlocks.SPINERIOS_DIRT, Pair.of(HoeItem::onlyIfAirAbove, HoeItem.changeIntoState(LirothBlocks.SPINERIOS_FARMLAND_BLOCK.defaultBlockState())));
        HoeItemAccess.setTillables(tillables);
        Liroth.LOGGER.info("Liroth: Tillables registered!");
    }
}