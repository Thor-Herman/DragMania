package com.mygdx.dragmania.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Texture;

import com.utilities.Collidable;

public class Car extends Collidable {
	
    private int acceleration;
    private int velocity;
    private int maxVelocity;
    private boolean allowedToDrive;
    
    public Car(Texture texture, int acceleration, int maxVelocity) {
        super(texture);
        this.velocity = 0;
        this.maxVelocity = maxVelocity;
        this.acceleration = acceleration;
        this.allowedToDrive = true;
    }

    public Car(float width, float height, int acceleration, int maxVelocity) {
        super(width, height);
        this.velocity = 0;
        this.maxVelocity = maxVelocity;
        this.acceleration = acceleration;
        this.allowedToDrive = true;
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
        reposition(new Vector2(0, getPosition().y + this.velocity*dt));
    }
    
    public int getAcceleration() {
        return this.acceleration;
    }
    
    public int getVelocity() {
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
