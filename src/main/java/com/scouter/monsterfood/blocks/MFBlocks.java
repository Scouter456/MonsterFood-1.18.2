package com.scouter.monsterfood.blocks;

import com.mojang.logging.LogUtils;
import com.scouter.monsterfood.MonsterFood;
import com.scouter.monsterfood.setup.Registration;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber(modid = MonsterFood.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MFBlocks {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MonsterFood.MODID);
    public static final RegistryObject<Block> ORETEST = BLOCKS.register("oretest", () -> new Block(BlockBehaviour.Properties.of(Material.BAMBOO_SAPLING).strength(3.0F, 6.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> NIGHTMARE = BLOCKS.register("nightmare", () -> new NightmareBlock(BlockBehaviour.Properties.of(Material.WATER_PLANT, MaterialColor.COLOR_BROWN).sound(SoundType.CALCITE).randomTicks().noOcclusion()));
    public static final RegistryObject<Block> GOLD_TRAPDOOR = BLOCKS.register("gold_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.GOLD).sound(SoundType.METAL).randomTicks().noOcclusion()));
    public static final RegistryObject<Block> SPICE = BLOCKS.register("spice", () -> new LavaSnailSpice(BlockBehaviour.Properties.of(Material.CLAY, MaterialColor.COLOR_RED).sound(SoundType.MOSS).randomTicks().noOcclusion().noCollission().instabreak()));

    public static final RegistryObject<Block> RED_BAMBOO_SAPLING = BLOCKS.register("red_bamboo_sapling", () -> new RedBambooSaplingBlock(BlockBehaviour.Properties.of(Material.BAMBOO_SAPLING).randomTicks().instabreak().noCollission().strength(1.0F).sound(SoundType.BAMBOO_SAPLING)));
    public static final RegistryObject<Block> RED_BAMBOO = BLOCKS.register("red_bamboo", () -> new RedBambooBlock(BlockBehaviour.Properties.of(Material.BAMBOO, MaterialColor.PLANT).randomTicks().instabreak().strength(1.0F).sound(SoundType.BAMBOO).noOcclusion().dynamicShape()));

    //TODO custom cutting board block
    public static final RegistryObject<Block> CUTTING_BOARD_BLOCK = BLOCKS.register("cutting_board", () -> new CuttingBoardBlock());

    //TODO REMOVE RAW FOOD
    public static final RegistryObject<Block> SKILLET = BLOCKS.register("skillet", () -> new SkilletBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).randomTicks().noOcclusion()));
    public static final RegistryObject<Block> COOKED_WALKING_MUSHROOM_GARLIC_BUTTER_SKILLET = BLOCKS.register("cooked_walking_mushroom_garlic_butter_skillet", () -> new MushroomGarlicButterSkillet(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).randomTicks().noOcclusion()));

    public static final DeferredRegister<MobEffect> MOB_EFFECT = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MonsterFood.MODID);


    @SubscribeEvent
    public static void registerItemblocks(RegistryEvent.Register<Item> evt) {
        IForgeRegistry<Item> r = evt.getRegistry();
        List<RegistryObject<? extends Block>> standard = Arrays.asList(MFBlocks.ORETEST);

        for (RegistryObject<? extends Block> b : standard) {
            LOGGER.info("Registering block item: " + b);
            r.register(new BlockItem(b.get(), Registration.defaultBuilder()).setRegistryName(b.get().getRegistryName()));
        }
    }
}
