package com.decodinator.liroth.client.rendering.skies;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.flashyreese.mods.nuit.api.NuitApi;
import me.flashyreese.mods.nuit.components.Blend;
import me.flashyreese.mods.nuit.components.Conditions;
import me.flashyreese.mods.nuit.components.Properties;
import me.flashyreese.mods.nuit.components.Texture;
import me.flashyreese.mods.nuit.mixin.SkyRendererAccessor;
import me.flashyreese.mods.nuit.render.NuitRenderBackend;
import me.flashyreese.mods.nuit.render.NuitShaders;
import me.flashyreese.mods.nuit.skybox.SkyboxType;
import me.flashyreese.mods.nuit.skybox.textured.TexturedSkybox;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

import java.util.List;
import java.util.function.Supplier;
import net.minecraft.client.renderer.ShaderInstance;

// don't tell me to do shit like this ever again.
public final class ZenithSkybox extends TexturedSkybox {
    public static final Codec<ZenithSkybox> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Properties.CODEC.optionalFieldOf("properties", Properties.of()).forGetter(skybox -> skybox.properties),
            Conditions.CODEC.optionalFieldOf("conditions", Conditions.of()).forGetter(skybox -> skybox.conditions),
            Blend.CODEC.optionalFieldOf("blend", Blend.normal()).forGetter(skybox -> skybox.blend),
            Texture.CODEC.fieldOf("texture").forGetter(skybox -> skybox.texture)
    ).apply(instance, ZenithSkybox::new));

    public static final SkyboxType<ZenithSkybox> TYPE = NuitApi.registerSkyboxType(
            ResourceLocation.fromNamespaceAndPath("liroth", "zenith"), 1, CODEC);

    private static final int LONGITUDE_SEGMENTS = 96;
    private static final int LATITUDE_SEGMENTS = 48;
    private static final float RADIUS = 100.0F;
    private static final double SOURCE_ART_RADIUS = 0.5;

    private final Texture texture;

    private ZenithSkybox(Properties properties, Conditions conditions, Blend blend, Texture texture) {
        super(properties, conditions, blend);
        this.texture = texture;
    }

    public static void register() {
    }

    @Override
    public void renderSkybox(SkyRendererAccessor skyRenderer, PoseStack poseStack, Matrix4f projectionMatrix,
                            float tickDelta, Camera camera, boolean foggy, Runnable setupFog) {
        boolean shaderPackActive = isIrisShaderPackActive();
        BufferBuilder builder = Tesselator.getInstance().begin(
                VertexFormat.Mode.QUADS,
                shaderPackActive ? DefaultVertexFormat.POSITION_TEX : NuitShaders.FRAME_BLENDED_SKYBOX_FORMAT);
        Matrix4f pose = poseStack.last().pose();

        for (int latitude = 0; latitude < LATITUDE_SEGMENTS; latitude++) {
            float v0 = (float) latitude / LATITUDE_SEGMENTS;
            float v1 = (float) (latitude + 1) / LATITUDE_SEGMENTS;
            double lat0 = Math.PI * (0.5 - v0);
            double lat1 = Math.PI * (0.5 - v1);

            for (int longitude = 0; longitude < LONGITUDE_SEGMENTS; longitude++) {
                float u0 = (float) longitude / LONGITUDE_SEGMENTS;
                float u1 = (float) (longitude + 1) / LONGITUDE_SEGMENTS;
                addVertex(builder, pose, lat0, u0, v0, shaderPackActive);
                addVertex(builder, pose, lat1, u0, v1, shaderPackActive);
                addVertex(builder, pose, lat1, u1, v1, shaderPackActive);
                addVertex(builder, pose, lat0, u1, v0, shaderPackActive);
            }
        }

        MeshData mesh = builder.buildOrThrow();
        Supplier<ShaderInstance> shader = shaderPackActive
                ? GameRenderer::getPositionTexShader
                : NuitShaders::getFrameBlendedSkybox;
        NuitRenderBackend.drawTextured(mesh, shader, texture.getTextureId());
    }

    private static boolean isIrisShaderPackActive() {
        if (!FabricLoader.getInstance().isModLoaded("iris")) {
            return false;
        }

        try {
            Class<?> irisApi = Class.forName("net.irisshaders.iris.api.v0.IrisApi");
            Object instance = irisApi.getMethod("getInstance").invoke(null);
            return (boolean) irisApi.getMethod("isShaderPackInUse").invoke(instance);
        } catch (ReflectiveOperationException | LinkageError ignored) {
            return false;
        }
    }

    private static void addVertex(BufferBuilder builder, Matrix4f pose, double latitude, float u, float v,
                                  boolean shaderPackActive) {
        double centeredLongitude = Math.PI * 2.0 * (u - 0.5);
        float x = (float) (-RADIUS * Math.cos(latitude) * Math.sin(centeredLongitude));
        float localY = (float) (RADIUS * Math.sin(latitude));
        float localZ = (float) (RADIUS * Math.cos(latitude) * Math.cos(centeredLongitude));

        float worldX = x / RADIUS;
        float worldY = localZ / RADIUS;
        float worldZ = -localY / RADIUS;
        double zenithAngle = Math.acos(Math.max(-1.0, Math.min(1.0, worldY)));

        double horizontalLength = Math.sqrt(worldX * worldX + worldZ * worldZ);
        double sourceRadius = zenithAngle <= Math.PI * 0.5
                ? SOURCE_ART_RADIUS * zenithAngle / (Math.PI * 0.5)
                : SOURCE_ART_RADIUS;

        float textureU;
        float textureV;
        if (horizontalLength > 1.0e-6) {
            textureU = (float) (0.5 + sourceRadius * worldX / horizontalLength);
            textureV = (float) (0.5 + sourceRadius * worldZ / horizontalLength);
        } else if (zenithAngle <= Math.PI * 0.5) {
            textureU = 0.5F;
            textureV = 0.5F;
        } else {
            textureU = 0.5F + (float) SOURCE_ART_RADIUS;
            textureV = 0.5F;
        }

        var vertex = builder.addVertex(pose, x, localZ, -localY).setUv(textureU, textureV);
        if (!shaderPackActive) {
            vertex.setUv1(Math.round(textureU * 32767.0F), Math.round(textureV * 32767.0F))
                    .setColor(0, 255, 255, 255);
        }
    }

    @Override
    public List<ResourceLocation> getTexturesToRegister() {
        return List.of(texture.getTextureId());
    }
}