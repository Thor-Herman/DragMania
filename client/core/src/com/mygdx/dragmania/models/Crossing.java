package com.mygdx.dragmania.models;


public class Crossing {

    private float yPos;

    public Crossing(float yPos) {
        this.yPos = yPos;
    }

    public void reposition(float newPos) {
        this.yPos = newPos;
    }

    public void update(float dt) {

    }
}
