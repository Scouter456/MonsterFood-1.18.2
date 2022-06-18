package com.scouter.monsterfood.items;

import com.scouter.monsterfood.MonsterFood;
import com.scouter.monsterfood.blocks.MFBlocks;
import com.scouter.monsterfood.entity.MFEntity;
import com.scouter.monsterfood.setup.Registration;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;



//import static com.scouter.monsterfood.setup.Registration.fromBlock;

public class MFItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MonsterFood.MODID);
    public static final RegistryObject<Item> bubble_lilly = ITEMS.register("bubble_lilly", () -> new Item(Registration.defaultBuilder()));
    public static final RegistryObject<Item> NIGHTMARE = fromBlock(MFBlocks.NIGHTMARE);
    public static final RegistryObject<Item> WALKING_MUSHROOM_SPAWN_EGG = ITEMS.register("walking_mushroom_spawn_egg", ()-> new ForgeSpawnEggItem(MFEntity.WALKINGMUSHROOM,
            0x84653b, 0xffecca, Registration.defaultBuilder()));
    public static CreativeModeTab creativeTab = new CreativeModeTab(MonsterFood.MODID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(NIGHTMARE.get());
        }
    };

    public static <B extends Block> RegistryObject<Item> fromBlock(RegistryObject<B> block){
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), Registration.defaultBuilder()));
    }
}
