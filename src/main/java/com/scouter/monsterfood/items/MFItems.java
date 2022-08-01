package com.scouter.monsterfood.items;

import com.scouter.monsterfood.MonsterFood;
import com.scouter.monsterfood.blocks.MFBlocks;
import com.scouter.monsterfood.entity.MFEntity;
import com.scouter.monsterfood.setup.Registration;
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


    //Monster parts
    public static final RegistryObject<Item> NIGHTMARE = fromBlock(MFBlocks.NIGHTMARE);
    public static final RegistryObject<Item> SPICE = fromBlock(MFBlocks.SPICE);
    public static final RegistryObject<Item> CUTTING_BOARD = fromBlock(MFBlocks.CUTTING_BOARD_BLOCK);
    public static final RegistryObject<Item> RED_BAMBOO = fromBlock(MFBlocks.RED_BAMBOO);
    public static final RegistryObject<Item> WALKING_MUSHROOM_FEET = ITEMS.register("walkingmushroom_feet", () -> new Item(Registration.defaultBuilder()));
    public static final RegistryObject<Item> WALKING_MUSHROOM_BODY = ITEMS.register("walkingmushroom_rump", () -> new Item(Registration.defaultBuilder()));
    public static final RegistryObject<Item> WALKING_MUSHROOM_SLIVER = ITEMS.register("walkingmushroom_sliver", () -> new Item(Registration.defaultBuilder()));

    //Ingredients
    public static final RegistryObject<Item> CUT_WALKING_MUSHROOM_FEET = ITEMS.register("cut_walkingmushroom_feet", () -> new Item(Registration.ingredientsBuilder()));
    public static final RegistryObject<Item> CUT_WALKING_MUSHROOM_BODY = ITEMS.register("cut_walkingmushroom_rump", () -> new Item(Registration.ingredientsBuilder()));
    public static final RegistryObject<Item> SALT = ITEMS.register("salt", () -> new Item(Registration.ingredientsBuilder()));
    public static final RegistryObject<Item> GARLIC = ITEMS.register("garlic", () -> new Item(Registration.ingredientsBuilder()));
    public static final RegistryObject<Item> GARLIC_CLOVES = ITEMS.register("garlic_cloves", () -> new Item(Registration.ingredientsBuilder()));


    public static final RegistryObject<Item> ONION = ITEMS.register("onion", () -> new Item(Registration.ingredientsBuilder()));
    public static final RegistryObject<Item> CUT_ONION = ITEMS.register("cut_onion", () -> new Item(Registration.ingredientsBuilder()));

    public static final RegistryObject<Item> BUTTER = ITEMS.register("butter", () -> new Item(Registration.ingredientsBuilder()));


    //Spices
    public static final RegistryObject<Item> WHITE_SPICE = ITEMS.register("white_spice", () -> new Item(Registration.ingredientsBuilder()));
    public static final RegistryObject<Item> RED_SPICE = ITEMS.register("red_spice", () -> new Item(Registration.ingredientsBuilder().fireResistant()));
    public static final RegistryObject<Item> BLACK_SPICE = ITEMS.register("black_spice", () -> new Item(Registration.ingredientsBuilder()));

    //Foods
    public static final RegistryObject<Item> RAW_WALKING_MUSHROOM_GARLIC_BUTTER_SKILLET = ITEMS.register("raw_walking_mushroom_garlic_butter_skillet", () -> new Item(Registration.foodsBuilder()));
    public static final RegistryObject<Item> COOKED_WALKING_MUSHROOM_GARLIC_BUTTER_SKILLET = fromBlockToFood(MFBlocks.COOKED_WALKING_MUSHROOM_GARLIC_BUTTER_SKILLET);


    //Cooking stuff
    public static final RegistryObject<Item> SKILLET = fromBlock(MFBlocks.SKILLET);


    //Spawn Eggs
    public static final RegistryObject<Item> WALKING_MUSHROOM_SPAWN_EGG = ITEMS.register("walking_mushroom_spawn_egg", ()-> new ForgeSpawnEggItem(MFEntity.WALKINGMUSHROOM,
            0xFFF6F6, 0xE01313, Registration.defaultBuilder()));
    public static final RegistryObject<Item> LAVA_SNAIL_SPAWN_EGG = ITEMS.register("lava_snail_spawn_egg", ()-> new ForgeSpawnEggItem(MFEntity.LAVASNAIL,
            0xFFF6F6, 0xE01313, Registration.defaultBuilder()));

    public static final RegistryObject<Item> JELLY_FISH_SPAWN_EGG = ITEMS.register("jelly_fish_spawn_egg", ()-> new ForgeSpawnEggItem(MFEntity.JELLY_FISH,
            0xFFF6F6, 0xE01313, Registration.defaultBuilder()));
    public static CreativeModeTab creativeTab = new CreativeModeTab(MonsterFood.MODID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(NIGHTMARE.get());
        }
    };

    public static CreativeModeTab creativeTabIngredients = new CreativeModeTab(MonsterFood.MODID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(CUT_WALKING_MUSHROOM_FEET.get());
        }
    };

    public static CreativeModeTab creativeTabFoods = new CreativeModeTab(MonsterFood.MODID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(COOKED_WALKING_MUSHROOM_GARLIC_BUTTER_SKILLET.get());
        }
    };

    public static <B extends Block> RegistryObject<Item> fromBlock(RegistryObject<B> block){
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), Registration.defaultBuilder()));
    }

    public static <B extends Block> RegistryObject<Item> fromBlockToFood(RegistryObject<B> block){
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), Registration.foodsBuilder().stacksTo(1)));
    }
}
