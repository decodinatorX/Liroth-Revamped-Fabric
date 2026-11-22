package com.decodinator.liroth.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.Block;

import java.util.Map;

@Mixin(AxeItem.class)
public interface AxeItemAccess {

    @Invoker("<init>")
    static AxeItem create(Tier tier, Properties properties) {
        throw new Error("Mixin did not apply");
    }

    @Accessor("STRIPPABLES")
    static Map<Block, Block> getStripables() {
        throw new Error("Mixin did not apply");
    }

    @Accessor("STRIPPABLES")
    @Mutable
    static void setStripables(Map<Block, Block> newMap) {
        throw new Error("Mixin did not apply");
    }
}
