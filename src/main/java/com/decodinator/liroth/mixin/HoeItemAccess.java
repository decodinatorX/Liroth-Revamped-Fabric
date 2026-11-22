package com.decodinator.liroth.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import com.mojang.datafixers.util.Pair;

import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Mixin(HoeItem.class)
public interface HoeItemAccess {

    @Invoker("<init>")
    static HoeItem create(Tier tier, Properties properties) {
        throw new Error("Mixin did not apply");
    }

    @Accessor("TILLABLES")
    static Map<Block, Pair<Predicate<UseOnContext>, Consumer<UseOnContext>>> getTillables() {
        throw new Error("Mixin did not apply");
    }

    @Accessor("TILLABLES")
    @Mutable
    static void setTillables(Map<Block, Pair<Predicate<UseOnContext>, Consumer<UseOnContext>>> tillables) {
        throw new Error("Mixin did not apply");
    }
}
