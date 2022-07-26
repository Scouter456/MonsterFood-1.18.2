package com.scouter.monsterfood.utils;

import com.scouter.monsterfood.MonsterFood;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;

public class utils {
    public static void enqueueImmediateTask(LevelAccessor level, Runnable task, boolean allowClient)
    {
        if (level.isClientSide() && allowClient)
        {
            task.run();
        }
        else
        {
            enqueueTask(level, task, 0);
        }
    }

    public static void enqueueTask(LevelAccessor level, Runnable task, int delay)
    {
        if (!(level instanceof ServerLevel slevel))
        {
            throw new IllegalArgumentException("Utils#enqueueTask() called with a non-ServerWorld");
        }

        MinecraftServer server = slevel.getServer();
        server.tell(new TickTask(server.getTickCount() + delay, task));
    }

    public static MutableComponent getTranslation(String key, Object... args) {
        return new TranslatableComponent(MonsterFood.MODID + "." + key, args);
    }
}
