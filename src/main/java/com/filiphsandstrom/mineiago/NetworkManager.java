package com.filiphsandstrom.mineiago;

import com.filiphsandstrom.mineiago.PacketRegistry;
import com.nukkitx.protocol.bedrock.*;
import com.nukkitx.protocol.bedrock.v361.Bedrock_v361;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ListenerInfo;

import java.net.InetSocketAddress;

public class NetworkManager {
    private BedrockServer server;

    public BedrockServer getServer() {
        return server;
    }

    public NetworkManager() {
        final ListenerInfo listenerInfo = ProxyServer.getInstance().getConfig().getListeners().stream().findFirst()
                .orElseGet(null);

        server = new BedrockServer(new InetSocketAddress("0.0.0.0", MineiaGo.PORT));
        
        BedrockPong pong = new BedrockPong();
        pong.setEdition("MCPE");
        pong.setMotd(listenerInfo.getMotd());
        pong.setPlayerCount(ProxyServer.getInstance().getOnlineCount());
        pong.setMaximumPlayerCount(listenerInfo.getMaxPlayers());
        pong.setGameType("Survival");
        pong.setProtocolVersion(MineiaGo.PROTOCOL);

        BedrockServerEventHandler eventHandler = new BedrockServerEventHandler() {
            @Override
            public boolean onConnectionRequest(InetSocketAddress address) {
                MineiaGo.getInstance().getLogger().info("Connection from " + address.toString());
                return true;
            }
            
            @Override
            public BedrockPong onQuery(InetSocketAddress address) {
                return pong;
            }
            
            @Override
            public void onSessionCreation(BedrockServerSession serverSession) {
                MineiaGo.getInstance().getLogger().info("Session from " + serverSession.getAddress());

                BedrockPlayer player = new BedrockPlayer(serverSession);

                serverSession.setLogging(true);
                serverSession.setPacketCodec(Bedrock_v361.V361_CODEC);

                //FIXME: remove session on disconnect
                // serverSession.addDisconnectHandler(() -> System.out.println("Disconnected"));

                PacketRegistry packets = new PacketRegistry();
                packets.serverSession = serverSession;
                serverSession.setPacketHandler(packets.handler);
            }
        };
        
        server.setHandler(eventHandler);
        server.bind().join();

        MineiaGo.getInstance().getLogger().info("Listening for Bedrock clients on 0.0.0.0:" + MineiaGo.PORT);
    }
}
