package com.scouter.monsterfood.datagen;

import com.scouter.monsterfood.blocks.MFBlocks;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.HashSet;
import java.util.Set;

public class LootTableBlocks extends BlockLoot {

    private final Set<Block> knownBlocks = new HashSet<>();

    @Override
    protected void add(Block block, LootTable.Builder builder) {
        super.add(block, builder);
        knownBlocks.add(block);
    }
    @Override
    protected  void addTables(){
        dropSelf(MFBlocks.ORETEST.get());
        dropSelf(MFBlocks.NIGHTMARE.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return knownBlocks;
    }
}
