package com.filiphsandstrom.bedrockproxy;

import com.filiphsandstrom.bedrockproxy.packets.*;
import com.whirvis.jraknet.RakNetPacket;

public class PacketRegistry {
    @SuppressWarnings("unchecked")
    public static void handlePacket(RakNetPacket packet, BedrockPlayer player) {
        try {
            short id = packet.getId();
            if (id <= 0) id = 1;

            NetworkType type = NetworkType.getById(id);

            if (type != NetworkType.UNKOWN && type.getHandle() != null) {
                DataPacket inst = (DataPacket) type.getHandle().getConstructor(RakNetPacket.class).newInstance(packet);
                inst.setPlayer(player);
                inst.handle();
            } else {
                BedrockProxy.getInstance().getLogger().warning(
                        String.format("Unknown packet 0x%s from %s",
                                Integer.toHexString(packet.getId()).toUpperCase(),
                                player.getSession().getAddress())
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
            player.disconnect(e.getMessage());
        }
    }

    public enum NetworkType {
        UNKOWN(-1),
        LOGIN_PACKET(0x01, LoginPacket.class),
        PLAY_STATUS_PACKET(0x02, PlayStatusPacket.class),
        SERVER_TO_CLIENT_HANDSHAKE_PACKET(0x03, ServerHandshakePacket.class),
        CLIENT_TO_SERVER_HANDSHAKE_PACKET(0x04),
        DISCONNECT_PACKET(0x05, DisconnectPacket.class),

        START_GAME_PACKET(0x0b, StartGamePacket.class),
        ADD_PLAYER_PACKET(0x0c),
        
        BATCH_PACKET(0xFE, BatchPacket.class);

        private final short id;
        public final short getId() {
            return id;
        }

        private Class handle;
        public Class getHandle() {
            return handle;
        }

        NetworkType(int id) {
            this.id = (short) id;
        }

        NetworkType(int id, Class handle) {
            this.id = (short) id;
            this.handle = handle;
        }

        public static NetworkType getById(short id) {
            for (NetworkType e : values()) {
                if (e.id == id) {
                    return e;
                }
            }
            return UNKOWN;
        }
    }
}
