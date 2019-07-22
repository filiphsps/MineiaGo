package com.filiphsandstrom.mineiago.packets;

import com.filiphsandstrom.mineiago.*;
import java.util.*;

import com.flowpowered.math.vector.Vector3f;
import com.flowpowered.math.vector.Vector3i;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.handler.BatchHandler;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import com.nukkitx.protocol.bedrock.packet.*;
import com.nukkitx.protocol.bedrock.packet.MovePlayerPacket.Mode;
import com.nukkitx.protocol.bedrock.packet.MovePlayerPacket.TeleportationCause;
import com.nukkitx.protocol.bedrock.packet.PlayStatusPacket.Status;
import com.nukkitx.protocol.bedrock.packet.SetSpawnPositionPacket.Type;

import io.netty.buffer.ByteBuf;
import lombok.NonNull;

public class BedrockPackets {
    public MineiaGoSession player;

    @NonNull
    public BatchHandler batchHandler = new BatchHandler() {
        @Override
        public void handle(BedrockSession session, ByteBuf compressed, Collection<BedrockPacket> packets) {
            for (BedrockPacket packet : packets) {
                BedrockPacketHandler handler = session.getPacketHandler();
                packet.handle(handler);
            }
        }
    };

    @NonNull
    public BedrockPacketHandler packetHandler = new BedrockPacketHandler() {
        // FIXME: separate between sent and received packets.
        @Override
        public boolean handle(LoginPacket packet) {
            MineiaGo.getInstance().getLogging().Debug(packet.toString());
            player.setChainData(packet.getChainData().toString());

            PlayStatusPacket status = new PlayStatusPacket();
            status.handle(packetHandler);
            status.setStatus(Status.LOGIN_SUCCESS);
            player.getBedrockSession().sendPacket(status);

            ResourcePacksInfoPacket resource_info = new ResourcePacksInfoPacket();
            resource_info.handle(packetHandler);
            resource_info.setForcedToAccept(false);
            resource_info.setScriptingEnabled(false);
            player.getBedrockSession().sendPacket(resource_info);
            return true;
        }

        @Override
        public boolean handle(DisconnectPacket packet) {
            MineiaGo.getInstance().getLogging().Debug(packet.toString());
            return true;
        }

        @Override
        public boolean handle(PlayStatusPacket packet) {
            MineiaGo.getInstance().getLogging().Debug(packet.toString());
            return true;
        }

        @Override
        public boolean handle(TextPacket packet) {
            MineiaGo.getInstance().getLogging().Debug(packet.toString());
            return true;
        }

        @Override
        public boolean handle(StartGamePacket packet) {
            MineiaGo.getInstance().getLogging().Debug(packet.toString());

            SetSpawnPositionPacket spawn = new SetSpawnPositionPacket();
            spawn.handle(packetHandler);
            spawn.setBlockPosition(new Vector3i(0, 5, 0));
            spawn.setSpawnForced(false);
            spawn.setSpawnType(Type.PLAYER_SPAWN);
            player.getBedrockSession().sendPacket(spawn);

            MovePlayerPacket move = new MovePlayerPacket();
            move.handle(packetHandler);
            move.setPosition(new Vector3f(0, 5, 0));
            move.setOnGround(true);
            move.setRotation(new Vector3f(0, 0, 0));
            move.setMode(Mode.NORMAL);
            move.setTeleportationCause(TeleportationCause.UNKNOWN);
            player.getBedrockSession().sendPacket(move);

            RespawnPacket respawn = new RespawnPacket();
            respawn.handle(packetHandler);
            respawn.setPosition(new Vector3f(0, 5, 0));
            player.getBedrockSession().sendPacket(respawn);
            return true;
        }

        @Override
        public boolean handle(ModalFormRequestPacket packet) {
            MineiaGo.getInstance().getLogging().Debug(packet.toString());
            return true;
        }

        @Override
        public boolean handle(ModalFormResponsePacket packet) {
            MineiaGo.getInstance().getLogging().Debug(packet.toString());
            return true;
        }

        @Override
        public boolean handle(SetTimePacket packet) {
            MineiaGo.getInstance().getLogging().Debug(packet.toString());
            return true;
        }

        @Override
        public boolean handle(RespawnPacket packet) {
            MineiaGo.getInstance().getLogging().Debug(packet.toString());
            return true;
        }

        @Override
        public boolean handle(MovePlayerPacket packet) {
            MineiaGo.getInstance().getLogging().Debug(packet.toString());
            return true;
        }

        @Override
        public boolean handle(SetSpawnPositionPacket packet) {
            MineiaGo.getInstance().getLogging().Debug(packet.toString());
            return true;
        }

        @Override
        public boolean handle(AdventureSettingsPacket packet) {
            MineiaGo.getInstance().getLogging().Debug(packet.toString());
            return true;
        }

        @Override
        public boolean handle(RequestChunkRadiusPacket packet) {
            MineiaGo.getInstance().getLogging().Debug(packet.toString());

            ChunkRadiusUpdatedPacket chunks = new ChunkRadiusUpdatedPacket();
            chunks.handle(packetHandler);
            chunks.setRadius(packet.getRadius());
            player.getBedrockSession().sendPacket(chunks);
            return true;
        }

        @Override
        public boolean handle(ChunkRadiusUpdatedPacket packet) {
            MineiaGo.getInstance().getLogging().Debug(packet.toString());
            return true;
        }

        @Override
        public boolean handle(LevelChunkPacket packet) {
            MineiaGo.getInstance().getLogging().Debug(packet.toString());
            return true;
        }

        @Override
        public boolean handle(ClientCacheStatusPacket packet) {
            MineiaGo.getInstance().getLogging().Debug(packet.toString());
            return true;
        }

        @Override
        public boolean handle(MapInfoRequestPacket packet) {
            MineiaGo.getInstance().getLogging().Debug(packet.toString());
            return true;
        }

        @Override
        public boolean handle(ResourcePackDataInfoPacket packet) {
            MineiaGo.getInstance().getLogging().Debug(packet.toString());
            return true;
        }

        @Override
        public boolean handle(ResourcePackStackPacket packet) {
            MineiaGo.getInstance().getLogging().Debug(packet.toString());
            return true;
        }

        @Override
        public boolean handle(ResourcePackChunkRequestPacket packet) {
            MineiaGo.getInstance().getLogging().Debug(packet.toString());
            return true;
        }

        @Override
        public boolean handle(ResourcePackClientResponsePacket packet) {
            MineiaGo.getInstance().getLogging().Debug(packet.toString());

            MapInfoRequestPacket map_info = new MapInfoRequestPacket();
            map_info.handle(packetHandler);
            map_info.setUniqueMapId(0);
            player.getBedrockSession().sendPacket(map_info);

            SetCommandsEnabledPacket commands_on = new SetCommandsEnabledPacket();
            commands_on.handle(packetHandler);
            commands_on.setCommandsEnabled(true);
            player.getBedrockSession().sendPacket(commands_on);
            return true;
        }
    };
}
