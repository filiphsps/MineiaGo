package com.filiphsandstrom.mineiago.packets;

// import com.filiphsandstrom.mineiago.utils.*;
import com.filiphsandstrom.mineiago.MineiaGo;
import com.filiphsandstrom.mineiago.NetworkManager;
import com.whirvis.jraknet.RakNetPacket;

import net.md_5.bungee.api.ProxyServer;

public class LoginPacket extends DataPacket {
    public LoginPacket(RakNetPacket packet) {
        super(packet);
    }

    @Override
    public void decode() {
        MineiaGo.getInstance().getLogger().info(this.toString());

        if (player.isLoggedIn())
            return;

        buffer().readerIndex(0);
        player.setProtocolVersion(361);
        //MineiaGo.getInstance().getLogger().info("Protocol: " + (player.getProtocolVersion()));

        //int len = readUnsignedShortLE();
        skip(10);
        byte[] data = read(502); // should be longer?!
        
        String payload = new String(data);
        MineiaGo.getInstance().getLogger().info(payload);

        //LoginDecoder loginDecoder = new LoginDecoder(chainJWT, clientDataJWT);
        //loginDecoder.decode();
        
        // player.setGameEdition(readUnsignedByte());

        if (!MineiaGo.isCompatible(player.getProtocolVersion())) {
            if (player.getProtocolVersion() > MineiaGo.PROTOCOL) {
                MineiaGo.getInstance().getLogger().info("Protocol: " + (player.getProtocolVersion()));
                MineiaGo.getInstance().getLogger().info("GameEdition: " + (player.getGameEdition()));

                NetworkManager.sendPacket(player, new PlayStatusPacket(PlayStatusPacket.Status.LOGIN_FAILED_SERVER));
                player.disconnect(ProxyServer.getInstance().getTranslation("outdated_server"));
            } else {
                NetworkManager.sendPacket(player, new PlayStatusPacket(PlayStatusPacket.Status.LOGIN_FAILED_CLIENT));
                player.disconnect(ProxyServer.getInstance().getTranslation("outdated_client"));
            }
            MineiaGo.getInstance().getLogger()
                    .info(String.format("Client from address %s tryied to login with protocol version %s",
                            player.getSession().getAddress(), player.getProtocolVersion()));
            return; // Do not attempt to decode for non-accepted protocols
        }
        player.setLoggedIn(true);

        NetworkManager.sendPacket(player, new PlayStatusPacket(PlayStatusPacket.Status.LOGIN_SUCCESS));
        NetworkManager.sendPacket(player, new ServerHandshakePacket(getPlayer()));

        NetworkManager.sendPacket(player, new StartGamePacket());

        NetworkManager.sendPacket(player, new SetTimePacket());
        NetworkManager.sendPacket(player, new AdventureSettingsPacket());

        NetworkManager.sendPacket(player, new PlayStatusPacket(PlayStatusPacket.Status.PLAYER_SPAWN));
    }
}
