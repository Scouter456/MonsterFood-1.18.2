package com.scouter.monsterfood.setup;

import com.scouter.monsterfood.MonsterFood;
import com.scouter.monsterfood.entity.MFEntity;
import com.scouter.monsterfood.entity.entities.WalkingMushroomEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
@Mod.EventBusSubscriber(modid = MonsterFood.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModSetup {

    public static void init(FMLCommonSetupEvent event){

    }

    @SubscribeEvent
    public static void onAttributeCreate(EntityAttributeCreationEvent event){
        event.put(MFEntity.WALKINGMUSHROOM.get(), WalkingMushroomEntity.setAttributes());
    }
}
