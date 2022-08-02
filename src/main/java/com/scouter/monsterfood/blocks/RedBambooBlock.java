package com.scouter.monsterfood.blocks;

import com.scouter.monsterfood.utils.MFTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BambooBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BambooLeaves;
import net.minecraft.world.level.material.FluidState;

import javax.annotation.Nullable;
import java.util.Random;

public class RedBambooBlock extends BambooBlock {
    public RedBambooBlock(Properties p_48874_) {
        super(p_48874_);
    }




    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        if (!fluidstate.isEmpty()) {
            return null;
        } else {
            BlockState blockstate = pContext.getLevel().getBlockState(pContext.getClickedPos().below());
            if (blockstate.is(MFTags.Blocks.RED_BAMBOO_PLANTABLE_ON)) {
                if (blockstate.is(MFBlocks.RED_BAMBOO_SAPLING.get())) {
                    return this.defaultBlockState().setValue(AGE, Integer.valueOf(0));
                } else if (blockstate.is(MFBlocks.RED_BAMBOO.get())) {
                    int i = blockstate.getValue(AGE) > 0 ? 1 : 0;
                    return this.defaultBlockState().setValue(AGE, Integer.valueOf(i));
                } else {
                    BlockState blockstate1 = pContext.getLevel().getBlockState(pContext.getClickedPos().above());
                    return blockstate1.is(MFBlocks.RED_BAMBOO.get()) ? this.defaultBlockState().setValue(AGE, blockstate1.getValue(AGE)) : MFBlocks.RED_BAMBOO_SAPLING.get().defaultBlockState();
                }
            } else {
                return null;
            }
        }
    }



    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (!pState.canSurvive(pLevel, pCurrentPos)) {
            pLevel.scheduleTick(pCurrentPos, this, 1);
        }

        if (pFacing == Direction.UP && pFacingState.is(MFBlocks.RED_BAMBOO.get()) && pFacingState.getValue(AGE) > pState.getValue(AGE)) {
            pLevel.setBlock(pCurrentPos, pState.cycle(AGE), 2);
        }

        return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return pLevel.getBlockState(pPos.below()).is(MFTags.Blocks.RED_BAMBOO_PLANTABLE_ON);
    }


    @Override
    protected void growBamboo(BlockState pState, Level pLevel, BlockPos pPos, Random pRandom, int pMaxTotalSize) {
        BlockState blockstate = pLevel.getBlockState(pPos.below());
        BlockPos blockpos = pPos.below(2);
        BlockState blockstate1 = pLevel.getBlockState(blockpos);
        BambooLeaves bambooleaves = BambooLeaves.NONE;
        if (pMaxTotalSize >= 1) {
            if (blockstate.is(MFBlocks.RED_BAMBOO.get()) && blockstate.getValue(LEAVES) != BambooLeaves.NONE) {
                if (blockstate.is(MFBlocks.RED_BAMBOO.get()) && blockstate.getValue(LEAVES) != BambooLeaves.NONE) {
                    bambooleaves = BambooLeaves.LARGE;
                    if (blockstate1.is(MFBlocks.RED_BAMBOO.get())) {
                        pLevel.setBlock(pPos.below(), blockstate.setValue(LEAVES, BambooLeaves.SMALL), 3);
                        pLevel.setBlock(blockpos, blockstate1.setValue(LEAVES, BambooLeaves.NONE), 3);
                    }
                }
            } else {
                bambooleaves = BambooLeaves.SMALL;
            }
        }

        int i = pState.getValue(AGE) != 1 && !blockstate1.is(MFBlocks.RED_BAMBOO.get()) ? 0 : 1;
        int j = (pMaxTotalSize < 11 || !(pRandom.nextFloat() < 0.25F)) && pMaxTotalSize != 15 ? 0 : 1;
        pLevel.setBlock(pPos.above(), this.defaultBlockState().setValue(AGE, Integer.valueOf(i)).setValue(LEAVES, bambooleaves).setValue(STAGE, Integer.valueOf(j)), 3);
    }

    @Override
    protected int getHeightAboveUpToMax(BlockGetter pLevel, BlockPos pPos) {
        int i;
        for(i = 0; i < 16 && pLevel.getBlockState(pPos.above(i + 1)).is(MFBlocks.RED_BAMBOO.get()); ++i) {
        }

        return i;
    }

    /**
     * @return the number of continuous bamboo blocks below the position passed in, up to 16.
     */
    @Override
    protected int getHeightBelowUpToMax(BlockGetter pLevel, BlockPos pPos) {
        int i;
        for(i = 0; i < 16 && pLevel.getBlockState(pPos.below(i + 1)).is(MFBlocks.RED_BAMBOO.get()); ++i) {
        }

        return i;
    }
}
