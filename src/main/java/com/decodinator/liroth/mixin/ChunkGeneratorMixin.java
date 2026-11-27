package com.decodinator.liroth.mixin;

import com.decodinator.liroth.core.worldgen.LirothSeededChunkGenerator;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ChunkGenerator.class)
public abstract class ChunkGeneratorMixin {
    @Redirect(method = "applyBiomeDecoration", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/WorldgenRandom;setDecorationSeed(JII)J"))
    private long LirothianDimensionSeed(WorldgenRandom random, long seed, int chunkX, int chunkZ, WorldGenLevel level, ChunkAccess chunk, StructureManager structureManager) {
        if ((Object) this instanceof LirothSeededChunkGenerator generator) {
            seed = generator.getDimensionSeed(level.getSeed());
        }
        return random.setDecorationSeed(seed, chunkX, chunkZ);
    }
}