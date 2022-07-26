package com.scouter.monsterfood.world;

import com.mojang.logging.LogUtils;
import com.scouter.monsterfood.entity.MFEntity;
import com.scouter.monsterfood.world.feature.MFConfiguredFeatures;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.slf4j.Logger;

import java.util.List;

public class MFGeneration {
    private static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void generateFeatures(BiomeLoadingEvent event){
        if (event.getCategory() != Biome.BiomeCategory.DESERT){
            event.getGeneration().addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, MFConfiguredFeatures.NIGHTMARE_COLONY.getHolder().get());

        }
        spawnCreatures(event);
    }

    public static void spawnCreatures(final BiomeLoadingEvent event){
        if(!(event.getCategory().equals(Biome.BiomeCategory.THEEND) && event.getCategory().equals(Biome.BiomeCategory.NETHER) && event.getCategory().equals(Biome.BiomeCategory.UNDERGROUND))){
            List<MobSpawnSettings.SpawnerData> base = event.getSpawns().getSpawner(MobCategory.MONSTER);
            base.add(new MobSpawnSettings.SpawnerData(MFEntity.WALKINGMUSHROOM.get(), 40, 7, 15));
        }
    }
}
