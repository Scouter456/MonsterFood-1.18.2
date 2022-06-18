package com.scouter.monsterfood.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.scouter.monsterfood.entity.entities.WalkingMushroomEntity;
import com.scouter.monsterfood.entity.model.WalkingMushroomModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Random;

import static com.scouter.monsterfood.MonsterFood.prefix;

public class WalkingMushroomRenderer extends GeoEntityRenderer<WalkingMushroomEntity> {
    //Random rand = new Random();
    //float randScale = rand.nextInt(1, 10);
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
}
