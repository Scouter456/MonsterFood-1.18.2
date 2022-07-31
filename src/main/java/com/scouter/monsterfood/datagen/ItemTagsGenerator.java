package com.scouter.monsterfood.datagen;

import com.scouter.monsterfood.MonsterFood;
import com.scouter.monsterfood.items.MFItems;
import com.scouter.monsterfood.utils.MFTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class ItemTagsGenerator extends ItemTagsProvider {
    public ItemTagsGenerator(DataGenerator generatorIn, BlockTagsProvider blockTagProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, blockTagProvider, MonsterFood.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {

        this.registerModTags();
    }

    private void registerModTags() {
        tag(MFTags.Items.GARLIC)
                .add(MFItems.GARLIC.get());

        tag(MFTags.Items.ONION)
                .add(MFItems.ONION.get());

        tag(MFTags.Items.BUTTER)
                .add(MFItems.BUTTER.get());

        tag(MFTags.Items.CUT_GARLIC)
                .add(MFItems.GARLIC_CLOVES.get());


        tag(MFTags.Items.CUT_ONION)
                .add(MFItems.CUT_ONION.get());


        tag(MFTags.Items.SPICE)
                .add(MFItems.WHITE_SPICE.get())
                .add(MFItems.BLACK_SPICE.get())
                .add(MFItems.RED_SPICE.get());

        tag(MFTags.Items.KNIVES)
                .add(MFItems.WOOD_KNIFE.get())
                .add(MFItems.STONE_KNIFE.get())
                .add(MFItems.IRON_KNIFE.get())
                .add(MFItems.GOLDEN_KNIFE.get())
                .add(MFItems.DIAMOND_KNIFE.get())
                .add(MFItems.NETHERITE_KNIFE.get())
                .add(MFItems.ADAMANTITE_KNIFE.get())
                .add(MFItems.MITHRIL_KNIFE.get());

    }

    private void registerForgeTags(){


    }

    @Override
    public String getName() { return "Monster Food Item Tags";}
}
