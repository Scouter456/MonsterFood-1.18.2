package com.scouter.monsterfood.setup;

import com.mojang.logging.LogUtils;
import com.scouter.monsterfood.blocks.MFBlockEntity;
import com.scouter.monsterfood.blocks.MFBlocks;
import com.scouter.monsterfood.entity.MFEntity;
import com.scouter.monsterfood.entity.MFEntityPlacement;
import com.scouter.monsterfood.items.MFItems;
import com.scouter.monsterfood.misc.MFSounds;
import com.scouter.monsterfood.recipes.MFRecipes;
import com.scouter.monsterfood.world.feature.MFConfiguredFeatures;
import com.scouter.monsterfood.world.feature.MFFeatures;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.List;

import static com.scouter.monsterfood.items.MFItems.*;

public class Registration {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static void init(){

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        MFBlocks.BLOCKS.register(bus);
        MFBlockEntity.BLOCK_ENTITY.register(bus);
        MFRecipes.SERIALIZERS.register(bus);
        MFItems.ITEMS.register(bus);
        MFEntity.ENTITY_TYPES.register(bus);
        MFConfiguredFeatures.CONFIGURED_FEATURES.register(bus);
        MFConfiguredFeatures.PLACED_FEATURES.register(bus);
        MFFeatures.FEATURES.register(bus);
        MFSounds.SOUNDS.register(bus);

    }

    public static final Item.Properties defaultBuilder() {
        return new Item.Properties().tab(creativeTab);
    }
    public static final Item.Properties ingredientsBuilder() {
        return new Item.Properties().tab(creativeTabIngredients);
    }

    public static final Item.Properties foodsBuilder() {
        return new Item.Properties().tab(creativeTabFoods);
    }

}
