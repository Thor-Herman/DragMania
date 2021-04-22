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

    public Pedestrian(Vector2 startPosition, float xVelocity, Texture texture) {
        super(startPosition, texture);
        this.xVelocity = xVelocity;
    }

    public Pedestrian(Vector2 startPosition, float xVelocity, float width, float height) {
        super(startPosition, width, height);
        this.xVelocity = xVelocity;
    }
  
    public void update(float dt) {
        Vector2 newPosition = new Vector2(getPosition().x + getXVelocity()*dt, getPosition().y + getYVelocity()*dt);
        reposition(newPosition);
    }

    public float getXVelocity() {
        return this.xVelocity;
    }

    public float getYVelocity() {
        return this.yVelocity;
    }

    public void setYVelocity(float newVelocity) {
        this.yVelocity = newVelocity;
    }

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
