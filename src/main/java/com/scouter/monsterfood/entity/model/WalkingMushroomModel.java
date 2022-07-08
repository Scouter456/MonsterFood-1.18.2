package com.scouter.monsterfood.entity.model;

import com.mojang.logging.LogUtils;
import com.scouter.monsterfood.entity.entities.WalkingMushroomEntity;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import software.bernie.example.entity.GeoExampleEntity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

import javax.annotation.Nullable;

import static com.scouter.monsterfood.MonsterFood.prefix;

public class WalkingMushroomModel extends AnimatedTickingGeoModel<WalkingMushroomEntity> {

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

    @Override
    public void setLivingAnimations(WalkingMushroomEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
    }
}
