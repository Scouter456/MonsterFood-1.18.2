package com.scouter.monsterfood.datagen;

import com.scouter.monsterfood.MonsterFood;
import com.scouter.monsterfood.blocks.MFBlocks;
import com.scouter.monsterfood.entity.MFEntity;
import com.scouter.monsterfood.items.MFItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class LanguageGenerator extends LanguageProvider {
    public LanguageGenerator(DataGenerator gen){
        super(gen, MonsterFood.MODID, "en_us");
    }

    @Override
    protected void addTranslations(){
        addBlock(MFBlocks.ORETEST, "Oretest");


        addItem(MFItems.bubble_lilly, "Bubble Lilly");
        //KNIVES
        addItem(MFItems.WOOD_KNIFE, "Wooden knife");
        addItem(MFItems.STONE_KNIFE, "Stone knife");
        addItem(MFItems.IRON_KNIFE, "Iron knife");
        addItem(MFItems.GOLDEN_KNIFE, "Golden knife");
        addItem(MFItems.DIAMOND_KNIFE, "Diamond knife");
        addItem(MFItems.NETHERITE_KNIFE, "Netherite knife");
        addItem(MFItems.ADAMANTITE_KNIFE, "Adamantite knife");
        addItem(MFItems.MITHRIL_KNIFE, "Mithril knife");

        //FOODS
        addItem(MFItems.WALKING_MUSHROOM_FEET, "Walking Mushroom Feet");
        addItem(MFItems.CUT_WALKING_MUSHROOM_FEET, "Cut Walking Mushroom Feet");
        addItem(MFItems.WALKING_MUSHROOM_BODY, "Walking Mushroom Rump");
        addItem(MFItems.CUT_WALKING_MUSHROOM_BODY, "Cut Walking Mushroom Rump");
        addItem(MFItems.WALKING_MUSHROOM_SLIVER, "Walking Mushroom Sliver");
        addItem(MFItems.WHITE_SPICE, "White Spice");
        addItem(MFItems.BLACK_SPICE, "Black Spice");
        addItem(MFItems.RED_SPICE, "Red Spice");
        addItem(MFItems.SALT, "Salt");
        addItem(MFItems.BUTTER, "Butter");
        addItem(MFItems.ONION, "Onion");
        addItem(MFItems.CUT_ONION, "Cut Onion");
        addItem(MFItems.GARLIC, "Garlic");
        addItem(MFItems.GARLIC_CLOVES, "Garlic Cloves");
        addItem(MFItems.SKILLET, "Skillet");
        addItem(MFItems.RAW_WALKING_MUSHROOM_GARLIC_BUTTER_SKILLET, "Raw Walking Mushroom Garlic Butter Skillet");
        addItem(MFItems.COOKED_WALKING_MUSHROOM_GARLIC_BUTTER_SKILLET, "Cooked Walking Mushroom Garlic Butter Skillet");

        addBlock(MFBlocks.NIGHTMARE, "Nightmare");
        addBlock(MFBlocks.SPICE, "Spice Block");

        //EGGS
        addItem(MFItems.WALKING_MUSHROOM_SPAWN_EGG, "Walking Mushroom Spawn Egg");
        addItem(MFItems.LAVA_SNAIL_SPAWN_EGG, "Lava Snail Spawn Egg");

        //ENTITIES
        addEntityType(MFEntity.WALKINGMUSHROOM, "Walking Mushroom");
        addEntityType(MFEntity.LAVASNAIL, "Lava Snail");
    }

    @Override
    public String getName() {
        return "Monster Food Languages: en_us";
    }
}
