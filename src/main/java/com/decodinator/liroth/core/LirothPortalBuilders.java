package com.decodinator.liroth.core;

import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.minecraft.resources.ResourceLocation;

public class LirothPortalBuilders {

	public static void init() {
		CustomPortalBuilder.beginPortal()  
	    .frameBlock(LirothBlocks.DIMENSIONAL_COMMUNICATOR)
        .customPortalBlock(LirothBlocks.LIROTH_DIMENSION_PORTAL)
	    .destDimID(new ResourceLocation("liroth:liroth_dimension"))  
	    .tintColor(33, 0, 33)
	    .flatPortal()
	    .registerPortal();
		
		CustomPortalBuilder.beginPortal()  
	    .frameBlock(LirothBlocks.CHARRED_ACCESSWAY_BLOCK)
        .customPortalBlock(LirothBlocks.DAMNATION_DIMENSION_PORTAL)
	    .destDimID(new ResourceLocation("liroth:damnation"))  
	    .tintColor(38, 0, 9)
	    .flatPortal()
	    .registerPortal();
		
		CustomPortalBuilder.beginPortal()  
	    .frameBlock(LirothBlocks.DAMNED_PASSAGEWAY)
        .customPortalBlock(LirothBlocks.JANTIRO_DIMENSION_PORTAL)
	    .destDimID(new ResourceLocation("liroth:jantiros_escape_dimension"))  
	    .tintColor(0, 38, 38)
	    .flatPortal()
	    .registerPortal();
		
		CustomPortalBuilder.beginPortal()  
	    .frameBlock(LirothBlocks.HAUNTED_THROUGHFARE_BLOCK)
        .customPortalBlock(LirothBlocks.JALSPHIRE_DIMENSION_PORTAL)
	    .destDimID(new ResourceLocation("liroth:jalsphire_plains"))  
	    .tintColor(0, 0, 38)
	    .flatPortal()
	    .registerPortal();
		
		CustomPortalBuilder.beginPortal()  
	    .frameBlock(LirothBlocks.JALSPHIRE_TRACKWAY)
        .customPortalBlock(LirothBlocks.DEVASTATED_DIMENSION_PORTAL)
	    .destDimID(new ResourceLocation("liroth:devastated_plains"))  
	    .tintColor(38, 31, 0)
	    .flatPortal()
	    .registerPortal();
	}
	
}