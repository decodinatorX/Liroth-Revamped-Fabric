package com.decodinator.liroth.compat.jei;

import java.util.Locale;

import com.decodinator.liroth.Liroth;

import mezz.jei.api.constants.ModIds;
import net.minecraft.resources.Identifier;

public class JEILirothConstants {

	public static final String TEXTURE_GUI_PATH = "textures/jei/gui/";
	public static final String TEXTURE_GUI_VANILLA = TEXTURE_GUI_PATH + "gui_vanilla.png";

	public static final Identifier RECIPE_GUI_VANILLA = Identifier.fromNamespaceAndPath(ModIds.JEI_ID, TEXTURE_GUI_VANILLA);

	public static final String GEM_SPLITTER = "liroth.gem.splitter";
	public static final String QUANTUM_EXTRACTOR = "quantum.extractor";

	public static final String RESOURCE_DOMAIN = Liroth.MOD_ID.toLowerCase(Locale.ENGLISH);

	public static final String TEXTURE_GUI_LIROTH = "textures/gui/gui_liroth.png";

	public static final Identifier RECIPE_GUI_LIROTH_MOD = Identifier.fromNamespaceAndPath(RESOURCE_DOMAIN, TEXTURE_GUI_LIROTH);

}