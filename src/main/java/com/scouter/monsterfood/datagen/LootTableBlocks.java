package com.scouter.monsterfood.datagen;

import com.scouter.monsterfood.blocks.LavaSnailSpice;
import com.scouter.monsterfood.blocks.MFBlocks;
import com.scouter.monsterfood.items.MFItems;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.HashSet;
import java.util.Set;

public class LootTableBlocks extends BlockLoot {

    private final Set<Block> knownBlocks = new HashSet<>();
    private static final LootItemCondition.Builder HAS_SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))));
    private static final LootItemCondition.Builder HAS_NO_SILK_TOUCH = HAS_SILK_TOUCH.invert();
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
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                .add(AlternativesEntry.alternatives(AlternativesEntry.alternatives(
                        applyExplosionDecay(block, LootItem.lootTableItem(item).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(LavaSnailSpice.SPICE_TIER, 1)))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))),
                                        applyExplosionDecay(block, LootItem.lootTableItem(item2).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(LavaSnailSpice.SPICE_TIER, 2)))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))),
                                        applyExplosionDecay(block, LootItem.lootTableItem(item3).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(LavaSnailSpice.SPICE_TIER, 3)))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))),
                                        applyExplosionDecay(block, LootItem.lootTableItem(item).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(LavaSnailSpice.SPICE_TIER, 4)))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                                .when(HAS_NO_SILK_TOUCH),
                AlternativesEntry.alternatives(LootItem.lootTableItem(block)))));
    }


    @Override
    protected Iterable<Block> getKnownBlocks() {
        return knownBlocks;
    }
}
