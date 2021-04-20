package com.mygdx.dragmania.views.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Button {

    protected Rectangle bounds;
    protected Vector2 position;
    protected Texture button;

    public Rectangle getBounds() {
        return bounds;
    }

    public Texture getButton() {
        return button;
    }

    public int getWidth() {
        return button.getWidth();
    }

    public int getHeight() {
        return button.getHeight();
    }

    public Vector2 getPosition() {
        return position;
    }

}
