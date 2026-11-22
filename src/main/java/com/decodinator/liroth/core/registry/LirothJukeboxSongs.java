package com.decodinator.liroth.core.registry;

import com.decodinator.liroth.Liroth;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.JukeboxSong;

public class LirothJukeboxSongs {
    public static final ResourceKey<JukeboxSong> LIROTH = create("liroth");
    public static final ResourceKey<JukeboxSong> SPICED = create("spiced");
    public static final ResourceKey<JukeboxSong> PIER = create("pier");
    public static final ResourceKey<JukeboxSong> DAMNATION = create("damnation");
    public static final ResourceKey<JukeboxSong> ESCAPE = create("escape");
    public static final ResourceKey<JukeboxSong> SPITE = create("spite");
    public static final ResourceKey<JukeboxSong> DESOLATION = create("desolation");

    private LirothJukeboxSongs() {
    }

    private static ResourceKey<JukeboxSong> create(String name) {
        return ResourceKey.create(Registries.JUKEBOX_SONG, Liroth.C.id(name));
    }
}