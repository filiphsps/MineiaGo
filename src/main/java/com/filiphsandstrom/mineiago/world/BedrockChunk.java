package com.filiphsandstrom.mineiago.world;

// TODO
public class BedrockChunk {
    private byte[] data = new byte[16*16*256];
    private int x;
    private int z;

    public BedrockChunk () {
        super();

        //TODO: create flat chunk
    }

    public byte[] getRaw() {
        return data;
    }
}
