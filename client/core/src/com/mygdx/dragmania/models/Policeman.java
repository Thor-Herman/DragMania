package com.mygdx.dragmania.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import java.time.Instant;

import java.util.ArrayList;

public class Policeman {
    private boolean hasTurned;
    private PolicemanAnimation policemanAnimation;
    private Vector2 position;
    private ArrayList<Integer> policeManTurnPositions;
    private ArrayList<Integer> policeManFakeTurnPositions;
    private Car car;
    private int currentTurnPositionIndex;
    private int turnTimeCounter;
    // Not correct position
    public static final int xPos = 200;
    public static final int yPos = 800;

    public Policeman(ArrayList<Integer> policeManTurnPositions, ArrayList<Integer> policeManFakeTurnPositions, Car car) {
        this.position = new Vector2(xPos, yPos);
        this.hasTurned = false;
        this.policemanAnimation = new PolicemanAnimation();
        this.policeManTurnPositions = policeManTurnPositions;
        this.policeManFakeTurnPositions = policeManFakeTurnPositions;
        this.car = car;
        currentTurnPositionIndex = 0;
        turnTimeCounter = 0;
    }

    public void update(float dt) {
        turnTimeCounter++;
        if(car.getPosition() > policeManTurnPositions.get(currentTurnPositionIndex)) {
            toggleTurn();
            currentTurnPositionIndex++;
            turnTimeCounter = 0;
        }
        else if (turnTimeCounter > 100 && hasTurned) {
            toggleTurn();
        }
        checkIfAllowedToDrive();
    }

    public void checkIfAllowedToDrive() {
        if (car.getVelocity() > 0 && hasTurned) {
            car.canDrive(false);
            //Start counter
        }

        //if counter > 2sek && !allowedtodrive
        //allow to drive
    }

    public void toggleTurn() {
        hasTurned = !hasTurned;
    }

    public boolean getHasTurned() {
        return hasTurned;
    }

    public Texture getAnimation(String animation) {
        return policemanAnimation.getAnimation(animation);
    }

    public Vector2 getPosition() {
        return position;
    }
}
