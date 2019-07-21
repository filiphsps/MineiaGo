package com.filiphsandstrom.mineiago.packets.java;

import com.filiphsandstrom.mineiago.BedrockPlayerInfo;
import com.filiphsandstrom.mineiago.MineiaGo;
import com.filiphsandstrom.mineiago.MineiaGoSession;
import com.filiphsandstrom.mineiago.packets.BedrockPackets;
import com.github.steveice10.packetlib.packet.Packet;
import com.nukkitx.protocol.bedrock.packet.TextPacket;
import com.nukkitx.protocol.bedrock.packet.TextPacket.Type;

public class ServerChatPacket {
    public ServerChatPacket(com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket packet,
            MineiaGoSession session) {
        MineiaGo.getInstance().getLogging().Debug("!!! " + packet.getMessage().getFullText());
        
        TextPacket text = new TextPacket();
        text.handle(new BedrockPackets().packetHandler);
        text.setMessage(packet.getMessage().getFullText());
        text.setType(Type.CHAT);
        session.getBedrockSession().sendPacket(text);
    }
}
