package com.decodinator.liroth.util;

// STOLEN FROM FLIB
// "https://github.com/Lothrazar/FLib/blob/trunk/1.21.1/src/main/java/com/lothrazar/library/recipe/ingredient/RandomizedOutputIngredient.java"

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

public class RandomizedOutputIngredient2 {
    public ItemStack bonus;
    public int percent;

    public RandomizedOutputIngredient2(int readInt, ItemStack readItem) {
          this.percent = readInt;
          this.bonus = readItem;
    }

    public static final Codec<RandomizedOutputIngredient2> CODEC = RecordCodecBuilder.create(instance -> instance.group(
          Codec.INT.optionalFieldOf("percent", 0).forGetter(r -> r.percent),
          ItemStack.OPTIONAL_CODEC.optionalFieldOf("bonus", ItemStack.EMPTY).forGetter(r -> r.bonus)
    ).apply(instance, RandomizedOutputIngredient2::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, RandomizedOutputIngredient2> STREAM_CODEC = StreamCodec.composite(
          ByteBufCodecs.VAR_INT, r -> r.percent,
          ItemStack.OPTIONAL_STREAM_CODEC, r -> r.bonus,
          RandomizedOutputIngredient2::new
    );
}