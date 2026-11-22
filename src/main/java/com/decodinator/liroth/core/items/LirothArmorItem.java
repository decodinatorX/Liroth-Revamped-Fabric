package com.decodinator.liroth.core.items;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.core.registry.LirothItems;
import org.betterx.bclib.interfaces.ItemModelProvider;
import org.betterx.wover.complex.api.equipment.ArmorSlot;
import org.betterx.wover.complex.api.equipment.ArmorTier;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.ItemAttributeModifiers;

public class LirothArmorItem extends ArmorItem implements ItemModelProvider {
    public static final ResourceLocation BASE_BLINDNESS_RESISTANCE = Liroth.C.mk("base_blindness_resistance");
    public static final ResourceLocation BASE_KNOCKBACK_RESISTANCE = Liroth.C.mk("base_knockback_resistance");
    public static final ResourceLocation MAX_HEALTH_BOOST = Liroth.C.mk("max_health_boost");
    public static final ResourceLocation TOUGHNESS_BOOST = Liroth.C.mk("toughness_boost");
    public static final ResourceLocation ARMOR_BOOST = Liroth.C.mk("armor_boost");

    public static Properties createDefaultEndArmorSettings(ArmorSlot slot, ArmorTier tier) {
        var values = tier.getValues(slot);
        if (values == null) {
            throw new IllegalArgumentException("Values for " + slot + " are not defined for " + tier);
        }

        return LirothItems.defaultSettings().durability(slot.armorType.getDurability(values.durability()));
    }

    public static ItemAttributeModifiers.Builder startAttributeBuilder(
            ArmorSlot slot,
            ArmorTier tier
    ) {
        return startAttributeBuilder(
                slot,
                tier,
                tier.armorMaterial.value().getDefense(slot.armorType) / 1.25f,
                tier.armorMaterial.value().toughness() / 1.25f,
                0.0f
        );
    }

    public static ItemAttributeModifiers.Builder startAttributeBuilder(
            ArmorSlot slot,
            ArmorTier tier,
            float defense,
            float toughness,
            float knockbackResistance
    ) {
        final EquipmentSlotGroup slotGroup = slotGroup(slot);
        final ItemAttributeModifiers.Builder builder = ItemAttributeModifiers
                .builder()
                .add(
                        Attributes.ARMOR,
                        new AttributeModifier(
                                armorBoostId(slot),
                                defense,
                                AttributeModifier.Operation.ADD_VALUE
                        ),
                        slotGroup
                )
                .add(
                        Attributes.ARMOR_TOUGHNESS,
                        new AttributeModifier(
                                toughnessBoostId(slot),
                                toughness,
                                AttributeModifier.Operation.ADD_VALUE
                        ),
                        slotGroup
                );

        if (knockbackResistance > 0.0f) {
            builder.add(
                    Attributes.KNOCKBACK_RESISTANCE,
                    new AttributeModifier(
                            knockbackResistanceId(slot),
                            knockbackResistance,
                            AttributeModifier.Operation.ADD_VALUE
                    ),
                    slotGroup
            );
        }

        return builder;
    }

    private static EquipmentSlotGroup slotGroup(ArmorSlot slot) {
        switch (slot) {
            case HELMET_SLOT:
                return EquipmentSlotGroup.HEAD;
            case CHESTPLATE_SLOT:
                return EquipmentSlotGroup.CHEST;
            case LEGGINGS_SLOT:
                return EquipmentSlotGroup.LEGS;
            case BOOTS_SLOT:
                return EquipmentSlotGroup.FEET;
            default:
                return EquipmentSlotGroup.CHEST;
        }
    }

    private static ResourceLocation armorBoostId(ArmorSlot slot) {
        return Liroth.C.mk("armor_boost_" + slot.name);
    }

    private static ResourceLocation toughnessBoostId(ArmorSlot slot) {
        return Liroth.C.mk("toughness_boost_" + slot.name);
    }

    private static ResourceLocation knockbackResistanceId(ArmorSlot slot) {
        return Liroth.C.mk("base_knockback_resistance_" + slot.name);
    }

    public static Properties createDefaultEndArmorSettings(
            ArmorSlot slot,
            ArmorTier tier,
            ItemAttributeModifiers attributes
    ) {
        final var props = createDefaultEndArmorSettings(slot, tier)
                .rarity(Rarity.RARE);
        if (attributes != null) {
            props.attributes(attributes);
        }
        return props;

    }

    public LirothArmorItem(ArmorTier tier, ArmorSlot slot, Properties settings) {
        super(tier.armorMaterial, slot.armorType, settings);
    }
}