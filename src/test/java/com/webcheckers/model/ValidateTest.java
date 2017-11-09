package com.webcheckers.model;

import static org.junit.Assert.*;

import com.webcheckers.model.board.Board;
import com.webcheckers.model.board.Piece;
import com.webcheckers.model.board.Space;
import com.webcheckers.ui.boardView.Move;
import com.webcheckers.ui.boardView.Position;
import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class ValidateTest {

    private Validate CuT;

    //Friendly Objects
    private Move move;
    private Position start;
    private Position end;
    private Board board;
    private Space[][] gameBoard;

    @Before
    public void test_setUp() {
        CuT = new Validate();

        try {
            board = new Board();
            gameBoard = board.getBoard();
            gameBoard[4][4].setPiece(new Piece(Piece.Color.RED, Piece.Type.SINGLE));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Test a valid diagonal, adjacent move "upwards" and to the left.
     */
    @Test
    public void test_diagonalAdjacent_0() {
        start = new Position(4,4);
        end = new Position(3, 3);
        move = new Move(start, end);

        //Place opponents piece adjacent to allow the diagonal move
        //gameBoard[3][3].setPiece(new Piece(Piece.Color.WHITE, Piece.Type.SINGLE));

        //No error message will be generated if valid
        assertNull(CuT.isValid(move, gameBoard));
    }

    /**
     * Test a valid diagonal, adjacent move "upwards" and to the right.
     */
    @Test
    public void test_diagonalAdjacent_1() {
        start = new Position(4,4);
        end = new Position(3, 5);
        move = new Move(start, end);

        //No error message will be generated if valid
        assertNull(CuT.isValid(move, gameBoard));
    }

    /**
     * Test a valid diagonal, adjacent move "downwards" and to the left.
     */
    @Test
    public void test_diagonalAdjacent_2() {
        start = new Position(4,4);
        end = new Position(5, 3);
        move = new Move(start, end);

        //Change piece to white to allow it to move in the tested direction
        gameBoard[4][4].setPiece(new Piece(Piece.Color.WHITE, Piece.Type.SINGLE));

        //No error message will be generated if valid
        assertNull(CuT.isValid(move, gameBoard));
    }

    /**
     * Test a valid diagonal, adjacent move "downwards" and to the right.
     */
    @Test
    public void test_diagonalAdjacent_3() {
        start = new Position(4,4);
        end = new Position(5, 5);
        move = new Move(start, end);

        //Change piece to white to allow it to move in the tested direction
        gameBoard[4][4].setPiece(new Piece(Piece.Color.WHITE, Piece.Type.SINGLE));

        //No error message will be generated if valid
        assertNull(CuT.isValid(move, gameBoard));
    }

    /**
     * Test an invalid adjacent move "upwards".
     */
    @Test
    public void test_Adjacent_0() {
        start = new Position(4,4);
        end = new Position(3, 4);
        move = new Move(start, end);

        String error = "The move was not diagonal";

        //No error message will be generated if valid
        assertEquals(error, CuT.isValid(move, gameBoard));
    }

    /**
     * Test an invalid diagonal jump "upwards" and to the left.
     */
    @Test
    public void test_diagonal_0() {
        start = new Position(4,4);
        end = new Position(2, 2);
        move = new Move(start, end);

        String error = "There are no jumps present. " +
                "Therefore you must move to a space that is " +
                "adjacent to the piece you wish to move.";

        //No error message will be generated if valid
        assertEquals(error, CuT.isValid(move, gameBoard));
    }
}
