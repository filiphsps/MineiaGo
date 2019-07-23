package com.filiphsandstrom.mineiago.utils;

import java.nio.ByteBuffer;
import java.util.List;

import com.github.steveice10.mc.protocol.data.game.chunk.Chunk;
import com.github.steveice10.mc.protocol.data.game.chunk.Column;
import com.github.steveice10.mc.protocol.data.game.world.block.BlockState;

/**
 * Used to convert Java 1.13+ chunk data to bedrock 1.12+ packet format.
 * @param column 1.13+ packet chunk data
 * @return byte[] bedrock 1.12+ chunk.data
 */
public class ChunkConverter {
    public byte[] convert(Column column) {
        //TODO: https://github.com/NukkitX/Nukkit/blob/d0568b2535568bd0cc6c8067f4c27b65236133a7/src/main/java/cn/nukkit/level/format/anvil/Anvil.java#L107

        Chunk[] sections = column.getChunks();
        int[] biomeData = column.getBiomeData();

        ByteBuffer stream = ByteBuffer.allocate(2048); // TODO: correct size
        int count = 0;

        for (int i = sections.length - 1; i >= 0; i--) {
            if (!sections[i].isEmpty()) {
                count = i + 1;
                break;
            }
        }

        stream.put((byte) count);
        for (int i = 0; i < count; i++) {
            stream.put((byte) 0);
            
            List<BlockState> section = sections[i].getBlocks().getStates();
            for (BlockState b : section) {
                stream.putInt(b.getId());
            }
        }

        for (int i : biomeData) {
            stream.putInt(i);
        }
        stream.put((byte) 0);

        return stream.array();
    }
}
