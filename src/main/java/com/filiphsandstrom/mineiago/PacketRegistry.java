package com.filiphsandstrom.mineiago;

import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import com.nukkitx.protocol.bedrock.packet.*;

import lombok.NonNull;

public class PacketRegistry {
    @NonNull
    public static BedrockPacketHandler handler = new BedrockPacketHandler() {
        @Override
        public boolean handle(AdventureSettingsPacket packet) {
            return false;
        }
    };
}
