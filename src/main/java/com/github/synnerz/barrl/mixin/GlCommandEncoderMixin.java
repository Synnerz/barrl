package com.github.synnerz.barrl.mixin;

import com.github.synnerz.barrl.utils.RendererPipelines;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.opengl.GlConst;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import net.minecraft.client.gl.GlCommandEncoder;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GlCommandEncoder.class)
public class GlCommandEncoderMixin {
    @Shadow
    @Nullable
    private RenderPipeline currentPipeline;

    @WrapOperation(
        method = "setPipelineAndApplyState",
        at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/opengl/GlConst;toGl(Lcom/mojang/blaze3d/platform/DepthTestFunction;)I")
    )
    private int getDepthFunc(DepthTestFunction function, Operation<Integer> original) {
        if (!RendererPipelines.getALWAYS_PASS_RENDER_PIPELINES().contains(currentPipeline)) return original.call(function);
        return GlConst.GL_ALWAYS;
    }
}
