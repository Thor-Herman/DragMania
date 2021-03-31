package com.mygdx.dragmania.models;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

public class PolicemanAnimation {
    private HashMap<String, Texture> animations;

    public PolicemanAnimation() {
        animations.put("TurnedAway", "bilde1.jpg");
        animations.put("TurnedTowards", "bilde2.jpg");
    }

    public Texture getAnimation(String animation) {
        return animations.get(animation);
    }
}
