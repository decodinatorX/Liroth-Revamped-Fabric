package com.decodinator.liroth.core.registry;

import com.decodinator.liroth.core.blockentities.*;
import com.decodinator.liroth.Liroth;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class LirothBlockEntities {
    public static BlockEntityType<FungalCampfireBlockEntity> FUNGAL_CAMPFIRE;
    public static BlockEntityType<LirothianPetroleumCampfireBlockEntity> LIROTHIAN_PETROLEUM_CAMPFIRE;
    public static BlockEntityType<SplitterBlockEntity> LIROTH_SPLITTER;
    public static BlockEntityType<QuantumBlockEntity> QUANTUM_EXTRACTOR;

    public static void init() {
        LirothBlocks.ensureStaticallyLoaded();
        FUNGAL_CAMPFIRE = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE,
                ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "fungal_campfire"),
                BlockEntityType.Builder.of(FungalCampfireBlockEntity::new, LirothBlocks.FUNGAL_CAMPFIRE).build(null));
        LIROTHIAN_PETROLEUM_CAMPFIRE = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE,
                ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "lirothian_petroleum_campfire"),
                BlockEntityType.Builder.of(LirothianPetroleumCampfireBlockEntity::new, LirothBlocks.LIROTHIAN_PETROLEUM_CAMPFIRE).build(null));
        LIROTH_SPLITTER = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE,
                ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "liroth_splitter"),
                BlockEntityType.Builder.of(SplitterBlockEntity::new, LirothBlocks.LIROTH_SPLITTER).build(null));
        QUANTUM_EXTRACTOR = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE,
                ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "quantum_extractor"),
                BlockEntityType.Builder.of(QuantumBlockEntity::new, LirothBlocks.QUANTUM_EXTRACTOR).build(null));
    }
}
