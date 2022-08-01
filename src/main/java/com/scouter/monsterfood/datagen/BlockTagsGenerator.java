package com.scouter.monsterfood.datagen;

import com.scouter.monsterfood.MonsterFood;
import com.scouter.monsterfood.blocks.MFBlocks;
import com.scouter.monsterfood.setup.Registration;
import com.scouter.monsterfood.utils.MFTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class BlockTagsGenerator extends BlockTagsProvider {
    public BlockTagsGenerator(DataGenerator generator, ExistingFileHelper helper) {
        super(generator, MonsterFood.MODID, helper);
    }

    @Override
    protected void addTags(){
        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(MFBlocks.RED_BAMBOO.get());

        tag(MFTags.Blocks.RED_BAMBOO_PLANTABLE_ON)
                .addTags(BlockTags.DIRT)
                .addTag(BlockTags.SAND)
                .add(Blocks.GRAVEL)
                .add(MFBlocks.RED_BAMBOO_SAPLING.get())
                .add(MFBlocks.RED_BAMBOO.get());

        //tag(BlockTags.MINEABLE_WITH_PICKAXE)
        //        .add(MFBlocks.ORETEST.get());

        //Add a tag for minimum requirement of tool for block
        //tag(BlockTags.NEEDS_IRON_TOOL)
        //        .add(MFBlocks.ORETEST.get());
    }


    @Override
    public String getName() { return "Monster Food Tags";}
}
