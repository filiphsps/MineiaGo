package com.filiphsandstrom.bedrockproxy.packets;

import io.netty.buffer.ByteBuf;
import com.filiphsandstrom.bedrockproxy.BedrockPlayer;
import com.filiphsandstrom.bedrockproxy.PacketRegistry;
import com.whirvis.jraknet.Packet;
import com.whirvis.jraknet.RakNetPacket;

public class DataPacket extends RakNetPacket {
    protected BedrockPlayer player;
    public BedrockPlayer getPlayer() {
        return player;
    }
    public void setPlayer(BedrockPlayer p) {
        player = p;
    }

    protected PacketRegistry.NetworkType networkId;
    public PacketRegistry.NetworkType getNetworkId() {
        return networkId;
    }

    public DataPacket(RakNetPacket packet) {
        super(packet);
        networkId = PacketRegistry.NetworkType.getById(packet.getId());
    }

    public DataPacket(Packet packet, PacketRegistry.NetworkType id) {
        super(packet);
        networkId = id;
    }

    public DataPacket(ByteBuf data) {
        super(data);
        networkId = PacketRegistry.NetworkType.getById(getId());
    }

    public DataPacket(PacketRegistry.NetworkType id) {
        super(id.getId());
        networkId = id;
    }

    public void writeBytes(byte[] bytes) {
        writeUnsignedInt(bytes.length);
        for (byte b : bytes) {
            writeByte(b);
        }
    }

    public byte[] readBytes() {
        int len = readUnsignedShort();
        if (buffer().readerIndex() + len > buffer().readableBytes()) {
            throw new RuntimeException("I can't read " + (buffer().readableBytes() - buffer().readerIndex() + len) + " bytes!");
        }
        byte[] r = new byte[len];
        read(r);
        return r;
    }

    public void skip(int bytes) {
        buffer().readerIndex(buffer().readerIndex() + bytes);
    }

    public void handle() {
        decode();
    }
}
