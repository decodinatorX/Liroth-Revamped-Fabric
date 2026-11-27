package com.decodinator.liroth.core.complexmaterials;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.core.registry.LirothBlocks;
import com.decodinator.liroth.core.registry.LirothItems;
import org.betterx.bclib.complexmaterials.WoodenComplexMaterial;
import org.betterx.bclib.complexmaterials.entry.SlotMap;
import org.betterx.bclib.complexmaterials.set.wood.WoodSlots;
import org.betterx.bclib.items.boat.BoatTypeOverride;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;

public class WoodMaterial extends WoodenComplexMaterial {
    private Block bark;
    private Block log;
    private boolean raft;

    public WoodMaterial(String name, MapColor woodColor, MapColor planksColor) {
        super(Liroth.C, name, name, woodColor, planksColor);
    }

    public WoodMaterial init() {
        return (WoodMaterial) super.init(
                LirothBlocks.getBlockRegistry(),
                LirothItems.getItemRegistry()
        );
    }

    public WoodMaterial useRaft() {
        this.raft = true;
        return this;
    }

    @Override
    protected SlotMap<WoodenComplexMaterial> createMaterialSlots() {
        SlotMap<WoodenComplexMaterial> slots = super.createMaterialSlots()
                .add(raft ? WoodSlots.RAFT : WoodSlots.BOAT)
                .add(raft ? WoodSlots.CHEST_RAFT : WoodSlots.CHEST_BOAT)
                .remove(WoodSlots.BOOKSHELF)
                .remove(WoodSlots.COMPOSTER)
                .remove(WoodSlots.BARREL)
                .remove(WoodSlots.LADDER)
                .remove(WoodSlots.SIGN)
                .remove(WoodSlots.HANGING_SIGN)
                .remove(WoodSlots.WALL_HANGING_SIGN)
                .remove(WoodSlots.WALL_SIGN);
        return slots;
    }

    @Override
    protected BoatTypeOverride supplyBoatType() {
        return BoatTypeOverride.create(C, getBaseName(), getBlock(WoodSlots.PLANKS), raft);
    }

    public boolean isTreeLog(Block block) {
        return block == getLog() || block == getBark();
    }

    public boolean isTreeLog(BlockState state) {
        return isTreeLog(state.getBlock());
    }

    public Block getLog() {
        if (log == null) {
            log = getBlock("log");
        }
        return log;
    }

    public Block getBark() {
        if (bark == null) {
            bark = getBlock("bark");
        }
        return bark;
    }
}