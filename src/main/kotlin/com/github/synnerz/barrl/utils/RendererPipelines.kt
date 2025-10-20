package com.github.synnerz.barrl.utils

import com.mojang.blaze3d.pipeline.BlendFunction
import com.mojang.blaze3d.pipeline.RenderPipeline
import com.mojang.blaze3d.platform.DepthTestFunction
import com.mojang.blaze3d.vertex.VertexFormat.DrawMode
import net.minecraft.client.gl.RenderPipelines
import net.minecraft.client.render.VertexFormats

// From devonian https://github.com/Synnerz/devonian/blob/main/src/main/kotlin/com/github/synnerz/devonian/utils/render/DLayers.kt
object RendererPipelines {
    val LINES = RenderPipeline.builder(RenderPipelines.RENDERTYPE_LINES_SNIPPET)
        .withLocation("barrl/lines")
        .withCull(false)
        .withBlend(BlendFunction.TRANSLUCENT)
        .withDepthWrite(true)
        .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
        .build()

    val LINES_ESP = RenderPipeline.builder(RenderPipelines.RENDERTYPE_LINES_SNIPPET)
        .withLocation("barrl/lines")
        .withCull(false)
        .withBlend(BlendFunction.TRANSLUCENT)
        .withDepthWrite(false)
        .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
        .build()

    val TRIANGLE_STRIP = RenderPipeline.builder(RenderPipelines.POSITION_COLOR_SNIPPET)
        .withLocation("barrl/triangle_strip")
        .withCull(false)
        .withVertexFormat(VertexFormats.POSITION_COLOR, DrawMode.TRIANGLE_STRIP)
        .withDepthWrite(true)
        .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
        .withBlend(BlendFunction.TRANSLUCENT)
        .build()

    val TRIANGLE_STRIP_ESP = RenderPipeline.builder(RenderPipelines.POSITION_COLOR_SNIPPET)
        .withLocation("barrl/triangle_strip_esp")
        .withCull(false)
        .withVertexFormat(VertexFormats.POSITION_COLOR, DrawMode.TRIANGLE_STRIP)
        .withDepthWrite(false)
        .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
        .withBlend(BlendFunction.TRANSLUCENT)
        .build()

    val BEACON_BEAM_OPAQUE = RenderPipeline.builder(RenderPipelines.RENDERTYPE_BEACON_BEAM_SNIPPET)
        .withLocation("barrl/beacon_beam_opaque")
        .build()

    val BEACON_BEAM_OPAQUE_ESP = RenderPipeline.builder(RenderPipelines.RENDERTYPE_BEACON_BEAM_SNIPPET)
        .withLocation("barrl/beacon_beam_opaque_esp")
        .withDepthWrite(false)
        .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
        .build()

    val BEACON_BEAM_TRANSLUCENT = RenderPipeline.builder(RenderPipelines.RENDERTYPE_BEACON_BEAM_SNIPPET)
        .withLocation("barrl/beacon_beam_translucent")
        .withDepthWrite(false)
        .withBlend(BlendFunction.TRANSLUCENT)
        .build()

    val BEACON_BEAM_TRANSLUCENT_ESP = RenderPipeline.builder(RenderPipelines.RENDERTYPE_BEACON_BEAM_SNIPPET)
        .withLocation("barrl/beacon_beam_translucent_esp")
        .withDepthWrite(false)
        .withBlend(BlendFunction.TRANSLUCENT)
        .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
        .build()
}