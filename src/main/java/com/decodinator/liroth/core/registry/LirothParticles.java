package com.decodinator.liroth.core.registry;

import com.decodinator.liroth.Liroth;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class LirothParticles {
    public static final SimpleParticleType GREEN_SPORE = FabricParticleTypes.simple();
    public static final SimpleParticleType PURPLE_FLAME = FabricParticleTypes.simple();
    public static final SimpleParticleType GREEN_FLAME = FabricParticleTypes.simple();
    public static final SimpleParticleType CLOAK = FabricParticleTypes.simple();

    public static SimpleParticleType LIROTH_PORTAL = FabricParticleTypes.simple();
    public static SimpleParticleType DAMNATION_PORTAL = FabricParticleTypes.simple();
    public static SimpleParticleType JANTIRO_PORTAL = FabricParticleTypes.simple();
    public static SimpleParticleType JALSPHIRE_PORTAL = FabricParticleTypes.simple();
    public static SimpleParticleType DEVASTATED_PORTAL = FabricParticleTypes.simple();

    public static void init() {
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "green_spore"), GREEN_SPORE);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "purple_flame"), PURPLE_FLAME);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "green_flame"), GREEN_FLAME);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "cloak"), CLOAK);

        Registry.register(BuiltInRegistries.PARTICLE_TYPE, ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "liroth_portal"), LIROTH_PORTAL);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "damnation_portal"), DAMNATION_PORTAL);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "jantiro_portal"), JANTIRO_PORTAL);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "jalsphire_portal"), JALSPHIRE_PORTAL);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "devastated_portal"), DEVASTATED_PORTAL);
    }
}
