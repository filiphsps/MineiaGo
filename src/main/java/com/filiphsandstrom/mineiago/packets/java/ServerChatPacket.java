package com.filiphsandstrom.mineiago.packets.java;

import com.filiphsandstrom.mineiago.MineiaGoSession;
import com.nukkitx.protocol.bedrock.packet.TextPacket;
import com.nukkitx.protocol.bedrock.packet.TextPacket.Type;

public class ServerChatPacket {
    public ServerChatPacket(com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket packet,
            MineiaGoSession session) {
        
        TextPacket text = new TextPacket();
        text.setMessage(packet.getMessage().getFullText());
        text.setType(Type.CHAT);
        session.getBedrockSession().sendPacket(text);
    }
}
