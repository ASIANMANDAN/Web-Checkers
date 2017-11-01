package com.webcheckers.ui.boardView.AjaxRoutes;

import com.webcheckers.ui.boardView.Move;
import com.webcheckers.ui.boardView.Position;
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
}
