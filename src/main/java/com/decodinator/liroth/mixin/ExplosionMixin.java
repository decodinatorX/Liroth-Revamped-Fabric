package com.decodinator.liroth.mixin;

import com.decodinator.liroth.util.FungalFiendExplosionHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ServerExplosion;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ServerExplosion.class)
public abstract class ExplosionMixin {
    @Shadow
    @Final
    private ServerLevel level;

    @Inject(method = "interactWithBlocks", at = @At("HEAD"))
    private void fungalShit(List<BlockPos> targetBlocks, CallbackInfo ci) {
        FungalFiendExplosionHandler.onFungalFiendExplosion(
                this.level,
                (ServerExplosion) (Object) this,
                targetBlocks
        );
    }
}
