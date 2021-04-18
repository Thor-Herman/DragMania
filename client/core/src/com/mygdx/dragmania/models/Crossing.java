package com.mygdx.dragmania.models;


public class Crossing {

    
    //private Pedestrian pedestrian = null;
    private float yPos;

    public Crossing(float yPos) {
        this.yPos = yPos;
        //this.pedestrian = pedestrian;
    }

    public void reposition(float newPos) {
        this.yPos = newPos;
    }

    public void update(float dt) {

    }

    
}
