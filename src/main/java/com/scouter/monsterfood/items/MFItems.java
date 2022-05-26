package com.scouter.monsterfood.items;

import com.scouter.monsterfood.MonsterFood;
import com.scouter.monsterfood.blocks.MFBlocks;
import com.scouter.monsterfood.setup.Registration;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

//import static com.scouter.monsterfood.setup.Registration.fromBlock;

public class MFItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MonsterFood.MODID);
    public static final RegistryObject<Item> bubble_lilly = ITEMS.register("bubble_lilly", () -> new Item(Registration.defaultBuilder()));

    public static CreativeModeTab creativeTab = new CreativeModeTab(MonsterFood.MODID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(bubble_lilly.get());
        }
    };
}
