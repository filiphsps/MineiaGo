package com.filiphsandstrom.mineiago.packets.java;

import com.filiphsandstrom.mineiago.MineiaGoSession;
import com.nukkitx.protocol.bedrock.packet.LevelChunkPacket;
public class ServerChunkDataPacket {
    public ServerChunkDataPacket(com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerChunkDataPacket packet,
            MineiaGoSession session) {
        
        LevelChunkPacket chunk = new LevelChunkPacket();
        chunk.setChunkX(packet.getColumn().getX());
        chunk.setChunkZ(packet.getColumn().getZ());
        chunk.setCachingEnabled(false);
        chunk.setSubChunksLength(0);
        
        //chunk.setData(); // TODO
        session.getBedrockSession().sendPacket(chunk);
    }
}
