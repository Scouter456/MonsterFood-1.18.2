package com.scouter.monsterfood.setup;

import com.scouter.monsterfood.MonsterFood;
import com.scouter.monsterfood.client.renderer.RenderLayerRegistration;
import com.scouter.monsterfood.entity.MFEntity;
import com.scouter.monsterfood.entity.renderer.LavaSnailRenderer;
import com.scouter.monsterfood.entity.renderer.WalkingMushroomRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
@Mod.EventBusSubscriber(modid = MonsterFood.MODID, value = Dist.CLIENT,bus = Mod.EventBusSubscriber.Bus.MOD)

public class ClientSetup {

    public static void init(FMLClientSetupEvent event){
        RenderLayerRegistration.init();
        EntityRenderers.register(MFEntity.WALKINGMUSHROOM.get(), WalkingMushroomRenderer::new);
        EntityRenderers.register(MFEntity.LAVASNAIL.get(), LavaSnailRenderer::new);
    }
}
