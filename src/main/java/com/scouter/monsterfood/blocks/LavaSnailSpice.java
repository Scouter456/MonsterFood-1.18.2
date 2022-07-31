package com.scouter.monsterfood.blocks;

import com.mojang.logging.LogUtils;
import com.scouter.monsterfood.blocks.interfaces.SimpleLavaLoggedBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Random;

public class LavaSnailSpice extends Block implements SimpleLavaLoggedBlock {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final IntegerProperty SPICE_TIER = MFBlockStateProperties.SPICE_TIER;
    public static final BooleanProperty LAVALOGGED = MFBlockStateProperties.LAVALOGGED;

    protected static final VoxelShape BLOCK_LAYER = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);
    protected static final VoxelShape COLL_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
    public LavaSnailSpice(Properties props) {
        super(props);
        this.registerDefaultState(this.stateDefinition.any().setValue(SPICE_TIER, Integer.valueOf(1)).setValue(LAVALOGGED, Boolean.valueOf(true)));

    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(MFBlockStateProperties.LAVALOGGED, MFBlockStateProperties.SPICE_TIER);
    }

    @Override
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return pType == PathComputationType.AIR && !this.hasCollision ? true : super.isPathfindable(pState, pLevel, pPos, pType);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return BLOCK_LAYER;
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom) {
        super.randomTick(pState, pLevel, pPos, pRandom);
        if(pLevel.getBlockState(pPos).getFluidState().getType() != Fluids.LAVA) {
            pLevel.setBlockAndUpdate(pPos, pState.setValue(SPICE_TIER, Integer.valueOf(1)));
        }
        if(pLevel.getBlockState(pPos).getFluidState().getType() == Fluids.LAVA) {
            pLevel.setBlockAndUpdate(pPos, pState.setValue(SPICE_TIER, Integer.valueOf(2)));
        }
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide) {
            return InteractionResult.CONSUME;
        }

        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        BlockState blockstate = pLevel.getBlockState(pPos);
        ItemStack itemstack1 = new ItemStack(Items.BUCKET);

        if(itemstack.is(Items.MILK_BUCKET)){
            pLevel.setBlockAndUpdate(pPos, blockstate.setValue(SPICE_TIER, Integer.valueOf(3)));
            pPlayer.setItemInHand(pHand, itemstack1);
            return InteractionResult.CONSUME_PARTIAL;
        }
        return InteractionResult.CONSUME;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockState blockstate = pContext.getLevel().getBlockState(pContext.getClickedPos());
        if (blockstate.is(this)) {
            return blockstate.setValue(SPICE_TIER, Integer.valueOf(1));
        } else {
            FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
            boolean flag = fluidstate.getType() == Fluids.LAVA;
            return super.getStateForPlacement(pContext).setValue(LAVALOGGED, Boolean.valueOf(flag));
        }
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (!pState.canSurvive(pLevel, pCurrentPos)) {
            return Blocks.AIR.defaultBlockState();
        } else {
            if (pState.getValue(LAVALOGGED)) {
                pLevel.scheduleTick(pCurrentPos, Fluids.LAVA, Fluids.LAVA.getTickDelay(pLevel));
            }
            return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
        }
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.below();
        return this.mayPlaceOn(pLevel.getBlockState(blockpos), pLevel, blockpos);
    }

    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return !pState.getCollisionShape(pLevel, pPos).getFaceShape(Direction.UP).isEmpty() || pState.isFaceSturdy(pLevel, pPos, Direction.UP);
    }
    @Override
    public boolean canBeReplaced(BlockState pState, BlockPlaceContext pUseContext) {
        return !pUseContext.isSecondaryUseActive() && pUseContext.getItemInHand().is(this.asItem()) && !pState.getValue(LAVALOGGED) ? true : super.canBeReplaced(pState, pUseContext);
    }

    @Override
    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(LAVALOGGED) ? Fluids.LAVA.getSource(false) : super.getFluidState(pState);
    }
}
