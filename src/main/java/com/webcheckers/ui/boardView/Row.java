package com.webcheckers.ui.boardView;

import com.webcheckers.model.board.Space;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * UI tier class that represents an iterable version of a Row.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class Row implements Iterable {

    private ArrayList<Space> rowOfSpaces = new ArrayList<>();
    private int index;

    /**
     * Creates Row and assigns index.
     *
     * @param index
     * @throws Exception
     */
    public Row(int index) throws Exception {
        this.index = index;
    }

    /**
     * Adds a space to on index in the Row.
     *
     * @param i index
     * @param newSpace New Space that is being assigned
     */
    public void addSpace(int i, Space newSpace){
        this.rowOfSpaces.add(i, newSpace);
    }

    /**
     * Retrieves the index of the Row
     *
     * @return index of row
     */
    public int getIndex() {
        return index;
    }

    @Override
    public Iterator<Space> iterator() {
        return rowOfSpaces.iterator();
    }
}
