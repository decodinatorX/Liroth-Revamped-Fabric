package com.decodinator.liroth.core.recipetypes;

import com.decodinator.liroth.util.RandomizedOutputIngredient;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

public class SimpleQuantumSerializer<T extends AbstractQuantumRecipe>implements RecipeSerializer<T> {
	private final SimpleQuantumSerializer.CookieBaker<T> factory;
	private final MapCodec<T> codec;
	private final StreamCodec<RegistryFriendlyByteBuf, T> streamCodec;

	public SimpleQuantumSerializer(CookieBaker<T> p_44330_, int p_44331_) {
		this.factory = p_44330_;
		this.codec = RecordCodecBuilder.mapCodec(
				p_300831_ -> p_300831_.group(
								Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
								Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(recipe -> recipe.ingredient),
								ItemStack.CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
								RandomizedOutputIngredient.CODEC.fieldOf("bonus").forGetter(recipe -> recipe.bonus),
								Codec.FLOAT.fieldOf("experience").orElse(0.0F).forGetter(recipe -> recipe.experience),
								Codec.INT.fieldOf("cookingtime").orElse(p_44331_).forGetter(recipe -> recipe.cookingTime)
						)
						.apply(p_300831_, factory::create)
		);
		this.streamCodec = StreamCodec.of(this::toNetwork, this::fromNetwork);
	}

	@Override
	public @NotNull MapCodec<T> codec() {
		return this.codec;
	}

	@Override
	public @NotNull StreamCodec<RegistryFriendlyByteBuf, T> streamCodec() {
		return this.streamCodec;
	}

	private T fromNetwork(RegistryFriendlyByteBuf buffer) {
		String s = buffer.readUtf();
		Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
		ItemStack itemstack = ItemStack.STREAM_CODEC.decode(buffer);
		RandomizedOutputIngredient bonus = RandomizedOutputIngredient.STREAM_CODEC.decode(buffer);
		float f = buffer.readFloat();
		int i = buffer.readVarInt();
		return this.factory.create(s, ingredient, itemstack, bonus, f, i);
	}


	private void toNetwork(RegistryFriendlyByteBuf buffer, T recipe) {
		buffer.writeUtf(recipe.group);
		Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient);
		ItemStack.STREAM_CODEC.encode(buffer, recipe.result);
		RandomizedOutputIngredient.STREAM_CODEC.encode(buffer, recipe.bonus);
		buffer.writeFloat(recipe.experience);
		buffer.writeVarInt(recipe.cookingTime);
	}

	public interface CookieBaker<T extends AbstractQuantumRecipe> {
		T create(String group, Ingredient ingredient, ItemStack result, RandomizedOutputIngredient bonus, float experience, int cookingTime);
	}
}