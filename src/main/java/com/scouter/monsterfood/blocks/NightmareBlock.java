package com.scouter.monsterfood.blocks;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.slf4j.Logger;

import javax.annotation.Nullable;

public class NightmareBlock extends Block implements SimpleWaterloggedBlock {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final IntegerProperty NIGHTMARES = MFBlockStateProperties.NIGHTMARES;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public NightmareBlock(BlockBehaviour.Properties props) {
        super(props);
        this.registerDefaultState(this.stateDefinition.any().setValue(NIGHTMARES, Integer.valueOf(1)).setValue(WATERLOGGED, Boolean.valueOf(true)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(MFBlockStateProperties.NIGHTMARES, BlockStateProperties.WATERLOGGED);
    }

    public static final int MAX_NIGHTMARE = 4;
    protected static final VoxelShape ONE_NIGHTMARE_AABB = Block.box(4, 0, 5, 11, 2, 12);
    protected static final VoxelShape TWO_NIGHTMARE_AABB = Block.box(2, 0, 2, 14, 2, 14);

    protected static final VoxelShape THREE_NIGHTMARE_AABB = Block.box(1, 0, 2, 14, 2, 15);
    protected static final VoxelShape FOUR_NIGHTMARE_AABB = Block.box(1, 0, 1, 15, 2, 15);

    //Effects
    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        if(!(pEntity instanceof Player)){
            return;
        }
        Player playerEntity = (Player)pEntity;
        playerEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 1000, 1));
        super.stepOn(pLevel, pPos, pState, pEntity);
    }

    //Shape
    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        if (pContext instanceof EntityCollisionContext) {
            EntityCollisionContext entitycollisioncontext = (EntityCollisionContext)pContext;
            Entity entity = entitycollisioncontext.getEntity();
            if(entity == null){}
            if(pState.getValue(NIGHTMARES) == 1){
                return ONE_NIGHTMARE_AABB;
            }
            if(pState.getValue(NIGHTMARES) == 2){
                return TWO_NIGHTMARE_AABB;
            }
            if(pState.getValue(NIGHTMARES) == 3){
                return THREE_NIGHTMARE_AABB;
            }
            if(pState.getValue(NIGHTMARES) == 4){
                return FOUR_NIGHTMARE_AABB;
            }
        }
        return ONE_NIGHTMARE_AABB;
    }

    @Override
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

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockState blockstate = pContext.getLevel().getBlockState(pContext.getClickedPos());
        if (blockstate.is(this)) {
            return blockstate.setValue(NIGHTMARES, Integer.valueOf(Math.min(4, blockstate.getValue(NIGHTMARES) + 1)));
        } else {
            FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
            boolean flag = fluidstate.getType() == Fluids.WATER;
            return super.getStateForPlacement(pContext).setValue(WATERLOGGED, Boolean.valueOf(flag));
        }
    }

    @Override
    public boolean canBeReplaced(BlockState pState, BlockPlaceContext pUseContext) {
        return !pUseContext.isSecondaryUseActive() && pUseContext.getItemInHand().is(this.asItem()) && pState.getValue(NIGHTMARES) < 4 ? true : super.canBeReplaced(pState, pUseContext);
    }

    //Update
    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (!pState.canSurvive(pLevel, pCurrentPos)) {
            return Blocks.AIR.defaultBlockState();
        } else {
            if (pState.getValue(WATERLOGGED)) {
                pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
            }
            return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
        }
    }

    //Placement
    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.below();
        return this.mayPlaceOn(pLevel.getBlockState(blockpos), pLevel, blockpos);
    }

    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return !pState.getCollisionShape(pLevel, pPos).getFaceShape(Direction.UP).isEmpty() || pState.isFaceSturdy(pLevel, pPos, Direction.UP);
    }

    @Override
    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }
}
