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
}
