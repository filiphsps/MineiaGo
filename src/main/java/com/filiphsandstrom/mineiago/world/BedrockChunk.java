package com.filiphsandstrom.mineiago.world;

import com.filiphsandstrom.mineiago.MineiaGo;
import com.flowpowered.math.vector.Vector3i;
import com.google.common.io.ByteStreams;

// TODO: http://github.com/NukkitX/Nukkit/blob/4ce13f2afb9a1a330df63a482bee9995ce1f96d5/src/main/java/cn/nukkit/level/format/generic/BaseChunk.java
public class BedrockChunk {
    private final int section_count = 16;

    private int x;
    private int z;

    private BedrockChunkSection[] sections = new BedrockChunkSection[section_count];

    public BedrockChunk(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public void setSection(int y, BedrockChunkSection section) {
        sections[y] = section;
    };
    public void setBlockId(Vector3i pos, int id) {
    }

    public void setFlat() {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                setBlockId(new Vector3i(x, 0, z), 1);
            }
        }
    }

    public byte[] dump() {
        byte[] dump = {0};
        try {
            dump = ByteStreams.toByteArray(MineiaGo.getInstance().getResourceAsStream("chunk"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dump;
    }
}
