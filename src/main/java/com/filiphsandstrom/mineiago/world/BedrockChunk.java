package com.filiphsandstrom.mineiago.world;

import java.nio.Buffer;
import java.nio.ByteBuffer;

import com.filiphsandstrom.mineiago.MineiaGo;
import com.flowpowered.math.vector.Vector3i;
import com.google.common.io.ByteStreams;

// TODO
public class BedrockChunk {
    private byte[] data;
    private int width = 16;
    private int length = 16;
    private int height = 256;
    private int size = 3 + 256 + 512 + (16 * 10241);

    private int x;
    private int z;

    public BedrockChunk() {
        super();

        data = new byte[(256 * 2) + 256];
        for (int i = 0; i < 256; i++) {
            data[i] = 1;
        }
    }

    public void setBlock(Vector3i pos, int type) {
    }

    public void setFlat() {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                setBlock(new Vector3i(x, 0, z), 1);
            }
        }
    }

    public byte[] dump() {
        byte[] dump = data;
        try {
            dump = ByteStreams.toByteArray(MineiaGo.getInstance().getResourceAsStream("chunk"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dump;

        /* int offset = 0;

        ByteBuffer buf = ByteBuffer.wrap(new byte[1 + (16 * 10241) + (256 * 2) + 256 + 1 + 1]);
        buf.putInt(offset, 16);
        offset += 1;

        for (int i = 0; i < 16; i++) {
            int size = 1 + ((16 * 16 * 16) + ((16 * 16 * 16) / 2) + ((16 * 16 * 16) / 2) + ((16 * 16 * 16) / 2));
            // TODO: block data
            offset += size;
        }

        // TODO: biome id
        buf.put(data, offset, data.length);
        offset += size;

        buf.putInt(offset, 0);
        offset += 1;

        buf.putInt(offset, 0);
        offset += 1;

        byte[] export = new byte[size];
        buf.get(export, 0, export.length);
        return export; */
    }
}
