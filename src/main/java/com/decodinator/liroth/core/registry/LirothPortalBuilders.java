package com.decodinator.liroth.core.registry;


import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.core.blocks.ChargePortalFrameTester;
import net.kyrptonaught.customportalapi.CustomPortalApiRegistry;
import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.minecraft.resources.ResourceLocation;

public class LirothPortalBuilders {
	private static boolean registered;

	public static void init() {
		onCommonSetup();
	}

	private static void onCommonSetup() {
		if (registered) return;
		registered = true;

		registerChargeFrameTester("liroth_portal_charge", LirothBlocks.DIMENSIONAL_COMMUNICATOR);
		registerChargeFrameTester("damnation_portal_charge", LirothBlocks.CHARRED_ACCESSWAY_BLOCK);
		registerChargeFrameTester("jantiro_portal_charge", LirothBlocks.DAMNED_PASSAGEWAY);
		registerChargeFrameTester("jalsphire_portal_charge", LirothBlocks.HAUNTED_THROUGHFARE_BLOCK);
		registerChargeFrameTester("devastated_portal_charge", LirothBlocks.JALSPHIRE_TRACKWAY);

		CustomPortalBuilder.beginPortal()
				.frameBlock(LirothBlocks.LIROTH_PORTAL_FRAME)
				.customPortalBlock(LirothBlocks.LIROTH_DIMENSION_PORTAL)
				.destDimID(ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "liroth_dimension"))
				.customIgnitionSource(ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "liroth_portal_charge"))
				.customFrameTester(ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "liroth_portal_charge"))
				.forcedSize(3, 3)
				.tintColor(33, 0, 33)
				.registerPortal();

		CustomPortalBuilder.beginPortal()
				.frameBlock(LirothBlocks.DAMNATION_PORTAL_FRAME)
				.customPortalBlock(LirothBlocks.DAMNATION_DIMENSION_PORTAL)
				.destDimID(ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "damnation"))
				.customIgnitionSource(ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "damnation_portal_charge"))
				.customFrameTester(ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "damnation_portal_charge"))
				.forcedSize(3, 3)
				.tintColor(38, 0, 9)
				.registerPortal();

		CustomPortalBuilder.beginPortal()
				.frameBlock(LirothBlocks.JANTIRO_PORTAL_FRAME)
				.customPortalBlock(LirothBlocks.JANTIRO_DIMENSION_PORTAL)
				.destDimID(ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "jantiros_escape_dimension"))
				.customIgnitionSource(ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "jantiro_portal_charge"))
				.customFrameTester(ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "jantiro_portal_charge"))
				.forcedSize(3, 3)
				.tintColor(0, 38, 38)
				.registerPortal();

		CustomPortalBuilder.beginPortal()
				.frameBlock(LirothBlocks.JALSPHIRE_PORTAL_FRAME)
				.customPortalBlock(LirothBlocks.JALSPHIRE_DIMENSION_PORTAL)
				.destDimID(ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "jalsphire_plains"))
				.customIgnitionSource(ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "jalsphire_portal_charge"))
				.customFrameTester(ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "jalsphire_portal_charge"))
				.forcedSize(3, 3)
				.tintColor(0, 0, 38)
				.registerPortal();

		CustomPortalBuilder.beginPortal()
				.frameBlock(LirothBlocks.DEVASTATED_PORTAL_FRAME)
				.customPortalBlock(LirothBlocks.DEVASTATED_DIMENSION_PORTAL)
				.destDimID(ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "devastated_plains"))
				.customIgnitionSource(ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "devastated_portal_charge"))
				.customFrameTester(ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, "devastated_portal_charge"))
				.forcedSize(3, 3)
				.tintColor(38, 31, 0)
				.registerPortal();
	}

	private static void registerChargeFrameTester(String path, net.minecraft.world.level.block.Block regularFrame) {
		ResourceLocation testerId = ResourceLocation.fromNamespaceAndPath(Liroth.MOD_ID, path);
		CustomPortalApiRegistry.registerPortalFrameTester(testerId, () -> new ChargePortalFrameTester(regularFrame));
	}
}