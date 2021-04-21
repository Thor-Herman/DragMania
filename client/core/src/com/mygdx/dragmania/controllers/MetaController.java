package com.mygdx.dragmania.controllers;

import java.util.Stack;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.utilities.GameClient;

public class MetaController extends Game {

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
        if (controllers.size() > 0) controllers.pop();
    }

    public void push(Controller controller) {
        controllers.push(controller);
    }

    @Override
    public void create() {
        LobbyController lobbyController = LobbyController.getInstance();
        GameController gameController = GameController.getInstance();
        lobbyController.connectToServer();
        lobbyController.createGame("TH");
        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Create finished");
    }

    @Override
    public void render() {
        System.out.println("Rendering...");
        float dt = Gdx.graphics.getDeltaTime();
        GameController.getInstance().update(dt, true);
    }
}
