package com.webcheckers.model;

import com.webcheckers.model.board.Board;
import com.webcheckers.model.board.Piece;
import com.webcheckers.model.board.Space;
import com.webcheckers.ui.boardView.Move;

import java.util.ArrayList;


/**
 * Model tier class that checks the board for a win.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class CheckForWin {

    public boolean checkForWin(int numWhitePieces, int numRedPieces, Space[][] board, Board.ActiveColor activeColor){

        // If a player doesn't have any more pieces
        if (numWhitePieces == 0 || numRedPieces == 0){
            return true;
        }

        //If Piece can't move straight
        if (cantMoveStraight(activeColor, board)){

            //If Piece Can't jump the pieces in front of it
            if(!isJumpPresent(activeColor, board)){
                return true;
            }
        }


        return false;
    }

    /**
     * Checks board for opponents pieces that can't move.
     * @param playerColor Color of current player
     * @param board board
     * @return true if opponent can't move.
     */
    private boolean cantMoveStraight(Board.ActiveColor playerColor, Space[][] board){
        //bools indicating whether each piece can't move
        ArrayList<Boolean> cantMoveBools = new ArrayList<>();

        Piece.Color opponentColor;
        Piece.Color currentColor;

        //After Move is made active colors are switched
        if (playerColor == Board.ActiveColor.RED){
            opponentColor = Piece.Color.RED;
            currentColor = Piece.Color.WHITE;
        }else{
            opponentColor = Piece.Color.WHITE;
            currentColor = Piece.Color.RED;
        }

        //Check all opponent Pieces on the board
        for (int row = 0; row < Board.size ; row++){
            for (int col = 0; col < Board.size; col ++){

                Space space = board[row][col];

                //Check only the opponents Pieces
                if (space.getPiece() != null && space.getPiece().getColor() == opponentColor){


                    if (col > 0 && col < Board.size - 1 && row > 0) {

                        //If Piece is a single
                        if (space.getPiece().getType() == Piece.Type.SINGLE){

                            //If piece is red
                            if (space.getPiece().getColor() == Piece.Color.RED){
                                Space left = board[row - 1][col - 1];
                                Space right = board[row - 1][col + 1];

                                //Can't Move
                                if ((left.getPiece() != null && left.getPiece().getColor() == currentColor) &&
                                        (right.getPiece() != null && right.getPiece().getColor() == currentColor)) {
                                    cantMoveBools.add(true);
                                } //Can Move
                                else{
                                    cantMoveBools.add(false);
                                }
                            }
                            //If piece is white
                            else {
                                Space left = board[row + 1][col - 1];
                                Space right = board[row + 1][col + 1];

                                //Can't Move
                                if ((left.getPiece() != null && left.getPiece().getColor() == currentColor) &&
                                        (right.getPiece() != null && right.getPiece().getColor() == currentColor)) {
                                    cantMoveBools.add(true);
                                }//Can Move
                                else{
                                    cantMoveBools.add(false);
                                }
                            }
                        }

                        //If Piece is a king
                        else{
                            //This is the appropriate check for king regardless of what color
                            Space upperleft = board[row - 1][col - 1];
                            Space upperRight = board[row - 1][col + 1];
                            Space lowerLeft = board[row + 1][col - 1];
                            Space lowerRight = board[row + 1][col + 1];

                            //Can't Move
                            if ((upperleft.getPiece() != null && upperleft.getPiece().getColor() == currentColor) &&
                                    (upperRight.getPiece() != null && upperRight.getPiece().getColor() == currentColor) &&
                                    (lowerLeft.getPiece() != null && lowerLeft.getPiece().getColor() == currentColor) &&
                                    (lowerRight.getPiece() != null && lowerRight.getPiece().getColor() == currentColor)) {
                                cantMoveBools.add(true);
                            }//Can Move
                            else{
                                cantMoveBools.add(false);
                            }
                        }

                    }
                    

                    //Check the top right corner
                    if (col == Board.size - 1 && row == 0) {
                        Space left = board[row + 1][col - 1];

                        //Can't Move
                        if (left.getPiece() != null && left.getPiece().getColor() == currentColor) {
                            cantMoveBools.add(true);
                        }//Can Move
                        else{
                            cantMoveBools.add(false);
                        }
                    }

                    //Check just the left side
                    if (col == Board.size - 1) {
                        Space left;
                        if (opponentColor == Piece.Color.WHITE){
                            left = board[row + 1][col - 1];
                        }else{
                            left = board[row - 1][col - 1];
                        }

                        //Can't Move
                        if (left.getPiece() != null && left.getPiece().getColor() == currentColor) {
                            cantMoveBools.add(true);
                        }//Can Move
                        else{
                            cantMoveBools.add(false);
                        }
                    }

                    //Check just the right side
                    if (col == 0) {
                        Space right = board[row - 1][col + 1];

                        //Can't move
                        if (right.getPiece() != null && right.getPiece().getColor() == currentColor) {
                            cantMoveBools.add(true);
                        }//Can Move
                        else{
                            cantMoveBools.add(false);
                        }
                    }

                }

            }
        }

        boolean cantMove = true;
        for (Boolean bool : cantMoveBools) {
            cantMove = bool && cantMove;
        }
        return cantMove;
    }
    /**
     * Determine if a jump is present for any one of the players pieces
     * on a Board
     *
     * @param playerColor gives current players color
     * @param board the game Board
     * @return whether or not one of the players pieces has a jump
     *         that can be made
     */
    private boolean isJumpPresent(Board.ActiveColor playerColor, Space[][] board) {

        Piece.Color opponentColor;
        if (playerColor == Board.ActiveColor.RED){
            opponentColor = Piece.Color.WHITE;
        }else{
            opponentColor = Piece.Color.RED;
        }

        for (int i=0; i < Board.size; i++) {
            for (int j=0; j < Board.size; j++) {

                Space space = board[i][j];

                //Indicates that the Space contains one of the players pieces
                if (space.getPiece() != null && space.getPiece().getColor() == opponentColor) {

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
