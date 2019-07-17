package com.filiphsandstrom.mineiago.world;

// TODO
public class BedrockChunk {
    private byte[] data = {0};
    private int x;
    private int z;

    public BedrockChunk () {
        super();
    }

    public void setFlat() {
        //TODO: create flat chunk
        
        byte[] chunk = {0};
        data = chunk;
    }
    public byte[] getRaw() {
        return data;
    }
}
