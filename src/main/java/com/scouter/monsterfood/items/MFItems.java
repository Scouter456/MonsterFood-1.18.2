package com.scouter.monsterfood.items;

import com.scouter.monsterfood.MonsterFood;
import com.scouter.monsterfood.blocks.MFBlocks;
import com.scouter.monsterfood.entity.MFEntity;
import com.scouter.monsterfood.setup.Registration;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;



//import static com.scouter.monsterfood.setup.Registration.fromBlock;

public class MFItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MonsterFood.MODID);
    public static final RegistryObject<Item> bubble_lilly = ITEMS.register("bubble_lilly", () -> new Item(Registration.defaultBuilder()));

    //Knives
    public static final RegistryObject<Item> WOOD_KNIFE = ITEMS.register("wood_knife", () -> new KnifeItem(Tiers.WOOD ,Registration.defaultBuilder().defaultDurability(Tiers.WOOD.getUses()).stacksTo(1)));
    public static final RegistryObject<Item> STONE_KNIFE = ITEMS.register("stone_knife", () -> new KnifeItem(Tiers.STONE ,Registration.defaultBuilder().defaultDurability(Tiers.STONE.getUses()).stacksTo(1)));
    public static final RegistryObject<Item> IRON_KNIFE = ITEMS.register("iron_knife", () -> new KnifeItem(Tiers.IRON ,Registration.defaultBuilder().defaultDurability(Tiers.IRON.getUses()).stacksTo(1)));
    public static final RegistryObject<Item> GOLDEN_KNIFE = ITEMS.register("golden_knife", () -> new KnifeItem(Tiers.GOLD ,Registration.defaultBuilder().defaultDurability(Tiers.GOLD.getUses()).stacksTo(1)));
    public static final RegistryObject<Item> DIAMOND_KNIFE = ITEMS.register("diamond_knife", () -> new KnifeItem(Tiers.DIAMOND ,Registration.defaultBuilder().defaultDurability(Tiers.DIAMOND.getUses()).stacksTo(1)));
    public static final RegistryObject<Item> NETHERITE_KNIFE = ITEMS.register("netherite_knife", () -> new KnifeItem(Tiers.NETHERITE ,Registration.defaultBuilder().fireResistant().defaultDurability(Tiers.NETHERITE.getUses()).stacksTo(1)));
    public static final RegistryObject<Item> ADAMANTITE_KNIFE = ITEMS.register("adamantite_knife", () -> new KnifeItem(/**Temporary**/Tiers.NETHERITE ,Registration.defaultBuilder().fireResistant().defaultDurability(/*TODO add adamantite tier*/4000).stacksTo(1)));
    public static final RegistryObject<Item> MITHRIL_KNIFE = ITEMS.register("mithril_knife", () -> new KnifeItem(/**Temporary**/Tiers.NETHERITE ,Registration.defaultBuilder().fireResistant().defaultDurability(/*TODO add mithril tier*/5000).stacksTo(1)));


    //Foods
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
