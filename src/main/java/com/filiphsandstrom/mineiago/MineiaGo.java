package com.filiphsandstrom.mineiago;

import net.md_5.bungee.api.plugin.Plugin;

public final class MineiaGo extends Plugin {
    public static final int PROTOCOL = 361;
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
