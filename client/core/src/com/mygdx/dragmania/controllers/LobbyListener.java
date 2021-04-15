package com.mygdx.dragmania.controllers;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.utilities.messages.LobbyResponse;

import java.util.Arrays;

public class LobbyListener extends Listener {
    
    LobbyController controller = LobbyController.getInstance();

    public void connected(Connection connection, Object object) {

    }

    public void received(Connection connection, Object object) {
        if (object instanceof LobbyResponse) {
            LobbyResponse response = (LobbyResponse) object;
            if (response.text.equals("Created")) {
                System.out.println(response.roomCode);
            }
            else if (response.text.equals("PlayerJoined")) System.out.println(Arrays.toString(response.usernames)); // controller.joinSuccess(response.roomCode);
            else controller.joinTimeout();
        }
    }
}
