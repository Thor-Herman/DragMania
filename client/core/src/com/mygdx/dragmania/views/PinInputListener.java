package com.mygdx.dragmania.views;

import com.badlogic.gdx.Input;

public class PinInputListener implements Input.TextInputListener {
    @Override
    public void input (String text) {
        if(text.length() == 4) {
            System.out.println(text);
        }
    }

    @Override
    public void canceled () {
    }
}
