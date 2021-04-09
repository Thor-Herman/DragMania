package com.files;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.FrameworkMessage.KeepAlive;

import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

public class ReceiveHandler extends Listener {

    public Map<Integer, Lobby> lobbies = new HashMap<>();

    public void disconnected(Connection connection) {
        System.out.println("Removed connection: " + connection.toString());
        Optional<Lobby> associatedLobby = lobbies.values().stream().filter(lobby -> lobby.contains(connection))
                .findFirst();
        if (associatedLobby.isPresent())
            associatedLobby.get().removeConnection(connection);
        else {
            System.out.println("Something went terribly wrong with disconnected method");
        }
    }

    public void received(Connection connection, Object object) {
        if (object instanceof KeepAlive)
            return;

    private void sendGameMap() {
        GameMapMessage map = generator.generateMap(2000);
        for (Connection client : clientsMap.keySet()) {
            client.sendTCP(map);
        }
    }

}
