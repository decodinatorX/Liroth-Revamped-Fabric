package com.decodinator.liroth.client;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.client.models.entities.*;
import com.decodinator.liroth.client.models.entities.projectiles.BeamLaserProjectileModel;
import com.decodinator.liroth.client.rendering.entities.*;
import com.decodinator.liroth.client.rendering.entities.projectiles.BeamLaserProjectileEntityRenderer;
import com.decodinator.liroth.client.rendering.entities.projectiles.LevoArrowRenderer;
import com.decodinator.liroth.client.rendering.skies.ZenithSkybox;
import com.decodinator.liroth.core.registry.*;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.particle.EndRodParticle;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.WhiteAshParticle;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.resources.ResourceLocation;

import com.decodinator.liroth.client.models.items.PotestiumHelmetModel;
import com.decodinator.liroth.client.rendering.items.PotestiumHelmetRenderer;
import com.decodinator.liroth.client.rendering.blocks.FungalCampfireBlockEntityRenderer;
import com.decodinator.liroth.client.screens.LirothSplitterScreen;
import com.decodinator.liroth.client.rendering.blocks.LirothianPetroleumCampfireBlockEntityRenderer;
import com.decodinator.liroth.client.screens.QuantumExtractorScreen;
import com.decodinator.liroth.mixin.ItemBlockRenderTypeAccess;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class LirothClient implements ClientModInitializer {
    public static final ModelLayerLocation MODEL_BEAM_LASER_PROJECTILE_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "beam_laser_projectile"), "main");

    public static final ModelLayerLocation MODEL_PIER_PEEP_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "pier_peep"), "main");
    public static final ModelLayerLocation MODEL_SHADE_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "shade"), "main");
    public static final ModelLayerLocation MODEL_FREAKSHOW_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "freakshow"), "main");
    public static final ModelLayerLocation MODEL_VILE_SHARK_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "vile_shark"), "main");
    public static final ModelLayerLocation MODEL_POTESTIUM_HELMET_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "potestium_helmet"), "main");

	@Override
	public void onInitializeClient() {
		ZenithSkybox.register();

        MenuScreens.register(LirothScreenHandlers.LIROTH_SPLITTER_SCREEN_HANDLER, LirothSplitterScreen::new);
        MenuScreens.register(LirothScreenHandlers.QUANTUM_EXTRACTOR_SCREEN_HANDLER, QuantumExtractorScreen::new);

		FluidRenderHandlerRegistry.INSTANCE.register(LirothFluids.LIROTH_FLUID_STILL, LirothFluids.LIROTH_FLUID_FLOWING, new SimpleFluidRenderHandler(
				ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID,"blocks/liroth_fluid_still"),
				ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID,"blocks/liroth_fluid_flowing")
		));
		
		BlockRenderLayerMap.INSTANCE.putFluids(RenderType.translucent(), LirothFluids.LIROTH_FLUID_STILL, LirothFluids.LIROTH_FLUID_FLOWING);
		
		FluidRenderHandlerRegistry.INSTANCE.register(LirothFluids.MOLTEN_SPINERIOS_STILL, LirothFluids.MOLTEN_SPINERIOS_FLOWING, new SimpleFluidRenderHandler(
				ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID,"blocks/molten_spinerios_still"),
				ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID,"blocks/molten_spinerios_flowing")
		));
        
        LirothRenders.renderCutOuts(blockRenderTypeMap -> ItemBlockRenderTypeAccess.getTypeByBlock().putAll(blockRenderTypeMap));
        
		EntityRendererRegistry.register(LirothEntities.BEAM_LASER_PROJECTILE_ENTITY, BeamLaserProjectileEntityRenderer::new);
        EntityRendererRegistry.register(LirothEntities.LEVO_ARROW, LevoArrowRenderer::new);

        EntityRendererRegistry.register(LirothEntities.FUNGAL_FIEND, FungalFiendRenderer::new);
        EntityRendererRegistry.register(LirothEntities.FORSAKEN_CORPSE, ForsakenCorpseRenderer::new);
        EntityRendererRegistry.register(LirothEntities.SKELETAL_FREAK, SkeletalFreakRenderer::new);
        EntityRendererRegistry.register(LirothEntities.WARP, WarpRenderer::new);
        EntityRendererRegistry.register(LirothEntities.SOUL_ARACHNID, SoulArachnidEntityRenderer::new);
        EntityRendererRegistry.register(LirothEntities.PIER_PEEP, PierPeepEntityRenderer::new);
        EntityRendererRegistry.register(LirothEntities.SHADE, ShadeEntityRenderer::new);
        EntityRendererRegistry.register(LirothEntities.PROWLER, ProwlerRenderer::new);
        EntityRendererRegistry.register(LirothEntities.FREAKSHOW, FreakshowEntityRenderer::new);
        EntityRendererRegistry.register(LirothEntities.VILE_SHARK, VileSharkEntityRenderer::new);
        EntityRendererRegistry.register(LirothEntities.LIROTHIAN_MIMIC, LirothianMimicRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(MODEL_BEAM_LASER_PROJECTILE_LAYER, BeamLaserProjectileModel::createBodyLayer);

        EntityModelLayerRegistry.registerModelLayer(MODEL_PIER_PEEP_LAYER, PierPeepModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(MODEL_SHADE_LAYER, ShadeModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(MODEL_FREAKSHOW_LAYER, FreakshowModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(MODEL_VILE_SHARK_LAYER, VileSharkModel::getTexturedModelData);

        EntityModelLayerRegistry.registerModelLayer(MODEL_POTESTIUM_HELMET_LAYER, PotestiumHelmetModel::getTexturedModelData);

        ParticleFactoryRegistry.getInstance().register(LirothParticles.CLOAK, EndRodParticle.Provider::new);
        ParticleFactoryRegistry.getInstance().register(LirothParticles.GREEN_FLAME, FlameParticle.Provider::new);
        ParticleFactoryRegistry.getInstance().register(LirothParticles.PURPLE_FLAME, FlameParticle.Provider::new);
        ParticleFactoryRegistry.getInstance().register(LirothParticles.GREEN_SPORE, WhiteAshParticle.Provider::new);
        
        BlockEntityRenderers.register(LirothBlockEntities.FUNGAL_CAMPFIRE, FungalCampfireBlockEntityRenderer::new);
        BlockEntityRenderers.register(LirothBlockEntities.LIROTHIAN_PETROLEUM_CAMPFIRE, LirothianPetroleumCampfireBlockEntityRenderer::new);

        ArmorRenderer.register(new PotestiumHelmetRenderer(), LirothItems.POTESTIUM_LIROTH_HELMET);
	}
}
