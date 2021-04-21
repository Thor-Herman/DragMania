package com.utilities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Texture;
import com.utilities.TextureScaler;

public abstract class Collidable {
    private Texture texture;
    protected Rectangle hitBox;
    private Vector2 startPosition;
    private Vector2 positionVector = new Vector2();

    protected Collidable(Vector2 startPosition, Texture texture) {
        this.startPosition = startPosition;
        this.texture = texture;
        this.hitBox = new Rectangle(startPosition.x, startPosition.y, texture.getWidth()-40, texture.getHeight()-40);
    }

    protected Collidable(Vector2 startPosition, float width, float height) {
        this.startPosition = startPosition;
        this.hitBox = new Rectangle(startPosition.x, startPosition.y, width, height);
    }

    public Texture getTexture() {
        return this.texture;
    }

    public Rectangle getHitBox() {
        return this.hitBox;
    }

    public Vector2 getStartPosition() {
        return this.startPosition;
    }

    public Vector2 getPosition() {
        return hitBox.getPosition(positionVector);
    }

    public void reposition(Vector2 position) {
        hitBox.setPosition(position);
    }

    public boolean collides(Collidable collidable) {
        return hitBox.overlaps(collidable.getHitBox());
    }

    public void setScaledTexture(double scale) {
        texture = TextureScaler.scale(texture, scale);
        hitBox.setWidth(texture.getWidth());
        hitBox.setHeight(texture.getHeight());
    }
    
}

