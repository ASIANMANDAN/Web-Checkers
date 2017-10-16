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
     * @param index index to assing to the row
     */
    public Row(int index) {
        this.index = index;
    }

    /**
     * Adds a space to on index in the Row.
     *
     * @param newSpace New Space that is being assigned
     */
    public void addSpace(Space newSpace){
        this.rowOfSpaces.add(newSpace);
    }

    /**
     * Retrieves the index of the Row
     *
     * @return index of row
     */
    public int getIndex() {
        return index;
    }

    /**
     * Iterates over the columns within a row.
     *
     * @return The Space in that column
     */
    @Override
    public Iterator<Space> iterator() {
        return rowOfSpaces.iterator();
    }
}
