package com.scouter.monsterfood.datagen;

import com.scouter.monsterfood.MonsterFood;
import com.scouter.monsterfood.blocks.MFBlockStateProperties;
import com.scouter.monsterfood.blocks.MFBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.models.blockstates.*;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static com.scouter.monsterfood.MonsterFood.prefix;

public class BlockModelGenerator extends BlockModelProvider {
    private final BlockModelProvider blockModels;
    final Consumer<BlockStateGenerator> blockStateOutput;
    public BlockModelGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper, Consumer<BlockStateGenerator> blockStateOutput) {
        super(generator, MonsterFood.MODID, existingFileHelper);
        this.blockStateOutput = blockStateOutput;
        this.blockModels = new BlockModelProvider(generator,  MonsterFood.MODID, existingFileHelper) {
            @Override protected void registerModels() {}
        };
    }
    private static Variant[] createRotatedVariants(ResourceLocation pModelLocation) {
        return new Variant[]{Variant.variant().with(VariantProperties.MODEL, pModelLocation), Variant.variant().with(VariantProperties.MODEL, pModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90), Variant.variant().with(VariantProperties.MODEL, pModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180), Variant.variant().with(VariantProperties.MODEL, pModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)};
    }

    private void createNightmares() {
        String baseName = MFBlocks.NIGHTMARE.getId().getPath() + "s";
        ModelFile nightmares = models().getExistingFile(prefix("nightmares"));
        ModelFile two_nightmares = models().getExistingFile(prefix("two_" + baseName));
        ModelFile three_nightmares = models().getExistingFile(prefix("three_" + baseName));
        ModelFile four_nightmares = models().getExistingFile(prefix("four_" + baseName));
        /*
        ModelFile nightmares = models().getExistingFile(prefix("nightmares"));
        List<ConfiguredModel> activeModels = new ArrayList<>();
        for(ModelFile modelFile : Arrays.asList(nightmares)){
            activeModels.add(new ConfiguredModel(modelFile,0,0,false));
            activeModels.add(new ConfiguredModel(modelFile,0,90,false));
            activeModels.add(new ConfiguredModel(modelFile,0,180,false));
            activeModels.add(new ConfiguredModel(modelFile,0,270,false));
        }

        getVariantBuilder(MFBlocks.NIGHTMARE.get()).forAllStates(s -> {
            return activeModels.toArray(new ConfiguredModel[0]);
*/
        }

    public BlockModelProvider models() {
        return blockModels;
    }
    @Override
    protected void registerModels() {
      this.createNightmares();
    }

    @Override
    public String getName() {
        return "Block Models: " + modid;
    }
}
