package com.scouter.monsterfood.entity;

import com.scouter.monsterfood.MonsterFood;
import com.scouter.monsterfood.entity.entities.JellyFishEntity;
import com.scouter.monsterfood.entity.entities.LavaSnailEntity;
import com.scouter.monsterfood.entity.entities.WalkingMushroomEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.scouter.monsterfood.MonsterFood.prefix;

public class MFEntity {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, MonsterFood.MODID);

    public static final RegistryObject<EntityType<WalkingMushroomEntity>> WALKINGMUSHROOM = ENTITY_TYPES.register("walking_mushroom",
            () -> EntityType.Builder.of(WalkingMushroomEntity::new, MobCategory.CREATURE)
                    .sized(0.6f, 0.6f)
                    .build(prefix("walking_mushroom").toString()));

    public static final RegistryObject<EntityType<LavaSnailEntity>> LAVASNAIL = ENTITY_TYPES.register("lava_snail",
            () -> EntityType.Builder.of(LavaSnailEntity::new, MobCategory.CREATURE)
                    .sized(2, 0.6f)
                    .build(prefix("lava_snail").toString()));


    public static final RegistryObject<EntityType<JellyFishEntity>> JELLY_FISH = ENTITY_TYPES.register("jelly_fish",
            () -> EntityType.Builder.of(JellyFishEntity::new, MobCategory.CREATURE)
                    .sized(2, -2f)
                    .build(prefix("jelly_fish").toString()));
}
