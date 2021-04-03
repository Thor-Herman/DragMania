package com.mygdx.dragmania.models;

import com.badlogic.gdx.math.Vector2;
import com.utilities.Collidable;
import com.badlogic.gdx.graphics.Texture;

public class Car extends Collidable {
	
    private int velocity;
    private int maxVelocity;
    
    public Car(int maxVelocity, Texture texture) {
        super(texture);
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
        reposition(new Vector2(0, getPosition().y + this.velocity*dt));
    }
    
    public int getVelocity() {
        return this.velocity;
    }

    public int getMaxVelocity() {
        return this.maxVelocity;
    }
}
