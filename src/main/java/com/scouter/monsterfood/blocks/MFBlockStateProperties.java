package com.scouter.monsterfood.blocks;

import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class MFBlockStateProperties extends BlockStateProperties {

    public static final IntegerProperty NIGHTMARES = IntegerProperty.create("nightmares", 1, 4);


}
