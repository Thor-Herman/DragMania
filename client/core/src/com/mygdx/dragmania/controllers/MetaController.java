package com.mygdx.dragmania.controllers;

import java.util.Stack;

public class MetaController {

    private Stack<Controller> controllers = new Stack<>();

    private static MetaController instance;

    private MetaController() {
    }

    public static MetaController getInstance() {
        if (instance == null)
            instance = new MetaController();
        return instance;
    }

    public void pop() {
        controllers.pop();
    }

    public void push(Controller controller) {
        controllers.push(controller);
    }
}
