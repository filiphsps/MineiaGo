package com.filiphsandstrom.mineiago.packets;

import com.filiphsandstrom.mineiago.PacketRegistry;
import com.whirvis.jraknet.RakNetPacket;

public class PlayStatusPacket extends DataPacket {
    private Status status;
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status s) {
        status = s;
    }

    public PlayStatusPacket(Status status) {
        super(PacketRegistry.NetworkType.PLAY_STATUS_PACKET);
        this.status = status;
    }

    public PlayStatusPacket(RakNetPacket packet) {
        super(packet);
    }

    @Override
    public void encode() {
        writeInt(status.getId());
    }

    public enum Status {
        LOGIN_SUCCESS(0),
        LOGIN_FAILED_CLIENT(1),
        LOGIN_FAILED_SERVER(2),
        PLAYER_SPAWN(3),
        LOGIN_FAILED_INVALID_TENANT(4),
        LOGIN_FAILED_VANILLA_EDU(5),
        LOGIN_FAILED_EDU_VANILLA(6),
        LOGIN_FAILED_SERVER_FULL(7);

        private final int id;
        public final int getId() {
            return id;
        }

        Status(int id) {
            this.id = id;
        }
    }
}
