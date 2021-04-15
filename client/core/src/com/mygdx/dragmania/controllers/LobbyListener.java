package com.mygdx.dragmania.controllers;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.utilities.messages.LobbyResponse;

public class LobbyListener extends Listener {
    
    LobbyController controller = LobbyController.getInstance();

    public void connected(Connection connection, Object object) {

    }

    public void received(Connection connection, Object object) {
        if (object instanceof LobbyResponse) {
            LobbyResponse response = (LobbyResponse) object;
            if (response.text.equals("Success")) System.out.println(response.usernames.toString()); // controller.joinSuccess(response.roomCode);
            else controller.joinTimeout();
        }
    }
}
