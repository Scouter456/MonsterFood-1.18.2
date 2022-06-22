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
        addBlock(MFBlocks.NIGHTMARE, "Nightmare");

        addItem(MFItems.bubble_lilly, "Bubble Lilly");
        addItem(MFItems.WALKING_MUSHROOM_SPAWN_EGG, "Walking Mushroom Spawn Egg");
        addItem(MFItems.WOOD_KNIFE, "Wooden knife");
        addItem(MFItems.STONE_KNIFE, "Stone knife");
        addItem(MFItems.IRON_KNIFE, "Iron knife");
        addItem(MFItems.GOLDEN_KNIFE, "Golden knife");
        addItem(MFItems.DIAMOND_KNIFE, "Diamond knife");
        addItem(MFItems.NETHERITE_KNIFE, "Netherite knife");
        addItem(MFItems.ADAMANTITE_KNIFE, "Adamantite knife");
        addItem(MFItems.MITHRIL_KNIFE, "Mithril knife");

        addEntityType(MFEntity.WALKINGMUSHROOM, "Walking Mushroom");
    }

    @Override
    public String getName() {
        return "Monster Food Languages: en_us";
    }
}
