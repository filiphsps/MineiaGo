package com.filiphsandstrom.mineiago;

import com.filiphsandstrom.mineiago.PacketRegistry;
import com.nukkitx.protocol.bedrock.*;
import com.nukkitx.protocol.bedrock.handler.*;
import com.nukkitx.protocol.bedrock.handler.BatchHandler;
import com.nukkitx.protocol.bedrock.v361.Bedrock_v361;

import io.netty.buffer.ByteBuf;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ListenerInfo;

import java.net.InetSocketAddress;
import java.util.Collection;

import lombok.Getter;

public class NetworkManager {
    @Getter
    private BedrockServer server;

    public NetworkManager() {
        final ListenerInfo listenerInfo = ProxyServer.getInstance().getConfig().getListeners().stream().findFirst()
                .orElseGet(null);

        server = new BedrockServer(new InetSocketAddress(MineiaGo.getInstance().getConfig().getAddress(),
                MineiaGo.getInstance().getConfig().getPort()));

        BedrockPong pong = new BedrockPong();
        pong.setEdition("MCPE");
        pong.setMotd(MineiaGo.getInstance().getConfig().getServername());
        pong.setSubMotd(listenerInfo.getMotd());
        pong.setPlayerCount(ProxyServer.getInstance().getOnlineCount());
        pong.setMaximumPlayerCount(listenerInfo.getMaxPlayers());
        pong.setGameType("Survival");
        pong.setProtocolVersion(MineiaGo.PROTOCOL);

        BedrockServerEventHandler eventHandler = new BedrockServerEventHandler() {
            @Override
            public boolean onConnectionRequest(InetSocketAddress address) {
                MineiaGo.getInstance().getLogging().Info("Connection from " + address.toString());
                return true;
            }

            @Override
            public BedrockPong onQuery(InetSocketAddress address) {
                return pong;
            }

            @Override
            public void onSessionCreation(BedrockServerSession serverSession) {
                MineiaGo.getInstance().getLogging().Info("Session from " + serverSession.getAddress());

                BedrockPlayer player = new BedrockPlayer(serverSession);

                serverSession.setLogging(true);
                serverSession.setPacketCodec(Bedrock_v361.V361_CODEC);

                // FIXME: remove session on disconnect
                // serverSession.addDisconnectHandler((reason) -> player.onDisconnect(reason));

                PacketRegistry packets = new PacketRegistry();
                packets.player = player;
                serverSession.setPacketHandler(packets.handler);
                serverSession.setBatchedHandler(new BatchHandler() {
                    @Override
                    public void handle(BedrockSession session, ByteBuf compressed, Collection<BedrockPacket> packets) {
                        for (BedrockPacket packet : packets) {
                            BedrockPacketHandler handler = session.getPacketHandler();
                            packet.handle(handler);
                        }
                    }
                });
            }
        };

        server.setHandler(eventHandler);
        server.bind().join();

        MineiaGo.getInstance().getLogging().Info("Listening for Bedrock clients on "
                + MineiaGo.getInstance().getConfig().getAddress() + ":" + MineiaGo.getInstance().getConfig().getPort());
    }

    public void Stop() {
        MineiaGo.getInstance().getLogging().Info("Shutting down the Bedrock server");
        server.close("MineiaGo is shutting down...");
    }
}
