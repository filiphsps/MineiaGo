package com.filiphsandstrom.bedrockproxy;

import lombok.Data;
import com.filiphsandstrom.bedrockproxy.packets.DisconnectPacket;
import com.filiphsandstrom.bedrockproxy.raknet.session.RakNetClientSession;

import java.util.HashMap;

public @Data
class BedrockPlayer {
    private static HashMap<Long, BedrockPlayer> players = new HashMap<>();
    public static HashMap<Long, BedrockPlayer> getPlayers() {
        return players;
    }

    private int protocolVersion;
    public int getProtocolVersion() {
        return protocolVersion;
    }
    public void setProtocolVersion(int p) {
        protocolVersion = p;
    }

    private short gameEdition;
    public short getGameEdition() {
        return gameEdition;
    }
    public void setGameEdition(short g) {
        gameEdition = g;
    }

    private String chainData;
    public String getChainData() {
        return chainData;
    }
    public void setChainData(String d) {
        chainData = d;
    }

    private String skinData;

    private byte[] serverKey;
    public byte[] getServerKey() {
        return serverKey;
    }

    private RakNetClientSession session;
    public RakNetClientSession getSession() {
        return session;
    }

    private boolean loggedIn;
    public boolean isLoggedIn() {
        return loggedIn;
    }
    public void setLoggedIn(boolean l) {
        loggedIn = l;
    }

    public static BedrockPlayer getPlayer(long id) {
        if (players.containsKey(id)) {
            return players.get(id);
        } else {
            BedrockPlayer p = new BedrockPlayer();
            players.put(id, p);
            return p;
        }
    }

    public static BedrockPlayer getPlayer(RakNetClientSession session) {
        long id = session.getGloballyUniqueId();

        if (players.containsKey(id)) {
            return players.get(id);
        } else {
            BedrockPlayer p = new BedrockPlayer();
            p.session = session;
            players.put(id, p);
            return p;
        }
    }

    public void enableEncryption(byte[] serverKey) {
        this.serverKey = serverKey;
    }

    public void disconnect(String reason) {
        DisconnectPacket packet = new DisconnectPacket();
        if (reason != null) {
            packet.setHideDisconnectionScreen(false);
            packet.setMessage(reason);
            NetworkManager.sendPacket(getSession(), packet);

            BedrockProxy.getInstance().getNetworkManager().getServer().removeSession(getSession(), reason);
        } else {
            packet.setHideDisconnectionScreen(true);
            NetworkManager.sendPacket(getSession(), packet);
            BedrockProxy.getInstance().getNetworkManager().getServer().removeSession(getSession());
        }
    }
}
