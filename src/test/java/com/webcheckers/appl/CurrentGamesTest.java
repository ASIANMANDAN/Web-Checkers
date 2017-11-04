package com.webcheckers.appl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.webcheckers.model.Player;
import com.webcheckers.model.board.Board;
import com.webcheckers.model.board.Space;
import com.webcheckers.ui.boardView.Move;
import com.webcheckers.ui.boardView.Position;
import org.junit.Before;
import org.junit.Test;

import javax.swing.text.DefaultEditorKit;
import java.util.ArrayList;

/**
 * The unit test suite for the {@link CurrentGames} component.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class CurrentGamesTest {

    private Space[][] gameBoard;
    private Player red;
    private Player white;
    private Player notInGame;
    private CurrentGames CuT;
    private Player nullPlayer;
    private Player oppOfNullPlayer;
    private Move move;

    @Before
    public void test_setUp() throws Exception {
        ArrayList<Game> cg = new ArrayList<>();
        Game game1 = mock(Game.class);
        Game game2 = mock(Game.class);
        Game game3 = mock(Game.class);

        try {
            Board board = new Board();
            board.newGame();
            gameBoard = board.getBoard();
        } catch (Exception e) {
            e.printStackTrace();
        }

        red = mock(Player.class);
        when(red.getUsername()).thenReturn("player");
        white = mock(Player.class);
        notInGame = mock(Player.class);

        oppOfNullPlayer = mock(Player.class);
        nullPlayer = mock(Player.class);


        //Add mocked games
        cg.add(game1);
        cg.add(game2);
        cg.add(game3);

        CuT = new CurrentGames(cg);

        //Mocking a move
        move = mock(Move.class);
        Position start = new Position(0,1);
        Position end = new Position(1,1);

        when(move.getStart()).thenReturn(start);
        when(move.getEnd()).thenReturn(end);

        //Add mocked Players to a game
        //Also tests that adding a game is functional since the game
        //is required by most tests
        try {
            CuT.addGame(red, white);
            CuT.addGame(oppOfNullPlayer, nullPlayer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Test that a new CurrentGames object can be constructed.
     */
    @Test
    public void test_create_currentGames() {
        new CurrentGames();
    }

    /**
     * Test that Players are in tracked as being in game.
     */
    @Test
    public void test_playerInGame_0() {
        assertTrue(CuT.playerInGame(red));
        assertTrue(CuT.playerInGame(white));
    }

    /**
     * Test that another player is not in a game.
     */
    @Test
    public void test_playerInGame_1() {
        assertNotNull(notInGame);
        assertFalse(CuT.playerInGame(notInGame));
    }

    /**
     * Test that for players in a game there is an opponent
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
     * Test that for a player not in a game there is no associated opponent.
     */
    @Test
    public void test_getOpponent_1() {
        assertNull(CuT.getOpponent(notInGame));
    }

    /**
     * Test that the correct URL is generated for a given player
     */
    @Test
    public void test_createURL() throws Exception {
        String URL = "/game?opponent=player";
        assertEquals(URL, CuT.createURL(white));
        URL = "/";
        CuT.endGame(nullPlayer);
        assertEquals(URL, CuT.createURL(nullPlayer));

    }

    /**
     * Test that Game returns the right red Player.
     */
    @Test
    public void test_getRed() {
        Player returned = CuT.getRedPlayer(red);
        assertNotNull(returned);
        assertEquals(red, returned);
        assertNotEquals(white, returned);
    }

    /**
     * Test that Game returns the right white Player.
     */
    @Test
    public void test_getWhite() {
        Player returned = CuT.getWhitePlayer(white);
        assertNotNull(returned);
        assertEquals(white, returned);
        assertNotEquals(red, returned);
    }

    /**
     * Test that the correct turn is returned from the Board object.
     */
    @Test
    public void test_getTurn_0() {
        assertEquals(Board.ActiveColor.RED, CuT.getTurn(red));
        assertNotEquals(Board.ActiveColor.WHITE, CuT.getTurn(red));
    }

    /**
     * Test that no turn is returned for a player not in a game.
     */
    @Test
    public void test_getTurn_1() {
        assertNull(CuT.getTurn(notInGame));
    }

    /**
     * Test that players can be removed from the correct Game object.
     */
    @Test
    public void test_removePlayer_0() {
        CuT.removePlayer(red);
        assertNull(CuT.getOpponent(white));
        assertNull(CuT.getRedPlayer(red));
    }

    /**
     * Test that players can be removed from the correct Game object.
     */
    @Test
    public void test_removePlayer_1() {
        CuT.removePlayer(white);
        assertNull(CuT.getOpponent(red));
        assertNull(CuT.getWhitePlayer(white));
    }

    /**
     * Test that a Game object can be removed from the list of active Games.
     */
    @Test
    public void test_endGame() {
        CuT.endGame(white);
        assertFalse(CuT.playerInGame(white));
        assertNull(CuT.getOpponent(red));
    }

    @Test
    public void test_getBoard(){
        Space[][] board = CuT.getBoard(red);
        assertEquals(board, gameBoard);
        CuT.endGame(nullPlayer);
        Space[][] nullBoard = CuT.getBoard(nullPlayer);
        assertEquals(null, nullBoard);
    }

    @Test
    public void test_makeMove(){
        assertTrue(CuT.makeMove(red, move));
        CuT.endGame(nullPlayer);
        assertFalse(CuT.makeMove(nullPlayer, move));

    }



}
