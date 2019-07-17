package com.filiphsandstrom.mineiago;

import java.net.Proxy;

import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.packetlib.Client;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.event.session.ConnectedEvent;
import com.github.steveice10.packetlib.event.session.DisconnectedEvent;
import com.github.steveice10.packetlib.event.session.DisconnectingEvent;
import com.github.steveice10.packetlib.event.session.PacketReceivedEvent;
import com.github.steveice10.packetlib.event.session.PacketSendingEvent;
import com.github.steveice10.packetlib.event.session.PacketSentEvent;
import com.github.steveice10.packetlib.event.session.SessionListener;
import com.github.steveice10.packetlib.tcp.TcpSessionFactory;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nukkitx.protocol.bedrock.BedrockServerSession;

import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ServerPing.PlayerInfo;

public class BedrockPlayer {
    public BedrockPlayer() {
        super();
    }

    public BedrockPlayer(BedrockServerSession bedrock_session) {
        super();

        setBedrockSession(bedrock_session);

        // TODO: offline mode, direct connect to child server if enabled.
    }

    public void onDisconnect(String reason) {
        // FIXME
        return;
    }

    public void setChainData(String chain) {
        // FIXME
        MineiaGo.getInstance().getLogging().Warning("chainData validation is not implemented!");
        return;

        // JsonObject ecryption = new JsonParser().parse(chain).getAsString("chain");

        /*
         * JsonObject data = new
         * JsonParser().parse(chain).getAsJsonObject().get("extraData").getAsJsonObject(
         * ); if(!data.isJsonObject()) return;
         * 
         * playerInfo = new BedrockPlayerInfo(data.get("displayName").getAsString(),
         * data.get("identity").getAsString());
         */
    }

    @Getter
    private BedrockPlayerInfo playerInfo;

    @Getter
    @Setter
    private BedrockServerSession bedrockSession;

    @Getter
    @Setter
    private Session javaSession;

    @Getter
    @Setter
    private String username = "";

    @Getter
    @Setter
    private String password = "";

    public void createJavaClient() throws Exception {
        if (username.isEmpty())
            throw new Exception("Username is empty");
        else if (password.isEmpty())
            throw new Exception("Password is empty");

        try {
            MinecraftProtocol protocol = new MinecraftProtocol(username, password);
            Client client = new Client("0.0.0.0", 25565, protocol, new TcpSessionFactory(Proxy.NO_PROXY));

            MineiaGo.getInstance().getLogging()
                    .Debug("[" + bedrockSession.getAddress() + "] Connecting to main server...");
            client.getSession().connect();
            client.getSession().addListener(new SessionListener() {

                @Override
                public void packetSent(PacketSentEvent event) {
                    return;
                }

                @Override
                public void packetSending(PacketSendingEvent event) {
                    return;
                }

                @Override
                public void disconnecting(DisconnectingEvent event) {
                    return;
                }

                @Override
                public void packetReceived(PacketReceivedEvent event) {
                    // TODO
                }

                @Override
                public void disconnected(DisconnectedEvent event) {
                    bedrockSession.disconnect(event.getReason());
                }

                @Override
                public void connected(ConnectedEvent event) {
                    setJavaSession(event.getSession());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isAuthenticated() {
        return (!password.isEmpty() && !username.isEmpty());
    }
}
