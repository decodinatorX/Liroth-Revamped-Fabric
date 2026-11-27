package com.decodinator.liroth.client.rendering.entities;

import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.Identifier;

public class ShadeRenderState extends HumanoidRenderState {
    public Identifier texture = DefaultPlayerSkin.getDefaultTexture();
    public float velocityLengthSqr;
}