package com.github.synnerz.barrl.utils

import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.RenderPhase
import net.minecraft.client.render.block.entity.BeaconBlockEntityRenderer
import net.minecraft.util.TriState
import java.util.*

// From devonian https://github.com/Synnerz/devonian/blob/main/src/main/kotlin/com/github/synnerz/devonian/utils/render/DPipelines.kt
object RendererLayers {
    private val lineWidths = HashMap<String, RenderLayer.MultiPhase>()

    fun lines(lineWidth: Double = 1.0, esp: Boolean = false): RenderLayer.MultiPhase {
        if (lineWidths.contains("lines$lineWidth")) return lineWidths["lines$lineWidth"]!!

        val key = if (esp) "lines_esp" else "lines"
        val lw = RenderPhase.LineWidth(OptionalDouble.of(lineWidth))
        val layer = RenderLayer.of(
            "barrl/$key",
            1536,
            false,
            true,
            if (esp) RendererPipelines.LINES_ESP else RendererPipelines.LINES,
            RenderLayer.MultiPhaseParameters
                .builder()
                .lineWidth(lw)
                .build(false)
        )

        lineWidths["$key$lineWidth"] = layer

        return layer
    }

    val TRIANGLE_STRIP = RenderLayer.of(
        "barrl/triangle_strip",
        1536,
        false,
        true,
        RendererPipelines.TRIANGLE_STRIP,
        RenderLayer.MultiPhaseParameters
            .builder()
            .build(false)
    )

    val TRIANGLE_STRIP_ESP = RenderLayer.of(
        "barrl/triangle_strip_esp",
        1536,
        false,
        true,
        RendererPipelines.TRIANGLE_STRIP_ESP,
        RenderLayer.MultiPhaseParameters
            .builder()
            .build(false)
    )

    val BEACON_BEAM_OPAQUE = RenderLayer.of(
        "barrl/beacon_beam_opaque",
        1536,
        false,
        true,
        RendererPipelines.BEACON_BEAM_OPAQUE,
        RenderLayer.MultiPhaseParameters
            .builder()
            .texture(RenderPhase.Texture(BeaconBlockEntityRenderer.BEAM_TEXTURE, TriState.FALSE, false))
            .build(false)
    )

    val BEACON_BEAM_OPAQUE_ESP = RenderLayer.of(
        "barrl/beacon_beam_opaque_esp",
        1536,
        false,
        true,
        RendererPipelines.BEACON_BEAM_OPAQUE_ESP,
        RenderLayer.MultiPhaseParameters
            .builder()
            .texture(RenderPhase.Texture(BeaconBlockEntityRenderer.BEAM_TEXTURE, TriState.FALSE, false))
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
            .texture(RenderPhase.Texture(BeaconBlockEntityRenderer.BEAM_TEXTURE, TriState.FALSE, false))
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
            .texture(RenderPhase.Texture(BeaconBlockEntityRenderer.BEAM_TEXTURE, TriState.FALSE, false))
            .build(false)
    )
}