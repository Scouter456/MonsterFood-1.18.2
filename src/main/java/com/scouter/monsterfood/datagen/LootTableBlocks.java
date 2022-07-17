package com.scouter.monsterfood.datagen;

import com.scouter.monsterfood.blocks.LavaSnailSpice;
import com.scouter.monsterfood.blocks.MFBlocks;
import com.scouter.monsterfood.items.MFItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SeaPickleBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

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
    protected void addTables() {
        //dropSelf(MFBlocks.ORETEST.get());
        //dropSelf(MFBlocks.NIGHTMARE.get());
        add(MFBlocks.SPICE.get(), (spices) -> spices(MFBlocks.SPICE.get(), MFItems.BLACK_SPICE.get(), MFItems.RED_SPICE.get(), MFItems.WHITE_SPICE.get()));
    }

    private static LootTable.Builder spices(Block block, Item item, Item item2, Item item3) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(applyExplosionDecay(block, LootItem.lootTableItem(item).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(LavaSnailSpice.SPICE_TIER, 1)))))).add(applyExplosionDecay(block, LootItem.lootTableItem(item2).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(LavaSnailSpice.SPICE_TIER, 2)))))).add(applyExplosionDecay(block, LootItem.lootTableItem(item3).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(LavaSnailSpice.SPICE_TIER, 3)))))).add(applyExplosionDecay(block, LootItem.lootTableItem(item).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(LavaSnailSpice.SPICE_TIER, 4)))))));
    }
         //return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(applyExplosionDecay(Blocks.SEA_PICKLE, LootItem.lootTableItem(p_124118_).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(p_124118_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SeaPickleBlock.PICKLES, 2)))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(3.0F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(p_124118_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SeaPickleBlock.PICKLES, 3)))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(p_124118_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SeaPickleBlock.PICKLES, 4)))))));

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return knownBlocks;
    }
}
