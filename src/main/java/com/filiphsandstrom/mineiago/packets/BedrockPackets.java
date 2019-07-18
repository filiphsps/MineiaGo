package com.filiphsandstrom.mineiago.packets;

import com.filiphsandstrom.mineiago.*;
import java.util.*;

import com.filiphsandstrom.mineiago.world.BedrockChunk;
import com.flowpowered.math.vector.Vector2f;
import com.flowpowered.math.vector.Vector3f;
import com.flowpowered.math.vector.Vector3i;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.data.GamePublishSetting;
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
    public BedrockPlayer player;

    @NonNull
    public BatchHandler batchHandler = new BatchHandler(){
        @Override
        public void handle(BedrockSession session, ByteBuf compressed, Collection<BedrockPacket> packets) {
            for (BedrockPacket packet : packets) {
                BedrockPacketHandler handler = session.getPacketHandler();
                packet.handle(packetHandler);
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

            StartGamePacket game = new StartGamePacket();
            game.handle(packetHandler);
            game.setUniqueEntityId(1);
            game.setRuntimeEntityId(1);
            game.setPlayerGamemode(1);
            game.setPlayerPosition(new Vector3f(0, 5, 0));
            game.setRotation(new Vector2f(0, 0));
            game.setDefaultSpawn(new Vector3i(0, 5, 0));
            game.setMultiplayerGame(true);
            game.setXblBroadcastMode(GamePublishSetting.PUBLIC);
            game.setPlatformBroadcastMode(GamePublishSetting.PUBLIC);
            game.setLevelId(MineiaGo.getInstance().getConfig().getServername());
            game.setWorldName(MineiaGo.getInstance().getConfig().getServername());
            game.setPremiumWorldTemplateId("");
            game.setMultiplayerCorrelationId("");
            game.setGeneratorId(2);
            game.setCommandsEnabled(true);
            player.getBedrockSession().sendPacket(game);
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
            if (packet.getMessage().toString().isEmpty())
                return true;
            return true;
        }

        @Override
        public boolean handle(StartGamePacket packet) {
            MineiaGo.getInstance().getLogging().Debug(packet.toString());

            SetTimePacket time = new SetTimePacket();
            time.handle(packetHandler);
            time.setTime(0);
            player.getBedrockSession().sendPacket(time);

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

            if (!player.isAuthenticated()) {
                String data[] = packet.getFormData().replace("[null,", "").replace("\"]", "").replaceAll("\"", "").split(",");
                player.setUsername(data[0]);
                player.setPassword(data[1]);

                player.createJavaClient();
            }
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
            if (packet.getPosition().toString().isEmpty())
                return true;

            MineiaGo.getInstance().getLogging().Debug(packet.toString());

            if (!player.isAuthenticated()) {
                ModalFormRequestPacket form = new ModalFormRequestPacket();
                form.handle(packetHandler);
                form.setFormId(1);
                form.setFormData(
                        "{\"type\":\"custom_form\", \"title\":\"Login to your Mojang account!\", \"content\": [{\"type\":\"label\", \"text\":\"Please login to your mojang account to access this sever!\"}, {\"type\":\"input\", \"text\":\"Email\", \"placeholder\":\"steve@mojang.com\", \"default\":\"\"}, {\"type\":\"input\", \"text\":\"Password\", \"placeholder\":\"password\", \"default\":\"\"}]}");
                player.getBedrockSession().sendPacket(form);
            }
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

            if (!player.isAuthenticated()) {
                Collection<BedrockPacket> chunk_packets = new ArrayList<BedrockPacket>();
                for (int x = -1; x <= 1; x++) {
                    for (int z = -1; z <= 1; z++) {
                        BedrockChunk Chunk = new BedrockChunk(x, z);
                        Chunk.setFlat();

                        LevelChunkPacket chunk = new LevelChunkPacket();
                        chunk.handle(packetHandler);
                        chunk.setData(Chunk.dump());
                        chunk.setChunkX(x);
                        chunk.setChunkZ(z);
                        chunk.setSubChunksLength(0);
                        chunk.setCachingEnabled(false);
                        chunk_packets.add(chunk);
                    }
                }
                player.getBedrockSession().sendWrapped(chunk_packets, false);
            }
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

            //TODO: only spawn after sending chunks
            PlayStatusPacket status = new PlayStatusPacket();
            status.handle(packetHandler);
            status.setStatus(Status.PLAYER_SPAWN);
            player.getBedrockSession().sendPacket(status);
            return true;
        }
    };
}
