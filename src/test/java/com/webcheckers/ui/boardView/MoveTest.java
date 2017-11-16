package com.webcheckers.ui.boardView;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * The unit test suite for the {@link Move} component.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class MoveTest {

    private Move CuT;

    //Friendly objects
    private Position start;
    private Position end;

    private int startRow = 7;
    private int startCol = 0;
    private int endRow = 6;
    private int endCol = 1;

    @Before
    public void test_setUp() {
        start = new Position(startRow, startCol);
        end = new Position(endRow, endCol);

        CuT = new Move(start, end);
    }

    /**
     * Test that the getStart method returns the correct Position.
     */
    @Test
    public void test_getStart() {
        assertEquals(start.getRow(), CuT.getStart().getRow());
        assertEquals(start.getCell(), CuT.getStart().getCell());
    }

    /**
     * Test that the getEnd method returns the correct Position.
     */
    @Test
    public void test_getEnd() {
        assertEquals(end.getRow(), CuT.getEnd().getRow());
        assertEquals(end.getCell(), CuT.getEnd().getCell());
    }

    /**
     * Test that the equals method identifies objects that are equal to
     * the one in question.
     */
    @Test
    public void test_equals() {
        Move m1 = new Move(start, end);
        Move m2 = new Move(end, start);
        String str = "Not a Position";

        //Test the object is equal to itself
        assertTrue(CuT.equals(CuT));
        //Test the object is equal to another object with equal attributes
        assertTrue(CuT.equals(m1));
        //Test the object is not equal to another object with different attributes
        assertFalse(CuT.equals(m2));
        //Test the object is not equal to another type of object
        assertFalse(CuT.equals(str));
    }
}
