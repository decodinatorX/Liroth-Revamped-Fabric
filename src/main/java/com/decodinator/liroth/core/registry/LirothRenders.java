package com.decodinator.liroth.core.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import com.decodinator.liroth.Liroth;

import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.world.level.block.Block;

public class LirothRenders {
    public static void renderCutOuts(Consumer<Map<Block, RenderType>> mapConsumer) {
        Liroth.LOGGER.warning("Liroth: Rendering Texture Shits...");
        Map<Block, RenderType> map = new HashMap<>();
        
        Liroth.LOGGER.warning("Liroth: Texture Shits Rendered!");

        mapConsumer.accept(map);
    }
}