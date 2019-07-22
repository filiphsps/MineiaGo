package com.filiphsandstrom.mineiago.packets.java;

import com.filiphsandstrom.mineiago.MineiaGoSession;
import com.nukkitx.protocol.bedrock.packet.TextPacket;
import com.nukkitx.protocol.bedrock.packet.TextPacket.Type;


public class ServerChatPacket {
    public ServerChatPacket(com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket packet,
            MineiaGoSession session) {
        
        TextPacket text = new TextPacket();
        text.handle(session.getBedrockSession().getPacketHandler());
        text.setMessage(packet.getMessage().toJsonString()); // TODO: chat-parser

        switch (packet.getType()) {
            case SYSTEM:
                text.setType(Type.ANNOUNCEMENT);
                break;
            case NOTIFICATION:
                text.setType(Type.POPUP);
                break;
            case CHAT:
            default:
                text.setType(Type.CHAT);
                break;
        }
        text.setNeedsTranslation(false);
        text.setPlatformChatId("");
        text.setParameters(null);
        text.setSourceName("server");
        text.setXuid(session.getPlayerInfo().getXuid());
        session.getBedrockSession().sendPacket(text);
    }
}
