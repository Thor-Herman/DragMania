package com.mygdx.dragmania.models;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Texture;

public class Car {
	
    private Vector2 position;
    private Vector2 velocity;
    private Texture carTexture;
    private static final int MAXVELOCITY = 5;
    
    public Car() {
        this.position = new Vector2(0, 0);
        this.velocity = new Vector2(0, 0);
    }

	// Accelerates when direction is positive. Deaccelerates when direction is negative.
    public void accelerate(int direction) {
        if (direction > 0 && velocity.y < MAXVELOCITY) {
            velocity.add(0, 1);
        } else if (direction < 0 && velocity.y > 0) {
            velocity.add(0, -1);
        }
    }
    
    public void update(float dt, boolean isTouching) {
        if (isTouching) {
            accelerate(1);
        } else {
            accelerate(-1);            
        }

        position.add(velocity.x*dt, velocity.y*dt);
    }

    public Texture getTexture() {
        return this.carTexture;
    }
    
    public Vector2 getPosition() {
        return this.position;
    }

    public Vector2 getVelocity() {
        return this.velocity;
    }
    
    @Override
    public String toString() {
        return "Pos: " + getPosition().y + ", velocity: " + getVelocity().y;
    }
}
