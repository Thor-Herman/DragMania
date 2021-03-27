package com.utilities;

import java.io.IOException;
import java.net.InetAddress;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

public class GameClient {

    private int tcpPort, udpPort;
    private Client client = new Client();
    private static final GameClient instance = new GameClient();

    private GameClient() {
    }

    public static GameClient getInstance() {
        return instance;
    }

    private void setup() {
        try {
            register(SomeRequest.class);
            register(SomeResponse.class);
            Log.set(Log.LEVEL_DEBUG);
            client.start();
            InetAddress address = client.discoverHost(Env.getUdpPort(), 5000);
            client.connect(5000, address, Env.getTcpPort(), Env.getUdpPort());
            setupListeners();
            client.sendTCP(new SomeRequest("Hello"));
        } catch (IOException e) {
            client.close();
            System.out.println("Something went wrong setting up the client");
        }
    }

    private void register(Class Message) {
        client.getKryo().register(Message);
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
        // while (true);
        // System.out.println("Client is up and running");
    }
}
