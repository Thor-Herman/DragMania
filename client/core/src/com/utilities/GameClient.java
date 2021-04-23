package com.utilities;

import java.io.IOException;
import java.util.Arrays;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import com.mygdx.dragmania.controllers.GameListener;
import com.mygdx.dragmania.controllers.LobbyController;
import com.mygdx.dragmania.controllers.LobbyListener;
import com.utilities.messages.*;

public class GameClient {

    private Client client;
    private int tcpPort, udpPort;
    private String ipAddress;
    private int roomCode;
    private static GameClient instance;

    private GameClient() {
        this.client = new Client();
        this.tcpPort = Env.getTcpPort();
        this.udpPort = Env.getUdpPort();
        this.ipAddress = Env.getIPAddress();
    }

    public static GameClient getInstance() {
        if (instance == null)
            instance = new GameClient();
        return instance;
    }

    public void setup() { // Don't move this inside constructor
        registerClasses();
        Log.set(Log.LEVEL_DEBUG);
        setupListeners();
        connectToServer();
    }

    public void sendScore(float score) {
        Score scoreMessage = new Score();
        scoreMessage.roomCode = roomCode;
        scoreMessage.score = score;
        client.sendTCP(scoreMessage);
    }

    public void joinGame(String username, int roomCode) {
        this.roomCode = roomCode;
        JoinLobbyRequest request = new JoinLobbyRequest();
        request.username = username;
        request.roomCode = roomCode;
        client.sendTCP(request);
    }

    public void createGame(String username) {
        CreateLobbyRequest request = new CreateLobbyRequest();
        request.username = username;
        client.sendTCP(request);
    }

    public void readyUp() {
        ReadyMessage ready = new ReadyMessage();
        ready.roomCode = roomCode;
        client.sendTCP(ready);
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
        kryo.register(CreateLobbyRequest.class);
        kryo.register(SomeResponse.class);
        kryo.register(Score.class);
        kryo.register(int[].class);
        kryo.register(String[].class);
        kryo.register(GameMapMessage.class);
        kryo.register(ErrorResponse.class);
        kryo.register(LobbyResponse.class);
        kryo.register(JoinLobbyRequest.class);
        kryo.register(ReadyMessage.class);
        kryo.register(GameOverMessage.class);
    }

    private void setupListeners() {
        client.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (object instanceof LobbyResponse) {
                    LobbyResponse message = (LobbyResponse) object;
                    if (message.text.equals("Created")){
                        System.out.println(message.roomCode);
                        roomCode = message.roomCode;
                    }

                }
                if (object instanceof ErrorResponse) {
                    ErrorResponse response = (ErrorResponse) object;
                    System.out.println(response.text);
                }
            }
        });
        client.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object o) {
                if (o instanceof Message) System.out.println("roomCode: " + roomCode);
            }
        });
        client.addListener(new LobbyListener());
        client.addListener(new GameListener()); // TODO: Only add when inside a game?
    }

    public void sendGameOver() {
        GameOverMessage message = new GameOverMessage();
        message.roomCode = roomCode;
        client.sendTCP(message);
    }

    public static void main(String[] args) {
        LobbyController controller = LobbyController.getInstance();
        controller.connectToServer();
        controller.createGame("TH");
        while (true)
            ; // Runs forever in order to receive server msg
    }

    public void leaveGame() {
        client.close();
        connectToServer();
    }
}
