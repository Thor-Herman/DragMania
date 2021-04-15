package com.mygdx.dragmania.controllers;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.utilities.messages.ErrorResponse;
import com.utilities.messages.LobbyResponse;

public class LobbyListener extends Listener {
    
    LobbyController controller = LobbyController.getInstance();

    public void connected(Connection connection) {
        controller.connected();
    }

    public void disconnected(Connection connection) {
        controller.disconnected();
    }

    public void received(Connection connection, Object object) {
        if (object instanceof LobbyResponse) {
            LobbyResponse response = (LobbyResponse) object;
            System.out.println(response.text);
            if (response.text.equals("Created")) controller.joinSuccess(response.roomCode, null);
            else if (response.text.equals("PlayerJoined")) controller.playerJoinedLobby(response.username); // controller.joinSuccess(response.roomCode);
            else if (response.text.equals("Success")) controller.joinSuccess(response.roomCode, response.usernames);
            else if (response.text.equals("PlayerLeft")) controller.playerLeftLobby(response.username);
        }
        if (object instanceof ErrorResponse) {
            ErrorResponse response = (ErrorResponse) object;
            controller.errorOccurred(response.text);
        }
    }
}
