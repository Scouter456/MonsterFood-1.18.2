package com.scouter.monsterfood.datagen;

import com.scouter.monsterfood.MonsterFood;
import com.scouter.monsterfood.blocks.MFBlocks;
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
    }

    @Override
    public String getName() {
        return "Monster Food Languages: en_us";
    }
}
