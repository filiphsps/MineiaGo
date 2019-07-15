package com.filiphsandstrom.mineiago;

import com.filiphsandstrom.mineiago.packets.DataPacket;
import com.filiphsandstrom.mineiago.PacketRegistry.NetworkType;

import com.nukkitx.protocol.bedrock.*;

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

        server.setHandler(new BedrockServerEventHandler() {
            @Override
            public boolean onConnectionRequest(InetSocketAddress address) {
                return true;
            }
            
            @Override
            public BedrockPong onQuery(InetSocketAddress address) {
                return pong;
            }
            
            @Override
            public void onSessionCreation(BedrockServerSession serverSession) {
                // serverSession.addDisconnectHandler(() -> System.out.println("Disconnected"));
                // serverSession.setPacketHandler(PacketRegistry.handlePacket);
            }
        });

        server.bind().join();
        MineiaGo.getInstance().getLogger().info("Listening for Bedrock clients on 0.0.0.0:" + MineiaGo.PORT);
    }
}
