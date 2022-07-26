package com.scouter.monsterfood.setup;

import com.scouter.monsterfood.structuresystem.network.PacketSyncStructureToClient;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import static com.scouter.monsterfood.MonsterFood.prefix;

public class Messages {

    private static SimpleChannel INSTANCE;

    private static int packetId = 0;

    private static int id() {return packetId++;}

    public static void register(){

        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(prefix("messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(PacketSyncStructureToClient.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PacketSyncStructureToClient::new)
                .encoder(PacketSyncStructureToClient::toBytes)
                .consumer(PacketSyncStructureToClient::handle)
                .add();

        net.messageBuilder(PacketSyncStructureToClient.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PacketSyncStructureToClient::new)
                .encoder(PacketSyncStructureToClient::toBytes)
                .consumer(PacketSyncStructureToClient::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(()-> player), message);
    }
}
