package com.scouter.monsterfood.datagen;

import com.mojang.logging.LogUtils;
import com.scouter.monsterfood.MonsterFood;
import com.scouter.monsterfood.blocks.MFBlockStateProperties;
import com.scouter.monsterfood.blocks.MFBlocks;
import net.minecraft.client.model.Model;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
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

    @Override
    public String getName() {
        return "Block States: " + MonsterFood.MODID;
    }
}
