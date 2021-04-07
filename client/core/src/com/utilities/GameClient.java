package com.utilities;

import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

public class GameClient {

    private Client client;
    private int tcpPort, udpPort;
    private String ipAddress;
    private static final GameClient instance = new GameClient();


    private GameClient() {
        this.client = new Client();
        this.tcpPort = Env.getTcpPort();
        this.udpPort = Env.getUdpPort();
        this.ipAddress = Env.getIPAddress();
    }

    public static GameClient getInstance() {
        return instance;
    }

    private void setup() {
        registerClasses();
        Log.set(Log.LEVEL_DEBUG);
        connectToServer();
        setupListeners();
    }

    public void sendScore(float score) {
        Score scoreMessage = new Score();
        scoreMessage.score = score;
        client.sendTCP(scoreMessage);
    }

    public void sendScore(float score) {
        Score scoreMessage = new Score();
        scoreMessage.score = score;
        client.sendTCP(scoreMessage);
    }

    private void connectToServer() {
        client.start();
        try {
            client.connect(5000, ipAddress, tcpPort, udpPort);
        } catch (IOException e) {
            client.close();
            System.out.println("Something went wrong setting up the client: " + e.toString());
        }
    }

    private void registerClasses() {
        Kryo kryo = client.getKryo();
        kryo.register(SomeRequest.class);
        kryo.register(SomeResponse.class);
        kryo.register(Score.class);
    }

    private void setupListeners() {
        client.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (object instanceof SomeResponse) {
                    SomeResponse response = (SomeResponse) object;
                    System.out.println(response.text);
                }
                if (object instanceof Score) {
                    Score score = (Score) object;
                    System.out.println(score.score);
                }
            }
        });
    }

    public static void main(String[] args) {
        GameClient client = getInstance();
        client.setup();
        client.sendScore(50.0f);
        while (true)
            ; // Runs forever in order to receive server msg
    }
}
