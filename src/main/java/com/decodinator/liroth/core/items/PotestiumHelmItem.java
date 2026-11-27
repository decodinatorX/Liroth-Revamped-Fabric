package com.decodinator.liroth.core.items;

import com.decodinator.liroth.client.rendering.items.PotestiumHelmetRenderer;
import com.decodinator.liroth.core.items.material.LirothArmorMaterial;
import com.google.common.base.Suppliers;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import com.geckolib.animatable.GeoItem;
import com.geckolib.animatable.client.GeoRenderProvider;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.object.PlayState;
import com.geckolib.renderer.GeoArmorRenderer;
import com.geckolib.util.GeckoLibUtil;
import net.minecraft.world.item.equipment.ArmorType;
import org.jetbrains.annotations.Nullable;


import java.util.function.Consumer;
import java.util.function.Supplier;

public class PotestiumHelmItem extends Item implements GeoItem {
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

	public PotestiumHelmItem()  {
		super(new Properties().humanoidArmor(LirothArmorMaterial.POTESTIUM_LIROTH.value(), ArmorType.HELMET));
	}

	@Override
	public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
		consumer.accept(new GeoRenderProvider() {
			private final Supplier<PotestiumHelmetRenderer<?>> renderer = Suppliers.memoize(PotestiumHelmetRenderer::new);

			@Nullable
			@Override
			public GeoArmorRenderer<?, ?> getGeoArmorRenderer(ItemStack itemStack, EquipmentSlot equipmentSlot) {
				return this.renderer.get();
			}
		});
	}

	@Override
	public void registerControllers(com.geckolib.animatable.manager.AnimatableManager.ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>("Main", 20, state -> {
			return PlayState.CONTINUE;
		}));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}