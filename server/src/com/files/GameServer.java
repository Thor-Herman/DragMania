package com.files;

import java.io.IOException;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

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
        kryo.register(SomeRequest.class);
        kryo.register(SomeResponse.class);
        kryo.register(Score.class);
    }

    private void startServer() {
        try {
            server.start();
            server.bind(tcpPort, udpPort);

        } catch (IOException e) {
            server.close();
            System.out.println("Failed to start server");
        }
    }

    private void setupListeners() {
        server.addListener(new ReceiveHandler());
    }

    public static void main(String[] args) {
        GameServer server = getInstance();
        server.setup();
        System.out.println("Server is ready to serve");
    }
}
