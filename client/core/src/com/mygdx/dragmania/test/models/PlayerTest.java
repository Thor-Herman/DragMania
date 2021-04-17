package com.mygdx.dragmania.test.models;

import com.mygdx.dragmania.models.Car;
import com.mygdx.dragmania.models.Player;
import com.mygdx.dragmania.models.Policeman;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class PlayerTest {

    private Player player;

    @Before
    public void setUp() {
        player = new Player("username");

    }

    @After
    public void tearDown() {
        player = null;
    }

    @Test
    public void testGameIsUp() {
        assertEquals(0, player.getTotalLosses());
        assertEquals(0, player.getTotalWins());
        player.gameIsUp(true);
        assertEquals(1, player.getTotalWins());
        assertEquals(0, player.getTotalLosses());
        player.gameIsUp(false);
        assertEquals(1, player.getTotalWins());
        assertEquals(1, player.getTotalLosses());

    }
}
