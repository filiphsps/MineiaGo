package com.filiphsandstrom.mineiago.world;

import java.util.*;
import java.io.*;
import com.filiphsandstrom.mineiago.MineiaGo;

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
            InputStream in = getClass().getResourceAsStream("/chunk"); 

            byte[] chunk = in.readAllBytes();
            data = chunk;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public byte[] getRaw() {
        return data;
    }
}
