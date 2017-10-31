package com.webcheckers.model.board;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

import static org.junit.Assert.*;

/**
 * The unit test suite for the {@Link Space} component.
 *
 * @author Dan Wang
 */
public class SpaceTest {

    private static final int tooBig = 8;
    private static final int tooSmall = -1;
    private static final int inRange = 4;
    private static final Space.Color color = Space.Color.BLACK;
    private static Space CuT;

    //mock object
    private Piece piece;

    @Before
    public void testSetup() throws Exception {
        CuT = new Space(inRange, inRange, color);
        piece = mock(Piece.class);
    }

    /**
     * Test that the constructor will not work with a column or row above 7.
     */
    @Test(expected = Exception.class)
    public void ctor_tooBig() throws Exception { new Space(tooBig, tooBig, color); }

    /**
     * Test that the constructor will not work with a row or column less than 0.
     */
    @Test(expected = Exception.class)
    public void ctor_tooSmall() throws Exception { new Space(tooSmall, tooSmall, color); }

    /**
     * Test that the constructor will work with a row and space within the range 0 - 7.
     */
    @Test
    public void ctor_inRange() throws Exception { new Space(inRange, inRange, color); }

    /**
     * Test the {@Link Space#getColor()} method.
     */
    @Test
    public void test_get_color() {
        assertEquals(Space.Color.BLACK, CuT.getColor());
    }

    /**
     * Test the {@Link Space#setPiece} method.
     */
    @Test
    public void test_set_piece() {
        CuT.setPiece(piece);
        assertTrue(CuT.getPiece().equals(piece));
        assertEquals(piece, CuT.getPiece());
    }

    /**
     * Test the {@Link Space#getPiece} method.
     */
    @Test
    public void test_get_piece() {
        CuT.setPiece(piece);
        Piece p1 = CuT.getPiece();
        assertTrue(p1.equals(piece));
    }

    /**
     * Test the {@Link Space#getRow} method.
     */
    @Test
    public void test_get_row() {
        assertEquals(inRange, CuT.getRow());
    }

    /**
     * Test the {@Link Space#getCol} method.
     */
    @Test
    public void test_get_col() {
        assertEquals(inRange, CuT.getCol());
    }

    /**
     * Test the {@Link Space#isValid} method.
     */
    @Test
    public void test_is_valid() throws Exception {
        //Test an unoccupied black space.
        assertTrue(CuT.isValid());
        //Test an occupied black space.
        CuT.setPiece(piece);
        assertFalse(CuT.isValid());
        //Test an unoccupied white space.
        Space CuT2 = new Space(inRange, inRange, Space.Color.WHITE);
        assertFalse(CuT2.isValid());
        //Test an occupied white space.
        CuT2.setPiece(piece);
        assertFalse(CuT2.isValid());
    }

}
