package com.github.synnerz.barrl.utils

import com.github.synnerz.barrl.Context
import net.minecraft.client.MinecraftClient
import net.minecraft.client.font.TextRenderer
import net.minecraft.client.render.LightmapTextureManager
import net.minecraft.client.render.VertexConsumer
import net.minecraft.client.render.VertexRendering
import net.minecraft.util.shape.VoxelShape
import java.awt.Color
import kotlin.math.sqrt

/**
 * This class holds the entire logic to render certain shapes in world
 * it is done this way, so it's easier to change and read the [Context] class's code
 */
object Render3D {
    private val minecraft = MinecraftClient.getInstance()
    private val textRenderer = minecraft.textRenderer

    /**
     * - Renders a filled shape
     * @param ctx The Context instance
     * @param shape The VoxelShape instance to render
     * @param ox The x offset
     * @param oy The y offset
     * @param oz The z offset
     * @param color The color
     * @param phase Whether to render through walls or not (`false` = no)
     */
    @JvmOverloads
    fun renderFilledShape(
        ctx: Context,
        shape: VoxelShape,
        ox: Double, oy: Double, oz: Double,
        color: Color, phase: Boolean = false
    ) {
        if (color.alpha == 0) return
        if (!ctx.stacksInit) return

        val consumers = ctx.consumers
        val matrices = ctx.stacks
        val layer = if (phase) RendererLayers.TRIANGLE_STRIP_ESP else RendererLayers.TRIANGLE_STRIP

        // TODO: make this more efficient later on
        //  (this does way too many calls but shouldn't matter much as of right now)
        shape.forEachBox { minX: Double, minY: Double, minZ: Double, maxX: Double, maxY: Double, maxZ: Double ->
            VertexRendering.drawFilledBox(
                matrices,
                consumers.getBuffer(layer),
                minX + ox, minY + oy, minZ + oz,
                maxX + ox, maxY + oy, maxZ + oz,
                color.red / 255f, color.green / 255f, color.blue / 255f, color.alpha / 255f
            )
        }
    }

    /**
     * - Renders a filled 1x1 box
     * @param ctx The Context instance
     * @param x
     * @param y
     * @param z
     * @param color Color instance
     * @param phase Whether to render through walls or not (`false` = no)
     * @param translate Whether to translate the position by the camera entity,
     *   this allows it to look in the correct place in some instances (`true` by default since it's often needed)
     */
    @JvmOverloads
    fun renderFilledBox(
        ctx: Context,
        x: Double, y: Double, z: Double,
        color: Color,
        phase: Boolean = false,
        translate: Boolean = true
    ) = renderFilledBox(ctx, x, y, z, 1.0, 1.0, color, phase, translate)

    /**
     * - Renders a filled box that is the size of the specified width/height
     * @param ctx The Context instance
     * @param x
     * @param y
     * @param z
     * @param width
     * @param height
     * @param color Color instance
     * @param phase Whether to render through walls or not (`false` = no)
     * @param translate Whether to translate the position by the camera entity,
     *   this allows it to look in the correct place in some instances (`true` by default since it's often needed)
     */
    @JvmOverloads
    fun renderFilledBox(
        ctx: Context,
        x: Double, y: Double, z: Double,
        width: Double, height: Double,
        color: Color,
        phase: Boolean = false,
        translate: Boolean = true
    ) {
        if (color.alpha == 0) return
        if (!ctx.stacksInit) return

        var cx = x
        var cz = z
        var cy = y
        val layer = if (phase) RendererLayers.TRIANGLE_STRIP_ESP else RendererLayers.TRIANGLE_STRIP
        val camPos = ctx.camera.pos.negate()

        // Add slightly more to the coords if phase is false
        //  since the block will take over it, and it won't render properly (if it's in a block)
        if (!phase) {
            cx += 0.003
            cy += 0.003
            cz += 0.003
        }

        if (translate) {
            ctx.stacks.push()
            ctx.stacks.translate(camPos.x, camPos.y, camPos.z)
        }

        VertexRendering.drawFilledBox(
            ctx.stacks,
            ctx.consumers.getBuffer(layer),
            cx, cy, cz,
            cx + width, cy + height + 0.003, cz + width,
            color.red / 255f, color.green / 255f, color.blue / 255f, color.alpha / 255f
        )

        if (translate) ctx.stacks.pop()
    }

    /**
     * - Renders a box at the given shape
     * @param ctx The Context instance
     * @param shape The VoxelShape instance to render
     * @param ox The x offset
     * @param oy The y offset
     * @param oz The z offset
     * @param color The color
     * @param phase Whether to render through walls or not (`false` = no)
     */
    @JvmOverloads
    fun renderBoxShape(
        ctx: Context,
        shape: VoxelShape,
        ox: Double, oy: Double, oz: Double,
        color: Color, phase: Boolean = false,
        lineWidth: Double = 1.0
    ) {
        if (color.alpha == 0) return
        if (!ctx.stacksInit) return

        val consumers = ctx.consumers
        val matrices = ctx.stacks
        val layer = RendererLayers.lines(lineWidth, phase)

        VertexRendering.drawOutline(
            matrices,
            consumers.getBuffer(layer),
            shape,
            ox, oy, oz,
            color.rgb
        )
    }

    /**
     * - Renders a 1x1 box
     * @param ctx The Context instance
     * @param x
     * @param y
     * @param z
     * @param color Color instance
     * @param phase Whether to render through walls or not (`false` = no)
     * @param translate Whether to translate the position by the camera entity,
     *   this allows it to look in the correct place in some instances (`true` by default since it's often needed)
     */
    @JvmOverloads
    fun renderBox(
        ctx: Context,
        x: Double, y: Double, z: Double,
        color: Color,
        phase: Boolean = false,
        translate: Boolean = true,
        lineWidth: Double = 1.0
    ) = renderBox(ctx, x, y, z, 1.0, 1.0, color, phase, translate, lineWidth)

    /**
     * - Renders a box that is the size of the specified width/height
     * @param ctx The Context instance
     * @param x
     * @param y
     * @param z
     * @param width
     * @param height
     * @param color Color instance
     * @param phase Whether to render through walls or not (`false` = no)
     * @param translate Whether to translate the position by the camera entity,
     *   this allows it to look in the correct place in some instances (`true` by default since it's often needed)
     */
    @JvmOverloads
    fun renderBox(
        ctx: Context,
        x: Double, y: Double, z: Double,
        width: Double, height: Double,
        color: Color,
        phase: Boolean = false,
        translate: Boolean = true,
        lineWidth: Double = 1.0
    ) {
        if (color.alpha == 0) return
        if (!ctx.stacksInit) return

        val layer = RendererLayers.lines(lineWidth, phase)
        val camPos = ctx.camera.pos.negate()

        if (translate) {
            ctx.stacks.push()
            ctx.stacks.translate(camPos.x, camPos.y, camPos.z)
        }

        VertexRendering.drawBox(
            ctx.stacks,
            ctx.consumers.getBuffer(layer),
            x, y, z,
            x + width, y + height, z + width,
            color.red / 255f, color.green / 255f, color.blue / 255f, color.alpha / 255f
        )

        if (translate) ctx.stacks.pop()
    }

    /**
     * - Renders a string
     * - NOTE: currently not supporting `\n` inside of strings but will in the future
     * @param ctx The [Context] instance
     * @param string The string to render
     * @param x
     * @param y
     * @param z
     * @param scale The scaling factor
     * @param backgroundBox Whether to render a background box or not
     * @param increase Whether to increase the size of the String depending on the player position
     * @param phase Whether to render through walls or not (`false` = no)
     * @param translate Whether to translate the position by the camera entity,
     *   this allows it to look in the correct place in some instances (`true` by default since it's often needed)
     */
    @JvmOverloads
    fun renderString(
        ctx: Context,
        string: String,
        x: Double, y: Double, z: Double,
        scale: Float = 1f,
        backgroundBox: Boolean = false,
        increase: Boolean = false,
        phase: Boolean = false,
        translate: Boolean = true
    ) {
        if (!ctx.stacksInit) return

        var toScale = scale
        val consumer = minecraft.bufferBuilders.entityVertexConsumers
        val offset = -textRenderer.getWidth(string) / 2f
        val textLayer = if (phase) TextRenderer.TextLayerType.SEE_THROUGH else TextRenderer.TextLayerType.NORMAL
        val camPos = ctx.camera.pos

        var ox = 0.0
        var oy = 0.0
        var oz = 0.0

        if (translate) {
            ox = camPos.x
            oy = camPos.y
            oz = camPos.z
        }

        val dx = (x - ox).toFloat()
        val dy = (y - oy).toFloat()
        val dz = (z - oz).toFloat()

        toScale *= if (increase) sqrt(dx * dx + dy * dy + dz * dz) / 120f else 0.025f

        ctx.stacks.push()
        ctx.stacks.peek().positionMatrix
            .translate(dx, dy, dz)
            .rotate(ctx.camera.rotation)
            .scale(toScale, -toScale, toScale)

        if (backgroundBox) {
            textRenderer.draw(
                string,
                offset,
                0f,
                0x20FFFFFF,
                true,
                ctx.stacks.peek().positionMatrix,
                consumer,
                textLayer,
                (minecraft.options.getTextBackgroundOpacity(0.25f) * 255).toInt() shl 24,
                LightmapTextureManager.MAX_BLOCK_LIGHT_COORDINATE
            )
        }

        textRenderer.draw(
            string,
            offset,
            0f,
            0xFFFFFFFF.toInt(),
            true,
            ctx.stacks.peek().positionMatrix,
            consumer,
            textLayer,
            0,
            LightmapTextureManager.MAX_BLOCK_LIGHT_COORDINATE
        )

        consumer.draw()
        ctx.stacks.pop()
    }

    /**
     * - Renders a beam
     * @param ctx The [Context] instance
     * @param x
     * @param y
     * @param z
     * @param color Color instance
     * @param phase Whether to render through walls or not (`false` = no)
     * @param translate Whether to translate the position by the camera entity,
     *   this allows it to look in the correct place in some instances (`true` by default since it's often needed)
     */
    @JvmOverloads
    fun renderBeam(
        ctx: Context,
        x: Double, y: Double, z: Double,
        color: Color,
        phase: Boolean = false,
        translate: Boolean = true
    ) {
        if (color.alpha == 0) return
        if (!ctx.stacksInit) return

        val world = minecraft.world ?: return
        val camPos = ctx.camera.pos
        var dx = 0.0
        var dy = 0.0
        var dz = 0.0

        if (translate) {
            dx = camPos.x
            dy = camPos.y
            dz = camPos.z
        }

        ctx.stacks.push()
        ctx.stacks.translate(x - dx, y - dy, z - dz)

        BeaconBeamRenderer.renderBeam(
            ctx.stacks,
            ctx.consumers,
            ctx.tickCounter.getTickProgress(true),
            world.time,
            color.rgb,
            phase
        )

        ctx.stacks.pop()
    }

    /**
     * - Renders a waypoint-like
     * @param ctx The [Context] instance
     * @param x
     * @param y
     * @param z
     * @param title The title to be displayed in the waypoint
     * @param increase Whether to increase the size of the String depending on the player position
     * @param color Color instance
     * @param phase Whether to render through walls or not (`false` = no)
     */
    @JvmOverloads
    fun renderWaypoint(
        ctx: Context,
        x: Double, y: Double, z: Double,
        color: Color,
        title: String? = null,
        increase: Boolean = false,
        phase: Boolean = false
    ) {
        if (color.alpha == 0) return
        if (!ctx.stacksInit) return

        val pos = minecraft.player ?: return
        val dx = x - pos.x
        val dy = y + 2 - pos.y
        val dz = z - pos.z

        renderFilledBox(ctx, x, y, z, Color(color.red, color.green, color.blue, color.alpha / 3), phase)
        renderBox(ctx, x, y, z, color, phase)
        renderBeam(ctx, x, y + 1, z, color, phase)
        val dist = sqrt(dx * dx + dy * dy + dz * dz)
        if (dist > 10.0) renderString(
            ctx,
            title ?: "%.2fm".format(dist),
            x + 0.5,
            y + 2.0,
            z + 0.5,
            backgroundBox = true,
            increase = increase,
            phase = phase
        )
    }

    /**
     * - Renders a tracer
     * @param ctx The [Context] instance
     * @param x
     * @param y
     * @param z
     * @param color Color instance
     * @param phase Whether to render through walls or not (`true` = yes)
     * @param translate Whether to translate the position by the camera entity,
     *   this allows it to look in the correct place in some instances (`true` by default since it's often needed)
     */
    @JvmOverloads
    fun renderTracer(
        ctx: Context,
        x: Double, y: Double, z: Double,
        color: Color,
        phase: Boolean = true,
        translate: Boolean = true
    ) {
        if (!ctx.stacksInit) return

        val layer = if (phase) RendererLayers.LINES_ESP else RendererLayers.LINES
        val camPos = ctx.camera.pos.negate()
        val look = ctx.camera.rotation

        val ox = (if (translate) x - camPos.x else x).toFloat()
        val oy = (if (translate) y - camPos.y else y).toFloat()
        val oz = (if (translate) z - camPos.z else z).toFloat()

        val consumer = ctx.consumers.getBuffer(layer)
        consumer.vertex(look.x, look.y, look.z).color(color.red, color.green, color.blue, color.alpha).normal(0f, 1f, 0f)
        consumer.vertex(ox, oy, oz).color(color.red, color.green, color.blue, color.alpha).normal(0f, 1f, 0f)
    }

    /**
     * - Adds a new vertex to the current [consumer]
     * @param consumer The [VertexConsumer] instance
     * @param x
     * @Param y
     * @param z
     * @param color Color instance
     */
    @JvmOverloads
    fun vert(consumer: VertexConsumer, x: Double, y: Double, z: Double = 0.0, color: Color = Color.WHITE) {
        consumer.vertex(x.toFloat(), y.toFloat(), z.toFloat()).color(color.rgb)
    }
}
