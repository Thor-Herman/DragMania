package com.mygdx.dragmania.controllers;

public class MetaController extends Controller {

    private static MetaController instance;

    private MetaController() {}

    public static MetaController getInstance() {
        if (instance == null) instance = new MetaController();
        return instance;
    }
}
