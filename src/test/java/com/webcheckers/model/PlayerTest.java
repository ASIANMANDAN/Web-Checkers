package com.webcheckers.model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * The unit test suite for the {@link Player} component.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class PlayerTest {

    private String userName = "user";

    /**
     * Test the constructor.
     */
    @Test
    public void test_newPlayer(){
        new Player(userName);
    }

    /**
     * Test the getUsername method.
     */
    @Test
    public void test_getUN(){
        Player pl = new Player(userName);
        assertEquals(userName, pl.getUsername());
    }

    /**
     * Test that the equals correctly distinguishes between
     * different Player objects.
     */
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
        Player player = new Player(userName);
        assertTrue(player.equals(player));
    }

    /**
     * Test the {@link Player#equals(Object)} method with a different object.
     */
    @Test
    public void test_diffObject(){
        Player player = new Player(userName);
        String playerString = userName;

        assertFalse(player.equals(playerString));
    }
}
