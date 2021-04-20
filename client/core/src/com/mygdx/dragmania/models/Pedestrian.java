package com.mygdx.dragmania.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.utilities.CarCrashListener;
import com.utilities.Collidable;

import java.util.ArrayList;
import java.util.Collection;

public class Pedestrian extends Collidable {

    private float velocity;
    private Collection<CarCrashListener> carCrashListeners = new ArrayList<>();

    public Pedestrian(Vector2 startPosition, float velocity, Texture texture) {
        super(startPosition, texture); 
        this.velocity = velocity;
    }

    public Pedestrian(Vector2 startPosition, float velocity, float width, float height) {
        super(startPosition, width, height);
        this.velocity = velocity;
    }
  
    public void update(float dt) {
        Vector2 newPosition = new Vector2(getPosition().x + this.velocity*dt, getPosition().y);
        reposition(newPosition);
    }

    public float getVelocity() {
        return this.velocity;
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
