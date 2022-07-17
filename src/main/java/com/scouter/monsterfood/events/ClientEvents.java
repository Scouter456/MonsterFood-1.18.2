package com.scouter.monsterfood.events;

import com.mojang.logging.LogUtils;
import com.scouter.monsterfood.MonsterFood;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod.EventBusSubscriber(modid = MonsterFood.MODID, value = Dist.CLIENT,bus = Mod.EventBusSubscriber.Bus.FORGE)

public class ClientEvents {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final ResourceLocation TEXTURE = new ResourceLocation(MonsterFood.MODID, "textures/mob_effect/effect.png");

    @SubscribeEvent
    public static void renderEffect(RenderGameOverlayEvent.Post event){
        if(Minecraft.getInstance().player == null) return;
    }
}
