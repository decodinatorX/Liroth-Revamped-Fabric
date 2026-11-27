package com.decodinator.liroth.client.rendering.entities.projectiles;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.core.entities.projectiles.LevoArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.resources.Identifier;
public class LevoArrowRenderer extends ArrowRenderer<LevoArrow, ArrowRenderState> {
    public static final Identifier SPECTRAL_ARROW_LOCATION = Identifier.fromNamespaceAndPath(Liroth.MOD_ID, "textures/entity/projectiles/levo_arrow.png");

    public LevoArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ArrowRenderState createRenderState() {
        return new ArrowRenderState();
    }

    @Override
    protected Identifier getTextureLocation(ArrowRenderState state) {
        return SPECTRAL_ARROW_LOCATION;
    }
}