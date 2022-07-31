package com.scouter.monsterfood.items;

import com.mojang.logging.LogUtils;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import org.slf4j.Logger;

public class KnifeItem extends Item {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static Item knife;
    public KnifeItem(Tier tier, Properties pProperties) {
        super(pProperties);
    }

}
