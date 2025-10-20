package com.github.synnerz.barrl.utils

import net.minecraft.client.render.OverlayTexture
import net.minecraft.client.render.VertexConsumer
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.math.ColorHelper
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.RotationAxis

object BeaconBeamRenderer {
    /**
     * - Edited version of renderBeam from mojang's code to allow for different Rendering Layers
     *  so that see through walls can be set
     * @author Mojang
     */
    internal fun renderBeam(
        matrices: MatrixStack,
        vertexConsumer: VertexConsumerProvider,
        partialTicks: Float,
        worldTime: Long,
        color: Int,
        phase: Boolean = false
    ) {
        val opaqueLayer = if (phase) RendererLayers.BEACON_BEAM_OPAQUE_ESP else RendererLayers.BEACON_BEAM_OPAQUE
        val translucentLayer = if (phase) RendererLayers.BEACON_BEAM_TRANSLUCENT_ESP else RendererLayers.BEACON_BEAM_TRANSLUCENT
        val heightScale = 1f
        val height = 320
        val innerRadius = 0.2f
        val outerRadius = 0.25f
        val time = Math.floorMod(worldTime, 40) + partialTicks
        val fixedTime = -time
        val wavePhase = MathHelper.fractionalPart(fixedTime * 0.2f - MathHelper.floor(fixedTime * 0.1f).toFloat())
        val animationStep = -1f + wavePhase
        var renderYOffset = height.toFloat() * heightScale * (0.5f / innerRadius) + animationStep

        matrices.push()
        matrices.translate(0.5, 0.0, 0.5)

        matrices.push()
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(time * 2.25f - 45.0f))

        renderBeamLayer(
            matrices,
            vertexConsumer.getBuffer(opaqueLayer),
            color,
            0f,
            innerRadius,
            innerRadius,
            0f,
            -innerRadius,
            0f,
            0f,
            -innerRadius,
            renderYOffset,
            animationStep
        )

        matrices.pop()

        renderYOffset = height.toFloat() * heightScale + animationStep

        renderBeamLayer(
            matrices,
            vertexConsumer.getBuffer(translucentLayer),
            ColorHelper.withAlpha(32, color),
            -outerRadius,
            -outerRadius,
            outerRadius,
            -outerRadius,
            -outerRadius,
            outerRadius,
            outerRadius,
            outerRadius,
            renderYOffset,
            animationStep
        )

        matrices.pop()
    }

    internal fun renderBeamLayer(
        matrices: MatrixStack,
        vertices: VertexConsumer,
        color: Int,
        x1: Float,
        z1: Float,
        x2: Float,
        z2: Float,
        x3: Float,
        z3: Float,
        x4: Float,
        z4: Float,
        v1: Float,
        v2: Float
    ) {
        val entry = matrices.peek()
        renderBeamFace(entry, vertices, color, x1, z1, x2, z2, v1, v2)
        renderBeamFace(entry, vertices, color, x4, z4, x3, z3, v1, v2)
        renderBeamFace(entry, vertices, color, x2, z2, x4, z4, v1, v2)
        renderBeamFace(entry, vertices, color, x3, z3, x1, z1, v1, v2)
    }

    internal fun renderBeamFace(
        matrix: MatrixStack.Entry,
        vertices: VertexConsumer,
        color: Int,
        x1: Float,
        z1: Float,
        x2: Float,
        z2: Float,
        v1: Float,
        v2: Float
    ) {
        renderBeamVertex(matrix, vertices, color, 320, x1, z1, 1f, v1)
        renderBeamVertex(matrix, vertices, color, 0, x1, z1, 1f, v2)
        renderBeamVertex(matrix, vertices, color, 0, x2, z2, 0f, v2)
        renderBeamVertex(matrix, vertices, color, 320, x2, z2, 0f, v1)
    }

    internal fun renderBeamVertex(
        matrix: MatrixStack.Entry,
        vertices: VertexConsumer,
        color: Int,
        y: Int,
        x: Float,
        z: Float,
        u: Float,
        v: Float
    ) {
        vertices.vertex(matrix, x, y.toFloat(), z).color(color).texture(u, v).overlay(OverlayTexture.DEFAULT_UV)
            .light(15728880).normal(matrix, 0.0f, 1.0f, 0.0f)
    }
}