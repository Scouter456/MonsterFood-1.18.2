package com.scouter.monsterfood.setup;

import com.scouter.monsterfood.client.renderer.RenderLayerRegistration;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {

    public static void init(FMLClientSetupEvent event){
        RenderLayerRegistration.init();

    }
}
