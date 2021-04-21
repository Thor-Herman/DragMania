package com.mygdx.dragmania.controllers;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.FrameworkMessage.KeepAlive;
import com.utilities.messages.GameMapMessage;
import com.utilities.messages.Score;

public class GameListener extends Listener {

    private GameController controller = GameController.getInstance();

    public void received(Connection connection, Object object) {
        if (object instanceof KeepAlive)
            return;
        else if (object instanceof Score)
            updateOpponentScore(object);
        else if (object instanceof GameMapMessage)
            setGameMap(object);
    }

    private void updateOpponentScore(Object object) {
        Score score = (Score) object;
        controller.setOpponentScore((int) score.score); // TODO: Make score an int
    }

    private void setGameMap(Object object) {
        GameMapMessage map = (GameMapMessage) object;
        controller.receiveGameMap(map);
    }

    public void disconnected(Connection connection) {
        controller.disconnected();
    }
}
