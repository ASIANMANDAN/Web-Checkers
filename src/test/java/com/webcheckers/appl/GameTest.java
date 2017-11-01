package com.webcheckers.appl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import com.webcheckers.model.Player;
import com.webcheckers.model.board.Board;
import com.webcheckers.model.board.Space;
import org.junit.Before;
import org.junit.Test;

/**
 * The unit test suite for the {@link Game} component.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class GameTest {

    private Player red;
    private Player white;
    //Player is not in the CuT
    private Player notInGame;
    private Space[][] gameBoard;
    private Game CuT;

    @Before
    public void testSetup() {
        red = mock(Player.class);
        white = mock(Player.class);
        notInGame = mock(Player.class);
        Board board = mock(Board.class);
        board.newGame();
        gameBoard = board.getBoard();

        try {
            CuT = new Game(red, white);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Test that you can construct a new Game.
     */
    @Test
    public void test_create_Game() {
        try {
            new Game(red, white);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Test that Game returns the right red Player.
     */
    @Test
    public void test_getRed() {
        Player returned = CuT.getRedPlayer();
        assertNotNull(returned);
        assertEquals(red, returned);
        assertNotEquals(white, returned);
    }

    /**
     * Test that Game returns the right white Player.
     */
    @Test
    public void test_getWhite() {
        Player returned = CuT.getWhitePlayer();
        assertNotNull(returned);
        assertEquals(white, returned);
        assertNotEquals(red, returned);
    }

    /**
     * Test that Game returns the right opponent for each Player.
     */
    @Test
    public void test_getOpponent_0() {
        Player returned = CuT.getOpponent(red);
        assertNotNull(returned);
        assertEquals(white, returned);
        assertNotEquals(red, returned);

        returned = CuT.getOpponent(white);
        assertNotNull(returned);
        assertEquals(red, returned);
        assertNotEquals(white, returned);
    }

    /**
     * Test that there is no opponent for a Player who is not in a game.
     */
    @Test
    public void test_getOpponent_1() {
        assertNull(CuT.getOpponent(notInGame));
    }

    /**
     * Test that both players are in a game.
     */
    @Test
    public void test_playerInGame_0() {
        assertTrue(CuT.playerInGame(red));
        assertTrue(CuT.playerInGame(white));
    }

    /**
     * Test that another player is not in the game.
     */
    @Test
    public void test_playerInGame_1() {
        assertNotNull(notInGame);
        assertFalse(CuT.playerInGame(notInGame));
    }

    /**
     * Test that players can be removed from games.
     */
    @Test
    public void test_removePlayer() {
        CuT.removePlayer(red);
        assertNull(CuT.getRedPlayer());

        CuT.removePlayer(white);
        assertNull(CuT.getWhitePlayer());
    }
}
