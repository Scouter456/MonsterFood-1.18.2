package com.scouter.monsterfood.world.feature;

import com.scouter.monsterfood.MonsterFood;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.CountConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MFFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, MonsterFood.MODID);
    private static final String NIGHTMARE_COLONY_NAME = "nightmare_colony";

    public static final RegistryObject<Feature<CountConfiguration>> NIGHTMARE_COLONY = FEATURES.register(NIGHTMARE_COLONY_NAME, () -> new NightmareFeature(CountConfiguration.CODEC));

}
