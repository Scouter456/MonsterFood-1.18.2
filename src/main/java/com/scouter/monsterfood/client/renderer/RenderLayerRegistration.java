package com.scouter.monsterfood.client.renderer;

import com.scouter.monsterfood.blocks.MFBlocks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;

public class RenderLayerRegistration {
    public static void init() {
        RenderType cutoutMipped = RenderType.cutoutMipped();
        RenderType cutout = RenderType.cutout();
        RenderType translucent = RenderType.translucent();
        RenderType translucentnocrumb = RenderType.translucentNoCrumbling();
        RenderType solid = RenderType.solid();

        ItemBlockRenderTypes.setRenderLayer(MFBlocks.NIGHTMARE.get(),cutout);

        ItemBlockRenderTypes.setRenderLayer(MFBlocks.SKILLET.get(),cutout);
        ItemBlockRenderTypes.setRenderLayer(MFBlocks.COOKED_WALKING_MUSHROOM_GARLIC_BUTTER_SKILLET.get(),cutout);
    }
}
