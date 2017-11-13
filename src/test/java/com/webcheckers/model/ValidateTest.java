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

    private final String ERROR_NOT_DIAGONAL = "That move was not diagonal";
    private final String ERROR_NO_JUMPS = "That move cannot be a jump since there " +
            "are no jumps are available.";
    private final String ERROR_MUST_JUMP = "A jump is currently present and must be taken.";

    private Validate CuT;

    //Friendly Objects
    private Move move;
    private Position start;
    private Position end;
    private Board board;

    @Before
    public void test_setUp() {
        CuT = new Validate();

        try {
            board = new Board();
            board.getBoard()[4][3].setPiece(new Piece(Piece.Color.RED, Piece.Type.SINGLE));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Test a valid diagonal, adjacent move "upwards" and to the left.
     */
    @Test
    public void test_diagonalAdjacent_0() {
        start = new Position(4,3);
        end = new Position(3, 2);
        move = new Move(start, end);

        //No error message will be generated if valid
        assertNull(CuT.isValid(move, board));
    }

    /**
     * Test a valid diagonal, adjacent move "upwards" and to the right.
     */
    @Test
    public void test_diagonalAdjacent_1() {
        start = new Position(4,3);
        end = new Position(3, 4);
        move = new Move(start, end);

        //No error message will be generated if valid
        assertNull(CuT.isValid(move, board));
    }

    /**
     * Test a valid diagonal, adjacent move "downwards" and to the left.
     */
    @Test
    public void test_diagonalAdjacent_2() {
        start = new Position(4,3);
        end = new Position(5, 2);
        move = new Move(start, end);

        //Change piece to white to allow it to move in the tested direction
        board.getBoard()[4][4].setPiece(new Piece(Piece.Color.WHITE, Piece.Type.SINGLE));

        //No error message will be generated if valid
        assertNull(CuT.isValid(move, board));
    }

    /**
     * Test a valid diagonal, adjacent move "downwards" and to the right.
     */
    @Test
    public void test_diagonalAdjacent_3() {
        start = new Position(4,3);
        end = new Position(5, 4);
        move = new Move(start, end);

        //Change piece to white to allow it to move in the tested direction
        board.getBoard()[4][4].setPiece(new Piece(Piece.Color.WHITE, Piece.Type.SINGLE));

        //No error message will be generated if valid
        assertNull(CuT.isValid(move, board));
    }

    /**
     * Test an invalid adjacent move "upwards".
     */
    @Test
    public void test_Adjacent_0() {
        start = new Position(4,3);
        end = new Position(3, 3);
        move = new Move(start, end);

        //No error message will be generated if valid
        assertEquals(ERROR_NOT_DIAGONAL, CuT.isValid(move, board));
    }

    /**
     * Test an invalid diagonal jump "upwards" and to the left.
     */
    @Test
    public void test_diagonal_0() {
        start = new Position(4,3);
        end = new Position(2, 1);
        move = new Move(start, end);

        //No error message will be generated if valid
        assertEquals(ERROR_NO_JUMPS, CuT.isValid(move, board));
    }

    /**
     * Test a valid jump "upwards" and to the left.
     */
    @Test
    public void test_jumpPresent_0() {
        start = new Position(4,3);
        end = new Position(2, 1);
        move = new Move(start, end);

        board.getBoard()[3][2].setPiece(new Piece(Piece.Color.WHITE, Piece.Type.SINGLE));

        assertNull(CuT.isValid(move, board));
    }

    /**
     * Test a valid jump "upwards" and to the right.
     */
    @Test
    public void test_jumpPresent_1() {
        start = new Position(4,3);
        end = new Position(2, 5);
        move = new Move(start, end);

        board.getBoard()[3][4].setPiece(new Piece(Piece.Color.WHITE, Piece.Type.SINGLE));

        assertNull(CuT.isValid(move, board));
    }

    /**
     * Test a valid jump "downwards" and to the left.
     */
    @Test
    public void test_jumpPresent_2() {
        start = new Position(2,3);
        end = new Position(4, 1);
        move = new Move(start, end);

        //Change piece to white to allow it to move in the tested direction
        board.getBoard()[2][3].setPiece(new Piece(Piece.Color.WHITE, Piece.Type.SINGLE));
        //Place opponents piece adjacent to allow the diagonal move
        board.getBoard()[3][2].setPiece(new Piece(Piece.Color.RED, Piece.Type.SINGLE));

        assertNull(CuT.isValid(move, board));
    }

    /**
     * Test a valid jump "downwards" and to the right.
     */
    @Test
    public void test_jumpPresent_3() {
        start = new Position(2,3);
        end = new Position(4, 5);
        move = new Move(start, end);

        //Change piece to white to allow it to move in the tested direction
        board.getBoard()[2][3].setPiece(new Piece(Piece.Color.WHITE, Piece.Type.SINGLE));
        //Place opponents piece adjacent to allow the diagonal move
        board.getBoard()[3][4].setPiece(new Piece(Piece.Color.RED, Piece.Type.SINGLE));

        assertNull(CuT.isValid(move, board));
    }

    /**
     * Test an invalid jump where the player tries to jump over nothing.
     */
    @Test
    public void test_jumpPresent_4() {
        start = new Position(4,3);
        end = new Position(2, 1);
        move = new Move(start, end);

        assertEquals(ERROR_NO_JUMPS, CuT.isValid(move, board));
    }

    /**
     * Test an invalid jump where the player tries to jump over their own piece.
     */
    @Test
    public void test_jumpPresent_5() {
        start = new Position(4,3);
        end = new Position(2, 1);
        move = new Move(start, end);

        board.getBoard()[3][2].setPiece(new Piece(Piece.Color.RED, Piece.Type.SINGLE));

        assertEquals(ERROR_NO_JUMPS, CuT.isValid(move, board));
    }

    /**
     * Test an invalid jump where a valid jump exists but the player
     * does not take it.
     */
    @Test
    public void test_jumpPresent_6() {
        start = new Position(4,3);
        end = new Position(2, 5);
        move = new Move(start, end);

        board.getBoard()[3][2].setPiece(new Piece(Piece.Color.WHITE, Piece.Type.SINGLE));

        assertEquals(ERROR_MUST_JUMP, CuT.isValid(move, board));
    }
}
