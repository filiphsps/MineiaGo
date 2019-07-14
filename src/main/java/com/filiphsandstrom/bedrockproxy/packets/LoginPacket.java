package com.filiphsandstrom.bedrockproxy.packets;

import com.filiphsandstrom.bedrockproxy.BedrockProxy;
import com.filiphsandstrom.bedrockproxy.NetworkManager;
import com.whirvis.jraknet.RakNetPacket;
import net.md_5.bungee.api.ProxyServer;

public class LoginPacket extends DataPacket {
    public LoginPacket(RakNetPacket packet) {
        super(packet);
    }

    @Override
    public void decode() {
        BedrockProxy.getInstance().getLogger().info("decoding..");

        if (player.isLoggedIn())
            return;

        buffer().readerIndex(0);
        
        player.setProtocolVersion(readInt());
        byte[] data = readBytes();
        
        player.setGameEdition(readUnsignedByte());

        if (!BedrockProxy.isCompatible(player.getProtocolVersion())) {
            if (player.getProtocolVersion() > BedrockProxy.PROTOCOL) {
                BedrockProxy.getInstance().getLogger().info("Protocol: " + (player.getProtocolVersion()));
                BedrockProxy.getInstance().getLogger().info("GameEditior: " + (player.getGameEdition()));

                NetworkManager.sendPacket(player, new PlayStatusPacket(PlayStatusPacket.Status.LOGIN_FAILED_SERVER));
                player.disconnect(ProxyServer.getInstance().getTranslation("outdated_server"));
            } else {
                NetworkManager.sendPacket(player, new PlayStatusPacket(PlayStatusPacket.Status.LOGIN_FAILED_CLIENT));
                player.disconnect(ProxyServer.getInstance().getTranslation("outdated_client"));
            }
            BedrockProxy.getInstance().getLogger()
                    .info(String.format("Client from address %s tryied to login with protocol version %s",
                            player.getSession().getAddress(), player.getProtocolVersion()));
            return; // Do not attempt to decode for non-accepted protocols
        }
        skip(3);

        player.setChainData(readString());

        // player.skinData = readStringLE();

        BedrockProxy.getInstance().getLogger().info(String.format("[%s] <-> Logged in!", "player"));

        player.setLoggedIn(true);

        NetworkManager.sendPacket(player, new PlayStatusPacket(PlayStatusPacket.Status.LOGIN_SUCCESS));
        NetworkManager.sendPacket(player, new ServerHandshakePacket(getPlayer()));
    }
}
