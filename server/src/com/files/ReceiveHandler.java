package com.files;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.FrameworkMessage.KeepAlive;

import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

import java.util.concurrent.ThreadLocalRandom;

public class ReceiveHandler extends Listener {

    public Map<Integer, Lobby> lobbies = new HashMap<>();

    public void disconnected(Connection connection) {
        System.out.println("Removed connection: " + connection.toString());
        Optional<Lobby> associatedLobby = lobbies.values().stream().filter(lobby -> lobby.contains(connection))
                .findFirst();
        if (associatedLobby.isPresent())
            associatedLobby.get().removeConnection(connection);
        else
            System.out.println("Something went terribly wrong with disconnected method");
    }

    public void received(Connection connection, Object object) {
        if (object instanceof KeepAlive)
            return;
        if (object instanceof CreateLobbyRequest) {
            createNewLobby(connection);
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

    private void createNewLobby(Connection connection) {
        Lobby lobby = new Lobby();
        int roomCode = generateRandomRoomCode();
        lobbies.put(roomCode, lobby);
        lobby.addUser(connection);
    }

    private int generateRandomRoomCode() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int roomCode;
        do {
            roomCode = random.nextInt(0, 9999);
        } while (lobbies.containsKey(roomCode));
        return roomCode;
    }
}
