package com.webcheckers.model.board;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.ui.boardView.Move;
import com.webcheckers.ui.boardView.Position;
import org.junit.Before;
import org.junit.Test;

/**
 *  Test for the board model.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class BoardTest {

    private Board CuT;

    private static Space[][] arrayBoard;
    private static Move move;

    @Before
    public void test_setup() throws Exception {
        Board board = new Board();
        arrayBoard = board.getBoard();

        //Mocking a move
        move = mock(Move.class);
        Position start = new Position(0,1);
        Position end = new Position(1,1);

        when(move.getStart()).thenReturn(start);
        when(move.getEnd()).thenReturn(end);

        CuT = new Board();
    }

    /**
     * Test the default constructor.
     *
     * @throws Exception occurs if the given column or row of a space
     * is greater or less than the bounds established by a standard
     * game board
     */
    @Test
    public void test_ctor_noArg() throws Exception {
        new Board();
    }

    /**
     * Test the constructor with arguments.
     *
     * @throws Exception occurs if the given column or row of a space
     * is greater or less than the bounds established by a standard
     * game board
     */
    @Test
    public void test_ctor_withArg() throws Exception {
        new Board(arrayBoard, Board.ActiveColor.RED);
    }

    /**
     * Tests the currentColor was initialized correctly.
     *
     * @throws Exception occurs if the given column or row of a space
     * is greater or less than the bounds established by a standard
     * game board
     */
    @Test
    public void test_currColor() throws Exception{
        Board CuT = new Board();
        assertEquals(Board.ActiveColor.RED, CuT.currentTurn);
    }

    /**
     * Tests to see if there is a valid empty board.
     *
     * @throws Exception occurs if the given column or row of a space
     * is greater or less than the bounds established by a standard
     * game board
     */
    @Test
    public void test_emptyBoard() throws Exception{
        Board CuT = new Board();
        int size = Board.size;
        Space[][] cutBoard = CuT.getBoard();

        for (int row = 0; row< size; row++){
            for (int col= 0; col< size; col++){
                if ((row%2 == 0 && col%2 == 1) || (row%2 == 1 && col%2 == 0)){
                    Space compareSpace = new Space(row, col, Space.Color.BLACK);
                    assertEquals(compareSpace, cutBoard[row][col]);
                }
                else{
                    Space compareSpace = new Space(row, col, Space.Color.WHITE);
                    assertEquals(compareSpace, cutBoard[row][col]);
                }

            }
        }

    }

    /**
     * Tests the newGame function to make sure the right pieces are in the right
     * places.
     *
     * @throws Exception occurs if the given column or row of a space
     * is greater or less than the bounds established by a standard
     * game board
     */
    @Test
    public void test_newGame() throws Exception {
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
     * Test that the makeMove method moves the given Piece to the correct
     * position on the Board.
     *
     * @throws Exception occurs if the given column or row of a space
     * is greater or less than the bounds established by a standard
     * game board
     */
    @Test
    public void test_makeMove() throws Exception{
        Board redTurnBoard = new Board(arrayBoard, Board.ActiveColor.RED);
        redTurnBoard.makeMove(move);
        Board whiteTurnBoard = new Board(arrayBoard, Board.ActiveColor.WHITE);
        whiteTurnBoard.makeMove(move);
    }

    /**
     * Test that the toggleTurn method correctly toggles turns.
     */
    @Test
    public void test_toggleTurn() {
        CuT.toggleTurn();
        assertEquals(Board.ActiveColor.WHITE, CuT.currentTurn);
        CuT.toggleTurn();
        assertEquals(Board.ActiveColor.RED, CuT.currentTurn);
    }

    /**
     * Test the enum values of ActiveColor.
     */
    @Test
    public void test_turnEnum(){
        assertEquals(Board.ActiveColor.RED, Board.ActiveColor.valueOf("RED"));
        assertEquals(Board.ActiveColor.WHITE, Board.ActiveColor.valueOf("WHITE"));
    }

    /**
     * Test the enum values of ViewMode.
     */
    @Test
    public void test_viewEnum(){
        assertEquals(Board.ViewMode.PLAY, Board.ViewMode.valueOf("PLAY"));
        assertEquals(Board.ViewMode.REPLAY, Board.ViewMode.valueOf("REPLAY"));
        assertEquals(Board.ViewMode.SPECTATE, Board.ViewMode.valueOf("SPECTATE"));

    }
}
