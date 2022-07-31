package com.scouter.monsterfood.blocks;

import com.scouter.monsterfood.MonsterFood;
import com.scouter.monsterfood.blocks.entity.CuttingBoardBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MFBlockEntity {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MonsterFood.MODID);

    public static final RegistryObject<BlockEntityType<CuttingBoardBlockEntity>> CUTTING_BOARD_BLOCK_ENTITY = BLOCK_ENTITY.register("cutting_board_block_entity",
            () -> BlockEntityType.Builder.of(CuttingBoardBlockEntity::new, MFBlocks.CUTTING_BOARD_BLOCK.get()).build(null));

}
