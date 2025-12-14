package com.github.synnerz.barrl.utils

import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.RenderPhase
import net.minecraft.client.render.block.entity.BeaconBlockEntityRenderer
import java.util.*
import kotlin.math.ceil

// From devonian https://github.com/Synnerz/devonian/blob/main/src/main/kotlin/com/github/synnerz/devonian/utils/render/DPipelines.kt
object RendererLayers {
    private data class RenderLayerKey(val lineWidth: Double, val esp: Boolean, val opaque: Boolean)
    private val cachedLineLayers = mutableMapOf<RenderLayerKey, RenderLayer.MultiPhase>()

    fun lines(lineWidth: Double = 1.0, phase: Boolean = false, opaque: Boolean): RenderLayer.MultiPhase {
        val lineWidth = ceil(lineWidth * 10.0) / 10.0
        return cachedLineLayers.getOrPut(RenderLayerKey(lineWidth, phase, opaque)) {
            val name = if (phase) "lines_esp_${lineWidth}_$opaque" else "lines_${lineWidth}_$opaque"
            val lw = RenderPhase.LineWidth(OptionalDouble.of(lineWidth))
            RenderLayer.of(
                "barrl/$name",
                1536,
                false,
                !opaque,
                if (phase) {
                    if (opaque) RendererPipelines.LINES_OPAQUE_ESP
                    else RendererPipelines.LINES_TRANSLUCENT_ESP
                } else {
                    if (opaque) RendererPipelines.LINES_OPAQUE
                    else RendererPipelines.LINES_TRANSLUCENT
                },
                RenderLayer.MultiPhaseParameters
                    .builder()
                    .lineWidth(lw)
                    .build(false)
            )
        }
    }

    val TRIANGLE_STRIP_OPAQUE = RenderLayer.of(
        "barrl/triangle_strip_opaque",
        1536,
        false,
        false,
        RendererPipelines.TRIANGLE_STRIP_OPAQUE,
        RenderLayer.MultiPhaseParameters
            .builder()
            .build(false)
    )

    val TRIANGLE_STRIP_OPAQUE_ESP = RenderLayer.of(
        "barrl/triangle_strip_opaque_esp",
        1536,
        false,
        false,
        RendererPipelines.TRIANGLE_STRIP_OPAQUE_ESP,
        RenderLayer.MultiPhaseParameters
            .builder()
            .build(false)
    )

    val TRIANGLE_STRIP_TRANSLUCENT = RenderLayer.of(
        "barrl/triangle_strip_translucent",
        1536,
        false,
        true,
        RendererPipelines.TRIANGLE_STRIP_TRANSLUCENT,
        RenderLayer.MultiPhaseParameters
            .builder()
            .build(false)
    )

    val TRIANGLE_STRIP_TRANSLUCENT_ESP = RenderLayer.of(
        "barrl/triangle_strip_translucent_esp",
        1536,
        false,
        true,
        RendererPipelines.TRIANGLE_STRIP_TRANSLUCENT_ESP,
        RenderLayer.MultiPhaseParameters
            .builder()
            .build(false)
    )

    val BEACON_BEAM_OPAQUE = RenderLayer.of(
        "barrl/beacon_beam_opaque",
        1536,
        false,
        false,
        RendererPipelines.BEACON_BEAM_OPAQUE,
        RenderLayer.MultiPhaseParameters
            .builder()
            .texture(RenderPhase.Texture(BeaconBlockEntityRenderer.BEAM_TEXTURE, false))
            .build(false)
    )

    val BEACON_BEAM_OPAQUE_ESP = RenderLayer.of(
        "barrl/beacon_beam_opaque_esp",
        1536,
        false,
        false,
        RendererPipelines.BEACON_BEAM_OPAQUE_ESP,
        RenderLayer.MultiPhaseParameters
            .builder()
            .texture(RenderPhase.Texture(BeaconBlockEntityRenderer.BEAM_TEXTURE, false))
            .build(false)
    )

    val BEACON_BEAM_TRANSLUCENT = RenderLayer.of(
        "barrl/beacon_beam_translucent",
        1536,
        false,
        true,
        RendererPipelines.BEACON_BEAM_TRANSLUCENT,
        RenderLayer.MultiPhaseParameters
            .builder()
            .texture(RenderPhase.Texture(BeaconBlockEntityRenderer.BEAM_TEXTURE, false))
            .build(false)
    )

    val BEACON_BEAM_TRANSLUCENT_ESP = RenderLayer.of(
        "barrl/beacon_beam_translucent_esp",
        1536,
        false,
        true,
        RendererPipelines.BEACON_BEAM_TRANSLUCENT_ESP,
        RenderLayer.MultiPhaseParameters
            .builder()
            .texture(RenderPhase.Texture(BeaconBlockEntityRenderer.BEAM_TEXTURE, false))
            .build(false)
    )
}