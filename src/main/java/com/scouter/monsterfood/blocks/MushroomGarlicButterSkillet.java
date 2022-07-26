package com.scouter.monsterfood.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

public class MushroomGarlicButterSkillet extends SkilletBlock{
    public MushroomGarlicButterSkillet(Properties props, MFBlockEnums state) {
        super(props, state);
    }

    protected static final VoxelShape SKILLET = Stream.of(
            Block.box(2, 0, 2, 14, 1, 14),
            Block.box(2, 1, 1, 14, 4, 2),
            Block.box(2, 1, 14, 14, 4, 15),
            Block.box(14, 1, 2, 15, 4, 14),
            Block.box(1, 1, 2, 2, 4, 14)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    protected static final VoxelShape MUSHROOM_GARLIC_BUTTER_SHAPE = Shapes.joinUnoptimized(SKILLET , Block.box(2, 1, 2, 14, 3, 14), BooleanOp.OR);



    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return state.getValue(FOOD) == 0 ? SKILLET : MUSHROOM_GARLIC_BUTTER_SHAPE;
    }

}
