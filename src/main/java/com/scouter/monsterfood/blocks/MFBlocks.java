package com.scouter.monsterfood.blocks;

import com.mojang.logging.LogUtils;
import com.scouter.monsterfood.MonsterFood;
import com.scouter.monsterfood.items.MFItems;
import com.scouter.monsterfood.setup.Registration;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
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
    public static final RegistryObject<Block> ORETEST = BLOCKS.register("oretest", () -> new Block(BlockBehaviour.Properties.of(Material.BAMBOO_SAPLING).strength(3.0F, 6.0F)));
    public static final RegistryObject<Block> NIGHTMARE = BLOCKS.register("nightmare", ()-> new NightmareBlock(BlockBehaviour.Properties.of(Material.WATER_PLANT, MaterialColor.COLOR_BROWN).sound(SoundType.CALCITE).randomTicks().noOcclusion()));






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
