package com.webcheckers.model;

import java.util.Iterator;

/**
 * Model tier class that represents the checkers board.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class Board implements Iterator{
    //TODO build up this class- create 2D array, initialize with pieces, etc.

    private Space[][] board;

    public Board() throws Exception {
        this.board = new Space[7][7];

        //Iterates through the board to populate array indices
        //with spaces
        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j < 7; j++) {
//                if (i%2 == 1 && j%2 == 0) {
//                    this.board[i][j] = new Space(j, Space.Color.BLACK);
//                }
//                if (i%2 == 0 && j%2 == 1) {
//                    this.board[i][j] = 2;
//                }
            }
        }
    }



    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Object next() {
        return null;
    }

}
