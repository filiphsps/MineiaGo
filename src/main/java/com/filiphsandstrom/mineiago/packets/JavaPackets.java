package com.filiphsandstrom.mineiago.packets;

import com.filiphsandstrom.mineiago.BedrockPlayerInfo;
import com.filiphsandstrom.mineiago.MineiaGoSession;
import com.filiphsandstrom.mineiago.packets.java.*;
import com.github.steveice10.packetlib.packet.Packet;

public class JavaPackets {
    public void handlePacket (String type, Packet packet, MineiaGoSession session) {
        try {
            Object handler = Class.forName("com.filiphsandstrom.mineiago.packets.java." + type).getConstructors()[0].newInstance(packet, session);
        } catch(Exception e) {
            // e.printStackTrace();
        }
    }
}
