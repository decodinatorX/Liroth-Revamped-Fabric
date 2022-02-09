package com.decodinator.liroth.mixin.access;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.DirtPathBlock;

@Mixin(DirtPathBlock.class)
public interface DirtPathBlockAccess {

    @Invoker("<init>")
    static DirtPathBlock create(AbstractBlock.Settings properties) {
        throw new Error("Mixin did not apply!");
    }
}
