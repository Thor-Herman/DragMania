package com.utilities;

import java.io.IOException;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

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
    private boolean isReconnecting = false;

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
        client.start();
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
        try {
            client.connect(5000, ipAddress, tcpPort, udpPort);
        } catch (IOException e) {
            client.close();
            System.out.println("Something went wrong when connecting: " + e.toString());
            attemptReconnect();
        }
    }

    public void attemptReconnect() {
        if (! isReconnecting) {
            isReconnecting = true;
            new Timer().scheduleAtFixedRate(new TimerTask(){
                @Override
                public void run(){
                    if (client.isConnected()) this.cancel();
                    connectToServer();
                }
            },0,5000);
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
        kryo.register(LeaveLobbyRequest.class);
    }

    private void setupListeners() {
        client.addListener(new ClientListener());
        client.addListener(new LobbyListener());
        client.addListener(new GameListener()); // TODO: Only add when inside a game?
    }

    public void sendGameOver() {
        GameOverMessage message = new GameOverMessage();
        message.roomCode = roomCode;
        client.sendTCP(message);
    }

    public void leaveGame() {
        LeaveLobbyRequest request = new LeaveLobbyRequest();
        request.roomCode = roomCode;
        client.sendTCP(request);
        roomCode = -1;
    }

    public void setRoomCode(int roomCode) {
        this.roomCode = roomCode;
    }
}
