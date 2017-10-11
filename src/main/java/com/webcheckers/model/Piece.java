package com.webcheckers.model;

/**
 * Model tier class that represents a basic checker piece on the checker board.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class Piece {

    private String color;

    /**
     * Creates either a red or white piece.
     *
     * @param color The color the piece is
     */
    public Piece(String color){
        this.color = color;
    }

    /**
     * Get the color of the chosen piece.
     *
     * @return The color of the piece
     */
    public String getColor() {
        return color;
    }

    /**
     * To be implemented later
     */
    //TODO
    public void move(){}
}
