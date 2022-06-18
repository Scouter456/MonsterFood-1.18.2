package com.scouter.monsterfood.world.feature;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.scouter.monsterfood.MonsterFood;
import com.scouter.monsterfood.blocks.MFBlocks;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockPileConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.CountConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.concurrent.Immutable;
import java.util.List;
import java.util.Random;

public class MFConfiguredFeatures {
    public static final DeferredRegister<ConfiguredFeature<?,?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, MonsterFood.MODID);
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, MonsterFood.MODID);
    private static final String NIGHTMARE_COLONY_NAME = "nightmare_colony";
    private static Random rand = new Random();
    public static final RegistryObject<ConfiguredFeature<?,?>> CONFIGURED_NIGHTMARE_COLONY = CONFIGURED_FEATURES.register(NIGHTMARE_COLONY_NAME, () -> new ConfiguredFeature<>(MFFeatures.NIGHTMARE_COLONY.get(), new CountConfiguration(rand.nextInt(6)+1)));

    public static final RegistryObject<PlacedFeature> NIGHTMARE_COLONY = PLACED_FEATURES.register("nightmare_colony", () -> new PlacedFeature(CONFIGURED_NIGHTMARE_COLONY.getHolder().orElseThrow(), ImmutableList.of(
            RarityFilter.onAverageOnceEvery(70),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP_TOP_SOLID,
            BiomeFilter.biome()
    )));

}
