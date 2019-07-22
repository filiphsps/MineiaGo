package com.filiphsandstrom.mineiago.packets.java;

import com.filiphsandstrom.mineiago.MineiaGoSession;
import com.nukkitx.protocol.bedrock.packet.SetPlayerGameTypePacket;
// import com.nukkitx.protocol.bedrock.packet.SetDifficultyPacket;

public class ServerJoinGamePacket {
    public ServerJoinGamePacket(com.github.steveice10.mc.protocol.packet.ingame.server.ServerJoinGamePacket packet,
            MineiaGoSession session) {

        //SetDifficultyPacket difficulty = new SetDifficultyPacket();
        //difficulty.setDifficulty(packet.get);
        //session.getBedrockSession().sendPacket(difficulty);
        
        SetPlayerGameTypePacket gamemode = new SetPlayerGameTypePacket();
        switch(packet.getGameMode()) {
            case SURVIVAL:
                gamemode.setGamemode(0);
            case CREATIVE:
                gamemode.setGamemode(1);
            case ADVENTURE:
                gamemode.setGamemode(2);
            case SPECTATOR:
                gamemode.setGamemode(3);
        }
        session.getBedrockSession().sendPacket(gamemode);
    }
}
