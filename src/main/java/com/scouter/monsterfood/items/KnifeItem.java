package com.scouter.monsterfood.items;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;

public class KnifeItem extends Item {

    public static Item knife;
    public KnifeItem(Tier tier, Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext){
        if(pContext.getLevel().getBlockState(pContext.getClickedPos()).getBlock() == Blocks.GRASS_BLOCK){
            this.knife = MFItems.STONE_KNIFE.get();
            return InteractionResult.PASS;
        } else if (pContext.getLevel().getBlockState(pContext.getClickedPos()).getBlock() == Blocks.SAND){

        } else return InteractionResult.FAIL;
        return InteractionResult.FAIL;
    }



}
