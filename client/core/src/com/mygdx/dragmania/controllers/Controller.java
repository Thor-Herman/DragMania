package com.mygdx.dragmania.controllers;

public abstract class Controller {

    protected MetaController metaController = MetaController.getInstance();

    public void back() {
        metaController.pop();
    }
}
