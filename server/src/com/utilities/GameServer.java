package com.utilities;

import java.io.IOException;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import com.utilities.messages.CreateLobbyRequest;
import com.utilities.messages.ErrorResponse;
import com.utilities.messages.GameMapMessage;
import com.utilities.messages.JoinLobbyRequest;
import com.utilities.messages.LobbyResponse;
import com.utilities.messages.Score;
import com.utilities.messages.SomeResponse;

public class GameServer {

    private static Server server;
    private int tcpPort, udpPort;
    private static GameServer instance = new GameServer();

    public static GameServer getInstance() {
        return instance;
    }

    private GameServer() {
        server = new Server();
        tcpPort = Env.getTcpPort();
        udpPort = Env.getUdpPort();
    }

    private void setup() {
        registerClasses();
        startServer();
        setupListeners();
    }

    private void registerClasses() {
        Kryo kryo = server.getKryo();
        kryo.register(CreateLobbyRequest.class);
        kryo.register(SomeResponse.class);
        kryo.register(Score.class);
        kryo.register(int[].class);
        kryo.register(String[].class);
        kryo.register(GameMapMessage.class);
        kryo.register(ErrorResponse.class);
        kryo.register(LobbyResponse.class);
        kryo.register(JoinLobbyRequest.class);
    }

    private void startServer() {
        try {
            server.start();
            server.bind(tcpPort, udpPort);

        } catch (IOException e) {
            server.close();
            System.out.println("Failed to start server: " + e.getMessage());
        }
    }

    private void setupListeners() {
        server.addListener(new ReceiveHandler());
    }

    public static void main(String[] args) {
        GameServer server = getInstance();
        server.setup();
    }
}
