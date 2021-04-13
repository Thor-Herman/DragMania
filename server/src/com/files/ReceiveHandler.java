package com.files;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.FrameworkMessage.KeepAlive;

import java.util.Map;
import java.util.HashMap;

public class ReceiveHandler extends Listener {

    public static final int LOBBY_PLAYER_CRITERIUM = 2;
    private Map<Connection, Float> clientsMap = new HashMap<>();
    private GameMapGenerator generator = new GameMapGenerator();

    public void connected(Connection connection) {
        System.out.println("Added connection: " + connection.toString());
        clientsMap.put(connection, 0.0f);
        if (clientsMap.size() == LOBBY_PLAYER_CRITERIUM)
            sendGameMap();
    }

    public void disconnected(Connection connection) {
        System.out.println("Removed connection: " + connection.toString());
        clientsMap.remove(connection);
    }

    public void received(Connection connection, Object object) {
        if (object instanceof KeepAlive)
            return;
        if (clientsMap.size() < LOBBY_PLAYER_CRITERIUM)
            sendNotEnoughPlayersMessage(connection);
        if (!clientsMap.containsKey(connection))
            clientsMap.put(connection, 0.0f); // Doesn't take into account different game rooms
        if (object instanceof Score)
            handleScoreMessage(connection, object);
    }

    private void sendGameMap() {
        GameMapMessage map = generator.generateMap(2000);
        for (Connection client : clientsMap.keySet()) {
            client.sendTCP(map);
        }
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
}
