package com.scouter.monsterfood.misc;

import com.mojang.blaze3d.systems.RenderSystem;
import com.scouter.monsterfood.MonsterFood;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.scouter.monsterfood.MonsterFood.prefix;

public class MFSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MonsterFood.MODID);

    public static final RegistryObject<SoundEvent> MUSHROOM_WALK = SOUNDS.register("mushroom_walk", ()-> new SoundEvent(prefix("mushroom_walk")));

}
