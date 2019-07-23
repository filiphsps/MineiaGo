package com.filiphsandstrom.mineiago.utils;

import java.io.File;
import java.nio.file.*;

import lombok.Getter;

import java.io.InputStream;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;


import com.filiphsandstrom.mineiago.MineiaGo;

public class Config {
    @Getter
    private String address;
    @Getter
    private int port;
    @Getter
    private String servername;
    @Getter
    private int loglevel = -1;

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
                in.close();
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
        if(config.getString("address").isEmpty()) config.set("address", "0.0.0.0");
        if(config.getInt("port") <= 0) config.set("port", 19132);
        if(config.getString("server_name").isEmpty()) config.set("server_name", "A MineiaGo Server");
        if(config.getInt("log_level") <= 0) config.set("log_level", -1);

        address = config.getString("address");
        port = config.getInt("port");
        servername = config.getString("server_name");
        loglevel = config.getInt("log_level");
    }

    public void Save () {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
