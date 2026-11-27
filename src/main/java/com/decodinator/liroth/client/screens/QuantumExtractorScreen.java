package com.decodinator.liroth.client.screens;

import com.decodinator.liroth.Liroth;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import org.jspecify.annotations.NonNull;
public class QuantumExtractorScreen extends AbstractContainerScreen<QuantumExtractorScreenHandler> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "textures/gui/quantum_extractor_gui.png");

    public QuantumExtractorScreen(QuantumExtractorScreenHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX = 42;
        this.titleLabelY = 3;
    }

    @Override
    public void extractBackground(@NonNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        super.extractBackground(graphics, mouseX, mouseY, delta);
        int k;
        int i = this.leftPos;
        int j = this.topPos;
        graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, i, j, 0.0F, 0.0F, imageWidth, imageHeight, 256, 256);
        if (this.menu.isBurning()) {
            k = this.menu.getFuelProgress();
            graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, i + 57, j + 33 + 12 - k, 176.0F, 65.0F - k, 14, k + 1, 256, 256);
        }
        int s = this.menu.getCookProgress(24);
        graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, i + 80, j + 24, 176.0F, 0.0F, s + 1, 17, 256, 256);
    }
}