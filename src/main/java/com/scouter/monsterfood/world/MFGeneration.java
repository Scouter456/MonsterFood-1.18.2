package com.scouter.monsterfood.world;

import com.mojang.logging.LogUtils;
import com.scouter.monsterfood.world.feature.MFConfiguredFeatures;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import org.slf4j.Logger;

import java.util.Locale;

public class MFGeneration {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static void onBiomeLoading(BiomeLoadingEvent event){
        if (event.getCategory() != Biome.BiomeCategory.DESERT){
            event.getGeneration().addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, MFConfiguredFeatures.NIGHTMARE_COLONY.getHolder().get());

        }
    }
}
