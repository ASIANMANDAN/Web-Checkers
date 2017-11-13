package com.webcheckers.ui.boardView;

/**
 * Represents the current location of a game Piece on the Board
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class Position {

    private int row;
    private int cell;

    /**
     * Constructor for Position object.
     *
     * @param row the row number the piece is on
     * @param col the column number in a row the piece is on
     */
    public Position(int row, int col) {
        this.row = row;
        this.cell = col;
    }

    /**
     * Get the column of the Position.
     *
     * @return the column number
     */
    public int getCell() {
        return cell;
    }

    /**
     * Get the row of the Position.
     *
     * @return the row number
     */
    public int getRow() {
        return row;
    }

    /**
     * Equals method to be used for comparing two Position objects.
     *
     * @param obj the Position object that is being compared
     * @return whether or not these two objects are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Position)) {
            return false;
        }
        final Position that = (Position) obj;
        return this.getRow() == that.getRow() && this.getCell() == that.getCell();
    }
}
