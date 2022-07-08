package com.scouter.monsterfood.datagen;

import com.scouter.monsterfood.misc.MFSounds;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

import static com.scouter.monsterfood.MonsterFood.MODID;
import static com.scouter.monsterfood.MonsterFood.prefix;

public class SoundsGenerator extends SoundDefinitionsProvider {
    /**
     * Creates a new instance of this data provider.
     *
     * @param generator The data generator instance provided by the event you are initializing this provider in.
     * @param helper    The existing file helper provided by the event you are initializing this provider in.
     */
    protected SoundsGenerator(DataGenerator generator, ExistingFileHelper helper) {
        super(generator, MODID, helper);
    }

    @Override
    public void registerSounds() {
        this.add(MFSounds.MUSHROOM_WALK.get(), SoundDefinition.definition().subtitle("walking_mushroom.walk")
                .with(SoundDefinitionsProvider.sound(prefix( "mushroom_walk")).volume(0.5d)));

    }

    @Override
    public String getName()
    {
        return "Monsterfood Sound Generator";
    }
}
