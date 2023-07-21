package com.decodinator.liroth.core.helpers;

import com.decodinator.liroth.Liroth;
import com.google.common.util.concurrent.Runnables;
import com.mojang.authlib.minecraft.BanDetails;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import com.mojang.math.Axis;
import com.mojang.realmsclient.RealmsMainScreen;
import com.mojang.realmsclient.gui.screens.RealmsNotificationsScreen;
import com.terraformersmc.modmenu.gui.ModsScreen;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.*;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.multiplayer.SafetyScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
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
	private static final ResourceLocation ACCESSIBILITY_TEXTURE = new ResourceLocation("textures/gui/accessibility.png");
	private final boolean minceraftEasterEgg;
	@Nullable
	private String splash;
	private Button resetDemoButton;
	@Nullable
	private RealmsNotificationsScreen realmsNotificationsScreen;
	private final PanoramaRenderer panorama = new PanoramaRenderer(CUBE_MAP);
	private final boolean fading;
	private long fadeInStart;
	@Nullable
	private LirothTitleScreen.WarningLabel warningLabel;
	public LirothTitleScreen() {
		this(false);
	}
	public LirothTitleScreen(boolean var1) {
		super(Component.translatable("narrator.screen.title"));
		this.fading = var1;
		this.minceraftEasterEgg = (double)RandomSource.create().nextFloat() < 1.0E-4;
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
				ACCESSIBILITY_TEXTURE,
				32,
				64,
				var1x -> this.minecraft.setScreen(new AccessibilityOptionsScreen(this, this.minecraft.options)),
				Component.translatable("narrator.button.accessibility")
			)
		);
		this.addRenderableWidget(
			new PlainTextButton(var2, this.height - 10, var1, 10, COPYRIGHT_TEXT, var1x -> this.minecraft.setScreen(new WinScreen(false, Runnables.doNothing())), this.font)
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
		if(FabricLoader.getInstance().isModLoaded("modmenu")) {
			this.addRenderableWidget(
					Button.builder(Component.translatable("menu.online"), var1x -> this.realmsButtonClicked())
						.bounds(this.width / 2 + 2, var1 + var2 * 2, 98, 20)
						.tooltip(var5)
						.build()
				)
				.active = var4;
		}else if (!FabricLoader.getInstance().isModLoaded("modmenu")) {
			this.addRenderableWidget(
					Button.builder(Component.translatable("menu.online"), var1x -> this.realmsButtonClicked())
						.bounds(this.width / 2 - 100, var1 + var2 * 2, 200, 20)
						.tooltip(var5)
						.build()
				)
				.active = var4;
		}
		if(FabricLoader.getInstance().isModLoaded("modmenu")) {
			this.addRenderableWidget(
					Button.builder(Component.translatable("category.modmenu.name"), var1x -> this.minecraft.setScreen(new ModsScreen(this)))
						.bounds(this.width / 2 - 100, var1 + var2 * 2, 98, 20)
						.tooltip(var5)
						.build()
				)
			.active = var4;
		}
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
	public void render(PoseStack var1, int var2, int var3, float var4) {
		if (this.fadeInStart == 0L && this.fading) {
			this.fadeInStart = Util.getMillis();
		}
		float var5 = this.fading ? (float)(Util.getMillis() - this.fadeInStart) / 1000.0F : 1.0F;
		this.panorama.render(var4, Mth.clamp(var5, 0.0F, 1.0F));
		boolean var6 = true;
		int var7 = this.width / 2 - 137;
		boolean var8 = true;
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, PANORAMA_OVERLAY);
		RenderSystem.enableBlend();
		RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.fading ? (float)Mth.ceil(Mth.clamp(var5, 0.0F, 1.0F)) : 1.0F);
		blit(var1, 0, 0, this.width, this.height, 0.0F, 0.0F, 16, 128, 16, 128);
		float var9 = this.fading ? Mth.clamp(var5 - 1.0F, 0.0F, 1.0F) : 1.0F;
		int var10 = Mth.ceil(var9 * 255.0F) << 24;
		if ((var10 & -67108864) != 0) {
			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			RenderSystem.setShaderTexture(0, LIROTH_LOGO);
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, var9);
			if (this.minceraftEasterEgg) {
				this.blitOutlineBlack(var7, 30, (var2x, var3x) -> {
					this.blit(var1, var2x + 0, var3x, 0, 0, 99, 44);
					this.blit(var1, var2x + 99, var3x, 129, 0, 27, 44);
					this.blit(var1, var2x + 99 + 26, var3x, 126, 0, 3, 44);
					this.blit(var1, var2x + 99 + 26 + 3, var3x, 99, 0, 26, 44);
					this.blit(var1, var2x + 155, var3x, 0, 45, 155, 44);
				});
			} else {
				this.blitOutlineBlack(var7, 30, (var2x, var3x) -> {
					this.blit(var1, var2x + 0, var3x, 0, 0, 155, 44);
					this.blit(var1, var2x + 155, var3x, 0, 45, 155, 44);
				});
			}
			RenderSystem.setShaderTexture(0, LIROTH_EDITION);
			blit(var1, var7 + 88, 67, 0.0F, 0.0F, 98, 14, 128, 16);
			if (this.warningLabel != null) {
				this.warningLabel.render(var1, var10);
			}
			if (this.splash != null) {
				var1.pushPose();
				var1.translate((float)(this.width / 2 + 90), 70.0F, 0.0F);
				var1.mulPose(Axis.ZP.rotationDegrees(-20.0F));
				float var11 = 1.8F - Mth.abs(Mth.sin((float)(Util.getMillis() % 1000L) / 1000.0F * (float) (Math.PI * 2)) * 0.1F);
				var11 = var11 * 100.0F / (float)(this.font.width(this.splash) + 32);
				var1.scale(var11, var11, var11);
				drawCenteredString(var1, this.font, this.splash, 0, -8, 16776960 | var10);
				var1.popPose();
			}
			String var15 = "Minecraft " + SharedConstants.getCurrentVersion().getName();
			if (this.minecraft.isDemo()) {
				var15 = var15 + " Demo";
			} else {
				var15 = var15 + ("release".equalsIgnoreCase(this.minecraft.getVersionType()) ? "" : "/" + this.minecraft.getVersionType());
			}
			if (Minecraft.checkModStatus().shouldReportAsModified()) {
				var15 = var15 + I18n.get("menu.modded");
			}
			drawString(var1, this.font, var15, 2, this.height - 10, 16777215 | var10);
			for(GuiEventListener var13 : this.children()) {
				if (var13 instanceof AbstractWidget) {
					((AbstractWidget)var13).setAlpha(var9);
				}
			}
			super.render(var1, var2, var3, var4);
			if (this.realmsNotificationsEnabled() && var9 >= 1.0F) {
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
		public void render(PoseStack var1, int var2) {
			this.label.renderBackgroundCentered(var1, this.x, this.y, 9, 2, 2097152 | Math.min(var2, 1426063360));
			this.label.renderCentered(var1, this.x, this.y, 9, 16777215 | var2);
		}
	}
}