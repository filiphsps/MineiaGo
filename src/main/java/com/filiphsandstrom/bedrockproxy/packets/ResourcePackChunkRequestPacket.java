package com.filiphsandstrom.bedrockproxy.packets;

import com.filiphsandstrom.bedrockproxy.NetworkManager;
import com.whirvis.jraknet.RakNetPacket;

public class ResourcePackChunkRequestPacket extends DataPacket {
    public ResourcePackChunkRequestPacket(RakNetPacket packet) {
        super(packet);
    }

    @Override
    public void decode() {
        // Doesn't really matter, we're currently not using the resource pack feature.
        NetworkManager.sendPacket(player, new ResourcePackDataInfoPacket());
    }
}
