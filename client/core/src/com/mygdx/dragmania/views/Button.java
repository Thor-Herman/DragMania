package com.mygdx.dragmania.views;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Button {

    protected Rectangle bounds;
    protected Vector2 position;
    protected Texture button;

    public Button(int x, int y, String filename) {
        position = new Vector2(x, y);
        button = new Texture(filename);
        bounds = new Rectangle(x, y, button.getWidth(), button.getHeight());
    }

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
