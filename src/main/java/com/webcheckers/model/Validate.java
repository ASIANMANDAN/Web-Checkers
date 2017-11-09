package com.webcheckers.model;

import com.webcheckers.model.board.Space;
import com.webcheckers.ui.boardView.Move;
import com.webcheckers.ui.boardView.Position;

/**
 * The unit test suite for the {@link Validate} component.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class Validate {

    /**
     * Determines if a proposed move is valid given the current board
     * configuration.
     *
     * @param move the proposed move to make
     * @param board the board the move will take place on
     * @return a message stating either that a move is valid or why
     *         one isn't
     */
    public String isValid(Move move, Space[][] board) {
        String message = null;

        boolean isAdjacent = isAdjacent(move);
        if (!isAdjacent){
            message =  "There are no jumps present. Therefore you must move to a space that is " +
                    "adjacent to the piece you wish to move.";
        }

        if (!isDiagonal(move, board)) {
            message =  "The move was not diagonal";
        }

        return message;

    }

    /**
     * Determine if a proposed Move is diagonal from the Pieces' start.
     *
     * @param move the proposed move to make
     * @param board the board the move will take place on
     * @return whether or not the move is diagonal
     */
    private boolean isDiagonal(Move move, Space[][] board) {
        Position start = move.getStart();
        Position end = move.getEnd();

        //Piece moving "upwards"
        if (start.getRow() > end.getRow()) {
            int distance = start.getRow() - end.getRow();

            //Piece moving left
            if (start.getCell() > end.getCell()) {
                int i = start.getRow() - distance;
                int j = start.getCell() - distance;

                return i == end.getRow() && j == end.getCell();
            }

            //Piece moving right
            if (start.getCell() < end.getCell()) {
                int i = start.getRow() - distance;
                int j = start.getCell() + distance;

                return i == end.getRow() && j == end.getCell();
            }

        }

        //Piece moving "downwards"
        if (start.getRow() < end.getRow()) {
            int distance = end.getRow() - start.getRow();

            //Piece moving left
            if (start.getCell() > end.getCell()) {
                int i = start.getRow() + distance;
                int j = start.getCell() - distance;

                return i == end.getRow() && j == end.getCell();
            }

            //Piece moving right
            if (start.getCell() < end.getCell()) {
                int i = start.getRow() + distance;
                int j = start.getCell() + distance;

                return i == end.getRow() && j == end.getCell();
            }
        }
        return false;
    }


    /**
     * Checks to see if the move made was adjacent, if not return false.
     * Does so by checking the displacements of the rows/cols.
     *
     * @param move Move to be checked.
     * @return bool if adjacent
     */
    private boolean isAdjacent(Move move) {

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
        return (rowDist == -1 || rowDist == 0 || rowDist == 1) &&
                (colDist == -1 || colDist == 0 || colDist == 1);
    }


}
