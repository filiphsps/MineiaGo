package com.filiphsandstrom.mineiago;

import java.util.*;

import com.filiphsandstrom.mineiago.world.BedrockChunk;
import com.flowpowered.math.vector.Vector2f;
import com.flowpowered.math.vector.Vector3f;
import com.flowpowered.math.vector.Vector3i;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.data.GamePublishSetting;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import com.nukkitx.protocol.bedrock.packet.*;
import com.nukkitx.protocol.bedrock.packet.MovePlayerPacket.Mode;
import com.nukkitx.protocol.bedrock.packet.MovePlayerPacket.TeleportationCause;
import com.nukkitx.protocol.bedrock.packet.PlayStatusPacket.Status;
import com.nukkitx.protocol.bedrock.packet.SetSpawnPositionPacket.Type;

import lombok.NonNull;

public class PacketRegistry {
    public BedrockPlayer player;

    @NonNull
    public BedrockPacketHandler handler = new BedrockPacketHandler() {
        // FIXME: separate between sent and received packets.
        @Override
        public boolean handle(LoginPacket packet) {
            MineiaGo.getInstance().getLogging().Debug(packet.toString());
            player.setChainData(packet.getChainData().toString());

            PlayStatusPacket status = new PlayStatusPacket();
            status.handle(handler);
            status.setStatus(Status.LOGIN_SUCCESS);
            player.getBedrockSession().sendPacket(status);

            StartGamePacket game = new StartGamePacket();
            game.handle(handler);
            game.setUniqueEntityId(1);
            game.setRuntimeEntityId(1);
            game.setPlayerGamemode(1);
            game.setPlayerPosition(new Vector3f(0, 75, 0));
            game.setRotation(new Vector2f(0, 0));
            game.setDefaultSpawn(new Vector3i(0, 75, 0));
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

            SetSpawnPositionPacket spawn = new SetSpawnPositionPacket();
            spawn.handle(handler);
            spawn.setBlockPosition(new Vector3i(0, 75, 0));
            spawn.setSpawnForced(false);
            spawn.setSpawnType(Type.PLAYER_SPAWN);
            player.getBedrockSession().sendPacket(spawn);

            MovePlayerPacket move = new MovePlayerPacket();
            move.handle(handler);
            move.setPosition(new Vector3f(0, 75, 0));
            move.setOnGround(true);
            move.setRotation(new Vector3f(0, 0, 0));
            move.setMode(Mode.NORMAL);
            move.setTeleportationCause(TeleportationCause.UNKNOWN);
            player.getBedrockSession().sendPacket(move);

            SetTimePacket time = new SetTimePacket();
            time.handle(handler);
            time.setTime(0);
            player.getBedrockSession().sendPacket(time);

            RespawnPacket respawn = new RespawnPacket();
            respawn.handle(handler);
            respawn.setPosition(new Vector3f(0, 75, 0));
            player.getBedrockSession().sendPacket(respawn);

            ResourcePacksInfoPacket resource_info = new ResourcePacksInfoPacket();
            resource_info.handle(handler);
            resource_info.setForcedToAccept(false);
            resource_info.setScriptingEnabled(false);
            player.getBedrockSession().sendPacket(resource_info);
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
            MineiaGo.getInstance().getLogging().Debug(packet.toString());

            if (!player.isAuthenticated()) {
                ModalFormRequestPacket form = new ModalFormRequestPacket();
                form.handle(handler);
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
            chunks.handle(handler);
            chunks.setRadius(1);
            player.getBedrockSession().sendPacket(chunks);

            if (!player.isAuthenticated()) {
                BedrockChunk Chunk = new BedrockChunk();
                Chunk.setFlat();

                Collection<BedrockPacket> chunk_packets = new ArrayList<BedrockPacket>();
                for (int x = -1; x <= 1; x++) {
                    for (int y = -1; y <= 1; y++) {
                        LevelChunkPacket chunk = new LevelChunkPacket();
                        chunk.handle(handler);
                        chunk.setData(Chunk.getRaw());
                        chunk.setChunkX(x);
                        chunk.setChunkZ(y);
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
            map_info.handle(handler);
            map_info.setUniqueMapId(0);
            player.getBedrockSession().sendPacket(map_info);

            PlayStatusPacket status = new PlayStatusPacket();
            status.handle(handler);
            status.setStatus(Status.PLAYER_SPAWN);
            player.getBedrockSession().sendPacket(status);
            return true;
        }
    };
}
