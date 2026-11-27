package com.decodinator.liroth.core.recipetypes;

import com.decodinator.liroth.util.RandomizedOutputIngredient;
import com.decodinator.liroth.util.RandomizedOutputIngredient2;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class SimpleSplitterSerializer<T extends AbstractSplitterRecipe> {
	private final SimpleSplitterSerializer.CookieBaker<T> factory;
	private final MapCodec<T> codec;
	private final StreamCodec<RegistryFriendlyByteBuf, T> streamCodec;
	private final RecipeSerializer<T> serializer;

	public SimpleSplitterSerializer(CookieBaker<T> p_44330_, int p_44331_) {
		this.factory = p_44330_;
		this.codec = RecordCodecBuilder.mapCodec(
				p_300831_ -> p_300831_.group(
								Identifier.CODEC.fieldOf("id").forGetter(p_300830_ -> p_300830_.id),
								Codec.STRING.optionalFieldOf("group", "").forGetter(p_300832_ -> p_300832_.group),
								Ingredient.CODEC.fieldOf("ingredient").forGetter(p_300833_ -> p_300833_.ingredient),
								ItemStackTemplate.CODEC.fieldOf("result").forGetter(p_300827_ -> p_300827_.result),
								RandomizedOutputIngredient.CODEC.fieldOf("bonus").forGetter(p_300827_ -> p_300827_.bonus),
								RandomizedOutputIngredient2.CODEC.fieldOf("bonus2").forGetter(p_300827_ -> p_300827_.bonus2),
								Codec.FLOAT.fieldOf("experience").orElse(0.0F).forGetter(p_300826_ -> p_300826_.experience),
								Codec.INT.fieldOf("cookingtime").orElse(p_44331_).forGetter(p_300834_ -> p_300834_.cookingTime)
						)
						.apply(p_300831_, factory::create)
		);
		this.streamCodec = StreamCodec.of(this::toNetwork, this::fromNetwork);
		this.serializer = new RecipeSerializer<>(this.codec, this.streamCodec);
	}


	public MapCodec<T> codec() {
		return this.codec;
	}

	public StreamCodec<RegistryFriendlyByteBuf, T> streamCodec() {
		return this.streamCodec;
	}

	public RecipeSerializer<T> serializer() {
		return this.serializer;
	}


	private T fromNetwork(RegistryFriendlyByteBuf buffer) {
		Identifier id = buffer.readIdentifier();
		String group = buffer.readUtf();
		Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
		ItemStackTemplate result = ItemStackTemplate.STREAM_CODEC.decode(buffer);
		RandomizedOutputIngredient bonus = RandomizedOutputIngredient.STREAM_CODEC.decode(buffer);
		RandomizedOutputIngredient2 bonus2 = RandomizedOutputIngredient2.STREAM_CODEC.decode(buffer);
		float experience = buffer.readFloat();
		int cookingTime = buffer.readVarInt();
		return this.factory.create(id, group, ingredient, result, bonus, bonus2, experience, cookingTime);
	}


	private void toNetwork(RegistryFriendlyByteBuf buffer, T recipe) {
		buffer.writeIdentifier(recipe.id);
		buffer.writeUtf(recipe.group);
		Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient);
		ItemStackTemplate.STREAM_CODEC.encode(buffer, recipe.result);
		RandomizedOutputIngredient.STREAM_CODEC.encode(buffer, recipe.bonus);
		RandomizedOutputIngredient2.STREAM_CODEC.encode(buffer, recipe.bonus2);
		buffer.writeFloat(recipe.experience);
		buffer.writeVarInt(recipe.cookingTime);
	}

	public interface CookieBaker<T extends AbstractSplitterRecipe> {
		T create(Identifier p_44353_, String p_44354_, Ingredient p_44355_, ItemStackTemplate p_44356_, RandomizedOutputIngredient bonus, RandomizedOutputIngredient2 bonus2, float p_44357_, int p_44358_);
	}
}