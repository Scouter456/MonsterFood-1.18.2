package com.scouter.monsterfood.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.scouter.monsterfood.entity.entities.JellyFishEntity;
import com.scouter.monsterfood.entity.entities.LavaSnailEntity;
import com.scouter.monsterfood.entity.model.JellyFishModel;
import com.scouter.monsterfood.entity.model.LavaSnailModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

public class JellyFishRenderer extends GeoEntityRenderer<JellyFishEntity> {
    public JellyFishRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new JellyFishModel());
        this.shadowRadius = 1.0f;
    }

    @Override
    public RenderType getRenderType(JellyFishEntity animatable, float partialTicks, PoseStack stack, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int PackedLightIn, ResourceLocation textureLocation){
        //stack.scale(2f,2f,2f);
        return RenderType.entityTranslucentCull(getTextureLocation(animatable));
    }

    @Override
    protected float getDeathMaxRotation(JellyFishEntity entity) {
        return 0f;
    }

    @Override
    public void render(GeoModel model, JellyFishEntity animatable, float partialTicks, RenderType type, PoseStack matrixStackIn,
                       @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn,
                       int packedOverlayIn, float red, float green, float blue, float alpha) {
        renderEarly(animatable, matrixStackIn, partialTicks, renderTypeBuffer, vertexBuilder, packedLightIn,
                packedOverlayIn, red, green, blue, alpha);

        if (renderTypeBuffer != null) {
            vertexBuilder = renderTypeBuffer.getBuffer(type);
        }
        //Makes it that the entity is only rendered with a red overlay when hurt but not on death.
        renderRecursively(model.topLevelBones.get(0), matrixStackIn, vertexBuilder, packedLightIn, OverlayTexture.pack(0,
                OverlayTexture.v(animatable.hurtTime > 0)), red, green, blue, alpha);
    }
}
