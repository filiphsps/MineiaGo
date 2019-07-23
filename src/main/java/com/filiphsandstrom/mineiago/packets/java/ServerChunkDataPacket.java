package com.filiphsandstrom.mineiago.packets.java;

import com.filiphsandstrom.mineiago.MineiaGoSession;
import com.filiphsandstrom.mineiago.utils.ChunkConverter;
import com.nukkitx.protocol.bedrock.packet.LevelChunkPacket;

public class ServerChunkDataPacket {
    public ServerChunkDataPacket(
            com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerChunkDataPacket packet,
            MineiaGoSession session) {

        ChunkConverter converter = new ChunkConverter();

        LevelChunkPacket chunk = new LevelChunkPacket();
        chunk.handle(session.getBedrockSession().getPacketHandler());
        chunk.setChunkX(packet.getColumn().getX());
        chunk.setChunkZ(packet.getColumn().getZ());
        chunk.setCachingEnabled(false);
        chunk.setSubChunksLength(0);

        chunk.setData(converter.convert(packet.getColumn()));
        session.getBedrockSession().sendPacket(chunk);
    }
}
