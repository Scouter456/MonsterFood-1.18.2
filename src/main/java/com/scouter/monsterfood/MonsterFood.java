package com.scouter.monsterfood;

import com.mojang.logging.LogUtils;
import com.scouter.monsterfood.setup.ClientSetup;
import com.scouter.monsterfood.setup.ModSetup;
import com.scouter.monsterfood.setup.Registration;
import com.scouter.monsterfood.world.MFGeneration;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import software.bernie.geckolib3.GeckoLib;

import java.util.Locale;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MonsterFood.MODID)
public class MonsterFood
{
    public static final String MODID = "monsterfood";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public MonsterFood()
    {
        Registration.init();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        IEventBus modbus = FMLJavaModLoadingContext.get().getModEventBus();
        modbus.addListener(ModSetup::init);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> modbus.addListener(ClientSetup::init));

        forgeBus.addListener(EventPriority.HIGH, MFGeneration::onBiomeLoading);
        GeckoLib.initialize();

    }


    public static ResourceLocation prefix(String name) {
        return new ResourceLocation(MODID, name.toLowerCase(Locale.ROOT));
    }
}
