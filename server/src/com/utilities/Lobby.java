package com.utilities;

import com.esotericsoftware.kryonet.Connection;
import com.utilities.messages.GameMapMessage;
import com.utilities.messages.JoinLobbyRequest;
import com.utilities.messages.Message;
import com.utilities.messages.Score;
import com.utilities.messages.SomeResponse;

import java.util.Map;
import java.util.HashMap;

public class Lobby {

    public static final int LOBBY_PLAYER_CRITERIUM = 2;
    private Map<Connection, Float> clientsMap = new HashMap<>();
    private GameMapGenerator generator = new GameMapGenerator();

    public void removeConnection(Connection connection) {
        clientsMap.remove(connection);
        // TODO: Notify other connections
    }

    public void addUser(Connection connection) {
        System.out.println("Added connection: " + connection.toString());
        final float INITIAL_SCORE = 0.0f;
        clientsMap.put(connection, INITIAL_SCORE);
        sendSuccessfulJoinMessage(connection);
        if (clientsMap.size() == LOBBY_PLAYER_CRITERIUM)
            sendGameMap();
    }

    public void received(Connection connection, Message message) {
        if (clientsMap.size() < LOBBY_PLAYER_CRITERIUM)
            sendNotEnoughPlayersMessage(connection);
        if (!clientsMap.containsKey(connection))
            clientsMap.put(connection, 0.0f); // Doesn't take into account different game rooms
        if (message instanceof Score)
            handleScoreMessage(connection, message);
        else if (message instanceof JoinLobbyRequest) {
            addUser(connection);
        }
    }

    private void sendGameMap() {
        GameMapMessage map = generator.generateMap();
        for (Connection client : clientsMap.keySet()) {
            client.sendTCP(map);
        }
    }

    private void sendSuccessfulJoinMessage(Connection connection) {
        SomeResponse response = new SomeResponse();
        response.text = "Success";
        connection.sendTCP(response);
    }

    private void sendNotEnoughPlayersMessage(Connection connection) {
        SomeResponse response = new SomeResponse();
        response.text = "Not enough clients connected yet";
        connection.sendTCP(response);
    }

    private void handleScoreMessage(Connection connection, Object object) {
        Score receiverScore = (Score) object;
        System.out.println(
                "Received score from connection " + connection.toString() + ": " + Float.toString(receiverScore.score));
        clientsMap.put(connection, receiverScore.score);
        sendMostRecentScores(connection);
    }

    private void sendMostRecentScores(Connection connection) {
        for (Connection client : clientsMap.keySet()) {
            if (client != connection) {
                Score score = new Score();
                score.score = clientsMap.get(client);
                connection.sendUDP(score);
            }
        }
    }

    public boolean contains(Connection connection) {
        return clientsMap.containsKey(connection);
    }
}     