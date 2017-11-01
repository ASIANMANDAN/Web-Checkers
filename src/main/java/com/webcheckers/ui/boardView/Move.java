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
}
