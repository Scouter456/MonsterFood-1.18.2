package com.scouter.monsterfood.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.scouter.monsterfood.entity.entities.WalkingMushroomEntity;
import com.scouter.monsterfood.entity.model.WalkingMushroomModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;
import java.util.Random;

import static com.scouter.monsterfood.MonsterFood.prefix;

public class WalkingMushroomRenderer extends GeoEntityRenderer<WalkingMushroomEntity> {
    //Random rand = new Random();
    //float randScale = rand.nextInt(1, 10);
    private float rotationYaw;
    public WalkingMushroomRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new WalkingMushroomModel());
        this.shadowRadius = 0.25f;
    }


    @Override
    public ResourceLocation getTextureLocation(WalkingMushroomEntity object) {
        return prefix("textures/entity/walkingmushroom/walkingmushroom.png");
    }

    @Override
    public RenderType getRenderType(WalkingMushroomEntity animatable, float partialTicks, PoseStack stack, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int PackedLightIn, ResourceLocation textureLocation){

        stack.scale(2f,2f,2f);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, PackedLightIn, textureLocation);
    }

    @Override
    protected void applyRotations(WalkingMushroomEntity entityLiving, PoseStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        if(entityLiving.isDeadOrDying()){
            super.applyRotations(entityLiving, matrixStackIn, ageInTicks, this.rotationYaw, partialTicks);
        }else{
            this.rotationYaw = rotationYaw;
            super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
        }
    }
    @Override
    protected float getDeathMaxRotation(WalkingMushroomEntity entity) {
        return 0f;
    }


    @Override
    public void render(GeoModel model, WalkingMushroomEntity animatable, float partialTicks, RenderType type, PoseStack matrixStackIn,
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
