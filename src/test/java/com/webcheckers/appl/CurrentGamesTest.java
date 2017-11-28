package com.webcheckers.appl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.webcheckers.model.Player;
import com.webcheckers.model.board.Board;
import com.webcheckers.model.board.Piece;
import com.webcheckers.model.board.Space;
import com.webcheckers.ui.boardView.Move;
import com.webcheckers.ui.boardView.Position;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

/**
 * The unit test suite for the {@link CurrentGames} component.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class CurrentGamesTest {

    private Player red;
    private Player white;
    private Player notInGame;
    private CurrentGames CuT;
    private Player nullPlayer;
    private Player oppOfNullPlayer;
    private Move move;

    @Before
    public void test_setUp() throws Exception {
        HashMap<Player, Game> cg = new HashMap<>();
        Game game1 = mock(Game.class);
        Game game2 = mock(Game.class);
        Game game3 = mock(Game.class);

        red = mock(Player.class);
        when(red.getUsername()).thenReturn("player");
        white = mock(Player.class);
        notInGame = mock(Player.class);

        oppOfNullPlayer = mock(Player.class);
        nullPlayer = mock(Player.class);


        //Add mocked games
        cg.put(red, game1);
        cg.put(white, game1);
        cg.put(red, game2);
        cg.put(white, game2);
        cg.put(red, game3);
        cg.put(white, game3);

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
     * Test that the toggleTurn method correctly toggles turns.
     */
    @Test
    public void test_toggleTurn() {
        CuT.toggleTurn(red);
        assertEquals(Board.ActiveColor.WHITE, CuT.getTurn(red));
        CuT.toggleTurn(red);
        assertEquals(Board.ActiveColor.RED, CuT.getTurn(red));
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
     * Test the getBoard method returns a correctly configured Board.
     *
     * @throws Exception occurs if the given column or row of a space
     * is greater or less than the bounds established by a standard
     * game board
     */
    @Test
    public void test_getBoard() throws Exception {
        Board CuT = new Board();
        CuT.newGame();
        int size = Board.size;
        Space[][] cutBoard = CuT.getBoard();

        //Check White Pieces
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < size; col++) {
                if (row == 0 || row == 2) {
                    if (col == 1 || col == 3 || col == 5 || col == 7) {
                        Piece comparePiece = new Piece(Piece.Color.WHITE, Piece.Type.SINGLE);
                        Space currentSpace = cutBoard[row][col];
                        Piece currentPiece = currentSpace.getPiece();
                        assertEquals(comparePiece, currentPiece);
                    }
                } else {
                    if (col == 0 || col == 2 || col == 4 || col == 6) {
                        Piece comparePiece = new Piece(Piece.Color.WHITE, Piece.Type.SINGLE);
                        Space currentSpace = cutBoard[row][col];
                        Piece currentPiece = currentSpace.getPiece();
                        assertEquals(comparePiece, currentPiece);
                    }
                }
            }
        }

        //Check Red Pieces
        for (int row = 5; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (row == 5 || row == 7) {
                    if (col == 0 || col == 2 || col == 4 || col == 6) {
                        Piece comparePiece = new Piece(Piece.Color.RED, Piece.Type.SINGLE);
                        Space currentSpace = cutBoard[row][col];
                        Piece currentPiece = currentSpace.getPiece();
                        assertEquals(comparePiece, currentPiece);
                    }
                } else {
                    if (col == 1 || col == 3 || col == 5 || col == 7) {
                        Piece comparePiece = new Piece(Piece.Color.RED, Piece.Type.SINGLE);
                        Space currentSpace = cutBoard[row][col];
                        Piece currentPiece = currentSpace.getPiece();
                        assertEquals(comparePiece, currentPiece);
                    }
                }
            }
        }

    }

    /**
     * Test that the makeMove method places a given Piece in the correct
     * location on the board;
     */
    @Test
    public void test_makeMove(){
        assertTrue(CuT.makeMove(red, move));
        CuT.removePlayer(nullPlayer);
        assertFalse(CuT.makeMove(nullPlayer, move));
    }
}
