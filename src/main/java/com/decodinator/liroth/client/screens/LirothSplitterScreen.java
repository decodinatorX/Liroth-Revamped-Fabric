package com.decodinator.liroth.client.screens;

import com.decodinator.liroth.Liroth;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import org.jspecify.annotations.NonNull;

public class LirothSplitterScreen extends AbstractContainerScreen<LirothSplitterScreenHandler> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "textures/gui/liroth_splitter_gui.png");

    public LirothSplitterScreen(LirothSplitterScreenHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX = 32;
    }

    @Override
    public void extractBackground(@NonNull GuiGraphicsExtractor matrices, int mouseX, int mouseY, float delta) {
        super.extractBackground(matrices, mouseX, mouseY, delta);
        int k;
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        int i = this.leftPos;
        int j = this.topPos;
        matrices.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x, y, 0, 0, imageWidth, imageHeight, 256, 256);
        if (((LirothSplitterScreenHandler)this.menu).isBurning()) {
            k = ((LirothSplitterScreenHandler)this.menu).getFuelProgress();
            matrices.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, i + 53, j + 36 + 12 - k, 176, 65 - k, 14, k + 1, 256, 256);
        }
        k = ((LirothSplitterScreenHandler)this.menu).getCookProgress(34);
        matrices.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, i + 73, j + 15, 176, 0, k, 53, 256, 256);
    }
}