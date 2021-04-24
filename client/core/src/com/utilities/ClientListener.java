package com.utilities;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.utilities.messages.ErrorResponse;
import com.utilities.messages.LobbyResponse;

public class ClientListener extends Listener {

    private GameClient client = GameClient.getInstance();

    public void received(Connection connection, Object object) {
        if (object instanceof LobbyResponse) {
            LobbyResponse message = (LobbyResponse) object;
            if (message.text.equals("Created")) {
                client.setRoomCode(message.roomCode);
            }
        }
        if (object instanceof ErrorResponse) {
            ErrorResponse response = (ErrorResponse) object;
            System.out.println(response.text);
        }
    }

    @Override
    public void disconnected(Connection connection) {
        System.out.println("Disconnected...");
        client.attemptReconnect();
    }
}
