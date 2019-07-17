package com.filiphsandstrom.mineiago;

import lombok.Getter;
import lombok.Setter;

public class BedrockPlayerInfo {
    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String uuid;

    @Getter
    @Setter
    private String xuid;

    public BedrockPlayerInfo() {}
    public BedrockPlayerInfo(String username, String uuid, String xuid) {
        this.username = username;
        this.uuid = uuid;
        this.xuid = xuid;
    }
}
