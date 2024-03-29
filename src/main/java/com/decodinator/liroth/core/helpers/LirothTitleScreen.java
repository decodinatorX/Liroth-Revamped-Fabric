/*
 * Decompiled with CFR 0.1.1 (FabricMC 57d88659).
 */
package com.decodinator.liroth.core.helpers;

import com.decodinator.liroth.Liroth;
import com.mojang.authlib.minecraft.BanDetails;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.RealmsMainScreen;
import com.mojang.realmsclient.gui.screens.RealmsNotificationsScreen;
import com.terraformersmc.modmenu.gui.ModsScreen;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.client.gui.components.SplashRenderer;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.AccessibilityOptionsScreen;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.CreditsAndAttributionScreen;
import net.minecraft.client.gui.screens.LanguageSelectScreen;
import net.minecraft.client.gui.screens.OptionsScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.multiplayer.SafetyScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.WorldOptions;
import net.minecraft.world.level.levelgen.presets.WorldPresets;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.LevelSummary;

import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

@Environment(value=EnvType.CLIENT)
public class LirothTitleScreen extends Screen {
	private static final Logger LOGGER = LogUtils.getLogger();
	private static final String DEMO_LEVEL_ID = "Demo_World";
	public static final Component COPYRIGHT_TEXT = Component.literal("Copyright Mojang AB. Do not distribute!");
	public static final CubeMap CUBE_MAP = new CubeMap(new ResourceLocation(Liroth.MOD_ID, "textures/gui/title/background/panorama"));
	private static final ResourceLocation PANORAMA_OVERLAY = new ResourceLocation("textures/gui/title/background/panorama_overlay.png");
    public static final ResourceLocation LIROTH_LOGO = new ResourceLocation(Liroth.MOD_ID, "textures/gui/title/minecraft.png");
    public static final ResourceLocation LIROTH_EDITION = new ResourceLocation(Liroth.MOD_ID, "textures/gui/title/edition.png");
	@Nullable
	private SplashRenderer splash;
	private Button resetDemoButton;
	@Nullable
	private RealmsNotificationsScreen realmsNotificationsScreen;
	private final PanoramaRenderer panorama = new PanoramaRenderer(CUBE_MAP);
	private final boolean fading;
	private long fadeInStart;
	@Nullable
	private LirothTitleScreen.WarningLabel warningLabel;
	private final LogoRenderer logoRenderer;
	public LirothTitleScreen() {
		this(false);
	}
	public LirothTitleScreen(boolean var1) {
		this(var1, null);
	}
	public LirothTitleScreen(boolean var1, @Nullable LogoRenderer var2) {
		super(Component.translatable("narrator.screen.title"));
		this.fading = var1;
		this.logoRenderer = (LogoRenderer)Objects.requireNonNullElseGet(var2, () -> new LogoRenderer(false));
	}
	private boolean realmsNotificationsEnabled() {
		return this.realmsNotificationsScreen != null;
	}
	@Override
	public void tick() {
		if (this.realmsNotificationsEnabled()) {
			this.realmsNotificationsScreen.tick();
		}
		this.minecraft.getRealms32BitWarningStatus().showRealms32BitWarningIfNeeded(this);
	}
	public static CompletableFuture<Void> preloadResources(TextureManager var0, Executor var1) {
		return CompletableFuture.allOf(
			var0.preload(LIROTH_LOGO, var1),
			var0.preload(LIROTH_EDITION, var1),
			var0.preload(PANORAMA_OVERLAY, var1),
			CUBE_MAP.preload(var0, var1)
		);
	}
	@Override
	public boolean isPauseScreen() {
		return false;
	}
	@Override
	public boolean shouldCloseOnEsc() {
		return false;
	}
	@Override
	protected void init() {
		if (this.splash == null) {
			this.splash = this.minecraft.getSplashManager().getSplash();
		}
		int var1 = this.font.width(COPYRIGHT_TEXT);
		int var2 = this.width - var1 - 2;
		boolean var3 = true;
		int var4 = this.height / 4 + 48;
		if (this.minecraft.isDemo()) {
			this.createDemoMenuOptions(var4, 24);
		} else {
			this.createNormalMenuOptions(var4, 24);
		}
		this.addRenderableWidget(
			new ImageButton(
				this.width / 2 - 124,
				var4 + 72 + 12,
				20,
				20,
				0,
				106,
				20,
				Button.WIDGETS_LOCATION,
				256,
				256,
				var1x -> this.minecraft.setScreen(new LanguageSelectScreen(this, this.minecraft.options, this.minecraft.getLanguageManager())),
				Component.translatable("narrator.button.language")
			)
		);
		this.addRenderableWidget(
			Button.builder(Component.translatable("menu.options"), var1x -> this.minecraft.setScreen(new OptionsScreen(this, this.minecraft.options)))
				.bounds(this.width / 2 - 100, var4 + 72 + 12, 98, 20)
				.build()
		);
		this.addRenderableWidget(
			Button.builder(Component.translatable("menu.quit"), var1x -> this.minecraft.stop()).bounds(this.width / 2 + 2, var4 + 72 + 12, 98, 20).build()
		);
		this.addRenderableWidget(
			new ImageButton(
				this.width / 2 + 104,
				var4 + 72 + 12,
				20,
				20,
				0,
				0,
				20,
				Button.ACCESSIBILITY_TEXTURE,
				32,
				64,
				var1x -> this.minecraft.setScreen(new AccessibilityOptionsScreen(this, this.minecraft.options)),
				Component.translatable("narrator.button.accessibility")
			)
		);
		this.addRenderableWidget(
			new PlainTextButton(var2, this.height - 10, var1, 10, COPYRIGHT_TEXT, var1x -> this.minecraft.setScreen(new CreditsAndAttributionScreen(this)), this.font)
		);
		this.minecraft.setConnectedToRealms(false);
		if (this.realmsNotificationsScreen == null) {
			this.realmsNotificationsScreen = new RealmsNotificationsScreen();
		}
		if (this.realmsNotificationsEnabled()) {
			this.realmsNotificationsScreen.init(this.minecraft, this.width, this.height);
		}
		if (!this.minecraft.is64Bit()) {
			this.warningLabel = new LirothTitleScreen.WarningLabel(
				this.font, MultiLineLabel.create(this.font, Component.translatable("title.32bit.deprecation"), 350, 2), this.width / 2, var4 - 24
			);
		}
	}
	private void createNormalMenuOptions(int var1, int var2) {
		this.addRenderableWidget(
			Button.builder(Component.translatable("menu.singleplayer"), var1x -> this.minecraft.setScreen(new SelectWorldScreen(this)))
				.bounds(this.width / 2 - 100, var1, 200, 20)
				.build()
		);
		Component var3 = this.getMultiplayerDisabledReason();
		boolean var4 = var3 == null;
		Tooltip var5 = var3 != null ? Tooltip.create(var3) : null;
		this.addRenderableWidget(Button.builder(Component.translatable("menu.multiplayer"), var1x -> {
			Object var2x = this.minecraft.options.skipMultiplayerWarning ? new JoinMultiplayerScreen(this) : new SafetyScreen(this);
			this.minecraft.setScreen((Screen)var2x);
		}).bounds(this.width / 2 - 100, var1 + var2 * 1, 200, 20).tooltip(var5).build()).active = var4;
			this.addRenderableWidget(
					Button.builder(Component.translatable("menu.online"), var1x -> this.realmsButtonClicked())
						.bounds(this.width / 2 - 100, var1 + var2 * 2, 200, 20)
						.tooltip(var5)
						.build()
				)
				.active = var4;
	}
	@Nullable
	private Component getMultiplayerDisabledReason() {
		if (this.minecraft.allowsMultiplayer()) {
			return null;
		} else {
			BanDetails var1 = this.minecraft.multiplayerBan();
			if (var1 != null) {
				return var1.expires() != null
					? Component.translatable("title.multiplayer.disabled.banned.temporary")
					: Component.translatable("title.multiplayer.disabled.banned.permanent");
			} else {
				return Component.translatable("title.multiplayer.disabled");
			}
		}
	}
	private void createDemoMenuOptions(int var1, int var2) {
		boolean var3 = this.checkDemoWorldPresence();
		this.addRenderableWidget(
			Button.builder(
					Component.translatable("menu.playdemo"),
					var2x -> {
						if (var3) {
							this.minecraft.createWorldOpenFlows().loadLevel(this, "Demo_World");
						} else {
							this.minecraft
								.createWorldOpenFlows()
								.createFreshLevel("Demo_World", MinecraftServer.DEMO_SETTINGS, WorldOptions.DEMO_OPTIONS, WorldPresets::createNormalWorldDimensions);
						}
			
					}
				)
				.bounds(this.width / 2 - 100, var1, 200, 20)
				.build()
		);
		this.resetDemoButton = this.addRenderableWidget(
			Button.builder(
					Component.translatable("menu.resetdemo"),
					var1x -> {
						LevelStorageSource var2x = this.minecraft.getLevelSource();
			
						try (LevelStorageSource.LevelStorageAccess var3x = var2x.createAccess("Demo_World")) {
							LevelSummary var4 = var3x.getSummary();
							if (var4 != null) {
								this.minecraft
									.setScreen(
										new ConfirmScreen(
											this::confirmDemo,
											Component.translatable("selectWorld.deleteQuestion"),
											Component.translatable("selectWorld.deleteWarning", var4.getLevelName()),
											Component.translatable("selectWorld.deleteButton"),
											CommonComponents.GUI_CANCEL
										)
									);
							}
						} catch (IOException var8) {
							SystemToast.onWorldAccessFailure(this.minecraft, "Demo_World");
							LOGGER.warn("Failed to access demo world", var8);
						}
			
					}
				)
				.bounds(this.width / 2 - 100, var1 + var2 * 1, 200, 20)
				.build()
		);
		this.resetDemoButton.active = var3;
	}
	private boolean checkDemoWorldPresence() {
		try {
			boolean var2;
			try (LevelStorageSource.LevelStorageAccess var1 = this.minecraft.getLevelSource().createAccess("Demo_World")) {
				var2 = var1.getSummary() != null;
			}
			return var2;
		} catch (IOException var6) {
			SystemToast.onWorldAccessFailure(this.minecraft, "Demo_World");
			LOGGER.warn("Failed to read demo world data", var6);
			return false;
		}
	}
	private void realmsButtonClicked() {
		this.minecraft.setScreen(new RealmsMainScreen(this));
	}
	@Override
	public void render(GuiGraphics var1, int var2, int var3, float var4) {
		if (this.fadeInStart == 0L && this.fading) {
			this.fadeInStart = Util.getMillis();
		}
		float var5 = this.fading ? (float)(Util.getMillis() - this.fadeInStart) / 1000.0F : 1.0F;
		this.panorama.render(var4, Mth.clamp(var5, 0.0F, 1.0F));
		RenderSystem.enableBlend();
		var1.setColor(1.0F, 1.0F, 1.0F, this.fading ? (float)Mth.ceil(Mth.clamp(var5, 0.0F, 1.0F)) : 1.0F);
		var1.blit(PANORAMA_OVERLAY, 0, 0, this.width, this.height, 0.0F, 0.0F, 16, 128, 16, 128);
		var1.setColor(1.0F, 1.0F, 1.0F, 1.0F);
		float var6 = this.fading ? Mth.clamp(var5 - 1.0F, 0.0F, 1.0F) : 1.0F;
		renderLogo(var1, this.width, var6);
		int var7 = Mth.ceil(var6 * 255.0F) << 24;
		if ((var7 & -67108864) != 0) {
			if (this.warningLabel != null) {
				this.warningLabel.render(var1, var7);
			}
			if (this.splash != null) {
				this.splash.render(var1, this.width, this.font, var7);
			}
			String var8 = "Minecraft " + SharedConstants.getCurrentVersion().getName();
			if (this.minecraft.isDemo()) {
				var8 = var8 + " Demo";
			} else {
				var8 = var8 + ("release".equalsIgnoreCase(this.minecraft.getVersionType()) ? "" : "/" + this.minecraft.getVersionType());
			}
			if (Minecraft.checkModStatus().shouldReportAsModified()) {
				var8 = var8 + I18n.get("menu.modded");
			}
			var1.drawString(this.font, var8, 2, this.height - 10, 16777215 | var7);
			for(GuiEventListener var10 : this.children()) {
				if (var10 instanceof AbstractWidget) {
					((AbstractWidget)var10).setAlpha(var6);
				}
			}
			super.render(var1, var2, var3, var4);
			if (this.realmsNotificationsEnabled() && var6 >= 1.0F) {
				RenderSystem.enableDepthTest();
				this.realmsNotificationsScreen.render(var1, var2, var3, var4);
			}
		}
	}
	@Override
	public boolean mouseClicked(double var1, double var3, int var5) {
		if (super.mouseClicked(var1, var3, var5)) {
			return true;
		} else {
			return this.realmsNotificationsEnabled() && this.realmsNotificationsScreen.mouseClicked(var1, var3, var5);
		}
	}
	@Override
	public void removed() {
		if (this.realmsNotificationsScreen != null) {
			this.realmsNotificationsScreen.removed();
		}
	}
	@Override
	public void added() {
		super.added();
		if (this.realmsNotificationsScreen != null) {
			this.realmsNotificationsScreen.added();
		}
	}
	private void confirmDemo(boolean var1) {
		if (var1) {
			try (LevelStorageSource.LevelStorageAccess var2 = this.minecraft.getLevelSource().createAccess("Demo_World")) {
				var2.deleteLevel();
			} catch (IOException var7) {
				SystemToast.onWorldDeleteFailure(this.minecraft, "Demo_World");
				LOGGER.warn("Failed to delete demo world", var7);
			}
		}
		this.minecraft.setScreen(this);
	}
	static record WarningLabel(Font font, MultiLineLabel label, int x, int y) {
		public void render(GuiGraphics var1, int var2) {
			this.label.renderBackgroundCentered(var1, this.x, this.y, 9, 2, 2097152 | Math.min(var2, 1426063360));
			this.label.renderCentered(var1, this.x, this.y, 9, 16777215 | var2);
		}
	}
	
    public void renderLogo(GuiGraphics guiGraphics, int i, float f) {
        this.renderLogo(guiGraphics, i, f, 30);
    }

    public void renderLogo(GuiGraphics guiGraphics, int i, float f, int j) {
        guiGraphics.setColor(1.0f, 1.0f, 1.0f, this.logoRenderer.keepLogoThroughFade ? 1.0f : f);
        int k = i / 2 - 128;
        guiGraphics.blit(this.logoRenderer.showEasterEgg ? LogoRenderer.EASTER_EGG_LOGO : LirothTitleScreen.LIROTH_LOGO, k, j, 0.0f, 0.0f, 256, 44, 256, 64);
        int l = i / 2 - 64;
        int m = j + 44 - 7;
        guiGraphics.blit(LirothTitleScreen.LIROTH_EDITION, l, m, 0.0f, 0.0f, 128, 14, 128, 16);
        guiGraphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }
}


