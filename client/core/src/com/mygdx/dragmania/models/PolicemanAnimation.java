package com.mygdx.dragmania.models;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

public class PolicemanAnimation {
    private HashMap<String, Texture> animations;

    public PolicemanAnimation() {
        Texture test1 = new Texture("8bit-pikachu.png");
        Texture test2 = new Texture("mickey_mouse.png");
        animations.put("TurnedAway", test1);
        animations.put("TurnedTowards", test2);
    }

    public Texture getAnimation(String animation) {
        return animations.get(animation);
    }
}
