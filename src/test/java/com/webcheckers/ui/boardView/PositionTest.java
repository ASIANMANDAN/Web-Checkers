package com.webcheckers.ui.boardView;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * The unit test suite for the {@link Position} component.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class PositionTest {

    private Position CuT;

    private int row = 4;
    private int col = 6;

    @Before
    public void test_setUp() {
        CuT = new Position(row, col);
    }

    /**
     * Test that the getRow method returns the correct number.
     */
    @Test
    public void test_getRow() {
        assertEquals(row, CuT.getRow());
    }

    /**
     * Test that the getCell method returns the correct number.
     */
    @Test
    public void test_getCell() {
        assertEquals(col, CuT.getCell());
    }

    /**
     * Test that the equals method identifies objects that are equal to
     * the one in question.
     */
    @Test
    public void test_equals() {
        Position p1 = new Position(row, col);
        Position p2 = new Position(col, row);
        String str = "Not a Position";

        //Test the object is equal to itself
        assertTrue(CuT.equals(CuT));
        //Test the object is equal to another object with equal attributes
        assertTrue(CuT.equals(p1));
        //Test the object is not equal to another object with different attributes
        assertFalse(CuT.equals(p2));
        //Test the object is not equal to another type of object
        assertFalse(CuT.equals(str));
    }
}
