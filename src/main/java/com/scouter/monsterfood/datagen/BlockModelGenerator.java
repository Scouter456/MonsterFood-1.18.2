package com.scouter.monsterfood.datagen;

import com.scouter.monsterfood.MonsterFood;
import com.scouter.monsterfood.blocks.MFBlockStateProperties;
import com.scouter.monsterfood.blocks.MFBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.models.blockstates.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Arrays;
import java.util.function.Consumer;

import static com.scouter.monsterfood.MonsterFood.prefix;

public class BlockModelGenerator extends BlockModelProvider {
    final Consumer<BlockStateGenerator> blockStateOutput;
    public BlockModelGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper, Consumer<BlockStateGenerator> blockStateOutput) {
        super(generator, MonsterFood.MODID, existingFileHelper);
        this.blockStateOutput = blockStateOutput;
    }
    private static Variant[] createRotatedVariants(ResourceLocation pModelLocation) {
        return new Variant[]{Variant.variant().with(VariantProperties.MODEL, pModelLocation), Variant.variant().with(VariantProperties.MODEL, pModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90), Variant.variant().with(VariantProperties.MODEL, pModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180), Variant.variant().with(VariantProperties.MODEL, pModelLocation).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)};
    }
/*
    private void createNightmares() {
//        this.createSimpleFlatItemModel(MFBlocks.NIGHTMARE.get());
        this.blockStateOutput.accept(MultiVariantGenerator.multiVariant(MFBlocks.NIGHTMARE.get()).with(PropertyDispatch.properties(MFBlockStateProperties.NIGHTMARES,  BlockStateProperties.WATERLOGGED)
                .select(1, false, Arrays.asList(createRotatedVariants(prefix("assets/monsterfood/models/block/nightmares"))))
                .select(2, false, Arrays.asList(createRotatedVariants((prefix("assets/monsterfood/models/block/two_nightmares")))))
                .select(3, false, Arrays.asList(createRotatedVariants((prefix("assets/monsterfood/models/block/three_nightmares")))))
                .select(4, false, Arrays.asList(createRotatedVariants((prefix("assets/monsterfood/models/block/four_nightmares")))))));
    }
*/
    @Override
    protected void registerModels() {
 //       this.createNightmares();
    }

    @Override
    public String getName() {
        return "Block Models: " + modid;
    }
}
