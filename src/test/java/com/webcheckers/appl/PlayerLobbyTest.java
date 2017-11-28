package com.webcheckers.appl;

import com.webcheckers.model.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The unit test suite for the {@link PlayerLobby} component.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class PlayerLobbyTest {

    private PlayerLobby CuT;

    private Player player;

    @Before
    public void testSetup(){
        player = mock(Player.class);
        CuT = new PlayerLobby();
    }

    /**
     * Test the sign in method will return INVALID when a username with " is entered
     */
    @Test
    public void test_sign_in_invalid(){
        String name = "\"";
        assertEquals(PlayerLobby.InputResult.INVALID, CuT.signIn(name));
    }

    /**
     * Test the sign in method will return SPACE when a username with a space
     * is entered
     */
    @Test
    public void test_sign_in_space() {
        String name = "ab ba";
        assertEquals(PlayerLobby.InputResult.SPACE, CuT.signIn(name));
    }

    /**
     * Test the sign in method will return EMPTY when a blank username is entered.
     */
    @Test
    public void test_sign_in_empty(){
        String name = "";
        assertEquals(PlayerLobby.InputResult.EMPTY, CuT.signIn(name));
    }

    /**
     * Test sign in will return TAKEN when a username in use is selected.
     */
    @Test
    public void test_sign_in_taken(){
        String name = "username";
        String name2 = name;
        CuT.signIn(name);
        assertEquals(PlayerLobby.InputResult.TAKEN, CuT.signIn(name2));
    }

    /**
     * Test sign in will return ACCEPTED when a valid username is entered
     */
    @Test
    public void test_sign_in_accepted(){
        String name = "username";
        assertEquals(PlayerLobby.InputResult.ACCEPTED, CuT.signIn(name));
    }

    /**
     * Test sign out will remove a player from the lobby and lower the number of active players.
     */
    @Test
    public void test_sign_out(){
        when(player.getUsername()).thenReturn("username");
        String name = "username";

        CuT.signIn(name);

        //Check to make sure player was properly signed in.
        assertEquals("1", CuT.getNumOfUsers());

        CuT.signOut(player);
        assertEquals("0", CuT.getNumOfUsers());
        assertTrue(CuT.getPlayerLobby().isEmpty());
    }

    /**
     * Test getNumOfUsers returns the correct number of signed in players
     */
    @Test
    public void test_get_num_of_players(){
        String name1 = "Dan";
        String name2 = "Kevin";

        assertEquals("0", CuT.getNumOfUsers());
        CuT.signIn(name1);
        assertEquals("1", CuT.getNumOfUsers());
        CuT.signIn(name2);
        assertEquals("2", CuT.getNumOfUsers());
    }

    /**
     * Test nameTaken will return the correct result for if a name is in use.
     */
    @Test
    public void test_name_taken(){
        String name = "username";

        assertFalse(CuT.nameTaken(name));
        CuT.signIn(name);
        assertTrue(CuT.nameTaken(name));
    }

    /**
     * Test isValidUsername will return the correct result for if a name contains ".
     */
    @Test
    public void test_is_valid_username(){
        String name1 = "\"";
        String name2 = "name";

        assertFalse(CuT.isValidUsername(name1));
        assertTrue(CuT.isValidUsername(name2));
    }

    /**
     * Test to see if get user list returns an accurate set of usernames.
     */
    @Test
    public void test_get_user_list(){
        String name = "name";
        String name2 = "username";

        assertEquals(0, CuT.getUserList().size());
        assertTrue(CuT.getUserList().isEmpty());

        CuT.signIn(name);
        assertEquals(1, CuT.getUserList().size());
        assertTrue(CuT.getUserList().contains(name));

        CuT.signIn(name2);
        assertEquals(2, CuT.getUserList().size());
        assertTrue(CuT.getUserList().contains(name));
        assertTrue(CuT.getUserList().contains(name2));
    }

    /**
     * Test to see if getPlayerLobby returns the correct map of players logged in.
     */
    @Test
    public void test_get_player_lobby(){
        String name = "name";
        assertNotNull(CuT.getPlayerLobby());

        CuT.signIn(name);
        assertTrue(CuT.getPlayerLobby() instanceof Map);
        assertEquals(1, CuT.getPlayerLobby().size());
        assertTrue(CuT.getPlayerLobby().containsKey(name));
    }

    /**
     * Test the enum values of InputResult.
     */
    @Test
    public void test_enum(){
        assertEquals(PlayerLobby.InputResult.ACCEPTED, PlayerLobby.InputResult.valueOf("ACCEPTED"));
        assertEquals(PlayerLobby.InputResult.INVALID, PlayerLobby.InputResult.valueOf("INVALID"));
        assertEquals(PlayerLobby.InputResult.EMPTY, PlayerLobby.InputResult.valueOf("EMPTY"));
        assertEquals(PlayerLobby.InputResult.TAKEN, PlayerLobby.InputResult.valueOf("TAKEN"));
    }
}
