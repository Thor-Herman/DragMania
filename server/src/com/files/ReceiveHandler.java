package com.files;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.FrameworkMessage.KeepAlive;

import java.util.Map;
import java.util.HashMap;

public class ReceiveHandler extends Listener {

    private Map<Connection, Float> clients = new HashMap<>();

    public void connected (Connection connection) {
        System.out.println("Added connection: " + connection.toString());
        clients.put(connection, 0.0f);
    }

    public void disconnected (Connection connection) {
        System.out.println("Removed connection: " + connection.toString());
        clients.remove(connection);
    }

    public void received(Connection connection, Object object) { 
        if (object instanceof KeepAlive) return;
        if (clients.size() < 2) {
            SomeResponse response = new SomeResponse();
            response.text = "Not enough clients connected yet";
            connection.sendTCP(response);   
        }
        if (! clients.containsKey(connection)) clients.put(connection, 0.0f); // Doesn't take into account different game rooms
        if (object instanceof Score) {
            System.out.println("Received score from connection " + connection.toString());
            Score receiverScore = (Score) object;
            System.out.println(receiverScore.score);
            clients.put(connection, receiverScore.score);
            for (Connection client : clients.keySet()) {
                if (client != connection) {
                    Score score = new Score();
                    score.score = clients.get(client);
                    connection.sendUDP(score);
                }
            }
        }
    }
}