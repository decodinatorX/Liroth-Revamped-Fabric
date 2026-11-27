package com.decodinator.liroth.client.models.blocks;

import com.decodinator.liroth.Liroth;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;

import com.google.common.collect.Maps;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

public class Patterns {
    //Block Models
    public final static Identifier BLOCK_EMPTY = Liroth.C.mk("patterns/block/empty.json");
    public final static Identifier BLOCK_BASE = Liroth.C.mk("patterns/block/block.json");
    public final static Identifier BLOCK_SIDED = Liroth.C.mk("patterns/block/block_sided.json");
    public final static Identifier BLOCK_BOTTOM_TOP = Liroth.C.mk("patterns/block/block_bottom_top.json");
    public final static Identifier BLOCK_SLAB = Liroth.C.mk("patterns/block/slab.json");
    public final static Identifier BLOCK_STAIR = Liroth.C.mk("patterns/block/stairs.json");
    public final static Identifier BLOCK_STAIR_INNER = Liroth.C.mk("patterns/block/inner_stairs.json");
    public final static Identifier BLOCK_STAIR_OUTER = Liroth.C.mk("patterns/block/outer_stairs.json");
    public final static Identifier BLOCK_WALL_POST = Liroth.C.mk("patterns/block/wall_post.json");
    public final static Identifier BLOCK_WALL_SIDE = Liroth.C.mk("patterns/block/wall_side.json");
    public final static Identifier BLOCK_WALL_SIDE_TALL = Liroth.C.mk("patterns/block/wall_side_tall.json");
    public final static Identifier BLOCK_FENCE_POST = Liroth.C.mk("patterns/block/fence_post.json");
    public final static Identifier BLOCK_FENCE_SIDE = Liroth.C.mk("patterns/block/fence_side.json");
    public final static Identifier BLOCK_BUTTON = Liroth.C.mk("patterns/block/button.json");
    public final static Identifier BLOCK_BUTTON_PRESSED = Liroth.C.mk("patterns/block/button_pressed.json");
    public final static Identifier BLOCK_PILLAR = Liroth.C.mk("patterns/block/pillar.json");
    public final static Identifier BLOCK_PLATE_UP = Liroth.C.mk("patterns/block/pressure_plate_up.json");
    public final static Identifier BLOCK_PLATE_DOWN = Liroth.C.mk("patterns/block/pressure_plate_down.json");
    public final static Identifier BLOCK_DOOR_TOP = Liroth.C.mk("patterns/block/door_top.json");
    public final static Identifier BLOCK_DOOR_TOP_HINGE = Liroth.C.mk("patterns/block/door_top_hinge.json");
    public final static Identifier BLOCK_DOOR_BOTTOM = Liroth.C.mk("patterns/block/door_bottom.json");
    public final static Identifier BLOCK_DOOR_BOTTOM_HINGE = Liroth.C.mk("patterns/block/door_bottom_hinge.json");
    public final static Identifier BLOCK_CROSS = Liroth.C.mk("patterns/block/cross.json");
    public final static Identifier BLOCK_CROSS_SHADED = Liroth.C.mk("patterns/block/cross_shaded.json");
    public final static Identifier BLOCK_GATE_CLOSED = Liroth.C.mk("patterns/block/fence_gate_closed.json");
    public final static Identifier BLOCK_GATE_CLOSED_WALL = Liroth.C.mk("patterns/block/wall_gate_closed.json");
    public final static Identifier BLOCK_GATE_OPEN = Liroth.C.mk("patterns/block/fence_gate_open.json");
    public final static Identifier BLOCK_GATE_OPEN_WALL = Liroth.C.mk("patterns/block/wall_gate_open.json");
    public final static Identifier BLOCK_TRAPDOOR = Liroth.C.mk("patterns/block/trapdoor.json");
    public final static Identifier BLOCK_LADDER = Liroth.C.mk("patterns/block/ladder.json");
    public final static Identifier BLOCK_BARREL_OPEN = Liroth.C.mk("patterns/block/barrel_open.json");
    public final static Identifier BLOCK_PEDESTAL_DEFAULT = Liroth.C.mk("patterns/block/pedestal_default.json");
    public final static Identifier BLOKC_PEDESTAL_COLUMN = Liroth.C.mk("patterns/block/pedestal_column.json");
    public final static Identifier BLOCK_PEDESTAL_COLUMN_TOP = Liroth.C.mk("patterns/block/pedestal_column_top.json");
    public final static Identifier BLOCK_PEDESTAL_TOP = Liroth.C.mk("patterns/block/pedestal_top.json");
    public final static Identifier BLOCK_PEDESTAL_BOTTOM = Liroth.C.mk("patterns/block/pedestal_bottom.json");
    public final static Identifier BLOCK_PEDESTAL_PILLAR = Liroth.C.mk("patterns/block/pedestal_pillar.json");
    public final static Identifier BLOCK_BOOKSHELF = Liroth.C.mk("patterns/block/bookshelf.json");
    public final static Identifier BLOCK_STONE_LANTERN_CEIL = Liroth.C.mk("patterns/block/stone_lantern_ceil.json");
    public final static Identifier BLOCK_STONE_LANTERN_FLOOR = Liroth.C.mk("patterns/block/stone_lantern_floor.json");
    public final static Identifier BLOCK_BULB_LANTERN_FLOOR = Liroth.C.mk("patterns/block/bulb_lantern_floor.json");
    public final static Identifier BLOCK_BULB_LANTERN_CEIL = Liroth.C.mk("patterns/block/bulb_lantern_ceil.json");
    public final static Identifier BLOCK_PETAL_COLORED = Liroth.C.mk("models/block/block_petal_colored.json");
    public final static Identifier BLOCK_COMPOSTER = Liroth.C.mk("patterns/block/composter.json");
    public final static Identifier BLOCK_COLORED = Liroth.C.mk("patterns/block/block_colored.json");
    public final static Identifier BLOCK_BARS_POST = Liroth.C.mk("patterns/block/bars_post.json");
    public final static Identifier BLOCK_BARS_SIDE = Liroth.C.mk("patterns/block/bars_side.json");
    public final static Identifier BLOCK_ANVIL = Liroth.C.mk("patterns/block/anvil.json");
    public final static Identifier BLOCK_CHAIN = Liroth.C.mk("patterns/block/chain.json");
    public final static Identifier BLOCK_CHANDELIER_FLOOR = Liroth.C.mk("patterns/block/chandelier_floor.json");
    public final static Identifier BLOCK_CHANDELIER_WALL = Liroth.C.mk("patterns/block/chandelier_wall.json");
    public final static Identifier BLOCK_CHANDELIER_CEIL = Liroth.C.mk("patterns/block/chandelier_ceil.json");
    public final static Identifier BLOCK_FURNACE = Liroth.C.mk("patterns/block/furnace.json");
    public final static Identifier BLOCK_FURNACE_LIT = Liroth.C.mk("patterns/block/furnace_glow.json");
    public final static Identifier BLOCK_TOP_SIDE_BOTTOM = Liroth.C.mk("patterns/block/top_side_bottom.json");
    public final static Identifier BLOCK_PATH = Liroth.C.mk("patterns/block/path.json");
    public final static Identifier BLOCK_FLOWER_POT = Liroth.C.mk("patterns/block/flower_pot.json");
    public final static Identifier BLOCK_FLOWER_POT_SOIL = Liroth.C.mk("patterns/block/flower_pot_soil.json");
    public final static Identifier BLOCK_POTTED_LEAVES = Liroth.C.mk("patterns/block/potted_leaves.json");

    //Item Models
    public final static Identifier ITEM_WALL = Liroth.C.mk("patterns/item/pattern_wall.json");
    public final static Identifier ITEM_FENCE = Liroth.C.mk("patterns/item/pattern_fence.json");
    public final static Identifier ITEM_BUTTON = Liroth.C.mk("patterns/item/pattern_button.json");
    public final static Identifier ITEM_CHEST = Liroth.C.mk("patterns/item/pattern_chest.json");
    public final static Identifier ITEM_BLOCK = Liroth.C.mk("patterns/item/pattern_block_item.json");
    public final static Identifier ITEM_GENERATED = Liroth.C.mk("patterns/item/pattern_item_generated.json");
    public final static Identifier ITEM_HANDHELD = Liroth.C.mk("patterns/item/pattern_item_handheld.json");
    public final static Identifier ITEM_SPAWN_EGG = Liroth.C.mk("patterns/item/pattern_item_spawn_egg.json");

    public static Optional<String> createItemGenerated(String name) {
        return createJson(ITEM_GENERATED, name);
    }

    public static Optional<String> createBlockSimple(String name) {
        return Patterns.createJson(Patterns.BLOCK_BASE, name, name);
    }

    public static Optional<String> createBlockPillar(String name) {
        return Patterns.createJson(Patterns.BLOCK_PILLAR, name, name);
    }

    public static String createJson(Reader data, String parent, String block) {
        try (BufferedReader buffer = new BufferedReader(data)) {
            return buffer.lines().collect(Collectors.joining()).replace("%parent%", parent).replace("%block%", block);
        } catch (Exception ex) {
            return null;
        }
    }

    public static Optional<String> createJson(Identifier patternId, String parent, String block) {
        ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
        try (InputStream input = resourceManager.getResource(patternId).get().open()) {
            return Optional.ofNullable(createJson(new InputStreamReader(input, StandardCharsets.UTF_8), parent, block));
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    public static Optional<String> createJson(Identifier patternId, String texture) {
        Map<String, String> textures = Maps.newHashMap();
        textures.put("%texture%", texture);
        return createJson(patternId, textures);
    }

    public static Optional<String> createJson(Identifier patternId, Map<String, String> textures) {
        ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
        try (InputStream input = resourceManager.getResource(patternId).get().open()) {
            String json = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8)).lines()
                    .collect(Collectors.joining());
            for (Entry<String, String> texture : textures.entrySet()) {
                json = json.replace(texture.getKey(), texture.getValue());
            }
            return Optional.of(json);
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

}