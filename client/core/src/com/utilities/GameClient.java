package com.utilities;

import java.io.IOException;
import java.net.InetAddress;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

public class GameClient {

    private Client client;
    private int tcpPort, udpPort;
    private static final GameClient instance = new GameClient();

    private GameClient() {
        this.client = new Client();
        this.tcpPort = Env.getTcpPort();
        this.udpPort = Env.getUdpPort();
    }

    public static GameClient getInstance() {
        return instance;
    }

    private void setup() {
            registerClasses();
            Log.set(Log.LEVEL_DEBUG);
            connectToServer();
            setupListeners();
            client.sendTCP(new SomeRequest("Hello"));
    }

    private void connectToServer() {
        client.start();
        InetAddress address = client.discoverHost(udpPort, 5000);
        try {
            client.connect(5000, address, tcpPort, udpPort);
        }
        catch (IOException e) {
            client.close();
            System.out.println("Something went wrong setting up the client: " + e.toString());
        }
    }

    private void registerClasses() {
        Kryo kryo = client.getKryo();
        kryo.register(SomeRequest.class);
        kryo.register(SomeResponse.class);
    }

    private void setupListeners() {
        client.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (object instanceof SomeResponse) {
                    SomeResponse response = (SomeResponse) object;
                    System.out.println(response.text);
                }
            }
        });
    }

    public static void main(String[] args) {
        GameClient client = getInstance();
        client.setup();
        while (true); // Runs forever in order to receive server msg
    }
}
