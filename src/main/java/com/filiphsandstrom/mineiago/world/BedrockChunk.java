package com.filiphsandstrom.mineiago.world;

import java.io.*;

// TODO
public class BedrockChunk {
    private byte[] data;
    private int x;
    private int z;

    public BedrockChunk () {
        super();

        //TODO: create flat chunk
    }

    public void setRandom() throws FileNotFoundException {
        try {
            byte[] chunk = {};
            data = chunk;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public byte[] getRaw() {
        return data;
    }
}
