package com.utilities;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.FrameworkMessage.KeepAlive;
import com.utilities.messages.CreateLobbyRequest;
import com.utilities.messages.ErrorResponse;
import com.utilities.messages.LobbyResponse;
import com.utilities.messages.Message;

import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

import java.util.concurrent.ThreadLocalRandom;

public class ReceiveHandler extends Listener {

    public Map<Integer, Lobby> lobbies = new HashMap<>();

    public void connected(Connection connection) {
        System.out.println("Connection: " + connection.toString());
    }

    public void disconnected(Connection connection) {
        System.out.println("Removed connection: " + connection.toString());
        Optional<Lobby> associatedLobby = lobbies.values().stream().filter(lobby -> lobby.contains(connection))
                .findFirst();
        if (associatedLobby.isPresent())
            associatedLobby.get().removeConnection(connection);
    }

    public void received(Connection connection, Object object) {
        if (object instanceof KeepAlive)
            return;
        if (object instanceof CreateLobbyRequest) {
            String username = ((CreateLobbyRequest) object).username;
            int roomCode = createNewLobby(connection, username);
            sendLobbyCode(connection, roomCode);
        }
        if (object instanceof Message) {
            Message message = (Message) object;
            handleMessage(connection, message);
        }
    }

    private void handleMessage(Connection connection, Message message) {
        if (!lobbies.containsKey(message.roomCode)) {
            sendErrorMessage(connection, "No such room code exists");
        } else {
            lobbies.get(message.roomCode).received(connection, message);
        }
    }

    private void sendErrorMessage(Connection connection, String message) {
        ErrorResponse error = new ErrorResponse();
        error.text = message;
        connection.sendTCP(error);
    }

    private int createNewLobby(Connection connection, String username) {
        int roomCode = generateRandomRoomCode();
        Lobby lobby = new Lobby(roomCode);
        lobby.addUser(connection, username);
        lobbies.put(roomCode, lobby);
        return roomCode;
    }

    private void sendLobbyCode(Connection connection, int roomCode) {
        LobbyResponse response = new LobbyResponse();
        response.roomCode = roomCode;
        response.text = "Created";
        connection.sendTCP(response);
    }

    private int generateRandomRoomCode() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int roomCode;
        do {
            roomCode = random.nextInt(1000, 9999);
        } while (lobbies.containsKey(roomCode));
        return roomCode;
    }
}
