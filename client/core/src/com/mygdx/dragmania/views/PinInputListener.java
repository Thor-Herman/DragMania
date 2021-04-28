package com.mygdx.dragmania.views;

import com.badlogic.gdx.Input;
import com.mygdx.dragmania.controllers.LobbyController;

public class PinInputListener implements Input.TextInputListener {
    @Override
    public void input (String text) {
        if(text.length() == 4) {
            LobbyController.getInstance().joinGame("test", Integer.parseInt(text));
            System.out.println(text);
        }
    }

    @Override
    public void canceled () {
    }
}
