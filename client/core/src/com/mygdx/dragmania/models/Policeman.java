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
    private long policemanTurnStart;
    private long policemanTurnStop;
    private long timePenaltyStart;
    private long timePenaltyStop;
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
        policemanTurnStart = 0;
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
        checkTimePenalty();
        checkPolicemanTurn();

    }

    public void checkPolicemanTurn() {
        // When policeman turns towards car, start timer and prevent car from driving
        if (hasTurned && policemanTurnStart == 0) {
            car.canDrive(false);
            policemanTurnStart = System.currentTimeMillis();
        }

        policemanTurnStop = System.currentTimeMillis();

        // Check if two seconds has gone since policeman turn
        // Allow car to drive again
        if (policemanTurnStart > 0 && (policemanTurnStop-policemanTurnStart) > 2000) {
            toggleTurn();
            car.canDrive(true);
            policemanTurnStart = 0;
        }
    }

    public void checkTimePenalty() {
        // Start timer for the duration of the policeman turn
        if (car.getVelocity() > 0 && hasTurned && timePenaltyStart == 0) {
            car.canDrive(false);
            timePenaltyStart = System.currentTimeMillis();
        }

        timePenaltyStop = System.currentTimeMillis();

        // Check if timepenalty is up
        if (timePenaltyStart > 0 && (timePenaltyStop-timePenaltyStart) > 4000) {
            car.canDrive(true);
            timePenaltyStart = 0;
        }

    }

    public void toggleTurn() {
        hasTurned = !hasTurned;
    }

    public boolean getHasTurned() {
        return hasTurned;
    }

    public Texture getAnimation() {
        if (hasTurned) {
            return policemanAnimation.getAnimation("TurnedTowards");
        }
        else {
            return policemanAnimation.getAnimation("TurnedAway");
        }
    }

    public Vector2 getPosition() {
        return position;
    }
}
