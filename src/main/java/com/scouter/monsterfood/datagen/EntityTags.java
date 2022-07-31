package com.scouter.monsterfood.datagen;

import com.scouter.monsterfood.MonsterFood;
import com.scouter.monsterfood.entity.MFEntity;
import com.scouter.monsterfood.utils.MFTags;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraftforge.common.data.ExistingFileHelper;

import static com.scouter.monsterfood.MonsterFood.prefix;

public class EntityTags extends EntityTypeTagsProvider {


    public EntityTags(DataGenerator pGenerator, ExistingFileHelper existingFileHelper) {
        super(pGenerator, MonsterFood.MODID, existingFileHelper);
    }

    protected void addTags() {
        tag(MFTags.EntityTypes.SNAIL).add(MFEntity.LAVASNAIL.get());
    }

    @Override
    public String getName() {
        return "Entity type tags provider";
    }
}
