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
    public static final RegistryObject<Item> WOOD_KNIFE = ITEMS.register("wood_knife", () -> new KnifeItem(Tiers.WOOD ,Registration.defaultBuilder().defaultDurability(Tiers.WOOD.getUses())));
    public static final RegistryObject<Item> STONE_KNIFE = ITEMS.register("stone_knife", () -> new KnifeItem(Tiers.STONE ,Registration.defaultBuilder().defaultDurability(Tiers.STONE.getUses())));
    public static final RegistryObject<Item> IRON_KNIFE = ITEMS.register("iron_knife", () -> new KnifeItem(Tiers.IRON ,Registration.defaultBuilder().defaultDurability(Tiers.IRON.getUses())));
    public static final RegistryObject<Item> GOLDEN_KNIFE = ITEMS.register("golden_knife", () -> new KnifeItem(Tiers.GOLD ,Registration.defaultBuilder().defaultDurability(Tiers.GOLD.getUses())));
    public static final RegistryObject<Item> DIAMOND_KNIFE = ITEMS.register("diamond_knife", () -> new KnifeItem(Tiers.DIAMOND ,Registration.defaultBuilder().defaultDurability(Tiers.DIAMOND.getUses())));
    public static final RegistryObject<Item> NETHERITE_KNIFE = ITEMS.register("netherite_knife", () -> new KnifeItem(Tiers.NETHERITE ,Registration.defaultBuilder().fireResistant().defaultDurability(Tiers.NETHERITE.getUses())));
    public static final RegistryObject<Item> ADAMANTITE_KNIFE = ITEMS.register("adamantite_knife", () -> new KnifeItem(/**Temporary**/Tiers.NETHERITE ,Registration.defaultBuilder().fireResistant().defaultDurability(/*TODO add adamantite tier*/4000)));
    public static final RegistryObject<Item> MITHRIL_KNIFE = ITEMS.register("mithril_knife", () -> new KnifeItem(/**Temporary**/Tiers.NETHERITE ,Registration.defaultBuilder().fireResistant().defaultDurability(/*TODO add mithril tier*/5000)));


    //Ingredients
    public static final RegistryObject<Item> NIGHTMARE = fromBlock(MFBlocks.NIGHTMARE);
    public static final RegistryObject<Item> WALKING_MUSHROOM_FEET = ITEMS.register("walkingmushroom_feet", () -> new Item(Registration.defaultBuilder()));
    public static final RegistryObject<Item> WALKING_MUSHROOM_BODY = ITEMS.register("walkingmushroom_rump", () -> new Item(Registration.defaultBuilder()));
    public static final RegistryObject<Item> WALKING_MUSHROOM_SLIVER = ITEMS.register("walkingmushroom_sliver", () -> new Item(Registration.defaultBuilder()));

    //Spices

    public static final RegistryObject<Item> WHITE_SPICE = ITEMS.register("white_spice", () -> new Item(Registration.defaultBuilder()));
    public static final RegistryObject<Item> RED_SPICE = ITEMS.register("red_spice", () -> new Item(Registration.defaultBuilder()));
    public static final RegistryObject<Item> BLACK_SPICE = ITEMS.register("black_spice", () -> new Item(Registration.defaultBuilder()));

    //Foods

    //Spawn Eggs
    public static final RegistryObject<Item> WALKING_MUSHROOM_SPAWN_EGG = ITEMS.register("walking_mushroom_spawn_egg", ()-> new ForgeSpawnEggItem(MFEntity.WALKINGMUSHROOM,
            0xFFF6F6, 0xE01313, Registration.defaultBuilder()));

    public static final RegistryObject<Item> LAVA_SNAIL_SPAWN_EGG = ITEMS.register("lava_snail_spawn_egg", ()-> new ForgeSpawnEggItem(MFEntity.LAVASNAIL,
            0xFFF6F6, 0xE01313, Registration.defaultBuilder()));
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
