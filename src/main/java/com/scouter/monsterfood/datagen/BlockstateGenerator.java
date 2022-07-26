package com.scouter.monsterfood.datagen;

import com.mojang.logging.LogUtils;
import com.scouter.monsterfood.MonsterFood;
import com.scouter.monsterfood.blocks.MFBlockStateProperties;
import com.scouter.monsterfood.blocks.MFBlocks;
import com.scouter.monsterfood.blocks.SkilletBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TrapDoorBlock;
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
    private static final int DEFAULT_ANGLE_OFFSET = 180;

    public BlockstateGenerator(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, MonsterFood.MODID, exFileHelper);
    }

    private String blockName(Block block) {
        return block.getRegistryName().getPath();
    }

    public ResourceLocation resourceBlock(String path) {
        return new ResourceLocation(MonsterFood.MODID, "block/" + path);
    }

    public ModelFile existingModel(Block block) {
        return new ModelFile.ExistingModelFile(resourceBlock(blockName(block)), models().existingFileHelper);
    }

    public ModelFile existingModel(String path) {
        return new ModelFile.ExistingModelFile(resourceBlock(path), models().existingFileHelper);
    }
    @Override
    protected void registerStatesAndModels(){
        nightmaresBuilder();
        spiceBuilder();
        skilletBuilder();
        trapdoorBlock((TrapDoorBlock) MFBlocks.GOLD_TRAPDOOR.get(), prefix("block/gold_trapdoor"), true);
        skilletFoodBlock(MFBlocks.COOKED_WALKING_MUSHROOM_GARLIC_BUTTER_SKILLET.get());
        //horizontalBlock(MFBlocks.NIGHTMARE.get(), models().orientableVertical(MFBlocks.NIGHTMARE.getId().getPath(), prefix("block/nightmare_side"), prefix("block/nightmare_fron")));
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
    private void skilletFoodBlock(Block block){
        getVariantBuilder(block).forAllStates(s ->{
            int foods = s.getValue(MFBlockStateProperties.FOOD);

            String suffix = "_food_" + (foods);

            return ConfiguredModel.builder()
                    .modelFile(existingModel(blockName(block) + suffix))
                    .rotationY(((int) s.getValue(SkilletBlock.FACING).toYRot() + DEFAULT_ANGLE_OFFSET) % 360)
                    .build();
        });
    }
    private void skilletBuilder(){
        ModelFile skillet = models().getExistingFile(prefix("skillet"));

        List<ConfiguredModel> activeModels = new ArrayList<>();
        for(ModelFile modelFile : Arrays.asList(skillet)){
            activeModels.add(new ConfiguredModel(modelFile,0,0,false));
            activeModels.add(new ConfiguredModel(modelFile,0,90,false));
            activeModels.add(new ConfiguredModel(modelFile,0,180,false));
            activeModels.add(new ConfiguredModel(modelFile,0,270,false));
        }
        getVariantBuilder(MFBlocks.SKILLET.get()).forAllStates(s -> {
            switch(s.getValue(BlockStateProperties.HORIZONTAL_FACING)) {
                case NORTH:
                    return Arrays.copyOfRange(activeModels.toArray(new ConfiguredModel[0]), 0,1) ;
                case EAST:
                    return Arrays.copyOfRange(activeModels.toArray(new ConfiguredModel[0]), 1,2) ;
                case SOUTH:
                    return Arrays.copyOfRange(activeModels.toArray(new ConfiguredModel[0]), 2,3) ;
                case WEST:
                    return Arrays.copyOfRange(activeModels.toArray(new ConfiguredModel[0]), 3,4) ;
                default:
                    return ConfiguredModel.builder().modelFile(skillet).build();
            }
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
