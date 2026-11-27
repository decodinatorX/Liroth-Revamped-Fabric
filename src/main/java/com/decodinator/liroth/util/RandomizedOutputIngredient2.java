package com.decodinator.liroth.util;

// STOLEN FROM FLIB
// "https://github.com/Lothrazar/FLib/blob/trunk/1.21.1/src/main/java/com/lothrazar/library/recipe/ingredient/RandomizedOutputIngredient.java"

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;

import java.util.Optional;

public class RandomizedOutputIngredient2 {

    public Optional<ItemStackTemplate> bonus;
    public int percent;

    public RandomizedOutputIngredient2(int readInt, Optional<ItemStackTemplate> readItem) {
        this.percent = readInt;
        this.bonus = readItem;
    }

    public ItemStack getStack() {
        return this.bonus.map(ItemStackTemplate::create).orElse(ItemStack.EMPTY);
    }

    public static final Codec<RandomizedOutputIngredient2> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.optionalFieldOf("percent", 0).forGetter(r -> r.percent),
            ItemStackTemplate.CODEC.optionalFieldOf("bonus").forGetter(r -> r.bonus)
    ).apply(instance, RandomizedOutputIngredient2::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, RandomizedOutputIngredient2> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, r -> r.percent,
            ByteBufCodecs.optional(ItemStackTemplate.STREAM_CODEC), r -> r.bonus,
            RandomizedOutputIngredient2::new
    );
}