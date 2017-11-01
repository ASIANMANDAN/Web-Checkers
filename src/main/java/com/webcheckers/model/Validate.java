package com.webcheckers.model;

import com.webcheckers.model.board.Space;
import com.webcheckers.ui.boardView.Move;
import com.webcheckers.ui.boardView.Position;

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
        if (!isDiagonal(move, board)) {
            return "The move was not diagonal";
        }
        return null;
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
}
