package com.scouter.monsterfood.blocks;

import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class MFBlockStateProperties extends BlockStateProperties {

    public static final IntegerProperty NIGHTMARES = IntegerProperty.create("nightmares", 1, 4);
    public static final IntegerProperty SPICE_TIER = IntegerProperty.create("spice_tier", 1, 4);
    public static final BooleanProperty LAVALOGGED = BooleanProperty.create("lavalogged");



}
