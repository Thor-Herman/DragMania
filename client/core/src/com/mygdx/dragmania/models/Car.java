package com.mygdx.dragmania.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Texture;

import com.utilities.Collidable;

public class Car extends Collidable {
	
    private static Vector2 startPosition = new Vector2(40, 5); // TODO: Adjust this later

    private int acceleration;
    private float velocity = 0;
    private int maxVelocity;
    private boolean allowedToDrive = true;

    public boolean getDrivingStatus() {
        return this.allowedToDrive;
    }
    
    public Car(Texture texture, int acceleration, int maxVelocity) {
        super(startPosition, texture);
        this.maxVelocity = maxVelocity;
        this.acceleration = acceleration;
    }

    public Car(float width, float height, int acceleration, int maxVelocity) {
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
            velocity--; // TODO: Maybe tweak this
        }
    }
    
    public void update(float dt, boolean isTouching) {
        accelerate(isTouching);
        Vector2 newPosition = new Vector2(getPosition().x, getPosition().y + this.velocity*dt);
        reposition(newPosition);
    }
    
    public int getAcceleration() {
        return this.acceleration;
    }
    
    public float getVelocity() {
        return this.velocity;
    }

    public int getMaxVelocity() {
        return this.maxVelocity;
    }

    public void setMaxVelocity(int newMaxVelocity) {
        this.maxVelocity = newMaxVelocity;
    }
    
    // Allow or disallow a car to drive
    public void canDrive(boolean allowed) {
        if (!allowed) {
            this.velocity = 0;
        }
        this.allowedToDrive = allowed;
    }
}
