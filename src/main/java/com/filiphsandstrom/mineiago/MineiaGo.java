package com.filiphsandstrom.mineiago;

import net.md_5.bungee.api.plugin.Plugin;

import com.filiphsandstrom.mineiago.utils.Config;
import com.filiphsandstrom.mineiago.utils.Logging;

import org.bstats.bungeecord.*;

import lombok.Getter;

public final class MineiaGo extends Plugin {
    public static final int PROTOCOL = 361;

    @Getter
    private static MineiaGo instance;
    @Getter
    private MetricsLite metrics;
    @Getter
    private Logging logging;
    @Getter
    private NetworkManager networkManager;
    @Getter
    private Config config;

    @Override
    public void onEnable() {
        instance = this;

        metrics = new MetricsLite(instance);
        logging = new Logging();
        config = new Config();
        networkManager = new NetworkManager();
    }

    @Override
    public void onDisable() {
        networkManager.Stop();
        config.Save();
    }
}
