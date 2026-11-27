package com.decodinator.liroth.core.items;

import com.decodinator.liroth.core.entities.projectiles.BeamLaserProjectileEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class BeamItem extends ArrowItem {

    public BeamItem() {
        super(new Item.Properties());
    }

    @Override
    public @NotNull AbstractArrow createArrow(Level level, ItemStack ammo, LivingEntity shooter, ItemStack weapon) {
        return BeamLaserProjectileEntity.createBeamProjectile(shooter);
    }
}