package com.filiphsandstrom.mineiago;

import java.io.*;
import java.util.*;

import com.filiphsandstrom.mineiago.world.*;
import com.flowpowered.math.vector.Vector2f;
import com.flowpowered.math.vector.Vector3f;
import com.flowpowered.math.vector.Vector3i;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.data.GamePublishSetting;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import com.nukkitx.protocol.bedrock.packet.*;
import com.nukkitx.protocol.bedrock.packet.PlayStatusPacket.Status;
import com.nukkitx.protocol.bedrock.packet.SetSpawnPositionPacket.Type;

import lombok.NonNull;

public class PacketRegistry {
    public BedrockPlayer player;

    @NonNull
    public BedrockPacketHandler handler = new BedrockPacketHandler() {
        @Override
        public boolean handle(LoginPacket packet) {
            MineiaGo.getInstance().getLogger().info("BedrockPacketHandler->LoginPacket");

            // TODO: handle chainData
            MineiaGo.getInstance().getLogger().info(packet.toString());

            PlayStatusPacket status = new PlayStatusPacket();
            status.handle(handler);
            status.setStatus(Status.LOGIN_SUCCESS);
            player.getBedrockSession().sendPacket(status);

            /*
             * MovePlayerPacket move = new MovePlayerPacket(); move.handle(handler);
             * move.setPosition(new Vector3f(0, 75, 0));
             * player.getBedrockSession().sendPacket(move);
             */

            StartGamePacket game = new StartGamePacket();
            game.handle(handler);
            game.setUniqueEntityId(-1);
            game.setRuntimeEntityId(-1);
            game.setPlayerGamemode(1);
            game.setPlayerPosition(new Vector3f(0, 75, 0));
            game.setRotation(new Vector2f(0, 0));
            game.setDefaultSpawn(new Vector3i(0, 75, 0));
            game.setMultiplayerGame(false);
            game.setXblBroadcastMode(GamePublishSetting.PUBLIC);
            game.setPlatformBroadcastMode(GamePublishSetting.PUBLIC);
            game.setLevelId("MinieaGo");
            game.setWorldName("MinieaGo");
            game.setPremiumWorldTemplateId("");
            game.setMultiplayerCorrelationId("MineiaGo");
            game.setGeneratorId(2);
            player.getBedrockSession().sendPacket(game);

            SetSpawnPositionPacket spawn = new SetSpawnPositionPacket();
            spawn.handle(handler);
            spawn.setBlockPosition(new Vector3i(0, 75, 0));
            spawn.setSpawnForced(true);
            spawn.setSpawnType(Type.WORLD_SPAWN);
            player.getBedrockSession().sendPacket(spawn);

            SetTimePacket time = new SetTimePacket();
            time.handle(handler);
            time.setTime(0);
            player.getBedrockSession().sendPacket(time);

            RespawnPacket respawn = new RespawnPacket();
            respawn.handle(handler);
            respawn.setPosition(new Vector3f(0, 75, 0));
            player.getBedrockSession().sendPacket(respawn);
            return true;
        }

        @Override
        public boolean handle(DisconnectPacket packet) {
            MineiaGo.getInstance().getLogger().info("BedrockPacketHandler->DisconnectPacket");
            return true;
        }

        @Override
        public boolean handle(PlayStatusPacket packet) {
            MineiaGo.getInstance().getLogger().info("BedrockPacketHandler->PlayStatusPacket");
            return true;
        }

        @Override
        public boolean handle(TextPacket packet) {
            MineiaGo.getInstance().getLogger().info("BedrockPacketHandler->TextPacket");
            return true;
        }

        @Override
        public boolean handle(StartGamePacket packet) {
            MineiaGo.getInstance().getLogger().info("BedrockPacketHandler->StartGamePacket");

            ResourcePacksInfoPacket resource_info = new ResourcePacksInfoPacket();
            resource_info.setForcedToAccept(false);
            resource_info.setScriptingEnabled(false);
            player.getBedrockSession().sendPacket(resource_info);

            ResourcePackClientResponsePacket resource = new ResourcePackClientResponsePacket();
            resource.setStatus(com.nukkitx.protocol.bedrock.packet.ResourcePackClientResponsePacket.Status.SEND_PACKS);
            player.getBedrockSession().sendPacket(resource);
            return true;
        }

        @Override
        public boolean handle(ServerSettingsRequestPacket packet) {
            MineiaGo.getInstance().getLogger()
                    .info("BedrockPacketHandler->ServerSettingsRequestPacket " + packet.toString());
            return true;
        }

        @Override
        public boolean handle(ServerSettingsResponsePacket packet) {
            MineiaGo.getInstance().getLogger().info("BedrockPacketHandler->ServerSettingsResponsePacket");
            return true;
        }

        @Override
        public boolean handle(SetTimePacket packet) {
            MineiaGo.getInstance().getLogger().info("BedrockPacketHandler->SetTimePacket");
            return true;
        }

        @Override
        public boolean handle(RespawnPacket packet) {
            MineiaGo.getInstance().getLogger().info("BedrockPacketHandler->RespawnPacket");
            return true;
        }

        @Override
        public boolean handle(MovePlayerPacket packet) {
            // MineiaGo.getInstance().getLogger().info("BedrockPacketHandler->MovePlayerPacket");
            /* MovePlayerPacket move = new MovePlayerPacket();
            move.setPosition(new Vector3f(0.0, 75.0, 0.0));
            move.setOnGround(true);
            player.getBedrockSession().sendPacket(move); */
            return true;
        }

        @Override
        public boolean handle(SetSpawnPositionPacket packet) {
            MineiaGo.getInstance().getLogger().info("BedrockPacketHandler->SetSpawnPositionPacket");
            return true;
        }

        @Override
        public boolean handle(AdventureSettingsPacket packet) {
            MineiaGo.getInstance().getLogger().info("BedrockPacketHandler->AdventureSettingsPacket");
            return true;
        }

        @Override
        public boolean handle(RequestChunkRadiusPacket packet) {
            MineiaGo.getInstance().getLogger().info("BedrockPacketHandler->RequestChunkRadiusPacket");

            ChunkRadiusUpdatedPacket chunks = new ChunkRadiusUpdatedPacket();
            chunks.handle(handler);
            chunks.setRadius(22);
            player.getBedrockSession().sendPacket(chunks);

            if(!player.isAuthenticated()) {
                BedrockChunk Chunk = new BedrockChunk();
                try {
                    Chunk.setRandom();
                } catch (Exception e) {
                    e.printStackTrace();
                }

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

                PlayStatusPacket status = new PlayStatusPacket();
                status.handle(handler);
                status.setStatus(Status.PLAYER_SPAWN);
                player.getBedrockSession().sendPacket(status);
            }
            return true;
        }

        @Override
        public boolean handle(ChunkRadiusUpdatedPacket packet) {
            MineiaGo.getInstance().getLogger().info("BedrockPacketHandler->ChunkRadiusUpdatedPacket");
            return true;
        }

        @Override
        public boolean handle(LevelChunkPacket packet) {
            // MineiaGo.getInstance().getLogger().info("BedrockPacketHandler->LevelChunkPacket");
            return true;
        }

        @Override
        public boolean handle(ClientCacheStatusPacket packet) {
            MineiaGo.getInstance().getLogger().info("BedrockPacketHandler->ClientCacheStatusPacket");
            return true;
        }

        @Override
        public boolean handle(MapInfoRequestPacket packet) {
            MineiaGo.getInstance().getLogger().info("BedrockPacketHandler->MapInfoRequestPacket " + packet.toString());
            
            if(!player.isAuthenticated()) {
                ServerSettingsResponsePacket form = new ServerSettingsResponsePacket();
                form.handle(handler);
                form.setFormId(1);
                form.setFormData("{\"type\":\"custom_form\", \"title\":\"Login to your mojang account!\", \"content\": [{\"type\":\"input\", \"text\":\"Email\", \"placeholder\":\"steve@mojang.com\", \"default\":\"\"}, {\"type\":\"input\", \"text\":\"Password\", \"placeholder\":\"password\", \"default\":\"\"}]}");
                player.getBedrockSession().sendPacket(form);
            }
            return true;
        }

        @Override
        public boolean handle(ResourcePackDataInfoPacket packet) {
            MineiaGo.getInstance().getLogger().info("BedrockPacketHandler->ResourcePackDataInfoPacket");
            return true;
        }

        @Override
        public boolean handle(ResourcePackStackPacket packet) {
            MineiaGo.getInstance().getLogger().info("BedrockPacketHandler->ResourcePackStackPacket");
            return true;
        }

        @Override
        public boolean handle(ResourcePackChunkRequestPacket packet) {
            MineiaGo.getInstance().getLogger().info("BedrockPacketHandler->ResourcePackChunkRequestPacket");
            return false;
        }

        @Override
        public boolean handle(ResourcePackClientResponsePacket packet) {
            MineiaGo.getInstance().getLogger()
                    .info("BedrockPacketHandler->ResourcePackClientResponsePacket " + packet.toString());

            if (packet.getStatus() == ResourcePackClientResponsePacket.Status.HAVE_ALL_PACKS) {
                MapInfoRequestPacket map_info = new MapInfoRequestPacket();
                map_info.handle(handler);
                map_info.setUniqueMapId(0);
                player.getBedrockSession().sendPacket(map_info);
            }
            return true;
        }
    };
}
