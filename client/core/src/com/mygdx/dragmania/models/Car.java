package com.mygdx.dragmania.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Texture;

import com.utilities.Collidable;

public class Car extends Collidable {
	
    private static Vector2 startPosition = new Vector2(0, 0); // TODO: Adjust this later

    private float acceleration;
    private float velocity = 0;
    private float maxVelocity;
    private boolean allowedToDrive = true;

    public Car(Texture texture, float acceleration, float maxVelocity) {
        super(startPosition, texture);
        this.maxVelocity = maxVelocity;
        this.acceleration = acceleration;
    }

    public Car(float width, float height, float acceleration, float maxVelocity) {
        super(startPosition, width, height);
        this.maxVelocity = maxVelocity;
        this.acceleration = acceleration;
    }

	// Accelerates when forward boolean is true. Decelerates when false.
    // Also checks if the car is allowed to drive
    private void accelerate(boolean forward) {
        if (forward && velocity < maxVelocity && allowedToDrive) {
            velocity += acceleration;
        } else if (!forward && velocity > 0) {
            velocity -= acceleration * 2; // TODO: Maybe tweak this
            if (velocity < 0) {
                velocity = 0;
            }
        }
    }
    
    public void update(float dt, boolean isTouching) {
        accelerate(isTouching);
        reposition(new Vector2(0, getPosition().y + (this.velocity*dt)));
    }
    
    public float getAcceleration() {
        return this.acceleration;
    }
    
    public float getVelocity() {
        return this.velocity;
    }

    public float getMaxVelocity() {
        return this.maxVelocity;
    }

    public void setMaxVelocity(int newMaxVelocity) {
        this.maxVelocity = newMaxVelocity;
    }

    public boolean getAllowedToDrive() {
        return allowedToDrive;
    }
    
    // Allow or disallow a car to drive
    public void canDrive(boolean allowed) {
        if (!allowed) {
            this.velocity = 0;
        }
        this.allowedToDrive = allowed;
    }

    public void dispose() {
        getTexture().dispose();
    }
}
