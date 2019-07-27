package com.filiphsandstrom.mineiago.utils;

import java.nio.ByteBuffer;

import com.github.steveice10.mc.protocol.data.game.chunk.BlockStorage;
import com.github.steveice10.mc.protocol.data.game.chunk.Chunk;
import com.github.steveice10.mc.protocol.data.game.chunk.Column;

/**
 * Used to convert Java 1.13+ chunk data to bedrock 1.12+ packet format.
 * 
 * @param column 1.13+ packet chunk data
 * @return byte[] bedrock 1.12+ chunk.data
 */
public class ChunkConverter {
    public byte[] convert(Column column) {
        /*
         * TODO: implement a section converter that converts the Minecraft Java 1.13+
         * section format to the Minecraft Bedrock 1.12+ format.
         * 
         * The following, while dated can be used for reference. It's still stuck on the
         * previous Minecraft Java Chunk format that isn't in use anymore but the
         * bedrock conversion seems to be up-to-date:
         * https://github.com/NukkitX/Nukkit/blob/
         * d0568b2535568bd0cc6c8067f4c27b65236133a7/src/main/java/cn/nukkit/level/format
         * /anvil/Anvil.java#L107
         * 
         * You can also use the following for reference when it comes to the Minecraft
         * Java 1.13+ section format https://minecraft.gamepedia.com/Talk:Chunk_format
         */


        Chunk[] chunks = column.getChunks();
        for (Chunk chunk : chunks) {
            BlockStorage blocks = chunk.getBlocks();

            for (int x = 0; x < 16; x++) {
                for (int y = 0; y < 16; y++) {
                    for (int z = 0; z < 16; z++) {
                        int block = blocks.get(x, y, z).getId();
                    }
                }
            }
        }

        ByteBuffer data = ByteBuffer.wrap(new byte[10]);
        return data.array();
    }
}
