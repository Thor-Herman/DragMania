package com.mygdx.dragmania.views.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class RematchButton extends Button {

    public RematchButton(int x, int y) {
        position = new Vector2(x, y);
        button = new Texture("rematch.png");
        bounds = new Rectangle(x, y, button.getWidth(), button.getHeight());
    }
}
