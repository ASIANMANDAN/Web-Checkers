package com.webcheckers.ui.boardView;

/**
 * An object to represent the change in a Pieces location on the Board.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class Move {

    private Position start;
    private Position end;

    /**
     * Constructor for a Move object.
     *
     * @param start the starting Position of the Piece
     * @param end the end Position of the Piece
     */
    public Move(Position start, Position end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Get the starting Position of the Piece.
     *
     * @return the Pieces start Position
     */
    public Position getStart() {
        return start;
    }

    /**
     * Get the end Position of the Piece.
     *
     * @return the Pieces end Position
     */
    public Position getEnd() {
        return end;
    }

    /**
     * Equals method to be used for comparing two Move objects.
     *
     * @param obj the Move object that is being compared
     * @return whether or not these two objects are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Move)) {
            return false;
        }
        final Move that = (Move) obj;
        return this.getStart().equals(that.getStart()) &&
                this.getEnd().equals(that.getEnd());
    }
}
