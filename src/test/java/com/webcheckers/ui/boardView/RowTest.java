package com.webcheckers.ui.boardView;

import com.webcheckers.model.board.Space;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Created by nate on 11/2/17.
 */
public class RowTest {
    private static int index = 0;

    //Friendly
    private static Space newSpace;

    @Before
    public void test_setup() throws Exception{
        newSpace = new Space(0 ,0 , Space.Color.BLACK);
    }

    @Test
    public void test_constr(){
        Row row = new Row(index);
        assertEquals(row.getIndex(), index);
        assertEquals(row.getRowOfSpaces(), new ArrayList<Space>());
    }

    @Test
    public void test_addSpace(){
        Row row = new Row(index);
        row.addSpace(newSpace);
        ArrayList<Space> rowOfSpaces = row.getRowOfSpaces();
        assertEquals(rowOfSpaces.get(0), newSpace);
    }

    @Test
    public void test_getIndex(){
        Row row = new Row(index);
        assertEquals(index, row.getIndex());
    }

    @Test
    public void test_getRowOfSpaces(){
        Row row = new Row(index);
        assertTrue(row.getRowOfSpaces() instanceof ArrayList);
    }

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
