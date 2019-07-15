package com.filiphsandstrom.mineiago.packets;

import com.filiphsandstrom.mineiago.MineiaGo;
import com.filiphsandstrom.mineiago.NetworkManager;
import com.filiphsandstrom.mineiago.PacketRegistry;
import com.whirvis.jraknet.RakNetPacket;

import net.md_5.bungee.api.ProxyServer;

public class StartGamePacket extends DataPacket {
    public StartGamePacket() {
        super(PacketRegistry.NetworkType.START_GAME_PACKET);
    }

    @Override
    public void encode() {
        writeLongLE(0); // Self Entity ID
        writeUnsignedLongLE(0); // Runtime Entity ID

        writeIntLE(1); // Player Gamemode

        writeFloat(0); // Spawn X
        writeFloat(75); // Spawn Y
        writeFloat(0); // Spawn Z

        writeFloat(0); // Pitch
        writeFloat(0); // Yaw

        writeIntLE(25565); // Seed
        writeIntLE(0); // Dimension
        writeIntLE(0); // Generator
        writeIntLE(1); // Server Gamemode
        writeIntLE(1); // Difficulty

        writeFloat(0); // Player X
        writeFloat(75); // Player Y
        writeFloat(0); // Player Z

        writeBoolean(false); // Achivments Disabled

        writeIntLE(0); // Time

        writeBoolean(false); // EDU Mode
        writeBoolean(false); // EDU Features

        writeFloat(0); // Rain Level
        writeFloat(0); // Lightning Level

        writeBoolean(false); // Platform-locked Content
        writeBoolean(true); // Commands Enabled
        writeBoolean(false); // Bonus-chest
        writeBoolean(true); // Map-enabled

        writeIntLE(0); // Permission Level

        writeIntLE(20); // Server Chunk Tick Range

        writeBoolean(false); // Platform-locked Behavior Pack
        writeBoolean(false); // Platform-locked Resource Pack
        writeBoolean(false); // Platform-locked World Template

        writeBoolean(false); // MSA-Gamertags only
        writeBoolean(false); // Is from World Template
        writeBoolean(true); // Is World Template locked

        writeString("mineiago"); // Level ID
        writeString("MineiaGo"); // Level ID
        writeString(null); // Level ID

        writeBoolean(false); // Is trial

        writeLong(0); // Current tick
        writeIntLE(0); // Enchantment Seed

        writeByte(0); // UNK1

        writeString("mineiago"); // Multiplayer Correlation ID

        writeByte(0); // UNK2
    }
}
