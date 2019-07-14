package com.filiphsandstrom.bedrockproxy;

import com.filiphsandstrom.bedrockproxy.packets.DataPacket;
import com.filiphsandstrom.bedrockproxy.raknet.RakNetPacket;
import com.filiphsandstrom.bedrockproxy.raknet.identifier.MCPEIdentifier;
import com.filiphsandstrom.bedrockproxy.raknet.protocol.Reliability;
import com.filiphsandstrom.bedrockproxy.raknet.server.RakNetServer;
import com.filiphsandstrom.bedrockproxy.raknet.server.RakNetServerListener;
import com.filiphsandstrom.bedrockproxy.raknet.session.RakNetClientSession;
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
        final ListenerInfo listenerInfo = ProxyServer.getInstance()
                .getConfig()
                .getListeners()
                .stream()
                .findFirst()
                .orElseGet(null);

        final int limit = (ProxyServer.getInstance().getConfig().getPlayerLimit() <= 0) ?
                Integer.MAX_VALUE : ProxyServer.getInstance().getConfig().getPlayerLimit();

        if (listenerInfo == null) {
            BedrockProxy.getInstance().getLogger().severe("No listener found! :(");
            return;
        }

        MCPEIdentifier id = new MCPEIdentifier(
                listenerInfo.getMotd(),
                BedrockProxy.PROTOCOL,
                BedrockProxy.VERSION,
                ProxyServer.getInstance().getOnlineCount(),
                listenerInfo.getMaxPlayers(),
                new Random().nextLong(),
                ProxyServer.getInstance().getName(),
                "Survival"
        );

        server = new RakNetServer(BedrockProxy.PORT, limit, id);
        server.setListener(this);

        server.startThreaded();
        BedrockProxy.getInstance().getLogger().info("Listening for MCPE clients on 0.0.0.0:" + BedrockProxy.PORT);
    }

    public static void sendPacket(RakNetClientSession session, DataPacket packet) {
        session.sendMessage(Reliability.RELIABLE_SEQUENCED, packet);
    }

    public static void sendPacket(BedrockPlayer player, DataPacket packet) {
        sendPacket(player.getSession(), packet);
    }

    @Override
    public void onClientConnect(RakNetClientSession session) {
        BedrockProxy.getInstance().getLogger().info(
                String.format("[%s] <-> Client has connected", session.getAddress())
        );
    }

    @Override
    public void onClientDisconnect(RakNetClientSession session, String reason) {
        BedrockPlayer.getPlayers().remove(session.getGloballyUniqueId());
        BedrockProxy.getInstance().getLogger().info(
                String.format("[%s] <-> Client has disconnected: %s", session.getAddress(), reason)
        );
    }

    @Override
    public void handleMessage(RakNetClientSession session, RakNetPacket pk, int channel) {
        PacketRegistry.handlePacket(pk, BedrockPlayer.getPlayer(session));
    }
}