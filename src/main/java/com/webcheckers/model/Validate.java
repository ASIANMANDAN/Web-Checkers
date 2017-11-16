package com.webcheckers.model;

import com.webcheckers.model.board.Board;
import com.webcheckers.model.board.Piece;
import com.webcheckers.model.board.Space;
import com.webcheckers.ui.boardView.Move;
import com.webcheckers.ui.boardView.Position;

/**
 * A class used to determine if a proposed Move complies with the
 * laws of American Checkers.
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

        //Stops the player from jumping when one isn't available
        if (!isJumpPresent(move, board) && didJump(move, board)) {
            message = "There was no jump present. Therefore you can't jump.";
        }

        //Forces the player to take an available jump
        if (isJumpPresent(move, board) && !didJump(move, board)) {
            message = "A jump is currently present and must be taken.";
        }

        //todo determine if this check is the same as check #1
        if (!isAdjacent(move) && !isJumpPresent(move, board)){
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

    /**
     * Determine if the given Move was a jump.
     *
     * @param move the proposed move to make
     * @param board the current game Board
     * @return whether or not the Move mad was a jump or not
     */
    private boolean didJump(Move move, Space[][] board) {
        Position start = move.getStart();
        Position end = move.getEnd();

        int row = start.getRow();
        int col = start.getCell();
        Space startSpace = board[row][col];

        //Piece moving "upwards"
        if (start.getRow() > end.getRow()) {
            int distance = start.getRow() - end.getRow();

            if (distance == 2 && opponentAdjacent(startSpace, board)) {
                return isDiagonal(move, board);
            }
        }

        //Piece moving "downwards"
        if (start.getRow() < end.getRow()) {
            int distance = end.getRow() - start.getRow();

            if (distance == 2 && opponentAdjacent(startSpace, board)) {
                return isDiagonal(move, board);
            }
        }

        return false;
    }

    /**
     * Determine if a jump is present for any one of the players pieces
     * on a Board
     *
     * @param move a Move used for determining the color of the player
     * @param board the game Board
     * @return whether or not one of the players pieces has a jump
     *         that can be made
     */
    private boolean isJumpPresent(Move move, Space[][] board) {

        int row = move.getStart().getRow();
        int col = move.getStart().getCell();

        Piece.Color color = board[row][col].getPiece().getColor();

        for (int i=0; i < Board.size; i++) {
            for (int j=0; j < Board.size; j++) {

                Space space = board[i][j];

                //Indicates that the Space contains one of the players pieces
                if (space.getPiece() != null && space.getPiece().getColor() == color) {

                    //Indicates an opponent is adjacent to a piece
                    if (opponentAdjacent(space, board)) {

                        if (canJump(space, board)) {
                            return true;
                        }
                    }
                }

            }
        }
        return false;
    }

    /**
     * Determine if an opponents piece is adjacent to a given Board Space.
     *
     * @param space the Space on the Board
     * @param board the game Board
     * @return whether or not an opponents piece is adjacent to the given Space
     */
    private boolean opponentAdjacent(Space space, Space[][] board) {

        int row = space.getRow();
        int col = space.getCol();
        Piece.Color color = space.getPiece().getColor();
        Piece.Type type = space.getPiece().getType();

        //Case when piece is RED and is SINGLE, only check in front of it
        if (color == Piece.Color.RED && type == Piece.Type.SINGLE) {

            //todo remove and clean up as it shouldn't matter if two opponents are adjacent
            //Check both sides of the given space
            if (col > 0 && col < Board.size - 1 && row > 0) {

                Space left = board[row - 1][col - 1];
                Space right = board[row - 1][col + 1];

                if ((left.getPiece() != null && left.getPiece().getColor() == Piece.Color.WHITE) ||
                        (right.getPiece() != null && right.getPiece().getColor() == Piece.Color.WHITE)) {
                    return true;
                }
            }

            //Check just the left side
            if (col == Board.size - 1) {
                Space left = board[row - 1][col - 1];
                if (left.getPiece() != null && left.getPiece().getColor() == Piece.Color.WHITE) {
                    return true;
                }
            }

            //Check just the right side
            if (col == 0) {
                Space right = board[row - 1][col + 1];
                if (right.getPiece() != null && right.getPiece().getColor() == Piece.Color.WHITE) {
                    return true;
                }
            }
        }

        //Case when piece is WHITE and is SINGLE, only check in front of it
        if (color == Piece.Color.WHITE && type == Piece.Type.SINGLE) {

            //Check both sides of the given space
            if (col > 0 && col < Board.size - 1 && row < Board.size - 1) {

                Space left = board[row + 1][col - 1];
                Space right = board[row + 1][col + 1];

                if ((left.getPiece() != null && left.getPiece().getColor() == Piece.Color.RED) ||
                        (right.getPiece() != null && right.getPiece().getColor() == Piece.Color.RED)) {
                    return true;
                }
            }

            //Check just the left side
            if (col == Board.size - 1) {
                Space left = board[row + 1][col - 1];
                if (left.getPiece() != null && left.getPiece().getColor() == Piece.Color.RED) {
                    return true;
                }
            }

            //Check just the right side
            if (col == 0) {
                Space right = board[row + 1][col + 1];
                if (right.getPiece() != null && right.getPiece().getColor() == Piece.Color.RED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Determines if a jump is possible from a given Board Space.
     *
     * @param space the Space on the Board to check
     * @param board the game Board
     * @return whether or not a jump is possible
     */
    private boolean canJump(Space space, Space[][] board) {

        int row = space.getRow();
        int col = space.getCol();
        Piece.Color color = space.getPiece().getColor();

        if (color == Piece.Color.RED) {

            //Ensure the piece would not go out of bounds if it jumped
            if (row - 2 >= 0) {

                //Check just the left side
                if ((col - 2) >= 0) {

                    //Find the Piece in between the start and end Position
                    Piece middle = board[row - 1][col - 1].getPiece();

                    if (board[row - 2][col - 2].getPiece() == null &&
                            middle != null && middle.getColor() == Piece.Color.WHITE) {
                        return true;
                    }
                }

                //Check right
                if ((col + 2) <= Board.size - 1) {

                    //Find the Piece in between the start and end Position
                    Piece middle = board[row - 1][col + 1].getPiece();

                    if (board[row - 2][col + 2].getPiece() == null &&
                            middle != null && middle.getColor() == Piece.Color.WHITE) {
                        return true;
                    }
                }
            }
        }

        if (color == Piece.Color.WHITE) {

            //Ensure the piece would not go out of bounds if it jumped
            if (row + 2 <= Board.size - 1) {

                //Check just the left side
                if ((col - 2) >= 0) {

                    //Find the Piece in between the start and end Position
                    Piece middle = board[row + 1][col - 1].getPiece();

                    if (board[row + 2][col - 2].getPiece() == null &&
                            middle != null && middle.getColor() == Piece.Color.RED) {
                        return true;
                    }
                }

                //Check right
                if ((col + 2) <= Board.size - 1) {

                    //Find the Piece in between the start and end Position
                    Piece middle = board[row + 1][col + 1].getPiece();

                    if (board[row + 2][col + 2].getPiece() == null &&
                            middle != null && middle.getColor() == Piece.Color.RED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
