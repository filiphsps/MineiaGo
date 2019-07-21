package com.filiphsandstrom.mineiago.packets.java;

import com.filiphsandstrom.mineiago.MineiaGoSession;
import com.nukkitx.protocol.bedrock.packet.SetTimePacket;

public class ServerUpdateTimePacket {
    public ServerUpdateTimePacket(com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerUpdateTimePacket packet,
            MineiaGoSession session) {
        
        SetTimePacket time = new SetTimePacket();
        time.setTime((int)packet.getTime());
        session.getBedrockSession().sendPacket(time);
    }
}
