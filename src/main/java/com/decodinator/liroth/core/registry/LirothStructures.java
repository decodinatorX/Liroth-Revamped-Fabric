package com.decodinator.liroth.core.registry;


import com.decodinator.liroth.Liroth;

import com.decodinator.liroth.core.structures.LirothFortressStructure;
import com.decodinator.liroth.core.structures.NovaTowerStructure;
import com.decodinator.liroth.core.structures.OldenLirothPortalStructure;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.StructureType;

public class LirothStructures {
    public static StructureType<LirothFortressStructure> LIROTH_FORTRESS;
    public static StructureType<NovaTowerStructure> NOVA_TOWER;
    public static StructureType<OldenLirothPortalStructure> OLDEN_LIROTH_PORTAL;

    public static void init() {
    	LIROTH_FORTRESS = Registry.register(BuiltInRegistries.STRUCTURE_TYPE, ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "liroth_fortress"), () -> (MapCodec<LirothFortressStructure>) LirothFortressStructure.CODEC);
    	NOVA_TOWER = Registry.register(BuiltInRegistries.STRUCTURE_TYPE, ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "nova_tower"), () -> (MapCodec<NovaTowerStructure>) NovaTowerStructure.CODEC);
    	OLDEN_LIROTH_PORTAL = Registry.register(BuiltInRegistries.STRUCTURE_TYPE, ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "olden_liroth_portal"), () -> (MapCodec<OldenLirothPortalStructure>) OldenLirothPortalStructure.CODEC);
    }
}