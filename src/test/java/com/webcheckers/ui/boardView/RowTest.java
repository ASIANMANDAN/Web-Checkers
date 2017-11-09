package com.webcheckers.ui.boardView;

import com.webcheckers.model.board.Space;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * The unit test suite for the {@link Row} component.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class RowTest {
    private static int index = 0;

    //Friendly
    private static Space newSpace;

    @Before
    public void test_setup() throws Exception{
        newSpace = new Space(0 ,0 , Space.Color.BLACK);
    }

    /**
     * Test the constructor.
     */
    @Test
    public void test_constr(){
        Row row = new Row(index);
        assertEquals(row.getIndex(), index);
        assertEquals(row.getRowOfSpaces(), new ArrayList<Space>());
    }

    /**
     * Test the addSpace method.
     */
    @Test
    public void test_addSpace(){
        Row row = new Row(index);
        row.addSpace(newSpace);
        ArrayList<Space> rowOfSpaces = row.getRowOfSpaces();
        assertEquals(rowOfSpaces.get(0), newSpace);
    }

    /**
     * Test the getIndex method.
     */
    @Test
    public void test_getIndex(){
        Row row = new Row(index);
        assertEquals(index, row.getIndex());
    }

    /**
     * Test the getRowOfSpaces method.
     */
    @Test
    public void test_getRowOfSpaces(){
        Row row = new Row(index);
        assertTrue(row.getRowOfSpaces() instanceof ArrayList);
    }

    /**
     * Test the iterator method needed by the Iterable interface.
     */
    @Test
    public void test_iterator(){
        Row row = new Row(index);
        row.addSpace(newSpace);
        Iterator<Space> itr = row.iterator();
        for (int i= 0; i< 1; i++){
            Space space = itr.next();
            assertTrue(space instanceof Space);
        }
    }

}
