package com.github.synnerz.barrl

import com.github.synnerz.barrl.utils.Render3D
import net.minecraft.client.render.Camera
import net.minecraft.client.render.RenderTickCounter
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.shape.VoxelShape
import java.awt.Color

class Context @JvmOverloads constructor(val isImmediate: Boolean = false) {
    lateinit var stacks: MatrixStack
    lateinit var consumers: VertexConsumerProvider
    lateinit var tickCounter: RenderTickCounter
    lateinit var camera: Camera

    internal val stacksInit: Boolean get() {
        if (!isImmediate) return true
        return ::stacks.isInitialized
    }

    fun setup(
        consumers: VertexConsumerProvider,
        tickCounter: RenderTickCounter,
        camera: Camera
    ) {
        this.consumers = consumers
        this.tickCounter = tickCounter
        this.camera = camera
    }

    /**
     * - Renders a filled shape
     * @param shape The VoxelShape instance
     * @param ox The x offset
     * @param oy The Y offset
     * @param oz The Z offset
     * @param color The color
     * @param phase Whether to render through walls or not (`false` = no)
     */
    @JvmOverloads
    fun renderFilledShape(
        shape: VoxelShape,
        ox: Double = 0.0,
        oy: Double = 0.0,
        oz: Double = 0.0,
        color: Color = Color.WHITE,
        phase: Boolean = false
    ) = Render3D.renderFilledShape(this, shape, ox, oy, oz, color, phase)

    /**
     * - Renders a filled 1x1 box
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
        x: Double = 0.0, y: Double = 0.0, z: Double = 0.0,
        color: Color = Color.WHITE,
        phase: Boolean = false,
        translate: Boolean = true
    ) = Render3D.renderFilledBox(this, x, y, z, color, phase, translate)

    /**
     * - Renders a filled box that is the size of the specified width/height
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
        x: Double, y: Double, z: Double,
        width: Double, height: Double,
        color: Color,
        phase: Boolean = false,
        translate: Boolean = true
    ) = Render3D.renderFilledBox(this, x, y, z, width, height, color, phase, translate)

    /**
     * - Renders a box at the given shape
     * @param shape The VoxelShape instance to render
     * @param ox The x offset
     * @param oy The Y offset
     * @param oz The Z offset
     * @param color The color
     * @param phase Whether to render through walls or not (`false` = no)
     */
    @JvmOverloads
    fun renderBoxShape(
        shape: VoxelShape,
        ox: Double, oy: Double, oz: Double,
        color: Color = Color.WHITE,
        phase: Boolean = false
    ) = Render3D.renderBoxShape(this, shape, ox, oy, oz, color, phase)

    /**
     * - Renders a 1x1 box
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
        x: Double, y: Double, z: Double,
        color: Color = Color.WHITE,
        phase: Boolean = false,
        translate: Boolean = true
    ) = Render3D.renderBox(this, x, y, z, color, phase, translate)

    /**
     * - Renders a box that is the size of the specified width/height
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
        x: Double, y: Double, z: Double,
        width: Double, height: Double,
        color: Color,
        phase: Boolean = false,
        translate: Boolean = true
    ) = Render3D.renderBox(this, x, y, z, width, height, color, phase, translate)

    /**
     * - Renders a string
     * - NOTE: currently not supporting `\n` inside of strings but will in the future
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
        string: String,
        x: Double, y: Double, z: Double,
        scale: Float = 1f,
        backgroundBox: Boolean = false,
        increase: Boolean = false,
        phase: Boolean = false,
        translate: Boolean = true
    ) = Render3D.renderString(this, string, x, y, z, scale, backgroundBox, increase, phase, translate)

    /**
     * - Renders a beam
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
        x: Double, y: Double, z: Double,
        color: Color = Color.WHITE,
        phase: Boolean = false,
        translate: Boolean = true
    ) = Render3D.renderBeam(this, x, y, z, color, phase, translate)

    /**
     * - Renders a waypoint-like
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
        x: Double, y: Double, z: Double,
        color: Color = Color.WHITE,
        title: String? = null,
        increase: Boolean = false,
        phase: Boolean = false
    ) = Render3D.renderWaypoint(this, x, y, z, color, title, increase, phase)

    companion object {
        @JvmStatic
        var Immediate: Context? = null
    }
}