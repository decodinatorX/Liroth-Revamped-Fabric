package com.decodinator.liroth.core.registry;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.core.items.*;
import com.decodinator.liroth.core.items.material.LirothArmorMaterial;
import com.decodinator.liroth.core.items.material.LirothToolMaterial;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.*;
import org.betterx.bclib.BCLib;
import org.betterx.bclib.behaviours.BehaviourBuilders;
import org.betterx.bclib.items.BaseDiscItem;
import org.betterx.bclib.items.BaseDrinkItem;
import org.betterx.bclib.items.BaseSpawnEggItem;
import org.betterx.bclib.items.ModelProviderItem;
import org.betterx.bclib.models.RecordItemModelProvider;
import org.betterx.bclib.items.tool.BaseAxeItem;
import org.betterx.bclib.items.tool.BaseHoeItem;
import org.betterx.bclib.items.tool.BasePickaxeItem;
import org.betterx.bclib.items.tool.BaseShovelItem;
import org.betterx.bclib.items.tool.BaseSwordItem;
import org.betterx.wover.item.api.ItemRegistry;
import org.betterx.wover.tag.api.predefined.CommonItemTags;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LirothItems {
    public static List<Item> ITEMS = new ArrayList<>();
    public static List<Item> SPAWN_EGGS = new ArrayList<>();
    public static List<Item> EQUIPMENT = new ArrayList<>();

    private static ItemRegistry ITEMS_REGISTRY;

    public final static Item ANOMALY_CHARGE = registerItem("anomaly_charge");

    public final static Item BLOODY_ENTRAILS = registerFood("bloody_entrails", Foods.ROTTEN_FLESH);

    public final static Item COOL_BEAM = registerArmorItem("cool_beam", new BeamItem());

    public final static Item DAMNATION_CHARGE = registerItem("damnation_charge");

    public final static Item EMBUED_LIROTH_GEM = registerItem("embued_liroth_gem");

    public final static Item GLEEMSTONE_DUST = registerItem("gleemstone_dust");

    public final static Item JALSPHIRE_CHARGE = registerItem("jalsphire_charge");
    public final static Item JALSPHIRE_DUST = registerItem("jalsphire_dust");
    public final static Item JALSPHIRE_DUST_HEAP = registerItem("jalsphire_dust_heap");
    public final static Item JALSPHIRE_GEM = registerItem("jalsphire_gem");

    public final static Item JANTIRO_CHARGE = registerItem("jantiro_charge");

    public final static Item LEVO_ARROW = registerItem("levo_arrow", new LevoArrowItem());

    public final static Item LIROTH_CHARGE = registerItem("liroth_charge");

    public final static Item LIROTH_BERRY = registerFood("liroth_berry", Foods.SWEET_BERRIES);

    public final static Item LIROTH_BLASTER = registerArmorItem("liroth_blaster", new LirothBlaster(new Item.Properties().stacksTo(1)));

    public final static Item LIROTH_BONE = registerItem("liroth_bone");

    public final static Item LIROTH_COATED_BREAD = registerFood("liroth_coated_bread", Foods.ENCHANTED_GOLDEN_APPLE);

    public final static Item LIROTH_DUST_ANSALUM = registerItem("liroth_dust_ansalum");
    public final static Item LIROTH_DUST_LUX = registerItem("liroth_dust_lux");
    public final static Item LIROTH_DUST_SALEM = registerItem("liroth_dust_salem");

    public final static Item LIROTHIAN_DUNGEON_KEY = registerItem("lirothian_dungeon_key", new KeyItem());

    public final static Item LIROTHIAN_COBALT_HORSE_ARMOR = registerArmorItem("lirothian_cobalt_horse_armor", new AnimalArmorItem(LirothArmorMaterial.LIROTHIAN_COBALT, AnimalArmorItem.BodyType.EQUESTRIAN, false, new Item.Properties()));
    public final static Item LIROTH_HORSE_ARMOR = registerArmorItem("liroth_horse_armor", new AnimalArmorItem(LirothArmorMaterial.LIROTH, AnimalArmorItem.BodyType.EQUESTRIAN, false, new Item.Properties()));
    public final static Item QUANTUM_HORSE_ARMOR = registerArmorItem("quantum_horse_armor", new AnimalArmorItem(LirothArmorMaterial.QUANTUM_LIROTH, AnimalArmorItem.BodyType.EQUESTRIAN, false, new Item.Properties()));
    public final static Item RUBY_HORSE_ARMOR = registerArmorItem("ruby_horse_armor", new AnimalArmorItem(LirothArmorMaterial.RUBY, AnimalArmorItem.BodyType.EQUESTRIAN, false, new Item.Properties()));
    public final static Item POTESTIUM_HORSE_ARMOR = registerArmorItem("potestium_horse_armor", new AnimalArmorItem(LirothArmorMaterial.POTESTIUM_LIROTH, AnimalArmorItem.BodyType.EQUESTRIAN, false, new Item.Properties()));
    public final static Item TOURMALINE_HORSE_ARMOR = registerArmorItem("tourmaline_horse_armor", new AnimalArmorItem(LirothArmorMaterial.TOURMALINE, AnimalArmorItem.BodyType.EQUESTRIAN, false, new Item.Properties()));
    public final static Item NETHERITE_HORSE_ARMOR = registerArmorItem("netherite_horse_armor", new AnimalArmorItem(ArmorMaterials.NETHERITE, AnimalArmorItem.BodyType.EQUESTRIAN, false, new Item.Properties()));

    public final static Item LIROTHIAN_PETROLEUM = registerItem("lirothian_petroleum");

    public final static Item LIROTH_SHEILD = registerArmorItem("liroth_shield", new CustomShieldItem(new Item.Properties().stacksTo(1), LirothToolMaterial.LIROTH.getUses()));
    public final static Item ANSALUM_LIROTH_SHEILD = registerArmorItem("ansalum_liroth_shield", new CustomShieldItem(new Item.Properties().stacksTo(1), LirothToolMaterial.LIROTH.getUses()));
    public final static Item LUX_LIROTH_SHEILD = registerArmorItem("lux_liroth_shield", new CustomShieldItem(new Item.Properties().stacksTo(1), LirothToolMaterial.LIROTH.getUses()));
    public final static Item SALEM_LIROTH_SHEILD = registerArmorItem("salem_liroth_shield", new CustomShieldItem(new Item.Properties().stacksTo(1), LirothToolMaterial.LIROTH.getUses()));
    public final static Item QUANTUM_LIROTH_SHEILD = registerArmorItem("quantum_liroth_shield", new CustomShieldItem(new Item.Properties().stacksTo(1), LirothToolMaterial.QUANTUM_LIROTH.getUses()));

    public final static Item LIROTH_SMITHING_UPGRADE_TEMPLATE = registerItem("liroth_upgrade_smithing_template");

    public final static Item LIROTH_SOUL_DUST = registerItem("liroth_soul_dust");

    public final static Item LIROTH_TEAR = registerItem("liroth_tear");

    public final static Item PURPETUATED_AMETHYST_SHARD = registerItem("purpetuated_amethyst_shard");

    public final static Item QUANTUM_DIAMOND = registerItem("quantum_diamond");
    public final static Item QUANTUM_PLATE = registerItem("quantum_plate");

    public final static Item POTESTIUM_SHARD = registerItem("potestium_shard");
    public final static Item POTESTIUM_PLATE = registerItem("potestium_plate");

    public final static Item SPINERIOS_STONE_PEBBLE = registerItem("spinerios_stone_pebble");

    public final static Item SWAP_UPGRADE_SMITHING_TEMPLATE = registerItem("swap_upgrade_smithing_template");

    public final static Item QUANTUM_SMITHING_UPGRADE_TEMPLATE = registerItem("quantum_upgrade_smithing_template");

    public final static Item POTESTIUM_SCYTHE = registerTool("potestium_scythe", new BaseHoeItem(LirothToolMaterial.POTESTIUM_LIROTH, 2, -1.2F, new Item.Properties()));

    public final static Item QUANTUM_LIROTH_HELMET = registerArmorItem("quantum_liroth_helmet", new ArmorItem(LirothArmorMaterial.QUANTUM_LIROTH, ArmorItem.Type.HELMET, new Item.Properties()));
    public final static Item QUANTUM_LIROTH_CHESTPLATE = registerArmorItem("quantum_liroth_chestplate", new ArmorItem(LirothArmorMaterial.QUANTUM_LIROTH, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public final static Item QUANTUM_LIROTH_LEGGINGS = registerArmorItem("quantum_liroth_leggings", new ArmorItem(LirothArmorMaterial.QUANTUM_LIROTH, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public final static Item QUANTUM_LIROTH_BOOTS = registerArmorItem("quantum_liroth_boots", new ArmorItem(LirothArmorMaterial.QUANTUM_LIROTH, ArmorItem.Type.BOOTS, new Item.Properties()));

    public final static Item POTESTIUM_LIROTH_HELMET = registerArmorItem("potestium_liroth_helmet", new PotestiumHelmItem(LirothArmorMaterial.POTESTIUM_LIROTH, ArmorItem.Type.HELMET, new Item.Properties()));
    public final static Item POTESTIUM_LIROTH_CHESTPLATE = registerArmorItem("potestium_liroth_chestplate", new ArmorItem(LirothArmorMaterial.POTESTIUM_LIROTH, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public final static Item POTESTIUM_LIROTH_LEGGINGS = registerArmorItem("potestium_liroth_leggings", new ArmorItem(LirothArmorMaterial.POTESTIUM_LIROTH, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public final static Item POTESTIUM_LIROTH_BOOTS = registerArmorItem("potestium_liroth_boots", new ArmorItem(LirothArmorMaterial.POTESTIUM_LIROTH, ArmorItem.Type.BOOTS, new Item.Properties()));

    public final static Item QUANTUM_LIROTH_SWORD = registerTool("quantum_liroth_sword", new BaseSwordItem(LirothToolMaterial.QUANTUM_LIROTH, 3, -2.4F, new Item.Properties()));
    public final static Item QUANTUM_LIROTH_SHOVEL = registerTool("quantum_liroth_shovel", new BaseShovelItem(LirothToolMaterial.QUANTUM_LIROTH, 1.5F, -3.0F, new Item.Properties()));
    public final static Item QUANTUM_LIROTH_PICKAXE = registerTool("quantum_liroth_pickaxe", new BasePickaxeItem(LirothToolMaterial.QUANTUM_LIROTH, 1, -2.8F, new Item.Properties()));
    public final static Item QUANTUM_LIROTH_AXE = registerTool("quantum_liroth_axe", new BaseAxeItem(LirothToolMaterial.QUANTUM_LIROTH, 6.0F, -3.0F, new Item.Properties()));
    public final static Item QUANTUM_LIROTH_HOE = registerTool("quantum_liroth_hoe", new BaseHoeItem(LirothToolMaterial.QUANTUM_LIROTH, -3, 0.0F, new Item.Properties()));

    public final static Item FORSAKEN_CORPSE_SPAWN_EGG = registerEgg("forsaken_corpse_spawn_egg", LirothEntities.FORSAKEN_CORPSE, 1842204, 10551525);
    public final static Item FUNGAL_FIEND_SPAWN_EGG = registerEgg("fungal_fiend_spawn_egg", LirothEntities.FUNGAL_FIEND, 1315860, 2031360);
    public final static Item SKELETAL_FREAK_SPAWN_EGG = registerEgg("skeletal_freak_spawn_egg", LirothEntities.SKELETAL_FREAK, 1513239, 16711900);
    public final static Item WARP_SPAWN_EGG = registerEgg("warp_spawn_egg", LirothEntities.WARP, 524305, 4260003);
    public final static Item SOUL_ARACHNID_SPAWN_EGG = registerEgg("soul_arachnid_spawn_egg", LirothEntities.WARP, 73758, 5078138);
    public final static Item PIER_PEEP_SPAWN_EGG = registerEgg("pier_peep_spawn_egg", LirothEntities.PIER_PEEP, 1638400, 11665663);
    public final static Item SHADE_SPAWN_EGG = registerEgg("shade_spawn_egg", LirothEntities.SHADE, 328965, 1579032);
    public final static Item PROWLER_SPAWN_EGG = registerEgg("prowler_spawn_egg", LirothEntities.PROWLER, 16312063, 4260003);
    public final static Item FREAKSHOW_SPAWN_EGG = registerEgg("freakshow_spawn_egg", LirothEntities.FREAKSHOW, 524305, 1579032);
    public final static Item LIROTHIAN_MIMIC_SPAWN_EGG = registerEgg("lirothian_mimic_spawn_egg", LirothEntities.LIROTHIAN_MIMIC, 41655, 10551525);
    public final static Item VILE_SHARK_SPAWN_EGG = registerEgg("vile_shark_spawn_egg", LirothEntities.VILE_SHARK, 5963996, 7667865);

    public final static Item LIROTH_MUSIC_DISC = registerDisc("liroth_music_disc", LirothJukeboxSongs.LIROTH);
    public final static Item PIER_MUSIC_DISC = registerDisc("pier_music_disc", LirothJukeboxSongs.PIER);
    public final static Item SPICED_MUSIC_DISC = registerDisc("spiced_music_disc", LirothJukeboxSongs.SPICED);
    public final static Item DAMNATION_MUSIC_DISC = registerDisc("damnation_music_disc", LirothJukeboxSongs.DAMNATION);
    public final static Item ESCAPE_MUSIC_DISC = registerDisc("escape_music_disc", LirothJukeboxSongs.ESCAPE);
    public final static Item SPITE_MUSIC_DISC = registerDisc("spite_music_disc", LirothJukeboxSongs.SPITE);
    public final static Item DESOLATION_MUSIC_DIST = registerDisc("desolation_music_disc", LirothJukeboxSongs.DESOLATION);

    public final static Item LIROTH_FLUID_BUCKET = registerItem("liroth_fluid_bucket", new BucketItem(LirothFluids.LIROTH_FLUID_STILL.getSource(), new Item.Properties()));
    public final static Item MOLTEN_SPINERIOS_BUCKET = registerItem("molten_spinerios_bucket", new BucketItem(LirothFluids.MOLTEN_SPINERIOS_STILL.getSource(), new Item.Properties()));

    public final static Item FUNGAL_TORCH = registerItem(
            "fungal_torch",
            new StandingAndWallBlockItem(
                    LirothBlocks.FUNGAL_TORCH,
                    LirothBlocks.WALL_FUNGAL_TORCH,
                    new Item.Properties(),
                    Direction.DOWN));
    public final static Item LIROTHIAN_PETROLEUM_TORCH = registerItem(
            "lirothian_petroleum_torch",
            new StandingAndWallBlockItem(
                    LirothBlocks.LIROTHIAN_PETROLEUM_TORCH,
                    LirothBlocks.WALL_LIROTHIAN_PETROLEUM_TORCH,
                    new Item.Properties(),
                    Direction.DOWN));

    public static List<Item> getModItems() {
        return getItemRegistry().allItems().toList();
    }

    public static Item registerDisc(String name, ResourceKey<JukeboxSong> sound) {
        Item item = BaseDiscItem.create(sound, BehaviourBuilders.createDisc());
        RecordItemModelProvider.add(item);
        getItemRegistry().register(name, item, CommonItemTags.MUSIC_DISCS);
        ITEMS.add(item);
        return item;
    }

    public static Item registerItem(String name) {
        return registerItem(name, new ModelProviderItem(makeItemSettings()));
    }

    public static Item registerItem(String name, Item item) {
        ITEMS.add(item);
        return getItemRegistry().register(name, item);
    }

    public static Item registerArmorItem(String name, Item item) {
        EQUIPMENT.add(item);
        return getItemRegistry().register(name, item);
    }

    public static TieredItem registerTool(String name, TieredItem item) {
        EQUIPMENT.add(item);
        return getItemRegistry().registerAsTool(name, item);
    }

    public static Item registerEgg(String name, EntityType<? extends Mob> type, int background, int dots) {
        Item item = getItemRegistry().registerEgg(name, new BaseSpawnEggItem(type, background, dots, makeItemSettings()));
        SPAWN_EGGS.add(item);
        return item;
    }

    public static Item registerFood(String name, int hunger, float saturation, MobEffectInstance... effects) {
        Item item = getItemRegistry().registerFood(name, ModelProviderItem::new, hunger, saturation, effects);
        ITEMS.add(item);
        return item;
    }

    public static Item registerFood(String name, FoodProperties foodComponent) {
        Item item = getItemRegistry().register(name, new ModelProviderItem(getItemRegistry()
                .createDefaultItemSettings()
                .food(foodComponent)));
        ITEMS.add(item);
        return item;
    }

    public static Item registerDrink(String name, int hunger, float saturation) {
        Item item = getItemRegistry().registerDrink(name, BaseDrinkItem::new, hunger, saturation);
        ITEMS.add(item);
        return item;
    }

    public static Item.Properties makeItemSettings() {
        return new Item.Properties();
    }

    public static List<Item> getItems() {
        return ITEMS;
    }

    public static List<Item> getCombatItems() {
        return EQUIPMENT;
    }

    public static List<Item> getSpawnEggs() {
        return SPAWN_EGGS;
    }

    @NotNull
    public static ItemRegistry getItemRegistry() {
        if (ITEMS_REGISTRY == null) {
            ITEMS_REGISTRY = ItemRegistry.forMod(Liroth.C);
        }
        return ITEMS_REGISTRY;
    }

    @ApiStatus.Internal
    public static void ensureStaticallyLoaded() {
    }
}