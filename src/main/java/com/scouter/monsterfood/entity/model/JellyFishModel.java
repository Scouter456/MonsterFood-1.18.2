package com.scouter.monsterfood.entity.model;

import com.scouter.monsterfood.entity.entities.JellyFishEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

import static com.scouter.monsterfood.MonsterFood.prefix;

public class JellyFishModel  extends AnimatedTickingGeoModel<JellyFishEntity> {
    @Override
    public ResourceLocation getModelLocation(JellyFishEntity object) {
        return prefix("geo/jellyfish.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(JellyFishEntity object) {
        return prefix("textures/entity/jellyfish/jellyfish_dark.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(JellyFishEntity animatable) {
        return prefix("animations/jellyfish.animation.json");
    }
}
