package com.decodinator.liroth.core.registry;

import com.decodinator.liroth.Liroth;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import org.betterx.wover.potions.api.PotionManager;

public final class LirothPotions {
    public static Potion STRONGER_LEAPING;
    public static Potion STRONGER_SWIFTNESS;
    public static Potion STRONGER_SLOWNESS;
    public static Potion STRONGER_TURTLE_MASTER;
    public static Potion STRONGER_HEALING;
    public static Potion STRONGER_HARMING;
    public static Potion STRONGER_POISON;
    public static Potion STRONGER_REGENERATION;
    public static Potion STRONGER_STRENGTH;

    private LirothPotions() {}

    public static void register() {
        STRONGER_LEAPING = registerPotion("stronger_leaping", new Potion("leaping", new MobEffectInstance(MobEffects.JUMP_BOOST, 3600, 2)));
        STRONGER_SWIFTNESS = registerPotion("stronger_swiftness", new Potion("swiftness", new MobEffectInstance(MobEffects.SPEED, 3600, 2)));
        STRONGER_SLOWNESS = registerPotion("stronger_slowness", new Potion("slowness", new MobEffectInstance(MobEffects.SLOWNESS, 800, 5)));
        STRONGER_TURTLE_MASTER = registerPotion("stronger_turtle_master", new Potion("turtle_master",
                new MobEffectInstance(MobEffects.SLOWNESS, 800, 7),
                new MobEffectInstance(MobEffects.RESISTANCE, 800, 5)));
        STRONGER_HEALING = registerPotion("stronger_healing", new Potion("healing", new MobEffectInstance(MobEffects.INSTANT_HEALTH, 1, 2)));
        STRONGER_HARMING = registerPotion("stronger_harming", new Potion("harming", new MobEffectInstance(MobEffects.INSTANT_DAMAGE, 1, 2)));
        STRONGER_POISON = registerPotion("stronger_poison", new Potion("poison", new MobEffectInstance(MobEffects.POISON, 1800, 2)));
        STRONGER_REGENERATION = registerPotion("stronger_regeneration", new Potion("regeneration", new MobEffectInstance(MobEffects.REGENERATION, 1800, 2)));
        STRONGER_STRENGTH = registerPotion("stronger_strength", new Potion("strength", new MobEffectInstance(MobEffects.STRENGTH, 3600, 2)));

        PotionManager.BOOTSTRAP_POTIONS.subscribe(builder -> {
            builder.addMix(Potions.STRONG_LEAPING, LirothItems.GLEEMSTONE_DUST, BuiltInRegistries.POTION.wrapAsHolder(STRONGER_LEAPING));
            builder.addMix(Potions.STRONG_SWIFTNESS, LirothItems.GLEEMSTONE_DUST, BuiltInRegistries.POTION.wrapAsHolder(STRONGER_SWIFTNESS));
            builder.addMix(Potions.STRONG_SLOWNESS, LirothItems.GLEEMSTONE_DUST, BuiltInRegistries.POTION.wrapAsHolder(STRONGER_SLOWNESS));
            builder.addMix(Potions.STRONG_TURTLE_MASTER, LirothItems.GLEEMSTONE_DUST, BuiltInRegistries.POTION.wrapAsHolder(STRONGER_TURTLE_MASTER));
            builder.addMix(Potions.STRONG_HEALING, LirothItems.GLEEMSTONE_DUST, BuiltInRegistries.POTION.wrapAsHolder(STRONGER_HEALING));
            builder.addMix(Potions.STRONG_HARMING, LirothItems.GLEEMSTONE_DUST, BuiltInRegistries.POTION.wrapAsHolder(STRONGER_HARMING));
            builder.addMix(Potions.STRONG_POISON, LirothItems.GLEEMSTONE_DUST, BuiltInRegistries.POTION.wrapAsHolder(STRONGER_POISON));
            builder.addMix(Potions.STRONG_REGENERATION, LirothItems.GLEEMSTONE_DUST, BuiltInRegistries.POTION.wrapAsHolder(STRONGER_REGENERATION));
            builder.addMix(Potions.STRONG_STRENGTH, LirothItems.GLEEMSTONE_DUST, BuiltInRegistries.POTION.wrapAsHolder(STRONGER_STRENGTH));
        });
    }

    private static Potion registerPotion(String name, Potion potion) {
        return Registry.register(BuiltInRegistries.POTION,
                Identifier.fromNamespaceAndPath(Liroth.MOD_ID, name), potion);
    }
}
