package com.filiphsandstrom.mineiago;

import java.io.File;
import java.nio.file.*;

import lombok.Getter;

import java.io.InputStream;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class Config {
    @Getter
    private int port;

    @Getter
    public String submotd;

    private Configuration config;
    public Configuration getConfig () {
        return config;
    }
    
    private File configFile;

    public Config () {
        MineiaGo instance = MineiaGo.getInstance();

        // Create plugin folder if it doesn't exist
        if(!instance.getDataFolder().exists())
            instance.getDataFolder().mkdir();
        
        configFile = new File(instance.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            try (InputStream in = instance.getResourceAsStream("config.yml")) {
                Files.copy(in, configFile.toPath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
            Setup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Setup () {
        port = config.getInt("port");
    }
}