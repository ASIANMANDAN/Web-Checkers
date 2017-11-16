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

    //Friendly Objects
    private static Space[][] arrayBoard;
    private static Move move;
    private Position start;
    private Position end;

    @Before
    public void test_setup() throws Exception {
        CuT = new Board();
        arrayBoard = CuT.getBoard();

        start = new Position(0,1);
        end = new Position(1,1);
        move = new Move(start, end);
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

    /**
     * Test that when jumped, a piece is removed from the Board.
     */
    @Test
    public void test_capturePiece() {
        start = new Position(4, 3);
        end = new Position(2, 5);
        move = new Move(start, end);

        arrayBoard[4][3].setPiece(new Piece(Piece.Color.RED, Piece.Type.SINGLE));
        //Create a piece to jump
        arrayBoard[3][4].setPiece(new Piece(Piece.Color.WHITE, Piece.Type.SINGLE));

        //Test that the piece was placed correctly
        assertNotNull(arrayBoard[3][4].getPiece());
        CuT.makeMove(move);
        //Test the piece was removed after the move
        assertNull(arrayBoard[3][4].getPiece());
    }

    /**
     * Test the makeKing method.
     */
    @Test
    public void test_makeKing() throws Exception {
        Board CuT = new Board();

        //Check white single piece in bottom row promotes
        Space space1 = CuT.getBoard()[7][0];
        Piece piece1 = new Piece(Piece.Color.WHITE, Piece.Type.SINGLE);
        space1.setPiece(piece1);
        //Check red single piece in top row promotes
        Space space2 = CuT.getBoard()[0][1];
        Piece piece2 = new Piece(Piece.Color.RED, Piece.Type.SINGLE);
        space2.setPiece(piece2);
        //Check piece won't promote when in the wrong row
        Space space3 = CuT.getBoard()[1][0];
        Piece piece3 = new Piece(Piece.Color.RED, Piece.Type.SINGLE);
        space3.setPiece(piece3);
        //Check piece won't promote when already a king
        Space space4 = CuT.getBoard()[0][7];
        Piece piece4 = new Piece(Piece.Color.RED, Piece.Type.KING);
        space4.setPiece(piece4);

        assertTrue(CuT.makeKing(space1));
        assertTrue(CuT.makeKing(space2));
        assertFalse(CuT.makeKing(space3));
        assertFalse(CuT.makeKing(space4));
    }
}
