package com.decodinator.liroth.core.complexmaterials;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.core.registry.LirothBlocks;
import com.decodinator.liroth.core.registry.LirothItems;
import org.betterx.bclib.complexmaterials.WoodenComplexMaterial;
import org.betterx.bclib.complexmaterials.entry.SlotMap;
import org.betterx.bclib.complexmaterials.set.wood.WoodSlots;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;

public class WoodMaterial extends WoodenComplexMaterial {
    private Block bark;
    private Block log;

    public WoodMaterial(String name, MapColor woodColor, MapColor planksColor) {
        super(Liroth.C, name, name, woodColor, planksColor);
    }

    public WoodMaterial init() {
        return (WoodMaterial) super.init(
                LirothBlocks.getBlockRegistry(),
                LirothItems.getItemRegistry()
        );
    }

    @Override
    protected SlotMap<WoodenComplexMaterial> createMaterialSlots() {
        return super.createMaterialSlots()
                .add(WoodSlots.BOAT)
                .add(WoodSlots.CHEST_BOAT)
                .remove(WoodSlots.BOOKSHELF)
                .remove(WoodSlots.COMPOSTER)
                .remove(WoodSlots.BARREL)
                .remove(WoodSlots.LADDER)
                .remove(WoodSlots.SIGN)
                .remove(WoodSlots.HANGING_SIGN)
                .remove(WoodSlots.WALL_HANGING_SIGN)
                .remove(WoodSlots.WALL_SIGN);
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