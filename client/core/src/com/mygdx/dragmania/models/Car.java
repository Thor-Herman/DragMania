package com.mygdx.dragmania.models;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Texture;

import com.utilities.Collidable;
import com.utilities.CarSound;

public class Car extends Collidable {
	
    // private Map<CarSound, Sound> sounds;
    private int acceleration;
    private int velocity;
    private int maxVelocity;
    
    public Car(Texture texture, int acceleration, int maxVelocity) {
        super(texture);
        this.velocity = 0;
        this.maxVelocity = maxVelocity;
        this.acceleration = acceleration;
        //this.sounds = sounds;
    }

    public Car(float width, float height, int acceleration, int maxVelocity) {
        super(width, height);
        this.velocity = 0;
        this.maxVelocity = maxVelocity;
        this.acceleration = acceleration;
        //this.sounds = sounds;
    }

	// Accelerates when forward boolean is true. Decelerates when false.
    private void accelerate(boolean forward) {
        if (forward && velocity < maxVelocity) {
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

    @Override
    public boolean collides (Collidable collidable){
        boolean collided = super.collides(collidable);
        if (collided) {
            System.out.println("The car collided");

            // Play crash sound
            //Sound crashSound = sounds.get(CarSound.CRASH);
            //crashSound.play();
            // System.out.println("Played sound");
        }
        return collided;
    }
}
