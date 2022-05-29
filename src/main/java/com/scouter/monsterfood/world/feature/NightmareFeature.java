package com.scouter.monsterfood.world.feature;

import com.mojang.serialization.Codec;
import com.scouter.monsterfood.blocks.MFBlocks;
import com.scouter.monsterfood.blocks.NightmareBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SeaPickleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.CountConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Random;

public class NightmareFeature extends Feature<CountConfiguration> {
    public NightmareFeature(Codec<CountConfiguration> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<CountConfiguration> pContext) {
        int i = 0;
        Random random = pContext.random();
        WorldGenLevel worldgenlevel = pContext.level();
        BlockPos blockpos = pContext.origin();
        int j = pContext.config().count().sample(random);

        for(int k = 0; k < j; ++k) {
            int l = random.nextInt(8) - random.nextInt(8);
            int i1 = random.nextInt(8) - random.nextInt(8);
            int j1 = worldgenlevel.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, blockpos.getX() + l, blockpos.getZ() + i1);
            BlockPos blockpos1 = new BlockPos(blockpos.getX() + l, j1, blockpos.getZ() + i1);
            BlockState blockstate = MFBlocks.NIGHTMARE.get().defaultBlockState().setValue(NightmareBlock.NIGHTMARES, Integer.valueOf(random.nextInt(4) + 1));
            if (worldgenlevel.getBlockState(blockpos1).is(Blocks.SAND) && blockstate.canSurvive(worldgenlevel, blockpos1)) {
                worldgenlevel.setBlock(blockpos1, blockstate, 2);
                ++i;
            }

        }
        return i > 0;
    }


}
