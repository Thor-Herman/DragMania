package com.mygdx.dragmania.views;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;


public class BackArrow {

    private Rectangle bounds;
    private Vector2 position;
    private Texture backArrow;

    public BackArrow(int x, int y) {
        position = new Vector2(x, y);
        backArrow = new Texture("textures/buttons/back_arrow.png");
        bounds = new Rectangle(x, y, backArrow.getWidth()/3, backArrow.getHeight()/3);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Texture getBackArrow() {
        return backArrow;
    }

    public int getWidth() {
        return backArrow.getWidth();
    }

    public int getHeight() {
        return backArrow.getHeight();
    }

    public Vector2 getPosition() {
        return position;
    }
}
