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
 * The unit test suite for the {@link Validate} component.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class ValidateTest {

    private final String ERROR_NOT_DIAGONAL = "That move was not diagonal";
    private final String ERROR_NO_JUMPS = "That move cannot be a jump since " +
            "there are no jumps available.";
    private final String ERROR_MUST_JUMP = "A jump is currently present and must be taken.";
    private final String ERROR_BACKWARDS = "That piece cannot move backwards";

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
        board.getBoard()[4][3].removePiece();
        board.getBoard()[4][3].setPiece(new Piece(Piece.Color.WHITE, Piece.Type.SINGLE));

        //No error message will be generated if valid
        assertNull(CuT.isValid(move, board));
    }

    /**
     * Test a valid diagonal, adjacent move "downwards" and to the right.
     */
    @Test
    public void test_diagonalAdjacent_3() throws Exception {
        start = new Position(4,3);
        end = new Position(5, 4);
        move = new Move(start, end);

        //Change piece to white to allow it to move in the tested direction
        board.getBoard()[4][3].removePiece();
        board.getBoard()[4][3].setPiece(new Piece(Piece.Color.WHITE, Piece.Type.SINGLE));

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

    /**
     * Test a valid double jump.
     */
    @Test
    public void test_doubleJump() {
        start = new Position(4,3);
        end = new Position(2, 5);
        move = new Move(start, end);

        board.getBoard()[3][4].setPiece(new Piece(Piece.Color.WHITE, Piece.Type.SINGLE));
        //Set up second jump
        board.getBoard()[1][4].setPiece(new Piece(Piece.Color.WHITE, Piece.Type.SINGLE));

        assertNull(CuT.isValid(move, board));


        Position start2 = end;
        Position end2 = new Position(0, 3);
        Move move2 = new Move(start2, end2);

        assertNull(CuT.continueJump(move, board));
    }

    /**
     * Test a valid triple jump.
     */
    @Test
    public void test_tripleJump() {
        start = new Position(7,0);
        end = new Position(5, 2);
        move = new Move(start, end);

        //Create new piece in a space that better allows for a triple jump
        board.getBoard()[7][0].setPiece(new Piece(Piece.Color.RED, Piece.Type.SINGLE));
        board.getBoard()[6][1].setPiece(new Piece(Piece.Color.WHITE, Piece.Type.SINGLE));
        //Set up second jump
        board.getBoard()[1][4].removePiece();
        board.getBoard()[4][3].setPiece(new Piece(Piece.Color.WHITE, Piece.Type.SINGLE));
        //Set up third jump
        board.getBoard()[2][5].setPiece(new Piece(Piece.Color.WHITE, Piece.Type.SINGLE));

        assertNull(CuT.isValid(move, board));

        Position start2 = end;
        Position end2 = new Position(3, 4);
        Move move2 = new Move(start2, end2);

        assertNull(CuT.continueJump(move, board));

        Position start3 = end2;
        Position end3 = new Position(1, 6);
        Move move3 = new Move(start3, end3);

        assertNull(CuT.continueJump(move, board));
    }

    /**
     * Test that the system correctly stops RED pieces from moving backwards.
     */
    @Test
    public void test_backwards_0() {
        start = new Position(4,3);
        end = new Position(5, 2);
        move = new Move(start, end);

        //No error message will be generated if valid
        assertEquals(ERROR_BACKWARDS, CuT.isValid(move, board));
    }

    /**
     * Test that the system correctly stops WHITE pieces from moving backwards.
     */
    @Test
    public void test_backwards_1() {
        start = new Position(4,3);
        end = new Position(2, 1);
        move = new Move(start, end);

        //Change piece to white to allow it to move in the tested direction
        board.getBoard()[4][3].removePiece();
        board.getBoard()[4][3].setPiece(new Piece(Piece.Color.WHITE, Piece.Type.SINGLE));

        //No error message will be generated if valid
        assertEquals(ERROR_BACKWARDS, CuT.isValid(move, board));
    }
}
