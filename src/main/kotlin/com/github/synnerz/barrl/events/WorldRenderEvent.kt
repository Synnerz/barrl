package com.github.synnerz.barrl.events

import com.github.synnerz.barrl.Context
import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory

object WorldRenderEvent {
    fun interface StartEvent {
        fun trigger(ctx: Context)
    }

    fun interface LastEvent {
        fun trigger(ctx: Context)
    }

    @JvmField
    val START = bake<StartEvent> { v -> StartEvent { ctx -> v.forEach { it.trigger(ctx) } } }
    @JvmField
    val LAST = bake<LastEvent> { v -> LastEvent { ctx -> v.forEach { it.trigger(ctx) } } }

    private inline fun <reified T> bake(noinline v: (Array<T>) -> T): Event<T> =
        EventFactory.createArrayBacked(T::class.java, v)
}