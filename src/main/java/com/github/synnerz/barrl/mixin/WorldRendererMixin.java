package com.github.synnerz.barrl.mixin;

import com.github.synnerz.barrl.Context;
import com.github.synnerz.barrl.events.WorldRenderEvent;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import net.minecraft.client.render.*;
import net.minecraft.client.util.Handle;
import net.minecraft.client.util.ObjectAllocator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.profiler.Profiler;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Shadow @Final private BufferBuilderStorage bufferBuilders;
    private Context ctx = new Context(true);

    @Inject(method = "render", at = @At("HEAD"))
    private void preRender(ObjectAllocator allocator, RenderTickCounter tickCounter, boolean renderBlockOutline, Camera camera, Matrix4f positionMatrix, Matrix4f projectionMatrix, GpuBufferSlice fog, Vector4f fogColor, boolean shouldRenderSky, CallbackInfo ci) {
        // Setup the context variable
        ctx.setup(bufferBuilders.getEntityVertexConsumers(), tickCounter, camera);
        Context.Companion.setImmediate(ctx);
        WorldRenderEvent.START.invoker().trigger(ctx);
    }

    @Inject(method = "method_62214", at = @At("RETURN"))
    private void postRender(GpuBufferSlice gpuBufferSlice, RenderTickCounter renderTickCounter, Camera camera, Profiler profiler, Matrix4f matrix4f, Handle handle, Handle handle2, boolean bl, Frustum frustum, Handle handle3, Handle handle4, CallbackInfo ci) {
        WorldRenderEvent.LAST.invoker().trigger(ctx);
    }

    @ModifyExpressionValue(method = "method_62214", at = @At(value = "NEW", target = "()Lnet/minecraft/client/util/math/MatrixStack;"))
    private MatrixStack setInternalStack(MatrixStack original) {
        ctx.setStacks(original);
        return original;
    }
}
