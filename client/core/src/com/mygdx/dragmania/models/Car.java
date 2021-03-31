package com.mygdx.dragmania.models;

import com.badlogic.gdx.math.Vector2;

public class Car {
    private Vector2 position;

    public void update(float dt, boolean isTouching) {

    }

    public int getPosition() {
        return (int)position.y;
    }

    public int getVelocity() {
        return 0;
    }

    public boolean canDrive(boolean canDRive) {
        return true;
    }
}
