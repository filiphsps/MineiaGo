package com.filiphsandstrom.mineiago.utils;

import java.nio.ByteBuffer;
import java.util.List;

import com.github.steveice10.mc.protocol.data.game.chunk.Chunk;
import com.github.steveice10.mc.protocol.data.game.chunk.Column;
import com.github.steveice10.mc.protocol.data.game.world.block.BlockState;

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
         * https://github.com/NukkitX/Nukkit/blob/d0568b2535568bd0cc6c8067f4c27b65236133a7/src/main/java/cn/nukkit/level/format/anvil/Anvil.java#L107
         * 
         * You can also use the following for reference when it comes to the Minecraft Java 1.13+ section format
         * https://minecraft.gamepedia.com/Talk:Chunk_format
         */

        byte[] temp = { 0 };
        return temp;
    }
}
