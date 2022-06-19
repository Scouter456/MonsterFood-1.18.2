package com.scouter.monsterfood.entity;

import com.scouter.monsterfood.MonsterFood;
import com.scouter.monsterfood.entity.entities.WalkingMushroomEntity;
import com.scouter.monsterfood.items.MFItems;
import com.scouter.monsterfood.setup.Registration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;
import static com.scouter.monsterfood.MonsterFood.prefix;

public class MFEntity {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, MonsterFood.MODID);

    public static final RegistryObject<EntityType<WalkingMushroomEntity>> WALKINGMUSHROOM = ENTITY_TYPES.register("walking_mushroom",
            () -> EntityType.Builder.of(WalkingMushroomEntity::new, MobCategory.CREATURE)
                    .sized(0.6f, 0.6f)
                    .build(prefix("walking_mushroom").toString()));



}
