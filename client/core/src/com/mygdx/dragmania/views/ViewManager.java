package com.mygdx.dragmania.views;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

public class ViewManager {

    private Stack<View> views;
    private static ViewManager instance;

    public ViewManager() {
        views = new Stack<View>();

    }

    public void push(View view) {
        views.push(view);
    }

    // Dispose must be implemented in views
    public void pop() {
        views.pop().dispose();
    }

    public void render(float dt){
        views.peek().render(dt);
    }    

    public static ViewManager getInstance() {
        if (instance == null) instance = new ViewManager();
        return instance;
    }
}
