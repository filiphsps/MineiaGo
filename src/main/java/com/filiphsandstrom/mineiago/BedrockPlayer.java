package com.filiphsandstrom.mineiago;

import java.net.Proxy;

import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.packetlib.Client;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.tcp.TcpSessionFactory;
import com.nukkitx.protocol.bedrock.BedrockServerSession;

public class BedrockPlayer {
    public BedrockPlayer() {
        super();
    }
    public BedrockPlayer(BedrockServerSession bedrock_session) {
        super();
        
        setBedrockSession(bedrock_session);

        // TODO: offline mode, direct connect to child server if enabled.
    }
    
    private BedrockServerSession bedrock_session;
    public BedrockServerSession getBedrockSession() {
        return bedrock_session;
    }
    public void setBedrockSession(BedrockServerSession session) {
        bedrock_session = session;
    }

    private Session java_session;
    public Session getJavaSession() {
        return java_session;
    }
    public void setJavaSession(Session session) {
        java_session = session;
    }

    private String username = "";
    public String getUsername() {
        return username;
    }
    public void setUsername(String name) {
        username = name;
    }
    private String password = "";
    public String getPassword() {
        return password;
    }
    public void setPassword(String pass) {
        password = pass;
    }

    // FIXME: only run once we've gotten auth details from client
    public void createJavaClient() {
        try {
            MinecraftProtocol protocol = new MinecraftProtocol(username, password);
            Client client = new Client("0.0.0.0", 25565, protocol, new TcpSessionFactory(Proxy.NO_PROXY));
            setJavaSession(client.getSession());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isAuthenticated() {
        return (!password.isEmpty() && !username.isEmpty());
    }
}
