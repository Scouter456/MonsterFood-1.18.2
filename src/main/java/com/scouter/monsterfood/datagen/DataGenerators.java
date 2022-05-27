package com.scouter.monsterfood.datagen;

import com.google.common.collect.Sets;
import com.scouter.monsterfood.MonsterFood;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.models.blockstates.BlockStateGenerator;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

import java.util.Set;
import java.util.function.Consumer;

@Mod.EventBusSubscriber(modid = MonsterFood.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)

public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent evt){
        if(evt.includeServer())
            registerServerProviders(evt.getGenerator(), evt);

        if(evt.includeClient())
            registerClientProviders(evt.getGenerator(), evt);


    }

    private static void registerClientProviders(DataGenerator generator, GatherDataEvent evt) {
        ExistingFileHelper helper = evt.getExistingFileHelper();
        //    generator.addProvider(new LootGenerator(generator));
        //generator.addProvider(new LootGenerator(generator));

    }

    private static void registerServerProviders(DataGenerator generator, GatherDataEvent evt) {
        ExistingFileHelper helper = evt.getExistingFileHelper();
        Set<BlockStateGenerator> set = Sets.newHashSet();
        Consumer<BlockStateGenerator> consumer1 = set::add;
        generator.addProvider(new BlockstateGenerator(generator, helper));
        generator.addProvider(new BlockModelGenerator(generator, helper, consumer1));
        //generator.addProvider(new ItemModelGenerator(generator, helper));
        generator.addProvider(new LanguageGenerator(generator));
        //generator.addProvider(new AdvancementGenerator(generator, helper));
    }
}
