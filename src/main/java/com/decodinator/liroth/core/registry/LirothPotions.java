package com.decodinator.liroth.core.registry;

import com.decodinator.liroth.Liroth;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;

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
        STRONGER_LEAPING = registerPotion("stronger_leaping", new Potion("leaping", new MobEffectInstance(MobEffects.JUMP, 3600, 2)));
        STRONGER_SWIFTNESS = registerPotion("stronger_swiftness", new Potion("swiftness", new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 3600, 2)));
        STRONGER_SLOWNESS = registerPotion("stronger_slowness", new Potion("slowness", new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 800, 5)));
        STRONGER_TURTLE_MASTER = registerPotion("stronger_turtle_master", new Potion("turtle_master",
                new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 800, 7),
                new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 800, 5)));
        STRONGER_HEALING = registerPotion("stronger_healing", new Potion("healing", new MobEffectInstance(MobEffects.HEAL, 1, 2)));
        STRONGER_HARMING = registerPotion("stronger_harming", new Potion("harming", new MobEffectInstance(MobEffects.HARM, 1, 2)));
        STRONGER_POISON = registerPotion("stronger_poison", new Potion("poison", new MobEffectInstance(MobEffects.POISON, 1800, 2)));
        STRONGER_REGENERATION = registerPotion("stronger_regeneration", new Potion("regeneration", new MobEffectInstance(MobEffects.REGENERATION, 1800, 2)));
        STRONGER_STRENGTH = registerPotion("stronger_strength", new Potion("strength", new MobEffectInstance(MobEffects.DAMAGE_BOOST, 3600, 2)));

        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
            builder.registerPotionRecipe(Potions.STRONG_LEAPING,
                    Ingredient.of(LirothItems.GLEEMSTONE_DUST), BuiltInRegistries.POTION.wrapAsHolder(STRONGER_LEAPING));
            builder.registerPotionRecipe(Potions.STRONG_SWIFTNESS,
                    Ingredient.of(LirothItems.GLEEMSTONE_DUST), BuiltInRegistries.POTION.wrapAsHolder(STRONGER_SWIFTNESS));
            builder.registerPotionRecipe(Potions.STRONG_SLOWNESS,
                    Ingredient.of(LirothItems.GLEEMSTONE_DUST), BuiltInRegistries.POTION.wrapAsHolder(STRONGER_SLOWNESS));
            builder.registerPotionRecipe(Potions.STRONG_TURTLE_MASTER,
                    Ingredient.of(LirothItems.GLEEMSTONE_DUST), BuiltInRegistries.POTION.wrapAsHolder(STRONGER_TURTLE_MASTER));
            builder.registerPotionRecipe(Potions.STRONG_HEALING,
                    Ingredient.of(LirothItems.GLEEMSTONE_DUST), BuiltInRegistries.POTION.wrapAsHolder(STRONGER_HEALING));
            builder.registerPotionRecipe(Potions.STRONG_HARMING,
                    Ingredient.of(LirothItems.GLEEMSTONE_DUST), BuiltInRegistries.POTION.wrapAsHolder(STRONGER_HARMING));
            builder.registerPotionRecipe(Potions.STRONG_POISON,
                    Ingredient.of(LirothItems.GLEEMSTONE_DUST), BuiltInRegistries.POTION.wrapAsHolder(STRONGER_POISON));
            builder.registerPotionRecipe(Potions.STRONG_REGENERATION,
                    Ingredient.of(LirothItems.GLEEMSTONE_DUST), BuiltInRegistries.POTION.wrapAsHolder(STRONGER_REGENERATION));
            builder.registerPotionRecipe(Potions.STRONG_STRENGTH,
                    Ingredient.of(LirothItems.GLEEMSTONE_DUST), BuiltInRegistries.POTION.wrapAsHolder(STRONGER_STRENGTH));
        });
    }

    private static Potion registerPotion(String name, Potion potion) {
        return Registry.register(BuiltInRegistries.POTION,
                ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, name), potion);
    }
}
