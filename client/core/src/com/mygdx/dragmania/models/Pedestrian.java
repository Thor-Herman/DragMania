package com.mygdx.dragmania.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.utilities.CarCrashListener;
import com.utilities.Collidable;

import java.util.ArrayList;
import java.util.Collection;

public class Pedestrian extends Collidable {

    private float xVelocity;
    private float yVelocity;
    private Collection<CarCrashListener> carCrashListeners = new ArrayList<>();

    public Pedestrian(Vector2 startPosition, float xVelocity, float yVelocity, Texture texture) {
        super(startPosition, texture);
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
    }

    public Pedestrian(Vector2 startPosition, float xVelocity, float yVelocity, float width, float height) {
        super(startPosition, width, height);
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
    }
  
    public void update(float dt) {
        Vector2 newPosition = new Vector2(getPosition().x + this.xVelocity*dt, getPosition().y - this.yVelocity*dt);
        reposition(newPosition);
    }

    public float getxVelocity() {
        return this.xVelocity;
    }

    public float getyVelocity() {
        return this.yVelocity;
    }
    /*
    public void setyVelocity(float newVelocity) {
        this.yVelocity = newVelocity;
    }
    */


    public void addCarCrashListener(CarCrashListener carCrashListener) {
        carCrashListeners.add(carCrashListener);
    }

    public void removeCarCrashListener(CarCrashListener carCrashListener) {
        carCrashListeners.remove(carCrashListener);
    }

    public void fireCarCrashAlarm() {
        for (CarCrashListener carCrashListener : carCrashListeners) {
            carCrashListener.carCrashAlarm(this);
        }
    }
}
