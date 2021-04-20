package com.mygdx.dragmania.views.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class JoinGameButton extends Button {

    public JoinGameButton(int x, int y) {
        position = new Vector2(x, y);
        button = new Texture("join_game.png");
        bounds = new Rectangle(x, y, button.getWidth(), button.getHeight());
    }
}
