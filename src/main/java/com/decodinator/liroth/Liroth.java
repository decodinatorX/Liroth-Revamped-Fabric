package com.decodinator.liroth;

import com.decodinator.liroth.core.blocks.basis.FabricToolModifiedBlockEvents;
import com.decodinator.liroth.core.entities.ShadeEntity;
import com.decodinator.liroth.core.registry.*;
import com.decodinator.liroth.core.worldgen.LirothSeededChunkGenerator;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;

import com.decodinator.liroth.core.registry.LirothStructures;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import org.betterx.wover.core.api.ModCore;

import java.util.logging.Logger;

public class Liroth implements ModInitializer {
	public static String MOD_ID = "liroth";
	private static final double NATURAL_LIROTH_MOB_CAP_RADIUS = 32.0;
	private static final int MAX_NATURAL_LIROTH_MOBS_NEAR_SPAWN = 10;
	public static final Logger LOGGER = Logger.getLogger("liroth");
	public static ModCore C = ModCore.create(MOD_ID);

    static {
        (new RegistrySetBuilder()).add(Registries.CONFIGURED_FEATURE, LirothConfiguredFeatures::bootstrap).add(Registries.PLACED_FEATURE, LirothPlacedFeatures::bootstrap);
    }

    public static final ResourceKey<CreativeModeTab> creativeItemsTab = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), ResourceLocation.fromNamespaceAndPath(MOD_ID, "liroth_items"));
	public static final ResourceKey<CreativeModeTab> creativeBlocksTab = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), ResourceLocation.fromNamespaceAndPath(MOD_ID, "liroth_blocks"));
	public static final ResourceKey<CreativeModeTab> creativeCombatTab = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), ResourceLocation.fromNamespaceAndPath(MOD_ID, "liroth_combat"));
	public static final ResourceKey<CreativeModeTab> creativePlantsTab = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), ResourceLocation.fromNamespaceAndPath(MOD_ID, "liroth_plants"));
	public static final ResourceKey<CreativeModeTab> creativeEntitiesTab = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), ResourceLocation.fromNamespaceAndPath(MOD_ID, "liroth_entities"));

		@Override
		public void onInitialize() {

			FabricToolModifiedBlockEvents.register();
			registerEntityAttackHandler();
			LOGGER.info("Liroth: Begin Loading...");
			LirothRegistries.registerFluids();
			LirothRegistries.registerBlocks();
		    LirothRegistries.registerItems();
			LirothPotions.register();
			LirothRegistries.registerBlockEntities();
		    LirothRegistries.registerEntities();
		    LirothRegistries.registerPortals();
		    LirothRegistries.registerParticles();
		    LirothRegistries.registerSounds();
		    LirothRegistries.registerFeatures();
		    LirothRegistries.registerStructures();
		    LirothRegistries.registerRecipeTypes();
			LirothRegistries.registerMenuTypes();
		    LirothRegistries.registerFuels();
	        Liroth.threadSafeLoadFinish();

			Registry.register(BuiltInRegistries.CHUNK_GENERATOR, ResourceLocation.fromNamespaceAndPath(MOD_ID, "dimension_seeded"), LirothSeededChunkGenerator.CODEC);

			Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, creativeItemsTab, FabricItemGroup.builder().icon(() -> new ItemStack(LirothBlocks.LIROTH_GEM.ingot))
					.displayItems((itemDisplayParameters, output) -> {
								for (var supplier : LirothItems.getItems()) {
									output.accept(supplier);
								}
							}
					).title(Component.translatable("itemGroup.liroth.items")
					).build());

			Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, creativeBlocksTab, FabricItemGroup.builder().icon(() -> new ItemStack(LirothBlocks.LIROTH_GEM_BLOCK.asItem()))
					.displayItems((itemDisplayParameters, output) -> {
								for (var supplier : LirothBlocks.getBlocks()) {
									output.accept(supplier);
								}
							}
					).title(Component.translatable("itemGroup.liroth.blocks")
					).build());

			Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, creativeCombatTab, FabricItemGroup.builder().icon(() -> new ItemStack(LirothBlocks.LIROTH_GEM.sword))
					.displayItems((itemDisplayParameters, output) -> {
								for (var supplier : LirothItems.getCombatItems()) {
									output.accept(supplier);
								}
							}
					).title(Component.translatable("itemGroup.liroth.combat")
					).build());

			Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, creativePlantsTab, FabricItemGroup.builder().icon(() -> new ItemStack(LirothBlocks.LIROTH_ROSE.asItem()))
					.displayItems((itemDisplayParameters, output) -> {
								for (var supplier : LirothBlocks.getPlantBlocks()) {
									output.accept(supplier);
								}
							}
					).title(Component.translatable("itemGroup.liroth.plants")
					).build());

			Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, creativeEntitiesTab, FabricItemGroup.builder().icon(() -> new ItemStack(LirothItems.FORSAKEN_CORPSE_SPAWN_EGG))
					.displayItems((itemDisplayParameters, output) -> {
								for (var supplier : LirothItems.getSpawnEggs()) {
									output.accept(supplier);
								}
							}
					).title(Component.translatable("itemGroup.liroth.entities")
					).build());
		}

		private void registerEntityAttackHandler() {
			AttackEntityCallback.EVENT.register((player, world, hand, target, hitResult) -> {
				if (world.isClientSide
						|| !(target instanceof ShadeEntity shade)
						|| !player.getMainHandItem().is(LirothTags.TORCHES)) {
					return InteractionResult.PASS;
				}

				shade.kill();
				return InteractionResult.FAIL;
			});
		}

		public static boolean allowNaturalLirothMobSpawn(ServerLevelAccessor level, MobSpawnType spawnType, net.minecraft.core.BlockPos pos) {
			if (spawnType != MobSpawnType.NATURAL) {
				return true;
			}

			double radius = NATURAL_LIROTH_MOB_CAP_RADIUS;
			AABB capArea = new AABB(
					pos.getX() - radius, pos.getY() - radius, pos.getZ() - radius,
					pos.getX() + radius, pos.getY() + radius, pos.getZ() + radius);
			long nearbyLirothMobs = level.getEntitiesOfClass(Mob.class, capArea,
					mob -> mob.isAlive()
							&& BuiltInRegistries.ENTITY_TYPE.getKey(mob.getType()).getNamespace().equals(MOD_ID))
					.size();
			return nearbyLirothMobs < MAX_NATURAL_LIROTH_MOBS_NEAR_SPAWN;
		}
		
	    public static class LirothRegistries {
	        public static void registerBlocks() {
	        	Liroth.LOGGER.warning("Liroth: Registering blocks...");
	        	LirothBlocks.ensureStaticallyLoaded();
	            Liroth.LOGGER.info("Liroth: Blocks registered!");
	        }
	        
	        public static void registerFluids() {
	        	Liroth.LOGGER.warning("Liroth: Registering fluids...");
	        	LirothFluids.init();
	            Liroth.LOGGER.info("Liroth: Fluids registered!");
	        }
	    	
	        public static void registerItems() {
	        	Liroth.LOGGER.warning("Liroth: Registering items...");
	        	LirothItems.ensureStaticallyLoaded();
	            Liroth.LOGGER.info("Liroth: Items registered!");
	        }

			public static void registerBlockEntities() {
				Liroth.LOGGER.warning("Liroth: Registering block entities...");
				LirothBlockEntities.init();
				Liroth.LOGGER.info("Liroth: Block Entities registered!");
			}

	        public static void registerEntities() {
	        	Liroth.LOGGER.warning("Liroth: Registering entities...");
	        	LirothEntities.init();
	            Liroth.LOGGER.info("Liroth: Entities registered!");
	        }
	        
	        public static void registerPortals() {
	        	Liroth.LOGGER.warning("Liroth: Registering portals...");
	        	LirothPortalBuilders.init();
	            Liroth.LOGGER.info("Liroth: Portals registered!");
	        }
	        
	        public static void registerParticles() {
	        	Liroth.LOGGER.warning("Liroth: Registering particles...");
	        	LirothParticles.init();
	            Liroth.LOGGER.info("Liroth: Particles registered!");
	        }
	        
	        public static void registerSounds() {
	        	Liroth.LOGGER.warning("Liroth: Registering sounds...");
	        	LirothSounds.init();
	            Liroth.LOGGER.info("Liroth: Sounds registered!");
	        }
	        
	        public static void registerFeatures() {
	        	Liroth.LOGGER.warning("Liroth: Registering features...");
				LirothFeatures.register();
				Liroth.LOGGER.info("Liroth: Features registered!");
	        }
	        
	        public static void registerStructures() {
	        	Liroth.LOGGER.warning("Liroth: Registering structures...");
				LirothStructures.init();      
				Liroth.LOGGER.info("Liroth: Structures registered!");
	        }
	        
	        public static void registerRecipeTypes() {
	        	Liroth.LOGGER.warning("Liroth: Registering Recipe Types...");
	        	LirothRecipeTypes.init();
	        	Liroth.LOGGER.info("Liroth: Recipe Types registered!");
	        }

			public static void registerMenuTypes() {
				Liroth.LOGGER.warning("Liroth: Registering Menu Types...");
				LirothScreenHandlers.init();
				Liroth.LOGGER.info("Liroth: Menu Types registered!");
			}
	        
	        public static void registerFuels() {
	        	Liroth.LOGGER.warning("Liroth: Registering fuels...");
	        	FuelRegistry registry = FuelRegistry.INSTANCE;
	        	registry.add(LirothBlocks.DAMNATION_FUNGAL_CLUSTER, 3200);
	        	registry.add(LirothBlocks.DAMNATION_FUNGUS, 1600);
	        	registry.add(LirothItems.LIROTHIAN_PETROLEUM, 1600);
	        	registry.add(LirothBlocks.LIROTHIAN_PETROLEUM_BLOCK, 16000);
	        	Liroth.LOGGER.info("Liroth: Fuels registered!");
	        }
	    }
	    
	    public static void threadSafeLoadFinish() {
	        LOGGER.warning("Liroth: Loading almost done...");
	        LirothStrippables.strippableLogsLiroth();
	        LirothHoeables.tillablesLiroth();
	        LirothFlattenables.addFlatfuckfridayevent();
	        LOGGER.info("Liroth: Loading Success!");
	    }
}
