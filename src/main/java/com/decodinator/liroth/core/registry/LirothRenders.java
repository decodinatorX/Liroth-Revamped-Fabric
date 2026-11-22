package com.decodinator.liroth.core.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import com.decodinator.liroth.Liroth;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;

public class LirothRenders {
    public static void renderCutOuts(Consumer<Map<Block, RenderType>> mapConsumer) {
        Liroth.LOGGER.warning("Liroth: Rendering Texture Shits...");
        Map<Block, RenderType> map = new HashMap<>();
        
        map.put(LirothBlocks.DAMNATION_SAPLING, RenderType.cutout());
        map.put(LirothBlocks.JAPZ_SAPLING, RenderType.cutout());
        map.put(LirothBlocks.KOOLAW_SAPLING, RenderType.cutout());
        map.put(LirothBlocks.LIROTH_SAPLING, RenderType.cutout());
        map.put(LirothBlocks.SPICED_SAPLING, RenderType.cutout());
        map.put(LirothBlocks.TALLPIER_SAPLING, RenderType.cutout());

        map.put(LirothBlocks.POINTED_JALSPHIRE_CRYSTAL, RenderType.cutout());
        map.put(LirothBlocks.POINTED_PETRIFIED_CRYSTAL, RenderType.cutout());
        
        map.put(LirothBlocks.DAMNATION_VINES_PLANT, RenderType.cutout());
        map.put(LirothBlocks.DAMNATION_VINES, RenderType.cutout());
        map.put(LirothBlocks.JAPZ_VINES, RenderType.cutout());
        map.put(LirothBlocks.JAPZ_VINES_PLANT, RenderType.cutout());
        map.put(LirothBlocks.PETRIFIED_VINES, RenderType.cutout());
        map.put(LirothBlocks.PETRIFIED_VINES_PLANT, RenderType.cutout());
        map.put(LirothBlocks.JAPZ_BLOSSOM, RenderType.cutout());
        map.put(LirothBlocks.DAMNATION_FUNGAL_FUSS, RenderType.cutout());
        map.put(LirothBlocks.DAMNATION_FUNGAL_HEAP, RenderType.cutout());
        map.put(LirothBlocks.DAMNATION_FUNGAL_PATCH, RenderType.cutout());
        map.put(LirothBlocks.FUNGAL_LANTERN, RenderType.cutout());
        map.put(LirothBlocks.LIROTHIAN_PETROLEUM_LANTERN, RenderType.cutout());
        
        map.put(LirothBlocks.LIROTH_ROSE, RenderType.cutout());
        map.put(LirothBlocks.WILITING_LIROTH_ROSE, RenderType.cutout());
        map.put(LirothBlocks.VILE_TENTACLE, RenderType.cutout());
        map.put(LirothBlocks.VILE_TENTACLE_TIP, RenderType.cutout());
        map.put(LirothBlocks.DAMNATION_FUNGAL_CLUSTER, RenderType.cutout());
        map.put(LirothBlocks.DAMNATION_FUNGUS, RenderType.cutout());
        map.put(LirothBlocks.SEA_EYE, RenderType.cutout());
        
//        map.put(LirothBlocks.CORRUPTED_BREWING_STAND, RenderType.getCutout());
        
        map.put(LirothBlocks.FUNGAL_TORCH, RenderType.cutout());
        map.put(LirothBlocks.WALL_FUNGAL_TORCH, RenderType.cutout());
        map.put(LirothBlocks.LIROTHIAN_PETROLEUM_TORCH, RenderType.cutout());
        map.put(LirothBlocks.WALL_LIROTHIAN_PETROLEUM_TORCH, RenderType.cutout());
        map.put(LirothBlocks.FUNGAL_CAMPFIRE, RenderType.cutout());
        map.put(LirothBlocks.LIROTHIAN_PETROLEUM_CAMPFIRE, RenderType.cutout());
        
        map.put(LirothBlocks.LIROTH_DIMENSION_PORTAL, RenderType.translucent());
        map.put(LirothBlocks.DAMNATION_DIMENSION_PORTAL, RenderType.translucent());
        map.put(LirothBlocks.JANTIRO_DIMENSION_PORTAL, RenderType.translucent());
        map.put(LirothBlocks.JALSPHIRE_DIMENSION_PORTAL, RenderType.translucent());
        map.put(LirothBlocks.DEVASTATED_DIMENSION_PORTAL, RenderType.translucent());

        map.put(LirothBlocks.LIROTH_FLUID_BLOCK, RenderType.translucent());
        
        map.put(LirothBlocks.LIROTH_GLASS_BLOCK, RenderType.translucent());
        map.put(LirothBlocks.FORCEFIELD, RenderType.translucent());
        
        Liroth.LOGGER.warning("Liroth: Texture Shits Rendered!");

        mapConsumer.accept(map);
    }
}