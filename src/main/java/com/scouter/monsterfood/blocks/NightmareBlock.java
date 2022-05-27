package com.scouter.monsterfood.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class NightmareBlock extends Block {
    public static final IntegerProperty NIGHTMARES = MFBlockStateProperties.NIGHTMARES;
    public NightmareBlock(BlockBehaviour.Properties props) {
        super(props);
        this.registerDefaultState(this.stateDefinition.any().setValue(NIGHTMARES, Integer.valueOf(1)));
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(MFBlockStateProperties.NIGHTMARES);
    }
    public static final int MAX_NIGHTMARE = 4;

    protected static final VoxelShape ONE_NIGHTMARE_AABB = Block.box(3.0D, 0.0D, 3.0D, 12.0D, 3.0D, 12.0D);
    protected static final VoxelShape TWO_NIGHTMARE_AABB = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 6.0D, 13.0D);
    protected static final VoxelShape THREE_NIGHTMARE_AABB = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 6.0D, 14.0D);
    protected static final VoxelShape FOUR_NIGHTMARE_AABB = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 7.0D, 14.0D);


    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        super.stepOn(pLevel, pPos, pState, pEntity);
        Player playerEntity = (Player)pEntity;
        playerEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 1000, 1));
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockState blockstate = pContext.getLevel().getBlockState(pContext.getClickedPos());
        return blockstate.is(this) ? blockstate.setValue(NIGHTMARES, Integer.valueOf(Math.min(4, blockstate.getValue(NIGHTMARES) + 1))) : super.getStateForPlacement(pContext);
    }

    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockState blockstate = pLevel.getBlockState(pPos.below());
        if (blockstate.is(this)) {
            return false;
        } else {
            if (blockstate.is(BlockTags.DIRT) || blockstate.is(Blocks.SAND) || blockstate.is(Blocks.RED_SAND)) {
                return true;
                }
            }

            return false;
    }
    public boolean canBeReplaced(BlockState pState, BlockPlaceContext pUseContext) {
        return !pUseContext.isSecondaryUseActive() && pUseContext.getItemInHand().is(this.asItem()) && pState.getValue(NIGHTMARES) < 4 ? true : super.canBeReplaced(pState, pUseContext);
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        switch(pState.getValue(NIGHTMARES)) {
            case 1:
            default:
                return ONE_NIGHTMARE_AABB;
            case 2:
                return TWO_NIGHTMARE_AABB;
            case 3:
                return THREE_NIGHTMARE_AABB;
            case 4:
                return FOUR_NIGHTMARE_AABB;
        }
    }


}
