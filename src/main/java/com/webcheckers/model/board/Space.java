package com.webcheckers.model.board;

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
    public enum Color {WHITE, BLACK}

    private int row;
    private int col;
    private Piece piece;
    private Color color;

    /**
     * Constructor for a space on a game board.
     *
     * @param row the row where the space is being created
     * @param col the column where the space is being created
     * @param color the color of that space
     * @throws Exception occurs if the given column is greater or less than
     * the bounds established by a standard game board
     */
    public Space(int row, int col, Color color) throws Exception {
        if ((col < 0 || row >= Board.size) && (row < 0 || row >= Board.size)) {
            throw new Exception("the  must be between 0 and the board SIZE.");
        } else {
            this.row = row;
            this.col = col;
            this.color = color;
            this.piece = null;
        }
    }

    /**
     * Retrieves the color of the space
     *
     * @return color of the space
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Determines if the space is valid and then "places" a piece
     * on the board.
     *
     * @param piece the Piece object to place
     */
    public void setPiece(Piece piece) {
        if (this.isValid()) {
            this.piece = piece;
        }
    }

    /**
     * Returns the piece that occupies the space.
     *
     * @return the piece on the space
     */
    public Piece getPiece() {
        return this.piece;
    }

    /**
     * Gets the row which the space is in.
     *
     * @return the array number of the row where the space is
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Gets the column which the space is in.
     *
     * @return the array number of the column where the space is
     */
    public int getCol() {
        return this.col;
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

    /**
     * Equals method for determining if two spaces are the same.
     *
     * @param obj the other space to compare
     * @return whether or not the spaces are equivalent
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Space)) return false;
        final Space that = (Space) obj;
        return this.color.equals(that.color) && this.piece.equals(that.piece) &&
                this.row == that.row && this.col == that.col;
    }
}
