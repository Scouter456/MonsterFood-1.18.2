package com.scouter.monsterfood.setup;

import com.scouter.monsterfood.MonsterFood;
import com.scouter.monsterfood.entity.MFEntity;
import com.scouter.monsterfood.entity.MFEntityPlacement;
import com.scouter.monsterfood.entity.entities.LavaSnailEntity;
import com.scouter.monsterfood.entity.entities.WalkingMushroomEntity;
import com.scouter.monsterfood.recipes.CuttingBoardRecipe;
import com.scouter.monsterfood.structuresystem.data.StructureEvents;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
@Mod.EventBusSubscriber(modid = MonsterFood.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModSetup {

    public static void init(FMLCommonSetupEvent event){
        event.enqueueWork(() -> {
            MFEntityPlacement.entityPlacement();
        });
    }

    @SubscribeEvent
    public static void onAttributeCreate(EntityAttributeCreationEvent event){
        event.put(MFEntity.WALKINGMUSHROOM.get(), WalkingMushroomEntity.setAttributes());
        event.put(MFEntity.LAVASNAIL.get(), LavaSnailEntity.setAttributes());
    }

    @SubscribeEvent
    public static void registerRecipeTypes(RegistryEvent.Register<RecipeSerializer<?>> event){
        //Registry.register(Registry.RECIPE_TYPE, CuttingBoardRecipe.Type.ID, CuttingBoardRecipe.Type.INSTANCE);

        event.getRegistry().register(CuttingBoardRecipe.SERIALIZER);
    }


}
