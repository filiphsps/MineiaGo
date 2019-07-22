package com.filiphsandstrom.mineiago.packets.java;

import com.filiphsandstrom.mineiago.MineiaGo;
import com.filiphsandstrom.mineiago.MineiaGoSession;
import com.flowpowered.math.vector.Vector2f;
import com.flowpowered.math.vector.Vector3f;
import com.flowpowered.math.vector.Vector3i;
import com.nukkitx.protocol.bedrock.data.GamePublishSetting;
import com.nukkitx.protocol.bedrock.packet.PlayStatusPacket;
import com.nukkitx.protocol.bedrock.packet.StartGamePacket;
import com.nukkitx.protocol.bedrock.packet.PlayStatusPacket.Status;

public class ServerJoinGamePacket {
    public ServerJoinGamePacket(com.github.steveice10.mc.protocol.packet.ingame.server.ServerJoinGamePacket packet,
            MineiaGoSession session) {

        // SetDifficultyPacket difficulty = new SetDifficultyPacket();
        // difficulty.setDifficulty(packet.get);
        // session.getBedrockSession().sendPacket(difficulty);

        StartGamePacket game = new StartGamePacket();
        game.handle(session.getBedrockSession().getPacketHandler());
        game.setUniqueEntityId(packet.getEntityId());
        game.setRuntimeEntityId(packet.getEntityId());
        game.setPlayerPosition(new Vector3f(0, 0, 0));
        game.setRotation(new Vector2f(0, 0));
        game.setDefaultSpawn(new Vector3i(0, 0, 0));
        game.setMultiplayerGame(true);
        game.setXblBroadcastMode(GamePublishSetting.PUBLIC);
        game.setPlatformBroadcastMode(GamePublishSetting.PUBLIC);
        game.setLevelId(MineiaGo.getInstance().getConfig().getServername());
        game.setWorldName(MineiaGo.getInstance().getConfig().getServername());
        game.setPremiumWorldTemplateId("");
        game.setMultiplayerCorrelationId("");
        game.setGeneratorId(2);
        game.setCommandsEnabled(true);

        switch (packet.getGameMode()) {
        case SURVIVAL:
            game.setPlayerGamemode(0);
            break;
        case CREATIVE:
            game.setPlayerGamemode(1);
            break;
        case ADVENTURE:
            game.setPlayerGamemode(2);
            break;
        case SPECTATOR:
            game.setPlayerGamemode(3);
            break;
        }
        session.getBedrockSession().sendPacket(game);

        PlayStatusPacket status = new PlayStatusPacket();
        status.handle(session.getBedrockSession().getPacketHandler());
        status.setStatus(Status.PLAYER_SPAWN);
        session.getBedrockSession().sendPacket(status);
    }
}
