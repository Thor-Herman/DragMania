package com.utilities;

import com.esotericsoftware.kryonet.Connection;
import com.utilities.messages.GameMapMessage;
import com.utilities.messages.GameOverMessage;
import com.utilities.messages.JoinLobbyRequest;
import com.utilities.messages.LobbyResponse;
import com.utilities.messages.Message;
import com.utilities.messages.ReadyMessage;
import com.utilities.messages.Score;
import com.utilities.messages.SomeResponse;
import com.utilities.messages.LeaveLobbyRequest;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Collection;
import java.util.Comparator;

public class Lobby {

    public static final int LOBBY_PLAYER_CRITERIUM = 2;
    private Map<Connection, Float> clientsMap = new HashMap<>();
    private GameMapGenerator generator = new GameMapGenerator();
    private final int roomCode;
    private Collection<Connection> readyPlayers = new ArrayList<>();

    public Lobby(int roomCode) {
        this.roomCode = roomCode;
    }

    public void removeConnection(Connection connection) {
        clientsMap.remove(connection);
        LobbyResponse response = new LobbyResponse();
        response.text = "PlayerLeft";
        response.username = connection.toString();
        clientsMap.keySet().forEach(c -> {
            c.sendTCP(response);
            System.out.println(c.toString());
        });
    }

    public void addUser(Connection connection, String username) {
        System.out.println("Added connection: " + connection.toString());
        final float INITIAL_SCORE = 0.0f;
        connection.setName(username);
        clientsMap.put(connection, INITIAL_SCORE);
        sendSuccessfulJoinMessage(connection);
        notifyOthersOfJoin(connection);
    }

    private void notifyOthersOfJoin(Connection connection) {
        LobbyResponse response = new LobbyResponse();
        response.text = "PlayerJoined";
        response.username = connection.toString();
        clientsMap.keySet().stream().filter(c -> c != connection).forEach(c -> c.sendTCP(response));
    }

    public void received(Connection connection, Message message) {
        if (clientsMap.size() < LOBBY_PLAYER_CRITERIUM)
            sendNotEnoughPlayersMessage(connection);
        if (message instanceof Score)
            handleScoreMessage(connection, message);
        else if (message instanceof JoinLobbyRequest) {
            String username = ((JoinLobbyRequest) message).username;
            addUser(connection, username);
        } else if (message instanceof ReadyMessage)
            readyUp(connection);
        else if (message instanceof GameOverMessage)
            handleGameOver();
        else if (message instanceof LeaveLobbyRequest) {
            handleLeaveLobbyRequest(connection);
        }
    }

    private void handleGameOver() {
        // TODO: Validate
        for (Connection client : clientsMap.keySet()) {
            GameOverMessage gameOverMessage = new GameOverMessage();
            gameOverMessage.won = false;
            float max = clientsMap.values().stream().max(Comparator.naturalOrder()).get();
            List<Connection> winner = clientsMap.entrySet().stream().filter(e -> e.getValue() == max)
                    .map(Map.Entry::getKey).collect(Collectors.toList());
            client.sendTCP(gameOverMessage);
            System.out.println("Game Over!!!");
        }
    }

    private void readyUp(Connection connection) {
        SomeResponse response = new SomeResponse();
        response.text = "Success";
        connection.sendTCP(response);
        if (!readyPlayers.contains(connection))
            readyPlayers.add(connection);
        if (readyPlayers.size() == LOBBY_PLAYER_CRITERIUM) {
            sendGameMap();
        }
    }

    private void sendGameMap() {
        GameMapMessage map = generator.generateMap(2000);
        for (Connection client : clientsMap.keySet()) {
            client.sendTCP(map);
        }
    }

    private void sendSuccessfulJoinMessage(Connection connection) {
        LobbyResponse response = new LobbyResponse();
        response.text = "Success";
        response.roomCode = roomCode;
        response.usernames = getUsernames();
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

    private void handleLeaveLobbyRequest(Connection connection) {
        LobbyResponse response = new LobbyResponse();
        response.text = "SuccessfullyLeft";
        connection.sendTCP(response);
        removeConnection(connection);
        System.out.println("Player requested leave");
    }

    public boolean contains(Connection connection) {
        return clientsMap.containsKey(connection);
    }

    public String[] getUsernames() {
        return clientsMap.keySet().stream().map(c -> c.toString()).toArray(String[]::new);
    }
}