package com.webcheckers.model.board;

import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.exceptions.ExceptionIncludingMockitoWarnings;

import static org.mockito.Mockito.mock;

import static org.junit.Assert.*;

/**
 * The unit test suite for the {@link Space} component.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
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
     * Test that the constructor will not work with a column or row
     * grater than the legal board size.
     *
     * @throws Exception occurs if the given column or row of a space
     * is greater or less than the bounds established by a standard
     * game board
     */
    @Test
    public void test_ctor_tooBig() throws Exception {
        try{
            new Space(tooBig, tooBig, color);
        }catch (Exception e){
            assertEquals("the  must be between 0 and the board SIZE.", e.getMessage());
        }

        try{
            new Space(tooBig, inRange, color);
        }catch (Exception e){
            assertEquals("the  must be between 0 and the board SIZE.", e.getMessage());
        }

        try{
            new Space(inRange, tooBig, color);
        }catch (Exception e){
            assertEquals("the  must be between 0 and the board SIZE.", e.getMessage());
        }

    }

    /**
     * Test that the constructor will not work with a row or column less than 0.
     *
     * @throws Exception occurs if the given column or row of a space
     * is greater or less than the bounds established by a standard
     * game board
     */
    @Test
    public void test_ctor_tooSmall() throws Exception {
        try{
            new Space(tooSmall, tooSmall, color);
        }catch (Exception e){
            assertEquals("the  must be between 0 and the board SIZE.", e.getMessage());
        }

        try{
            new Space(tooSmall, inRange, color);
        }catch (Exception e){
            assertEquals("the  must be between 0 and the board SIZE.", e.getMessage());
        }

        try{
            new Space(inRange, tooSmall, color);
        }catch (Exception e){
            assertEquals("the  must be between 0 and the board SIZE.", e.getMessage());
        }
    }

    /**
     * Test that the constructor will work with a row and space within the
     * legal range.
     *
     * @throws Exception occurs if the given column or row of a space
     * is greater or less than the bounds established by a standard
     * game board
     */
    @Test
    public void test_ctor_inRange() throws Exception {
        new Space(inRange, inRange, color);
    }

    /**
     * Test that the constructor will not work when given a row that
     * is out of the legal range.
     *
     * @throws Exception occurs if the given column or row of a space
     * is greater or less than the bounds established by a standard
     * game board
     */
    @Test
    public void test_ctor_outRange() throws Exception {
        try{
            new Space(tooSmall, tooBig, color);
        }catch (Exception e){
            assertEquals("the  must be between 0 and the board SIZE.", e.getMessage());
        }

        try{
            new Space(tooBig, tooSmall, color);
        }catch (Exception e){
            assertEquals("the  must be between 0 and the board SIZE.", e.getMessage());
        }


    }

    /**
     * Test the {@link Space#getColor()} method.
     */
    @Test
    public void test_get_color() {
        assertEquals(Space.Color.BLACK, CuT.getColor());
    }

    /**
     * Test the {@link Space#setPiece} method.
     */
    @Test
    public void test_set_piece() {
        CuT.setPiece(piece);
        assertTrue(CuT.getPiece().equals(piece));
        assertEquals(piece, CuT.getPiece());
    }

    /**
     * Test the {@link Space#getPiece} method.
     */
    @Test
    public void test_get_piece() {
        CuT.setPiece(piece);
        Piece p1 = CuT.getPiece();
        assertTrue(p1.equals(piece));
    }

    /**
     * Test the {@link Space#getRow} method.
     */
    @Test
    public void test_get_row() {
        assertEquals(inRange, CuT.getRow());
    }

    /**
     * Test the {@link Space#getCol} method.
     */
    @Test
    public void test_get_col() {
        assertEquals(inRange, CuT.getCol());
    }

    /**
     * Test the {@link Space#isValid} method.
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

    /**
     * Test that the equals method correctly distinguishes between Spaces.
     *
     * @throws Exception occurs if the given column or row of a space
     * is greater or less than the bounds established by a standard
     * game board
     */
    @Test
    public void test_equals() throws Exception {
        Space compareSameSpace = new Space(inRange, inRange, color);
        Space compareDiffSpace1 = new Space(3, 6, Space.Color.BLACK);
        Space compareDiffSpace2 = new Space(4, 6, Space.Color.BLACK);
        Space compareDiffSpace3 = new Space(5, 4, Space.Color.BLACK);
        Space compareDiffSpace4 = new Space(3, 6, Space.Color.WHITE);
        Space compareDiffSpace5 = new Space(4, 6, Space.Color.WHITE);
        Space compareDiffSpace6 = new Space(5, 4, Space.Color.WHITE);


        String notASpace = "NOT A SPACE";

        // Test every possible combination of the if statement
        assertTrue(CuT.equals(compareSameSpace));
        assertFalse(CuT.equals(compareDiffSpace1));
        assertFalse(CuT.equals(compareDiffSpace2));
        assertFalse(CuT.equals(compareDiffSpace3));
        assertFalse(CuT.equals(compareDiffSpace4));
        assertFalse(CuT.equals(compareDiffSpace5));
        assertFalse(CuT.equals(compareDiffSpace6));

        // To compare different Color combination
        assertFalse(compareDiffSpace1.equals(compareDiffSpace4));
        assertFalse(CuT.equals(notASpace));

    }

    /**
     * Test the enum values for Color.
     */
    @Test
    public void test_enum(){
        assertEquals(Space.Color.BLACK, Space.Color.valueOf("BLACK"));
        assertEquals(Space.Color.WHITE, Space.Color.valueOf("WHITE"));
    }

}
