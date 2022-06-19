package com.scouter.monsterfood.entity;

import com.scouter.monsterfood.entity.entities.WalkingMushroomEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ambient.AmbientCreature;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MFEntityPlacement {
    public  static void entityPlacement() {
        SpawnPlacements.register(MFEntity.WALKINGMUSHROOM.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MushroomEntity::passPeacefulAndYCheck);
    }
}
