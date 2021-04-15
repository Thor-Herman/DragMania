package com.mygdx.dragmania.controllers;

public class MetaController extends Controller {

    private static MetaController instance = new MetaController();

    private MetaController() {}

    public static MetaController getInstance() {
        return instance;
    }
}
