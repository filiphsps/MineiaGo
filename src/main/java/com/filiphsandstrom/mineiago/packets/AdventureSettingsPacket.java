package com.filiphsandstrom.mineiago.packets;

import com.filiphsandstrom.mineiago.PacketRegistry;


public class AdventureSettingsPacket extends DataPacket {
    public AdventureSettingsPacket() {
        super(PacketRegistry.NetworkType.ADVENTURE_SETTINGS_PACKET);
    }

    @Override
    public void encode() {
        writeUnsignedIntLE(0);
        writeUnsignedIntLE(0);
        writeUnsignedIntLE(0);
        writeUnsignedIntLE(0);
        writeUnsignedIntLE(0);
        writeLongLE(0);
    }
}
