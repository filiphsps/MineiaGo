package com.filiphsandstrom.mineiago;

import net.md_5.bungee.api.ServerPing.PlayerInfo;

public class BedrockPlayerInfo extends PlayerInfo  {
    public BedrockPlayerInfo(String username, String uuid) {
        super(username, uuid);
    }
}
