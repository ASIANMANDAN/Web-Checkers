package com.webcheckers.model.board;

/**
 * Model tier class that represents a basic checker piece on the checker board.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class Piece {
    //Accepted piece colors
    public enum Color {RED, WHITE}
    //Piece types
    public enum Type {SINGLE, KING}

    private Color color;
    private Type type;

    /**
     * Creates either a red or white piece.
     *
     * @param color The color the piece is
     */
    public Piece(Color color, Type type){
        this.color = color;
        this.type = type;
    }

    /**
     * Get the color of the chosen piece.
     *
     * @return The color of the piece
     */
    public Color getColor() {
        return color;
    }

    /**
     * Get the type of the piece
     *
     * @return piece type
     */
    public Type getType(){
        return type;
    }

    /**
     * Equals method for determining if two pieces are the same.
     *
     * @param obj the other piece to compare
     * @return whether or not the two pieces are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Piece)) return false;
        final Piece that = (Piece) obj;
        return this.color.equals(that.color) && this.type.equals(that.type);
    }
}
