package com.webcheckers.model;

/**
 * Model tier class which represents a space on the board.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class Space {

    //Enum to represent the possible colors of a space.
    public enum Color {WHITE, BLACK};

    private int cellIdx;
    private Piece piece;
    private Color color;

    /**
     * Constructor for a space on a game board.
     *
     * @param cellIdx what cell within a row the space is
     * @param color the color of that space
     * @throws Exception occurs if the given cellIdx is greater or less than
     * the bounds established by a standard game board
     */
    public Space(int cellIdx, Color color) throws Exception {
        if (cellIdx < 0 || cellIdx > 7) {
            throw new Exception("cellIdx must be between 0 and 7.");
        } else {
            this.cellIdx = cellIdx;
            this.color = color;
            this.piece = null;
        }
    }

    /**
     * Determines if the space is valid and then "places" a piece
     * on the board.
     *
     * @param piece the Piece object to place
     * @return a boolean value, whether or not the piece was placed
     */
    public boolean setPiece(Piece piece) {
        if (this.isValid()) {
            this.piece = piece;
            return true;
        }
        return false;
    }

    /**
     * Gets the cell within a row that the space is in.
     *
     * @return the index number of the row where the space lies
     */
    public int getCellIdx() {
        return this.cellIdx;
    }

    /**
     * Determines if a space is empty and the proper color
     * based on the standard rules of chess.
     *
     * @return a boolean value, if the space can have a piece in it
     */
    public boolean isValid() {
        return this.color == Color.BLACK && this.piece == null;
    }
}
