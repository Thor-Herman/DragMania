package com.mygdx.dragmania.test.models;

import com.mygdx.dragmania.models.LobbyModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import static org.junit.Assert.assertEquals;

public class LobbyModelTest {

    private LobbyModel lobby;

    @Before
    public void setUp() {
        lobby = new LobbyModel(1, true);
    }

    @Test(expected = IllegalStateException.class)
    public void testCannotAddMoreThanMaxPlayers() {
        for (int i = 0; i < LobbyModel.MAX_PLAYER_COUNT; i++) {
            lobby.playerJoinedLobby("username");
        }
        lobby.playerJoinedLobby("username");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCannotGetUsernamesOutOfBounds() {
        lobby.getUsername(LobbyModel.MAX_PLAYER_COUNT + 1);
    }

}
