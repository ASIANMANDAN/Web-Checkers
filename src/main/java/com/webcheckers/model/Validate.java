package com.webcheckers.model;

import com.webcheckers.model.board.Board;
import com.webcheckers.model.board.Piece;
import com.webcheckers.model.board.Space;
import com.webcheckers.ui.boardView.Move;
import com.webcheckers.ui.boardView.Position;

import java.util.ArrayList;

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
    public String isValid(Move move, Board board) {
        String message = null;

        ArrayList<Move> validJumps = isJumpPresent(move, board);

        //Case for when a jump is available but was not taken
        if (validJumps.size() > 0 && !didJump(move, validJumps)) {
            message = "A jump is currently present and must be taken.";
        }

        if (validJumps.size() == 0 && !isAdjacent(move)) {
            message = "That move cannot be a jump since there are no jumps available.";
        }

        if (!isDiagonal(move)) {
            message = "That move was not diagonal";
        }

        if (isBackwards(move, board)) {
            message = "That piece cannot move backwards";
        }

        return message;
    }

    /**
     * Special validation used when multiple jumps can be made. Allows only
     * additional jumps to be made.
     *
     * @param move the proposed move to make
     * @param board the board the move will take place on
     * @return a message stating either that a move is valid or why
     *         one isn't
     */
    public String continueJump(Move move, Board board) {
        String message = null;

        //Get the Space where the moving piece currently stands
        int row = move.getStart().getRow();
        int col = move.getStart().getCell();
        Space space = board.getBoard()[row][col];

        //Get any possible jumps available at that Space
        ArrayList<Move> validJumps = getJumps(space, board);

        if (validJumps.size() == 0) {
            message = "No further jumps can be made.";
        }

        if (validJumps.size() > 0 && !didJump(move, validJumps)) {
            message = "A jump is currently present and must be taken.";
        }

        if (isBackwards(move, board)) {
            message = "That piece cannot move backwards";
        }
        return message;
    }

    /**
     * Determine if a proposed Move is diagonal from the Pieces' start.
     *
     * @param move the proposed move to make
     * @return whether or not the move is diagonal
     */
    private boolean isDiagonal(Move move) {
      
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
    private boolean isAdjacent(Move move){

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
            return colDist == -1 || colDist == 0 || colDist == 1;
        }else{
            return false;
        }
    }

    /**
     * Determine if the given Move was a jump.
     *
     * @param move the proposed move to make
     * @param validJumps a list of all valid jumps that can currently be made
     * @return whether or not the Move mad was a jump or not
     */
    private boolean didJump(Move move, ArrayList<Move> validJumps) {
        return validJumps.contains(move);
    }

    /**
     * Determine if a jump is present for any one of the players pieces
     * on a Board
     *
     * @param move a Move used for determining the color of the player
     * @param board the game Board
     * @return an ArrayList of valid jumps. If the list is empty then no jump
     *         is present.
     */
    private ArrayList<Move> isJumpPresent(Move move, Board board) {
        ArrayList<Move> validJumps = new ArrayList<>();

        int row = move.getStart().getRow();
        int col = move.getStart().getCell();

        Space[][] gameBoard = board.getBoard();
        Piece.Color color = gameBoard[row][col].getPiece().getColor();

        //Iterate through the board
        for (int i=0; i < Board.size; i++) {
            for (int j=0; j < Board.size; j++) {

                Space space = gameBoard[i][j];
                Piece piece = space.getPiece();

                //Determine if a piece belongs to the active players
                if (piece != null && piece.getColor() == color) {

                    //Indicates an opponent is adjacent to a piece
                    if (opponentNearby(space, board)) {
                        //Collect all legal jumps to the list
                        validJumps.addAll(getJumps(space, board));
                    }
                }
            }
        }
        return validJumps;
    }

    /**
     * Determine if an opponents piece is adjacent to a given Board Space.
     *
     * @param space the Space on the Board
     * @param board the game Board
     * @return whether or not an opponents piece is adjacent to the given Space
     */
    private boolean opponentNearby(Space space, Board board) {

        int row = space.getRow();
        int col = space.getCol();

        Space[][] gameBoard = board.getBoard();

        Piece piece = space.getPiece();
        Piece.Color color = piece.getColor();

        //Ensure that the top spaces can be checked
        if ((row - 1) >= 0) {

            //Ensure that the left side can be checked
            if ((col - 1) >= 0) {

                //Get the piece in the upper left space and see if it is an opponents
                Piece adjacentPiece = gameBoard[row - 1][col - 1].getPiece();
                if (adjacentPiece != null && adjacentPiece.getColor() != color) {
                    return true;
                }
            }

            //Ensure that the right side can be checked
            if ((col + 1) <= Board.size - 1) {

                //Get the piece in the upper right space and see if it is an opponents
                Piece adjacentPiece = gameBoard[row - 1][col + 1].getPiece();
                if (adjacentPiece != null && adjacentPiece.getColor() != color) {
                    return true;
                }
            }
        }

        //Ensure that the bottom spaces can be checked
        if ((row + 1) <= Board.size - 1) {

            //Ensure that the left side can be checked
            if ((col - 1) >= 0) {

                //Get the piece in the bottom left space and see if it is an opponents
                Piece adjacentPiece = gameBoard[row + 1][col - 1].getPiece();
                if (adjacentPiece != null && adjacentPiece.getColor() != color) {
                    return true;
                }
            }

            //Ensure that the right side can be checked
            if ((col + 1) <= Board.size - 1) {

                //Get the piece in the bottom right space and see if it is an opponents
                Piece adjacentPiece = gameBoard[row + 1][col + 1].getPiece();
                if (adjacentPiece != null && adjacentPiece.getColor() != color) {
                    return true;
                }
            }
        }

        //No opponent is adjacent to the given space
        return false;
    }

    /**
     * Get a collection of all valid jump movements that can be made from
     * a given Space on the Board.
     *
     * @param space the Space where possible jumps will start from
     * @param board the game board
     * @return a collection of all valid jump Moves
     */
    private ArrayList<Move> getJumps(Space space, Board board) {
        ArrayList<Move> jumps = new ArrayList<>();

        int row = space.getRow();
        int col = space.getCol();

        Space[][] gameBoard = board.getBoard();

        Piece piece = space.getPiece();
        Piece.Color color = piece.getColor();

        //Ensure that a jump upwards can be made by the piece and won't be out
        //of bounds
        if (((row - 2) >= 0 && color == Piece.Color.RED) ||
                ((row - 2) >= 0 && piece.getType() == Piece.Type.KING)) {

            //Ensure that a jump in the upper left direction won't be out of bounds
            if ((col - 2) >= 0) {

                Space middle = gameBoard[row - 1][col - 1];
                Space end = gameBoard[row - 2][col - 2];
                Piece pieceMiddle = middle.getPiece();
                Piece pieceEnd = end.getPiece();

                //Check that the piece being jumped over exists and is of
                //the opposite color. Also check that the end space does not
                //already have a piece next to it
                if (pieceMiddle != null && pieceMiddle.getColor() != color &&
                        pieceEnd == null) {

                    Position pStart = new Position(row, col);
                    Position pEnd = new Position(end.getRow(), end.getCol());
                    Move move = new Move(pStart, pEnd);

                    //Add move to list of valid jumps
                    jumps.add(move);
                }
            }

            //Ensure that a jump in the upper right direction won't be out of bounds
            if ((col + 2) <= Board.size - 1) {

                Space middle = gameBoard[row - 1][col + 1];
                Space end = gameBoard[row - 2][col + 2];
                Piece pieceMiddle = middle.getPiece();
                Piece pieceEnd = end.getPiece();

                //Check that the piece being jumped over exists and is of
                //the opposite color. Also check that the end space does not
                //already have a piece next to it
                if (pieceMiddle != null && pieceMiddle.getColor() != color &&
                        pieceEnd == null) {

                    Position pStart = new Position(row, col);
                    Position pEnd = new Position(end.getRow(), end.getCol());
                    Move move = new Move(pStart, pEnd);

                    //Add move to list of valid jumps
                    jumps.add(move);
                }
            }
        }

        //Ensure that a jump downwards can be made by the piece and won't be out
        //of bounds
        if (((row + 2) <= Board.size - 1 && color == Piece.Color.WHITE) ||
                ((row + 2) <= Board.size - 1 && piece.getType() == Piece.Type.KING)) {

            //Ensure that a jump in the lower left direction won't be out of bounds
            if ((col - 2) >= 0) {

                Space middle = gameBoard[row + 1][col - 1];
                Space end = gameBoard[row + 2][col - 2];
                Piece pieceMiddle = middle.getPiece();
                Piece pieceEnd = end.getPiece();

                //Check that the piece being jumped over exists and is of
                //the opposite color. Also check that the end space does not
                //already have a piece next to it
                if (pieceMiddle != null && pieceMiddle.getColor() != color &&
                        pieceEnd == null) {

                    Position pStart = new Position(row, col);
                    Position pEnd = new Position(end.getRow(), end.getCol());
                    Move move = new Move(pStart, pEnd);

                    //Add move to list of valid jumps
                    jumps.add(move);
                }
            }

            //Ensure that a jump in the lower right direction won't be out of bounds
            if ((col + 2) <= Board.size - 1) {

                Space middle = gameBoard[row + 1][col + 1];
                Space end = gameBoard[row + 2][col + 2];
                Piece pieceMiddle = middle.getPiece();
                Piece pieceEnd = end.getPiece();

                //Check that the piece being jumped over exists and is of
                //the opposite color. Also check that the end space does not
                //already have a piece next to it
                if (pieceMiddle != null && pieceMiddle.getColor() != color &&
                        pieceEnd == null) {

                    Position pStart = new Position(row, col);
                    Position pEnd = new Position(end.getRow(), end.getCol());
                    Move move = new Move(pStart, pEnd);

                    //Add move to list of valid jumps
                    jumps.add(move);
                }
            }
        }
        return jumps;
    }
}
