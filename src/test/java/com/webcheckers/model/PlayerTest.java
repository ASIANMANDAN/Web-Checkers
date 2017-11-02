package com.webcheckers.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * The unit test suite for the {@link Player} component.
 *
 * @author Dan Wang
 */
public class PlayerTest {

    private static final String username = "username";

    private static Player CuT;

    @Before
    public void testSetup(){
        CuT = new Player(username);
    }

    /**
     * Test the {@link Player#getUsername()} method.
     */
    @Test
    public void test_get_username(){
        assertEquals(username, CuT.getUsername());
    }

    /**
     * Test the {@link Player#equals(Object)} method.
     */
    @Test
    public void test_equals(){
        Player CuT2 = new Player(username);
        Player CuT3 = new Player("Dan");
        //Test two players with the same username are equal.
        assertTrue(CuT.equals(CuT2));
        //Test two players with different usernames are not equal.
        assertFalse(CuT.equals(CuT3));
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
