package com.mygdx.dragmania.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Policeman {

    private boolean hasTurned;
    private PolicemanAnimation policemanAnimation;
    private Vector2 position;

    private ArrayList<Integer> policemanTurnPositions;
    private ArrayList<Integer> policemanFakeTurnPositions;

    private Car car;
    private int currentTurnPositionIndex;

    private long policemanTurnStart;
    private long policemanTurnStop;
    private long timePenaltyStart;
    private long timePenaltyStop;

    // Duration in milliseconds
    private int timePenaltyDuration;
    private int policeTurnDuration;

    // Not correct position
    public static final int xPos = 200;
    public static final int yPos = 800;

    public Policeman(ArrayList<Integer> policemanTurnPositions, ArrayList<Integer> policemanFakeTurnPositions, Car car) {

        this.position = new Vector2(xPos, yPos);
        this.hasTurned = false;
        // The line below can be commented out to run PolicemanTest
        this.policemanAnimation = new PolicemanAnimation();
        this.policemanTurnPositions = policemanTurnPositions;
        this.policemanFakeTurnPositions = policemanFakeTurnPositions;
        this.car = car;
        currentTurnPositionIndex = 0;
        policemanTurnStart = 0;
        timePenaltyStart = 0;
        timePenaltyDuration = 4000;
        policeTurnDuration = 2000;

    }

    public void update(float dt) {
        checkIfPolicemanShouldTurn();
        checkIfAllowedToDrive();
    }

    public void checkIfAllowedToDrive() {
        checkTimePenalty();
        checkPolicemanTurn();

    }

    // Check if the policeman has turned or if he should turn back
    // Allow or disallow car to drive
    public void checkPolicemanTurn() {
        // When policeman turns towards car, start timer and prevent car from driving
        if (hasTurned && policemanTurnStart == 0) {
            car.canDrive(false);
            policemanTurnStart = System.currentTimeMillis();
        }

        policemanTurnStop = System.currentTimeMillis();

        // Check if two seconds has gone since policeman turn
        // Allow car to drive again
        if (policemanTurnStart > 0 && (policemanTurnStop-policemanTurnStart) > policeTurnDuration) {
            toggleTurn(false);
            car.canDrive(true);
            policemanTurnStart = 0;
        }
    }

    // Check if player should receive timepenalty or if the timepenalty is up
    public void checkTimePenalty() {
        // Start timer for timepenalty
        if (car.getVelocity() > 0 && hasTurned && timePenaltyStart == 0) {
            car.canDrive(false);
            timePenaltyStart = System.currentTimeMillis();
        }

        timePenaltyStop = System.currentTimeMillis();

        // Check if timepenalty is up
        if (timePenaltyStart > 0 && (timePenaltyStop-timePenaltyStart) > timePenaltyDuration) {
            car.canDrive(true);
            timePenaltyStart = 0;
        }

    }

    // Use turn positions and car position to determine if the policeman should turn towards player
    public void checkIfPolicemanShouldTurn() {
        if (car.getPosition().y > policemanTurnPositions.get(currentTurnPositionIndex) && !hasTurned) {
            toggleTurn(true);
            currentTurnPositionIndex++;
        }
    }

    public void toggleTurn(boolean turnedTowards) {
        hasTurned = turnedTowards;
    }

    public boolean getHasTurned() {
        return hasTurned;
    }

    public Texture getAnimation() {
        if (hasTurned) {
            return this.policemanAnimation.getTurnedTowardsAnimation();
        }
        else {
            return this.policemanAnimation.getTurnedAwayAnimation();
        }
    }

    public Vector2 getPosition() {
        return position;
    }
}
