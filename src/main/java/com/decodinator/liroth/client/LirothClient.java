package com.decodinator.liroth.client;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.client.models.entities.*;
import com.decodinator.liroth.client.models.entities.projectiles.BeamLaserProjectileModel;
import com.decodinator.liroth.client.rendering.entities.*;
import com.decodinator.liroth.client.rendering.entities.projectiles.BeamLaserProjectileEntityRenderer;
import com.decodinator.liroth.client.rendering.entities.projectiles.LevoArrowRenderer;
import com.decodinator.liroth.client.rendering.skies.ZenithSkybox;
import com.decodinator.liroth.core.registry.*;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.particle.EndRodParticle;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.WhiteAshParticle;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;

import com.decodinator.liroth.client.rendering.blocks.FungalCampfireBlockEntityRenderer;
import com.decodinator.liroth.client.screens.LirothSplitterScreen;
import com.decodinator.liroth.client.rendering.blocks.LirothianPetroleumCampfireBlockEntityRenderer;
import com.decodinator.liroth.client.screens.QuantumExtractorScreen;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;

public class LirothClient implements ClientModInitializer {
    public static final ModelLayerLocation MODEL_BEAM_LASER_PROJECTILE_LAYER = new ModelLayerLocation(Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "beam_laser_projectile"), "main");

    public static final ModelLayerLocation MODEL_PIER_PEEP_LAYER = new ModelLayerLocation(Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "pier_peep"), "main");
    public static final ModelLayerLocation MODEL_SHADE_LAYER = new ModelLayerLocation(Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "shade"), "main");
    public static final ModelLayerLocation MODEL_FREAKSHOW_LAYER = new ModelLayerLocation(Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "freakshow"), "main");
    public static final ModelLayerLocation MODEL_VILE_SHARK_LAYER = new ModelLayerLocation(Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "vile_shark"), "main");

    @Override
    public void onInitializeClient() {
        ZenithSkybox.register();

        MenuScreens.register(LirothScreenHandlers.LIROTH_SPLITTER_SCREEN_HANDLER, LirothSplitterScreen::new);
        MenuScreens.register(LirothScreenHandlers.QUANTUM_EXTRACTOR_SCREEN_HANDLER, QuantumExtractorScreen::new);

        FluidRenderingRegistry.register(LirothFluids.LIROTH_FLUID_STILL, LirothFluids.LIROTH_FLUID_FLOWING, fluidModel("liroth_fluid"));
        FluidRenderingRegistry.register(LirothFluids.MOLTEN_SPINERIOS_STILL, LirothFluids.MOLTEN_SPINERIOS_FLOWING, fluidModel("molten_spinerios"));

        EntityRenderers.register(LirothEntities.BEAM_LASER_PROJECTILE_ENTITY, BeamLaserProjectileEntityRenderer::new);
        EntityRenderers.register(LirothEntities.LEVO_ARROW, LevoArrowRenderer::new);

        EntityRenderers.register(LirothEntities.FUNGAL_FIEND, FungalFiendRenderer::new);
        EntityRenderers.register(LirothEntities.FORSAKEN_CORPSE, ForsakenCorpseRenderer::new);
        EntityRenderers.register(LirothEntities.SKELETAL_FREAK, SkeletalFreakRenderer::new);
        EntityRenderers.register(LirothEntities.WARP, WarpRenderer::new);
        EntityRenderers.register(LirothEntities.SOUL_ARACHNID, SoulArachnidRenderer::new);
        EntityRenderers.register(LirothEntities.PIER_PEEP, PierPeepEntityRenderer::new);
        EntityRenderers.register(LirothEntities.SHADE, ShadeEntityRenderer::new);
        EntityRenderers.register(LirothEntities.PROWLER, ProwlerRenderer::new);
        EntityRenderers.register(LirothEntities.FREAKSHOW, FreakshowEntityRenderer::new);
        EntityRenderers.register(LirothEntities.VILE_SHARK, VileSharkEntityRenderer::new);
        EntityRenderers.register(LirothEntities.LIROTHIAN_MIMIC, LirothianMimicRenderer::new);

        ModelLayerRegistry.registerModelLayer(MODEL_BEAM_LASER_PROJECTILE_LAYER, BeamLaserProjectileModel::createBodyLayer);

        ModelLayerRegistry.registerModelLayer(MODEL_PIER_PEEP_LAYER, PierPeepModel::getTexturedModelData);
        ModelLayerRegistry.registerModelLayer(MODEL_SHADE_LAYER, ShadeModel::getTexturedModelData);
        ModelLayerRegistry.registerModelLayer(MODEL_FREAKSHOW_LAYER, FreakshowModel::getTexturedModelData);
        ModelLayerRegistry.registerModelLayer(MODEL_VILE_SHARK_LAYER, VileSharkModel::getTexturedModelData);

        ParticleProviderRegistry.getInstance().register(LirothParticles.CLOAK, EndRodParticle.Provider::new);
        ParticleProviderRegistry.getInstance().register(LirothParticles.GREEN_FLAME, FlameParticle.Provider::new);
        ParticleProviderRegistry.getInstance().register(LirothParticles.PURPLE_FLAME, FlameParticle.Provider::new);
        ParticleProviderRegistry.getInstance().register(LirothParticles.GREEN_SPORE, WhiteAshParticle.Provider::new);

        BlockEntityRenderers.register(LirothBlockEntities.FUNGAL_CAMPFIRE, FungalCampfireBlockEntityRenderer::new);
        BlockEntityRenderers.register(LirothBlockEntities.LIROTHIAN_PETROLEUM_CAMPFIRE, LirothianPetroleumCampfireBlockEntityRenderer::new);
    }

    private static FluidModel.Unbaked fluidModel(String name) {
        Material still = new Material(Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "block/" + name + "_still")).withForceTranslucent(true);
        Material flowing = new Material(Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "block/" + name + "_flowing")).withForceTranslucent(true);
        return new FluidModel.Unbaked(still, flowing, null, null);
    }
}
