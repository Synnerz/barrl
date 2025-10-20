package com.github.synnerz.barrl.utils

import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.RenderPhase
import net.minecraft.client.render.block.entity.BeaconBlockEntityRenderer
import net.minecraft.util.TriState

// From devonian https://github.com/Synnerz/devonian/blob/main/src/main/kotlin/com/github/synnerz/devonian/utils/render/DPipelines.kt
object RendererLayers {
    val LINES = RenderLayer.of(
        "barrly/lines",
        1536,
        false,
        true,
        RendererPipelines.LINES,
        RenderLayer.MultiPhaseParameters
            .builder()
            .build(false)
    )

    val LINES_ESP = RenderLayer.of(
        "barrly/lines_esp",
        1536,
        false,
        true,
        RendererPipelines.LINES_ESP,
        RenderLayer.MultiPhaseParameters
            .builder()
            .build(false)
    )

    val TRIANGLE_STRIP = RenderLayer.of(
        "barrly/triangle_strip",
        1536,
        false,
        true,
        RendererPipelines.TRIANGLE_STRIP,
        RenderLayer.MultiPhaseParameters
            .builder()
            .build(false)
    )

    val TRIANGLE_STRIP_ESP = RenderLayer.of(
        "barrly/triangle_strip_esp",
        1536,
        false,
        true,
        RendererPipelines.TRIANGLE_STRIP_ESP,
        RenderLayer.MultiPhaseParameters
            .builder()
            .build(false)
    )

    val BEACON_BEAM_OPAQUE = RenderLayer.of(
        "barrly/beacon_beam_opaque",
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
        "barrly/beacon_beam_opaque_esp",
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
        "barrly/beacon_beam_translucent",
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
        "barrly/beacon_beam_translucent_esp",
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