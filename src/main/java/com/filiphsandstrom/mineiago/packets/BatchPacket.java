package com.filiphsandstrom.mineiago.packets;

import com.filiphsandstrom.mineiago.Compression;
import com.filiphsandstrom.mineiago.PacketRegistry;
import com.whirvis.jraknet.RakNetPacket;

import io.netty.buffer.*;

public class BatchPacket extends DataPacket {
    public BatchPacket() {
        super(PacketRegistry.NetworkType.BATCH_PACKET);
    }

    public BatchPacket(RakNetPacket packet) {
        super(packet);
    }

    @Override
    public void decode() {
        if (buffer().readableBytes() < 2) return;

        try {
            ByteBuf Buffer = Compression.inflate(buffer());

            byte[] buf = new byte[Buffer.readableBytes()];
            Buffer.readBytes(buf);

            setBuffer(buf);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        if (buffer().readableBytes() == 0) {
            throw new RuntimeException("Decoded BatchPacket payload is empty");
        }

        buffer().readerIndex(2);
        while (buffer().readerIndex() < buffer().readableBytes()) {
            PacketRegistry.handlePacket(new RakNetPacket(readBytes()), getPlayer());
        }
    }
}
