package com.decodinator.liroth.core.registry;

import com.decodinator.liroth.Liroth;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class LirothSounds {
    public static final ResourceLocation LIROTH_BLASTER_FIRING_SOUND_ID = ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "liroth_blaster_firing");
    public static SoundEvent LIROTH_BLASTER_FIRING_SOUND_EVENT = SoundEvent.createVariableRangeEvent(LIROTH_BLASTER_FIRING_SOUND_ID);
    
    // Fungal Fiend
    public static final ResourceLocation FUNGAL_FIEND_DEATH_SOUND_ID = ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "fungal_fiend_death");
    public static SoundEvent FUNGAL_FIEND_DEATH_SOUND_EVENT = SoundEvent.createVariableRangeEvent(FUNGAL_FIEND_DEATH_SOUND_ID);    
    public static final ResourceLocation FUNGAL_FIEND_HURT_SOUND_ID = ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "fungal_fiend_hurt");
    public static SoundEvent FUNGAL_FIEND_HURT_SOUND_EVENT = SoundEvent.createVariableRangeEvent(FUNGAL_FIEND_HURT_SOUND_ID);
    public static final ResourceLocation FUNGAL_FIEND_FUSE_SOUND_ID = ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "fungal_fiend_fuse");
    public static SoundEvent FUNGAL_FIEND_FUSE_SOUND_EVENT = SoundEvent.createVariableRangeEvent(FUNGAL_FIEND_FUSE_SOUND_ID);
    
    // Warp
    public static final ResourceLocation WARP_DEATH_SOUND_ID = ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "warp_death");
    public static SoundEvent WARP_DEATH_SOUND_EVENT = SoundEvent.createVariableRangeEvent(WARP_DEATH_SOUND_ID);    
    public static final ResourceLocation WARP_HURT_SOUND_ID = ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "warp_hurt");
    public static SoundEvent WARP_HURT_SOUND_EVENT = SoundEvent.createVariableRangeEvent(WARP_HURT_SOUND_ID);
    public static final ResourceLocation WARP_IDLE_SOUND_ID = ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "warp_idle");
    public static SoundEvent WARP_IDLE_SOUND_EVENT = SoundEvent.createVariableRangeEvent(WARP_IDLE_SOUND_ID);
    
    // Shade
    public static final ResourceLocation SHADE_DEATH_SOUND_ID = ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "shade_death");
    public static SoundEvent SHADE_DEATH_SOUND_EVENT = SoundEvent.createVariableRangeEvent(SHADE_DEATH_SOUND_ID);    
    public static final ResourceLocation SHADE_HURT_SOUND_ID = ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "shade_hurt");
    public static SoundEvent SHADE_HURT_SOUND_EVENT = SoundEvent.createVariableRangeEvent(SHADE_HURT_SOUND_ID);
    public static final ResourceLocation SHADE_IDLE_SOUND_ID = ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "shade_idle");
    public static SoundEvent SHADE_IDLE_SOUND_EVENT = SoundEvent.createVariableRangeEvent(SHADE_IDLE_SOUND_ID);
    
    // AMS - C418
    public static final ResourceLocation LIROTH_BIOME_MUSIC_SOUND_ID = ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "music.liroth.liroth_biome");
    public static SoundEvent LIROTH_BIOME_MUSIC_SOUND_EVENT = SoundEvent.createVariableRangeEvent(LIROTH_BIOME_MUSIC_SOUND_ID);
    
    // ATX - C418
    public static final ResourceLocation TALLPIER_BIOME_MUSIC_SOUND_ID = ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "music.liroth.tallpier_biome");
    public static SoundEvent TALLPIER_BIOME_MUSIC_SOUND_EVENT = SoundEvent.createVariableRangeEvent(TALLPIER_BIOME_MUSIC_SOUND_ID);
    
    // Fake Triplets - C418
    public static final ResourceLocation SPICED_DESERT_MUSIC_SOUND_ID = ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "music.liroth.spiced_desert");
    public static SoundEvent SPICED_DESERT_MUSIC_SOUND_EVENT = SoundEvent.createVariableRangeEvent(SPICED_DESERT_MUSIC_SOUND_ID);
    
    // Home - C418
    public static final ResourceLocation DAMNATION_MUSIC_SOUND_ID = ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "music.liroth.damnation");
    public static SoundEvent DAMNATION_MUSIC_SOUND_EVENT = SoundEvent.createVariableRangeEvent(DAMNATION_MUSIC_SOUND_ID);
    
    // Hope - C418
    public static final ResourceLocation ESCAPE_MUSIC_SOUND_ID = ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "music.liroth.escape");
    public static SoundEvent ESCAPE_MUSIC_SOUND_EVENT = SoundEvent.createVariableRangeEvent(ESCAPE_MUSIC_SOUND_ID);
    
    // Keighley - C418
    public static final ResourceLocation JALSPHIRE_MUSIC_SOUND_ID = ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "music.liroth.jalsphire");
    public static SoundEvent JALSPHIRE_MUSIC_SOUND_EVENT = SoundEvent.createVariableRangeEvent(JALSPHIRE_MUSIC_SOUND_ID);
    
    // Impostor Syndrome - C418
    public static final ResourceLocation DESOLATE_MUSIC_SOUND_ID = ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "music.liroth.desolate");
    public static SoundEvent DESOLATE_MUSIC_SOUND_EVENT = SoundEvent.createVariableRangeEvent(DESOLATE_MUSIC_SOUND_ID);    
    
    public static void init() {
        Registry.register(BuiltInRegistries.SOUND_EVENT, LirothSounds.LIROTH_BLASTER_FIRING_SOUND_ID, LIROTH_BLASTER_FIRING_SOUND_EVENT);
        Registry.register(BuiltInRegistries.SOUND_EVENT, LirothSounds.FUNGAL_FIEND_DEATH_SOUND_ID, FUNGAL_FIEND_DEATH_SOUND_EVENT);
        Registry.register(BuiltInRegistries.SOUND_EVENT, LirothSounds.FUNGAL_FIEND_HURT_SOUND_ID, FUNGAL_FIEND_HURT_SOUND_EVENT);
        Registry.register(BuiltInRegistries.SOUND_EVENT, LirothSounds.FUNGAL_FIEND_FUSE_SOUND_ID, FUNGAL_FIEND_FUSE_SOUND_EVENT);
        Registry.register(BuiltInRegistries.SOUND_EVENT, LirothSounds.WARP_DEATH_SOUND_ID, WARP_DEATH_SOUND_EVENT);
        Registry.register(BuiltInRegistries.SOUND_EVENT, LirothSounds.WARP_HURT_SOUND_ID, WARP_HURT_SOUND_EVENT);
        Registry.register(BuiltInRegistries.SOUND_EVENT, LirothSounds.WARP_IDLE_SOUND_ID, WARP_IDLE_SOUND_EVENT);
        Registry.register(BuiltInRegistries.SOUND_EVENT, LirothSounds.SHADE_DEATH_SOUND_ID, SHADE_DEATH_SOUND_EVENT);
        Registry.register(BuiltInRegistries.SOUND_EVENT, LirothSounds.SHADE_HURT_SOUND_ID, SHADE_HURT_SOUND_EVENT);
        Registry.register(BuiltInRegistries.SOUND_EVENT, LirothSounds.SHADE_IDLE_SOUND_ID, SHADE_IDLE_SOUND_EVENT);
        Registry.register(BuiltInRegistries.SOUND_EVENT, LirothSounds.LIROTH_BIOME_MUSIC_SOUND_ID, LIROTH_BIOME_MUSIC_SOUND_EVENT);
        Registry.register(BuiltInRegistries.SOUND_EVENT, LirothSounds.TALLPIER_BIOME_MUSIC_SOUND_ID, TALLPIER_BIOME_MUSIC_SOUND_EVENT);
        Registry.register(BuiltInRegistries.SOUND_EVENT, LirothSounds.SPICED_DESERT_MUSIC_SOUND_ID, SPICED_DESERT_MUSIC_SOUND_EVENT);
        Registry.register(BuiltInRegistries.SOUND_EVENT, LirothSounds.DAMNATION_MUSIC_SOUND_ID, DAMNATION_MUSIC_SOUND_EVENT);
        Registry.register(BuiltInRegistries.SOUND_EVENT, LirothSounds.ESCAPE_MUSIC_SOUND_ID, ESCAPE_MUSIC_SOUND_EVENT);
        Registry.register(BuiltInRegistries.SOUND_EVENT, LirothSounds.JALSPHIRE_MUSIC_SOUND_ID, JALSPHIRE_MUSIC_SOUND_EVENT);
        Registry.register(BuiltInRegistries.SOUND_EVENT, LirothSounds.DESOLATE_MUSIC_SOUND_ID, DESOLATE_MUSIC_SOUND_EVENT);
    }
}
