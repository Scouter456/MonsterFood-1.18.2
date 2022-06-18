package com.scouter.monsterfood.entity.model;

import com.scouter.monsterfood.entity.entities.WalkingMushroomEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import static com.scouter.monsterfood.MonsterFood.prefix;

public class WalkingMushroomModel extends AnimatedGeoModel<WalkingMushroomEntity> {
    @Override
    public ResourceLocation getModelLocation(WalkingMushroomEntity object) {
        return prefix("geo/walkingmushroom.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(WalkingMushroomEntity object) {
        return prefix("textures/entity/walkingmushroom/walkingmushroom.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(WalkingMushroomEntity animatable) {
        return prefix("animations/walkingmushroom.animation.json");
    }
}
