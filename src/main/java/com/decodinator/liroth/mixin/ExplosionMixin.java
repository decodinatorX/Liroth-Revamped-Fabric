package com.decodinator.liroth.mixin;

import com.decodinator.liroth.util.FungalFiendExplosionHandler;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Explosion.class)
public abstract class ExplosionMixin {
    @Shadow
    @Final
    private Level level;

    @Inject(method = "finalizeExplosion", at = @At("TAIL"))
    private void liroth$applyFungalFiendEffect(boolean spawnParticles, CallbackInfo ci) {
        FungalFiendExplosionHandler.onFungalFiendExplosion(this.level, (Explosion) (Object) this);
    }
}
