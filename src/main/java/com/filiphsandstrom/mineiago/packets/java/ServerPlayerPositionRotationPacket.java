package com.filiphsandstrom.mineiago.packets.java;

import com.flowpowered.math.vector.Vector3f;

import com.filiphsandstrom.mineiago.MineiaGoSession;
import com.nukkitx.protocol.bedrock.packet.MovePlayerPacket;

public class ServerPlayerPositionRotationPacket {
    public ServerPlayerPositionRotationPacket(com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket packet,
            MineiaGoSession session) {
        
        MovePlayerPacket pos = new MovePlayerPacket();
        pos.handle(session.getBedrockSession().getPacketHandler());
        pos.setPosition(new Vector3f(packet.getX(), packet.getY(), packet.getZ()));
        session.getBedrockSession().sendPacket(pos);
    }
}
