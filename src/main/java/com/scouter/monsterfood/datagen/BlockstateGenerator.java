package com.scouter.monsterfood.datagen;

import com.mojang.logging.LogUtils;
import com.scouter.monsterfood.MonsterFood;
import com.scouter.monsterfood.blocks.MFBlockStateProperties;
import com.scouter.monsterfood.blocks.MFBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.scouter.monsterfood.MonsterFood.prefix;

public class BlockstateGenerator extends BlockStateProvider {
    private static final Logger LOGGER = LogUtils.getLogger();

    public BlockstateGenerator(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, MonsterFood.MODID, exFileHelper);
    }
    @Override
    protected void registerStatesAndModels(){
        nightmaresBuilder();
        spiceBuilder();

        trapdoorBlock((TrapDoorBlock) MFBlocks.GOLD_TRAPDOOR.get(), prefix("block/gold_trapdoor"), true);

    }

    private void spiceBuilder(){
        ModelFile red_spice = models().getExistingFile(prefix("red_spice"));
        ModelFile white_spice = models().getExistingFile(prefix("white_spice"));
        ModelFile black_spice = models().getExistingFile(prefix("black_spice"));

        getVariantBuilder(MFBlocks.SPICE.get()).forAllStates(s -> {
            switch(s.getValue(MFBlockStateProperties.SPICE_TIER)) {
                case 1:
                case 4:
                    return ConfiguredModel.builder().modelFile(black_spice).build();
                case 2:
                    return ConfiguredModel.builder().modelFile(red_spice).build();
                case 3:
                    return ConfiguredModel.builder().modelFile(white_spice).build();
            }
            return ConfiguredModel.builder().modelFile(red_spice).build();
        });

    }

    private void nightmaresBuilder(){
        String baseName = MFBlocks.NIGHTMARE.getId().getPath() + "s";
        ModelFile nightmares = models().getExistingFile(prefix("nightmares"));
        ModelFile two_nightmares = models().getExistingFile(prefix("two_" + baseName));
        ModelFile three_nightmares = models().getExistingFile(prefix("three_" + baseName));
        ModelFile four_nightmares = models().getExistingFile(prefix("four_" + baseName));

        List<ConfiguredModel> activeModels = new ArrayList<>();
        for(ModelFile modelFile : Arrays.asList(nightmares, two_nightmares, three_nightmares, four_nightmares)){
            activeModels.add(new ConfiguredModel(modelFile,0,0,false));
            activeModels.add(new ConfiguredModel(modelFile,0,90,false));
            activeModels.add(new ConfiguredModel(modelFile,0,180,false));
            activeModels.add(new ConfiguredModel(modelFile,0,270,false));
        }
        getVariantBuilder(MFBlocks.NIGHTMARE.get()).forAllStates(s -> {
            switch(s.getValue(MFBlockStateProperties.NIGHTMARES)) {
                case 1:
                    return Arrays.copyOfRange(activeModels.toArray(new ConfiguredModel[0]), 0,4) ;
                case 2:
                    return Arrays.copyOfRange(activeModels.toArray(new ConfiguredModel[0]), 4,8) ;
                case 3:
                    return Arrays.copyOfRange(activeModels.toArray(new ConfiguredModel[0]), 8,12) ;
                case 4:
                    return Arrays.copyOfRange(activeModels.toArray(new ConfiguredModel[0]), 12,16) ;
                default:
                case 0:
                    return ConfiguredModel.builder().modelFile(nightmares).build();
            }
        });
    }

    private void spiceLayers(){

    }

    @Override
    public String getName() {
        return "Block States: " + MonsterFood.MODID;
    }
}
