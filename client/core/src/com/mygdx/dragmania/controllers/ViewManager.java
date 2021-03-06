package com.mygdx.dragmania.controllers;

import com.mygdx.dragmania.views.View;

import java.util.Stack;

public class ViewManager {

    private Stack<View> views;
    private static ViewManager instance;

    private ViewManager() {
        views = new Stack<View>();
    }

    public void push(View view) {
        if (views.size() > 0) views.pop();
        views.push(view);
    }

    public void disposeView() {
        views.peek().dispose();
    }

    public void render(float dt) {
        views.peek().render(dt);
    }

    public static ViewManager getInstance() {
        if (instance == null)
            instance = new ViewManager();
        return instance;
    }
}
