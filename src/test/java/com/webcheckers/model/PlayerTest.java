package com.webcheckers.model;

import org.junit.Test;
import static org.junit.Assert.*;
import com.webcheckers.model.Player;

/**
 * file: checkers-app
 * language: java
 * author: erl3193@rit.edu Emily Lederman
 * description:
 */
public class PlayerTest {

    String userName = "user";

    @Test
    public void test_newPlayer(){
        new Player(userName);
    }

    @Test
    public void test_getUN(){
        Player pl = new Player(userName);
        assertEquals(userName, pl.getUsername());
    }

    @Test
    public void test_equals(){
        Player p1 = new Player(userName);
        Player p2 = new Player(userName);
        assertTrue(p1.equals(p2));
    }

    /**
     * Test the {@link Player#equals(Object)} method with the same Player object.
     */
    @Test
    public void test_samePlayerObject(){
        Player player = new Player("playerName");
        assertTrue(player.equals(player));

    }

    /**
     * Test the {@link Player#equals(Object)} method with a different object.
     */
    @Test
    public void test_diffObject(){
        Player player = new Player("playerName");
        String playerString = "playerString";

        assertFalse(player.equals(playerString));
    }

}
