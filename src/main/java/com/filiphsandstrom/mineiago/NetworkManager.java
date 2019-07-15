package com.filiphsandstrom.mineiago;

import com.filiphsandstrom.mineiago.packets.DataPacket;
import com.filiphsandstrom.mineiago.PacketRegistry.NetworkType;
import com.whirvis.jraknet.RakNetPacket;
import com.whirvis.jraknet.identifier.MinecraftIdentifier;
import com.whirvis.jraknet.protocol.Reliability;
import com.whirvis.jraknet.server.RakNetServer;
import com.whirvis.jraknet.server.RakNetServerListener;
import com.whirvis.jraknet.session.RakNetClientSession;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ListenerInfo;

import java.util.Random;

public class NetworkManager implements RakNetServerListener {
    private RakNetServer server;
    public RakNetServer getServer() {
        return server;
    }

    public NetworkManager() {
        // Get max players from the first listener found
        final ListenerInfo listenerInfo = ProxyServer.getInstance().getConfig().getListeners().stream().findFirst()
                .orElseGet(null);

        final int limit = (ProxyServer.getInstance().getConfig().getPlayerLimit() <= 0) ? Integer.MAX_VALUE
                : ProxyServer.getInstance().getConfig().getPlayerLimit();

        if (listenerInfo == null) {
            MineiaGo.getInstance().getLogger().severe("No listener found! :(");
            return;
        }

        MinecraftIdentifier id = new MinecraftIdentifier(listenerInfo.getMotd(), MineiaGo.PROTOCOL,
                MineiaGo.VERSION, ProxyServer.getInstance().getOnlineCount(), listenerInfo.getMaxPlayers(),
                new Random().nextLong(), ProxyServer.getInstance().getName(), "Survival");

        server = new RakNetServer(MineiaGo.PORT, limit);
        server.setIdentifier(id);
        server.addListener(this);

        server.startThreaded();
        MineiaGo.getInstance().getLogger().info("Listening for Bedrock clients on 0.0.0.0:" + MineiaGo.PORT);
    }

    public static void sendPacket(RakNetClientSession session, DataPacket packet) {
        session.sendMessage(Reliability.RELIABLE_SEQUENCED, packet);
    }

    public static void sendPacket(BedrockPlayer player, DataPacket packet) {
        sendPacket(player.getSession(), packet);
        MineiaGo.getInstance().getLogger().info("[PROTOCOL Send] " + NetworkType.getById(packet.getId()) + " -> " + player.getSession().getAddress());
    }

    @Override
    public void onClientConnect(RakNetClientSession session) {
        MineiaGo.getInstance().getLogger()
                .info(String.format("[%s] <-> Client has connected", session.getAddress()));
        
    }

    @Override
    public void onClientDisconnect(RakNetClientSession session, String reason) {
        BedrockPlayer.getPlayers().remove(session.getGloballyUniqueId());
        MineiaGo.getInstance().getLogger()
                .info(String.format("[%s] <-> Client has disconnected: %s", session.getAddress(), reason));
    }

    @Override
    public void handleMessage(RakNetClientSession session, RakNetPacket packet, int channel) {
        MineiaGo.getInstance().getLogger().info("[PROTOCOL Recv] " + session.getAddress() + " -> " + NetworkType.getById(packet.getId()));
        PacketRegistry.handlePacket(packet, BedrockPlayer.getPlayer(session));
    }
}
