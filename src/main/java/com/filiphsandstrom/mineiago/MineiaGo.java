package com.filiphsandstrom.mineiago;

import net.md_5.bungee.api.plugin.Plugin;
import org.bstats.bungeecord.*;

public final class MineiaGo extends Plugin {
    public static final int PROTOCOL = 361;

    public int port;
    public int getPort() {
        return port;
    }
    public void setPort(int p) {
        port = p;
    }

    private static MineiaGo instance;
    public static MineiaGo getInstance() {
        return instance;
    }

    private MetricsLite metrics;
    public MetricsLite getMetrics() {
        return metrics;
    }

    private NetworkManager networkManager;
    public NetworkManager getNetworkManager() {
        return networkManager;
    }

    private Config config;
    public Config getConfig() {
        return config;
    }

    @Override
    public void onEnable() {
        instance = this;

        metrics = new MetricsLite(instance);
        config = new Config();
        networkManager = new NetworkManager();
    }

    @Override
    public void onDisable() {
        networkManager.Stop();
    }
}
