package com.utilities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Texture;

public abstract class Collidable {
    private Texture texture;
    private Rectangle hitBox;

    public Collidable(Texture texture) {
        this.texture = texture;
        this.hitBox = new Rectangle(0, 0, texture.getWidth(), texture.getHeight());
    }

    public Collidable(float width, float height) {
        this.hitBox = new Rectangle(0, 0, width, height);
    }

    public Texture getTexture() {
        return this.texture;
    }

    public Rectangle getHitBox() {
        return this.hitBox;
    }

    public Vector2 getPosition() {
        Vector2 pos = new Vector2();
        return hitBox.getPosition(pos);
    }

    public void reposition(Vector2 position) {
        hitBox.setPosition(position);
    }

    public boolean collides(Collidable collidable) {
        return hitBox.overlaps(collidable.getHitBox());
    }
}

