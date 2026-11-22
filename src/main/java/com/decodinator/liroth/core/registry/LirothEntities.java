package com.decodinator.liroth.core.registry;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.core.entities.projectiles.LevoArrow;
import com.decodinator.liroth.core.items.BeamItem;
import com.decodinator.liroth.core.entities.ForsakenCorpseEntity;
import com.decodinator.liroth.core.entities.FreakshowEntity;
import com.decodinator.liroth.core.entities.FungalFiendEntity;
import com.decodinator.liroth.core.entities.LirothianMimicEntity;
import com.decodinator.liroth.core.entities.PierPeepEntity;
import com.decodinator.liroth.core.entities.ProwlerEntity;
import com.decodinator.liroth.core.entities.ShadeEntity;
import com.decodinator.liroth.core.entities.SkeletalFreakEntity;
import com.decodinator.liroth.core.entities.SoulArachnidEntity;
import com.decodinator.liroth.core.entities.VileSharkEntity;
import com.decodinator.liroth.core.entities.WarpEntity;
import com.decodinator.liroth.core.entities.projectiles.BeamLaserProjectileEntity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.item.ItemStack;


public class LirothEntities {
    
    public static final EntityType<FungalFiendEntity> FUNGAL_FIEND = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "fungal_fiend"),
            monster(FungalFiendEntity::new, EntityDimensions.fixed(0.6f, 2f))
    );
    
    public static final EntityType<ForsakenCorpseEntity> FORSAKEN_CORPSE = Registry.register(
    		BuiltInRegistries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "forsaken_corpse"),
            monster(ForsakenCorpseEntity::new, EntityDimensions.fixed(0.6f, 1.95f))
    );
    
    public static final EntityType<SkeletalFreakEntity> SKELETAL_FREAK = Registry.register(
    		BuiltInRegistries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "skeletal_freak"),
            monster(SkeletalFreakEntity::new, EntityDimensions.fixed(0.6f, 1.95f))
    );
    
    public static final EntityType<WarpEntity> WARP = Registry.register(
    		BuiltInRegistries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "warp"),
            monster(WarpEntity::new, EntityDimensions.fixed(0.6f, 2.9f))
    );
    
    public static final EntityType<SoulArachnidEntity> SOUL_ARACHNID = Registry.register(
    		BuiltInRegistries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "soul_arachnid"),
            monster(SoulArachnidEntity::new, EntityDimensions.fixed(0.6f, 2.9f))
    );
    
    public static final EntityType<PierPeepEntity> PIER_PEEP = Registry.register(
    		BuiltInRegistries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "pier_peep"),
            monster(PierPeepEntity::new, EntityDimensions.fixed(0.6f, 2.9f))
    );
    
    public static final EntityType<ShadeEntity> SHADE = Registry.register(
    		BuiltInRegistries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "shade"),
            monster(ShadeEntity::new, EntityDimensions.fixed(0.6f, 2.9f))
    );
    
    public static final EntityType<ProwlerEntity> PROWLER = Registry.register(
    		BuiltInRegistries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "prowler"),
            monster(ProwlerEntity::new, EntityDimensions.fixed(0.6f, 2.9f))
    );
    
    public static final EntityType<FreakshowEntity> FREAKSHOW = Registry.register(
    		BuiltInRegistries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "freakshow"),
            monster(FreakshowEntity::new, EntityDimensions.fixed(0.6f, 2.9f))
    );
    
    public static final EntityType<VileSharkEntity> VILE_SHARK = Registry.register(
    		BuiltInRegistries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "vile_shark"),
            waterCreature(VileSharkEntity::new, EntityDimensions.fixed(0.6f, 2.9f))
    );
    
    public static final EntityType<LirothianMimicEntity> LIROTHIAN_MIMIC = Registry.register(
    		BuiltInRegistries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "lirothian_mimic"),
            monster(LirothianMimicEntity::new, EntityDimensions.fixed(0.6f, 2.9f))
    );
    
	public static final EntityType<BeamLaserProjectileEntity> BEAM_LASER_PROJECTILE_ENTITY = Registry.register(
			BuiltInRegistries.ENTITY_TYPE,
			ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "beam_laser_projectile_entity"),
			FabricEntityTypeBuilder.<BeamLaserProjectileEntity>create(MobCategory.MISC, BeamLaserProjectileEntity::new)
					.dimensions(EntityDimensions.fixed(0.25F, 0.25F)) // dimensions in Minecraft units of the projectile
					.trackRangeBlocks(10) // necessary for all thrown projectiles (as it prevents it from breaking, lol)
					.build() // VERY IMPORTANT DON'T DELETE FOR THE LOVE OF GOD PSLSSSSSS
	);

    public static final EntityType<LevoArrow> LEVO_ARROW = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "levo_arrow"),
            FabricEntityTypeBuilder.<LevoArrow>create(MobCategory.MISC, LevoArrow::new)
                    .dimensions(EntityDimensions.fixed(0.25F, 0.25F))
                    .trackRangeBlocks(10)
                    .build()
    );

    public static AbstractArrow createBeamProjectile(LivingEntity entity, ItemStack stack, float damageModifier) {
    	BeamItem beamItem = (BeamItem)(stack.getItem() instanceof BeamItem ? stack.getItem() : LirothItems.COOL_BEAM);
    	AbstractArrow persistentProjectileEntity = beamItem.createArrow(entity.level(), stack, entity, entity.getWeaponItem());
        return persistentProjectileEntity;
    }

    private static <T extends Monster> EntityType<T> monster(EntityType.EntityFactory<T> factory, EntityDimensions dimensions) {
        return FabricEntityTypeBuilder.<T>createMob()
                .spawnGroup(MobCategory.MONSTER)
                .entityFactory(factory)
                .dimensions(dimensions)
                .spawnRestriction(SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                        (type, level, spawnType, pos, random) -> Monster.checkMonsterSpawnRules(type, level, spawnType, pos, random)
                                && Liroth.allowNaturalLirothMobSpawn(level, spawnType, pos))
                .build();
    }

    private static <T extends WaterAnimal> EntityType<T> waterCreature(EntityType.EntityFactory<T> factory, EntityDimensions dimensions) {
        return FabricEntityTypeBuilder.<T>createMob()
                .spawnGroup(MobCategory.WATER_CREATURE)
                .entityFactory(factory)
                .dimensions(dimensions)
                .spawnRestriction(SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                        (type, level, spawnType, pos, random) -> WaterAnimal.checkSurfaceWaterAnimalSpawnRules(type, level, spawnType, pos, random)
                                && Liroth.allowNaturalLirothMobSpawn(level, spawnType, pos))
                .build();
    }

    static {
		net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry.register(FUNGAL_FIEND, FungalFiendEntity.createFungalFiendAttributes());
		net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry.register(FORSAKEN_CORPSE, ForsakenCorpseEntity.createForsakenCorpseAttributes());
		net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry.register(SKELETAL_FREAK, SkeletalFreakEntity.createSkeletalFreakAttributes());
		net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry.register(WARP, WarpEntity.createWarpAttributes());
		net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry.register(SOUL_ARACHNID, SoulArachnidEntity.createSoulArachnidAttributes());
		net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry.register(PIER_PEEP, PierPeepEntity.createPierPeepAttributes());
		net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry.register(SHADE, ShadeEntity.createShadeAttributes());
		net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry.register(PROWLER, ProwlerEntity.createProwlerAttributes());
		net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry.register(FREAKSHOW, FreakshowEntity.createFreakshowAttributes());
		net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry.register(VILE_SHARK, VileSharkEntity.createVileSharkAttributes());
		net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry.register(LIROTHIAN_MIMIC, LirothianMimicEntity.createLirothianMimicAttributes());

        
    }

    public static void init() {
    }
}
