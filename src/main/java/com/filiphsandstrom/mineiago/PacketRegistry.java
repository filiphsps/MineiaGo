package com.filiphsandstrom.mineiago;

import java.util.Arrays;

import com.filiphsandstrom.mineiago.world.BedrockChunk;
import com.flowpowered.math.vector.Vector2f;
import com.flowpowered.math.vector.Vector3f;
import com.flowpowered.math.vector.Vector3i;
import com.nukkitx.protocol.bedrock.data.GamePublishSetting;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import com.nukkitx.protocol.bedrock.packet.*;
import com.nukkitx.protocol.bedrock.packet.PlayStatusPacket.Status;

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

            StartGamePacket game = new StartGamePacket();
            game.handle(handler);

            game.setUniqueEntityId(-1);
            game.setRuntimeEntityId(-1);
            game.setPlayerGamemode(0);
            game.setPlayerPosition(new Vector3f(0, 50, 0));
            game.setRotation(new Vector2f(0, 0));
            game.setDefaultSpawn(new Vector3i(0, 50, 0));
            game.setMultiplayerGame(true);
            game.setXblBroadcastMode(GamePublishSetting.PUBLIC);
            game.setPlatformBroadcastMode(GamePublishSetting.PUBLIC);
            game.setLevelId("MinieaGo");
            game.setWorldName("MinieaGo");
            game.setPremiumWorldTemplateId("");
            game.setMultiplayerCorrelationId("MineiaGo");
            game.setGeneratorId(1);
            player.getBedrockSession().sendPacket(game);
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
        public boolean handle(SetTimePacket packet) {
            MineiaGo.getInstance().getLogger().info("BedrockPacketHandler->SetTimePacket");
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
            chunks.setRadius(packet.getRadius());
            player.getBedrockSession().sendPacket(chunks);
            return true;
        }
        @Override
        public boolean handle(ChunkRadiusUpdatedPacket packet) {
            MineiaGo.getInstance().getLogger().info("BedrockPacketHandler->ChunkRadiusUpdatedPacket");

            BedrockChunk Chunk = new BedrockChunk();
            byte[] data = Chunk.getRaw();
            Arrays.fill(data, (byte)0);

            LevelChunkPacket chunk = new LevelChunkPacket();
            chunk.handle(handler);
            chunk.setChunkX(0);
            chunk.setChunkZ(0);
            chunk.setData(data);
            return true;
        }
        @Override
        public boolean handle(LevelChunkPacket packet) {
            MineiaGo.getInstance().getLogger().info("BedrockPacketHandler->LevelChunkPacket");
            return true;
        }

        @Override
        public boolean handle(ResourcePackDataInfoPacket packet) {
            MineiaGo.getInstance().getLogger().info("BedrockPacketHandler->ResourcePackDataInfoPacket");
            return true;
        }
        @Override
        public boolean handle(ResourcePackChunkRequestPacket packet) {
            MineiaGo.getInstance().getLogger().info("BedrockPacketHandler->ResourcePackChunkRequestPacket");
            return true;
        }
        @Override
        public boolean handle(ResourcePackClientResponsePacket packet) {
            MineiaGo.getInstance().getLogger().info("BedrockPacketHandler->ResourcePackClientResponsePacket");

            PlayStatusPacket status = new PlayStatusPacket();
            status.handle(handler);
            status.setStatus(Status.PLAYER_SPAWN);
            player.getBedrockSession().sendPacket(status);
            return true;
        }
    };
}
