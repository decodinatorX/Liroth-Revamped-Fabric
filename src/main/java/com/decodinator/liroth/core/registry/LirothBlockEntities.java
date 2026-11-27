package com.decodinator.liroth.core.registry;

import com.decodinator.liroth.core.blockentities.*;
import com.decodinator.liroth.Liroth;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.entity.BlockEntityType;
import java.util.Set;

public class LirothBlockEntities {
    public static BlockEntityType<FungalCampfireBlockEntity> FUNGAL_CAMPFIRE;
    public static BlockEntityType<LirothianPetroleumCampfireBlockEntity> LIROTHIAN_PETROLEUM_CAMPFIRE;
    public static BlockEntityType<SplitterBlockEntity> LIROTH_SPLITTER;
    public static BlockEntityType<QuantumBlockEntity> QUANTUM_EXTRACTOR;

    public static void init() {
        LirothBlocks.ensureStaticallyLoaded();
        FUNGAL_CAMPFIRE = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE,
                Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "fungal_campfire"),
                new BlockEntityType<>(FungalCampfireBlockEntity::new, Set.of(LirothBlocks.FUNGAL_CAMPFIRE)));
        LIROTHIAN_PETROLEUM_CAMPFIRE = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE,
                Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "lirothian_petroleum_campfire"),
                new BlockEntityType<>(LirothianPetroleumCampfireBlockEntity::new, Set.of(LirothBlocks.LIROTHIAN_PETROLEUM_CAMPFIRE)));
        LIROTH_SPLITTER = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE,
                Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "liroth_splitter"),
                new BlockEntityType<>(SplitterBlockEntity::new, Set.of(LirothBlocks.LIROTH_SPLITTER)));
        QUANTUM_EXTRACTOR = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE,
                Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "quantum_extractor"),
                new BlockEntityType<>(QuantumBlockEntity::new, Set.of(LirothBlocks.QUANTUM_EXTRACTOR)));
    }
}
