package com.decodinator.liroth.client.rendering.skies;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.renderpearl.api.buffers.GpuBufferSlice;
import com.mojang.renderpearl.api.pipeline.IndexType;
import com.mojang.renderpearl.api.pipeline.PrimitiveTopology;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import com.mojang.renderpearl.api.vertex.VertexFormat;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.flashyreese.mods.nuit.IrisCompat;
import me.flashyreese.mods.nuit.api.NuitApi;
import me.flashyreese.mods.nuit.api.skyboxes.SkyboxRenderContext;
import me.flashyreese.mods.nuit.api.skyboxes.SkyboxType;
import me.flashyreese.mods.nuit.components.Blend;
import me.flashyreese.mods.nuit.components.Conditions;
import me.flashyreese.mods.nuit.components.Properties;
import me.flashyreese.mods.nuit.components.Texture;
import me.flashyreese.mods.nuit.render.NuitRenderBackend;
import me.flashyreese.mods.nuit.render.NuitRenderPipelines;
import me.flashyreese.mods.nuit.skybox.AbstractSkybox;
import me.flashyreese.mods.nuit.skybox.textured.TexturedSkybox;
import net.minecraft.resources.Identifier;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryUtil;

import java.util.List;
import java.util.Objects;

// if i do another change to this shit and it don't work imma be pissed off

public final class ZenithSkybox extends TexturedSkybox {
    public static final Codec<ZenithSkybox> CODEC = RecordCodecBuilder.create(instance -> instance.group(Properties.CODEC.optionalFieldOf("properties", Properties.of()).forGetter(AbstractSkybox::getProperties), Conditions.CODEC.optionalFieldOf("conditions", Conditions.of()).forGetter(AbstractSkybox::getConditions), Blend.CODEC.optionalFieldOf("blend", Blend.normal()).forGetter(TexturedSkybox::getBlend), Texture.CODEC.fieldOf("texture").forGetter(skybox -> skybox.texture)).apply(instance, ZenithSkybox::new));
    public static final SkyboxType<ZenithSkybox> TYPE = NuitApi.registerSkyboxType(Identifier.fromNamespaceAndPath("liroth", "zenith"), 1, CODEC);

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
    public void renderSkybox(SkyboxRenderContext context, Matrix4f modelViewMatrix, GpuBufferSlice dynamicTransforms) {
        context.applyFog();
        boolean shaderPackActive = IrisCompat.isShaderPackInUse();
        RenderPipeline pipeline = shaderPackActive ? NuitRenderPipelines.texturedSkybox(this.getBlend().getBlendFunction()) : NuitRenderPipelines.frameBlendedTexturedSkybox(this.getBlend().getBlendFunction());

        try (ByteBufferBuilder byteBufferBuilder = NuitRenderPipelines.byteBufferBuilder(pipeline, LONGITUDE_SEGMENTS * LATITUDE_SEGMENTS * 4)) {
            if (shaderPackActive) {
                BufferBuilder builder = NuitRenderPipelines.bufferBuilder(byteBufferBuilder, pipeline);
                for (int latitude = 0; latitude < LATITUDE_SEGMENTS; latitude++) {
                    float v0 = (float) latitude / LATITUDE_SEGMENTS;
                    float v1 = (float) (latitude + 1) / LATITUDE_SEGMENTS;
                    double lat0 = Math.PI * (0.5 - v0);
                    double lat1 = Math.PI * (0.5 - v1);

                    for (int longitude = 0; longitude < LONGITUDE_SEGMENTS; longitude++) {
                        float u0 = (float) longitude / LONGITUDE_SEGMENTS;
                        float u1 = (float) (longitude + 1) / LONGITUDE_SEGMENTS;
                        addVertex(builder, lat0, u0);
                        addVertex(builder, lat1, u0);
                        addVertex(builder, lat1, u1);
                        addVertex(builder, lat0, u1);
                    }
                }
                NuitRenderBackend.drawTextured(pipeline, builder.buildOrThrow(), dynamicTransforms, NuitRenderBackend.SAMPLER0_NAME, texture.getTextureId());
            }
            else {
                FrameBlendedMeshBuilder builder = new FrameBlendedMeshBuilder(byteBufferBuilder, pipeline);
                for (int latitude = 0; latitude < LATITUDE_SEGMENTS; latitude++) {
                    float v0 = (float) latitude / LATITUDE_SEGMENTS;
                    float v1 = (float) (latitude + 1) / LATITUDE_SEGMENTS;
                    double lat0 = Math.PI * (0.5 - v0);
                    double lat1 = Math.PI * (0.5 - v1);

                    for (int longitude = 0; longitude < LONGITUDE_SEGMENTS; longitude++) {
                        float u0 = (float) longitude / LONGITUDE_SEGMENTS;
                        float u1 = (float) (longitude + 1) / LONGITUDE_SEGMENTS;
                        addVertex(builder, lat0, u0);
                        addVertex(builder, lat1, u0);
                        addVertex(builder, lat1, u1);
                        addVertex(builder, lat0, u1);
                    }
                }
                NuitRenderBackend.drawTextured(pipeline, builder.buildOrThrow(), dynamicTransforms, NuitRenderBackend.SAMPLER0_NAME, texture.getTextureId());
            }
        }
    }

    private static void addVertex(BufferBuilder builder, double latitude, float u) {
        VertexCoordinates coordinates = coordinates(latitude, u);
        builder.addVertex(coordinates.x, coordinates.y, coordinates.z).setUv(coordinates.u, coordinates.v);
    }

    private static void addVertex(FrameBlendedMeshBuilder builder, double latitude, float u) {
        VertexCoordinates coordinates = coordinates(latitude, u);
        builder.addVertex(coordinates.x, coordinates.y, coordinates.z, coordinates.u, coordinates.v);
    }

    private static VertexCoordinates coordinates(double latitude, float u) {
        double centeredLongitude = Math.PI * 2.0 * (u - 0.5);
        float x = (float) (-RADIUS * Math.cos(latitude) * Math.sin(centeredLongitude));
        float localY = (float) (RADIUS * Math.sin(latitude));
        float localZ = (float) (RADIUS * Math.cos(latitude) * Math.cos(centeredLongitude));

        float worldX = x / RADIUS;
        float worldY = localZ / RADIUS;
        float worldZ = -localY / RADIUS;
        double zenithAngle = Math.acos(Math.clamp(worldY, -1.0, 1.0));

        double horizontalLength = Math.sqrt(worldX * worldX + worldZ * worldZ);
        double sourceRadius = zenithAngle <= Math.PI * 0.5 ? SOURCE_ART_RADIUS * zenithAngle / (Math.PI * 0.5) : SOURCE_ART_RADIUS;

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
        return new VertexCoordinates(x, localZ, -localY, textureU, textureV);
    }

    @Override
    public List<Identifier> getTexturesToRegister() {
        return List.of(texture.getTextureId());
    }

    private record VertexCoordinates(float x, float y, float z, float u, float v) {
    }

    private static final class FrameBlendedMeshBuilder {
        private final ByteBufferBuilder byteBufferBuilder;
        private final VertexFormat vertexFormat;
        private final int vertexSize;
        private final int positionOffset;
        private final int uv0Offset;
        private final int nextUvOffset;
        private final int frameBlendOffset;
        private int vertexCount;

        private FrameBlendedMeshBuilder(ByteBufferBuilder byteBufferBuilder, RenderPipeline pipeline) {
            this.byteBufferBuilder = byteBufferBuilder;
            this.vertexFormat = NuitRenderPipelines.vertexFormat(pipeline);
            this.vertexSize = this.vertexFormat.getVertexSize();
            this.positionOffset = elementOffset("Position");
            this.uv0Offset = elementOffset("UV0");
            this.nextUvOffset = elementOffset("NextUV");
            this.frameBlendOffset = elementOffset("FrameBlend");
        }

        private void addVertex(float x, float y, float z, float u, float v) {
            long pointer = this.byteBufferBuilder.reserve(this.vertexSize);
            MemoryUtil.memPutFloat(pointer + this.positionOffset, x);
            MemoryUtil.memPutFloat(pointer + this.positionOffset + 4L, y);
            MemoryUtil.memPutFloat(pointer + this.positionOffset + 8L, z);
            MemoryUtil.memPutFloat(pointer + this.uv0Offset, u);
            MemoryUtil.memPutFloat(pointer + this.uv0Offset + 4L, v);
            MemoryUtil.memPutFloat(pointer + this.nextUvOffset, u);
            MemoryUtil.memPutFloat(pointer + this.nextUvOffset + 4L, v);
            MemoryUtil.memPutFloat(pointer + this.frameBlendOffset, 0.0F);
            this.vertexCount++;
        }

        private MeshData buildOrThrow() {
            int indexCount = PrimitiveTopology.QUADS.indexCount(this.vertexCount);
            return new MeshData(Objects.requireNonNull(this.byteBufferBuilder.build(), "Failed to build skybox vertex buffer or some shit"), new MeshData.DrawState(this.vertexFormat, this.vertexCount, indexCount, PrimitiveTopology.QUADS, IndexType.least(indexCount)));
        }

        private int elementOffset(String name) {
            return Objects.requireNonNull(this.vertexFormat.getElement(name), "Missing some fuckin stupid ahh skybox vertex attribute: " + name).offset();
        }
    }
}