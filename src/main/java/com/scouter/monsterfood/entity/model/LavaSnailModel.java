package com.scouter.monsterfood.entity.model;

import com.scouter.monsterfood.entity.entities.LavaSnailEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

import static com.scouter.monsterfood.MonsterFood.prefix;

public class LavaSnailModel extends AnimatedTickingGeoModel<LavaSnailEntity> {
    @Override
    public ResourceLocation getModelLocation(LavaSnailEntity object) {
        return prefix("geo/lavasnail.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(LavaSnailEntity object) {
        return prefix("textures/entity/lavasnail/lavasnail.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(LavaSnailEntity animatable) {
        return prefix("animations/lavasnail.animation.json");
    }
}
