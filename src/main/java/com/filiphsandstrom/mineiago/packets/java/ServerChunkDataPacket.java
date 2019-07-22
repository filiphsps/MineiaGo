package com.filiphsandstrom.mineiago.packets.java;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import com.filiphsandstrom.mineiago.MineiaGoSession;
import com.github.steveice10.mc.protocol.data.game.chunk.Chunk;
import com.github.steveice10.mc.protocol.data.game.world.block.BlockState;
import com.nukkitx.protocol.bedrock.packet.LevelChunkPacket;
public class ServerChunkDataPacket {
    public ServerChunkDataPacket(com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerChunkDataPacket packet,
            MineiaGoSession session) {
        
        LevelChunkPacket chunk = new LevelChunkPacket();
        chunk.setChunkX(packet.getColumn().getX());
        chunk.setChunkZ(packet.getColumn().getZ());
        chunk.setCachingEnabled(false);
        chunk.setSubChunksLength(0);

        ByteBuffer stream = ByteBuffer.allocate(2048);

        int count = 0;

        Chunk[] sections = packet.getColumn().getChunks();
        for (int i = sections.length - 1; i >= 0; i--) {
            if (!sections[i].isEmpty()) {
                count = i + 1;
                break;
            }
        }

        for (int i = 0; i < count; i++) {
            stream.put((byte) 0);

            List<BlockState> section = sections[i].getBlocks().getStates();
            for (BlockState b : section) {
                stream.putInt(b.getId());
            }
        }

        for (int i : packet.getColumn().getBiomeData()) {
            stream.putInt(i);
        }
        stream.put((byte) 0);

        chunk.setData(stream.array()); // TODO
        session.getBedrockSession().sendPacket(chunk);
    }
}
