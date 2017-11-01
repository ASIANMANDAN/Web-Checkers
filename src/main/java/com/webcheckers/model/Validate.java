package com.webcheckers.model;

/**
 * file: checkers-app
 * language: java
 * author: erl3193@rit.edu Emily Lederman
 * description:
 */

import com.webcheckers.model.board.Space;
import com.webcheckers.ui.boardView.Move;
import com.webcheckers.ui.boardView.Position;

public class Validate {

    public String isValid(Move move, Space[][] board) {

        boolean isAdjacent = isAdjacent(move);
        if (isAdjacent){
            return null;
        }else{
            return "There are no jumps present. Therefore you must move to a space that is " +
                    "adjacent to the piece you wish to move.";
        }

    }

    public boolean isDiagonal(Move move, Space[][] board) {
        Position start = move.getStart();
        return false;
    }


    /**
     * Checks to see if the move made was adjacent, if not return false.
     * Does so by checking the displacements of the rows/cols.
     * @param move Move to be checked.
     * @return bool if adjacent
     */
    public boolean isAdjacent(Move move){

        // Get Positions
        Position initialPosition = move.getStart();
        Position finalPosition = move.getEnd();

        // Get row and column of first position
        int rowOfFirst = initialPosition.getRow();
        int colOfFirst = initialPosition.getCell();

        //Get row and column of second position
        int rowOfSecond = finalPosition.getRow();
        int colOfSecond = finalPosition.getCell();

        int rowDist = rowOfFirst - rowOfSecond;
        int colDist = colOfFirst - colOfSecond;

        // Check displacement to see if move was adjacent
        if(rowDist == -1 || rowDist == 0 || rowDist == 1){
            if (colDist == -1 || colDist == 0 || colDist == 1){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }

    }



}
