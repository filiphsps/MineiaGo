package com.filiphsandstrom.mineiago;

import com.nukkitx.protocol.bedrock.v361.Bedrock_v361;

import net.md_5.bungee.api.plugin.Plugin;

public final class MineiaGo extends Plugin {
    public static final int PROTOCOL = Bedrock_v361.V361_CODEC.getProtocolVersion();
    public static final int PORT = 19132;

    private static MineiaGo instance;
    public static MineiaGo getInstance() {
        return instance;
    }

    private NetworkManager networkManager;
    public NetworkManager getNetworkManager() {
        return networkManager;
    }

    @Override
    public void onEnable() {
        instance = this;
        networkManager = new NetworkManager();
    }
}
