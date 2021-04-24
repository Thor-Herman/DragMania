package com.mygdx.dragmania.controllers;

import com.mygdx.dragmania.views.View;

import java.util.Stack;

public class ViewManager {

    private Stack<View> views;
    private static ViewManager instance;

    public ViewManager() {
        views = new Stack<View>();

    }

    public void push(View view) {

        System.out.println(views.size());
        views.push(view);
        System.out.println(views.size());

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