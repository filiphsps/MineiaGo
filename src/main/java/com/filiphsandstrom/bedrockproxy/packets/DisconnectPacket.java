package com.filiphsandstrom.bedrockproxy.packets;

import lombok.Getter;
import lombok.Setter;
import com.filiphsandstrom.bedrockproxy.PacketRegistry;
import com.filiphsandstrom.bedrockproxy.raknet.RakNetPacket;

@Getter
@Setter
public class DisconnectPacket extends DataPacket {
    private boolean hideDisconnectionScreen;
    private String message;

    public DisconnectPacket() {
        super(PacketRegistry.NetworkType.DISCONNECT_PACKET);
    }

    public DisconnectPacket(RakNetPacket packet) {
        super(packet);
    }

    @Override
    public void encode() {
        writeBoolean(hideDisconnectionScreen);

        if (!hideDisconnectionScreen) {
            writeString(message);
        }
    }
}
