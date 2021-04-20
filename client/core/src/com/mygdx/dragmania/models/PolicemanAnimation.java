package com.mygdx.dragmania.models;

import com.badlogic.gdx.graphics.Texture;

public class PolicemanAnimation {

    private Texture turnedAway;
    private Texture turnedTowards;
    private Texture turnedSideways;

    public PolicemanAnimation() {
        turnedAway = new Texture("policeman_turned_away.png");
        turnedTowards = new Texture("policeman_turned_towards.png");
        turnedSideways = new Texture("policeman_turned_sideways.png");

    }

    public Texture getTurnedAwayAnimation() {
        return turnedAway;
    }

    public Texture getTurnedTowardsAnimation() {
        return turnedTowards;
    }

    public Texture getTurnedSidewaysAnimation() {
        return turnedSideways;
    }

}
