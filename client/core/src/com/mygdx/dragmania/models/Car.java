package com.mygdx.dragmania.models;

public class Car {
	
    private int position;
    private int velocity;
    private int maxVelocity;
    private boolean allowedToDrive;
    
    public Car(int maxVelocity) {
        this.position = 0;
        this.velocity = 0;
        this.maxVelocity = maxVelocity;
        allowedToDrive = true;
    }

	// Accelerates when forward boolean is true. Decelerates when false.
    // Also checks if the car is allowed to drive
    private void accelerate(boolean forward) {
        if (forward && velocity < maxVelocity && allowedToDrive) {
            velocity++;
        } else if (!forward && velocity > 0) {
            velocity--;
        }
    }
    
    public void update(float dt, boolean isTouching) {
        accelerate(isTouching);
        position += velocity*dt;
    }

    public int getPosition() {
        return this.position;
    }
    
    public int getVelocity() {
        return this.velocity;
    }

    public int getMaxVelocity() {
        return this.maxVelocity;
    }

    // Allow or disallow a car to drive
    public void canDrive(boolean allowed) {
        if (!allowed) {
            this.velocity = 0;
        }
        this.allowedToDrive = allowed;
    }
}
