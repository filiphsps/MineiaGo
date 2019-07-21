package com.filiphsandstrom.mineiago.packets.java;

import com.filiphsandstrom.mineiago.MineiaGoSession;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientKeepAlivePacket;

public class ServerKeepAlivePacket {
    public ServerKeepAlivePacket(com.github.steveice10.mc.protocol.packet.ingame.server.ServerKeepAlivePacket packet,
            MineiaGoSession session) {
        
        ClientKeepAlivePacket alive = new ClientKeepAlivePacket(packet.getPingId());
        session.getJavaSession().send(alive);
    }
}
