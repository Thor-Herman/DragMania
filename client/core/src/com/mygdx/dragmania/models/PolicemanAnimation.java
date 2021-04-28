package com.mygdx.dragmania.models;

import com.badlogic.gdx.graphics.Texture;

public class PolicemanAnimation {

    private Texture turnedAway;
    private Texture turnedTowards;
    private Texture turnedSideways;

    public PolicemanAnimation() {
        turnedAway = new Texture("textures/policeman/policeman_turned_away.png");
        turnedTowards = new Texture("textures/policeman/policeman_turned_towards.png");
        turnedSideways = new Texture("textures/policeman/policeman_turned_sideways.png");
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

    public void dispose() {
        turnedSideways.dispose();
        turnedTowards.dispose();
        turnedAway.dispose();
    }
}
