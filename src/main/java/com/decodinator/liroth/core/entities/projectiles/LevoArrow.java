package com.decodinator.liroth.core.entities.projectiles;

import com.decodinator.liroth.core.registry.LirothEntities;
import com.decodinator.liroth.core.registry.LirothItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SpellParticleOption;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;

public class LevoArrow extends AbstractArrow {
    private int duration = 20;

    public LevoArrow(EntityType<? extends LevoArrow> entityType, Level level) {
        super(entityType, level);
    }

    public LevoArrow(Level level, LivingEntity owner, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
        super(LirothEntities.LEVO_ARROW, owner, level, pickupItemStack, firedFromWeapon);
    }

    public LevoArrow(Level level, double x, double y, double z, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
        super(LirothEntities.LEVO_ARROW, x, y, z, level, pickupItemStack, firedFromWeapon);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide() && !this.isInGround()) {
            this.level().addParticle(SpellParticleOption.create(ParticleTypes.INSTANT_EFFECT, -1, 1.0F), this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
        }
    }

    @Override
    protected void doPostHurtEffects(LivingEntity target) {
        super.doPostHurtEffects(target);
        MobEffectInstance effect = new MobEffectInstance(MobEffects.LEVITATION, this.duration, 20);
        target.addEffect(effect, this.getEffectSource());
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.duration = input.getIntOr("Duration", 20);
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putInt("Duration", this.duration);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(LirothItems.LEVO_ARROW);
    }
}