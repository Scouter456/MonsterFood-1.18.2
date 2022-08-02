package com.scouter.monsterfood.world;

import com.mojang.logging.LogUtils;
import com.scouter.monsterfood.entity.MFEntity;
import com.scouter.monsterfood.world.feature.MFConfiguredFeatures;
import net.minecraft.resources.ResourceLocation;
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
    public static void generateFeatures(BiomeLoadingEvent event) {
        //LOGGER.info("Categories: " + event.getCategory().getName().toLowerCase() + " temperature: " + event.getClimate().temperature + " downfall: " + event.getClimate().downfall + " effects: " + event.getEffects().getAmbientAdditionsSettings());

        if (event.getCategory() != Biome.BiomeCategory.DESERT) {
            event.getGeneration().addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, MFConfiguredFeatures.NIGHTMARE_COLONY.getHolder().get());

        }

        if (event.getName().equals(new ResourceLocation("minecraft", "bamboo_jungle"))) {
            event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, MFConfiguredFeatures.RED_BAMBOO_NO_PODZOL_PLACED.getHolder().get());
            event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, MFConfiguredFeatures.RED_BAMBOO_PODZOL_PLACED.getHolder().get());
        }
        spawnCreatures(event);
    }

    public static void spawnCreatures(final BiomeLoadingEvent event) {
        if (!(event.getCategory().equals(Biome.BiomeCategory.THEEND) && event.getCategory().equals(Biome.BiomeCategory.NETHER) && event.getCategory().equals(Biome.BiomeCategory.UNDERGROUND))) {
            List<MobSpawnSettings.SpawnerData> base = event.getSpawns().getSpawner(MobCategory.MONSTER);
            base.add(new MobSpawnSettings.SpawnerData(MFEntity.WALKINGMUSHROOM.get(), 40, 7, 15));
        }
    }
}
