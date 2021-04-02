package com.mygdx.dragmania.models;

public class Car {
	
    private int position;
    private int velocity;
    private int maxVelocity;
    
    public Car(int maxVelocity) {
        this.position = 0;
        this.velocity = 0;
        this.maxVelocity = maxVelocity;
    }

	// Accelerates when forward boolean is true. Decelerates when false.
    private void accelerate(boolean forward) {
        if (forward && velocity < maxVelocity) {
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
}
