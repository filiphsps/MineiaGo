package com.filiphsandstrom.bedrockproxy.packets;

import com.filiphsandstrom.bedrockproxy.PacketRegistry;


public class SetTimePacket extends DataPacket {
    public SetTimePacket() {
        super(PacketRegistry.NetworkType.SET_TIME_PACKET);
    }

    @Override
    public void encode() {
        writeIntLE(0);
    }
}
