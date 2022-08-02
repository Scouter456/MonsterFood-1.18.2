package com.scouter.monsterfood.world.feature;

import com.google.common.collect.ImmutableList;
import com.scouter.monsterfood.MonsterFood;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.CountConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Random;

public class MFConfiguredFeatures {
    public static final DeferredRegister<ConfiguredFeature<?,?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, MonsterFood.MODID);
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, MonsterFood.MODID);
    private static final String NIGHTMARE_COLONY_NAME = "nightmare_colony";
    private static final String RED_BAMBOO_FEATURE_NO_PODZOL_NAME = "red_bamboo_no_podzol_feature";
    private static final String RED_BAMBOO_FEATURE_PODZOL_NAME = "red_bamboo_podzol_feature";
    private static Random rand = new Random();
    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_NIGHTMARE_COLONY = CONFIGURED_FEATURES.register(NIGHTMARE_COLONY_NAME, () -> new ConfiguredFeature<>(MFFeatures.NIGHTMARE_COLONY.get(), new CountConfiguration(rand.nextInt(6)+1)));
    public static final RegistryObject<ConfiguredFeature<?,?>> RED_BAMBOO_FEATURE_NO_PODZOL = CONFIGURED_FEATURES.register(RED_BAMBOO_FEATURE_NO_PODZOL_NAME, () -> new ConfiguredFeature<>(MFFeatures.RED_BAMBOO_FEATURE.get(), new ProbabilityFeatureConfiguration(0f)));
    public static final RegistryObject<ConfiguredFeature<?,?>> RED_BAMBOO_FEATURE_PODZOL = CONFIGURED_FEATURES.register(RED_BAMBOO_FEATURE_PODZOL_NAME, () -> new ConfiguredFeature<>(MFFeatures.RED_BAMBOO_FEATURE.get(), new ProbabilityFeatureConfiguration(0.2f)));

    public static final RegistryObject<PlacedFeature> NIGHTMARE_COLONY = PLACED_FEATURES.register("nightmare_colony", () -> new PlacedFeature(CONFIGURED_NIGHTMARE_COLONY.getHolder().orElseThrow(), ImmutableList.of(
            RarityFilter.onAverageOnceEvery(70),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP_TOP_SOLID,
            BiomeFilter.biome()
    )));

    public static final RegistryObject<PlacedFeature> RED_BAMBOO_NO_PODZOL_PLACED = PLACED_FEATURES.register(RED_BAMBOO_FEATURE_NO_PODZOL_NAME, () -> new PlacedFeature(RED_BAMBOO_FEATURE_NO_PODZOL.getHolder().orElseThrow(), ImmutableList.of(
            RarityFilter.onAverageOnceEvery(400),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP,
            BiomeFilter.biome()
    )));

    public static final RegistryObject<PlacedFeature> RED_BAMBOO_PODZOL_PLACED = PLACED_FEATURES.register(RED_BAMBOO_FEATURE_PODZOL_NAME, () -> new PlacedFeature(RED_BAMBOO_FEATURE_PODZOL.getHolder().orElseThrow(), ImmutableList.of(
            RarityFilter.onAverageOnceEvery(400),
            NoiseBasedCountPlacement.of(160, 35.0D, 0.5D),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
            BiomeFilter.biome()
    )));

}
