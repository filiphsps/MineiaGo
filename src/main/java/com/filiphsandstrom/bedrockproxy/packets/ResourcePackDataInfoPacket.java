package com.filiphsandstrom.bedrockproxy.packets;

import com.filiphsandstrom.bedrockproxy.PacketRegistry;
import com.whirvis.jraknet.RakNetPacket;


public class ResourcePackDataInfoPacket extends DataPacket {
    public ResourcePackDataInfoPacket() {
        super(PacketRegistry.NetworkType.RESOURCE_PACK_DATA_INFO_PACKET);
    }
    public ResourcePackDataInfoPacket(RakNetPacket packet) {
        super(packet);
    }

    @Override
    public void encode() {
        writeString("");
        writeIntLE(0);
        writeIntLE(0);
        writeLongLE(0);
        writeString("");
        writeBoolean(false);
        writeByte(0);
    }
}
