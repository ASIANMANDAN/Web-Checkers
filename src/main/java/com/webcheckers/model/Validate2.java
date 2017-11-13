package com.webcheckers.model;

import com.webcheckers.model.board.Board;
import com.webcheckers.model.board.Piece;
import com.webcheckers.model.board.Space;
import com.webcheckers.ui.boardView.Move;
import com.webcheckers.ui.boardView.Position;

import java.util.ArrayList;

public class Validate2 {

    public String isValid(Move move, Board board) {
        return null;
    }

    private ArrayList<Move> getAllMoves(Move move, Board board) {
        ArrayList<Move> validMoves = new ArrayList<>();
        ArrayList<Move> validJumps = new ArrayList<>();

        Space[][] gameboard = board.getBoard();

        int row = move.getStart().getRow();
        int col = move.getStart().getCell();

        Piece.Color color = gameboard[row][col].getPiece().getColor();

        //Iterate through the board model
        for (int i=0; i < Board.size; i++) {
            for (int j=0; j < Board.size; j++) {

                Space space = gameboard[i][j];
                Piece piece = space.getPiece();

                //Determine if a piece belongs to the active players
                if (piece != null && piece.getColor() == color) {
                    ArrayList<Move> temp = new ArrayList<>();

                    //Check if an opponent is nearby, if one is,
                    //determine if it can be jumped
                    if (opponentNearyby(space, board)) {
                        temp = getJumps(space, board);
                        validJumps.addAll(temp);
                    }

                    //If a jump is available then no other move can be made
                    if (validJumps.size() == 0) {
                        temp = getMoves(space, board);
                        validMoves.addAll(temp);
                    }
                }
            }
        }
        return null;
    }

    private ArrayList<Move> getMoves(Space space, Board board) {
        ArrayList<Move> moves = new ArrayList<>();

        int row = space.getRow();
        int col = space.getCol();

        Space[][] gameBoard = board.getBoard();

        Piece piece = space.getPiece();
        Piece.Color color = piece.getColor();

        //Ensure that a move upwards can be made by the piece and won't be out
        //of bounds
        if (((row - 1) >= 0 && color == Piece.Color.RED) ||
                ((row - 1) >= 0 && piece.getType() == Piece.Type.KING)) {

            //Ensure that a move in the upper left direction won't be out of bounds
            if ((col - 1) >= 0) {

                Space end = gameBoard[row - 1][col - 1];
                Piece pieceEnd = end.getPiece();

                //Check that the Space in question is not taken
                if (pieceEnd == null) {

                    Position pStart = new Position(row, col);
                    Position pEnd = new Position(end.getRow(), end.getCol());
                    Move move = new Move(pStart, pEnd);

                    //Add move to list of valid moves
                    moves.add(move);
                }
            }

            //Ensure that a move in the upper right direction won't be out of bounds
            if ((col + 1) <= Board.size - 1) {

                Space end = gameBoard[row - 1][col + 1];
                Piece pieceEnd = end.getPiece();

                //Check that the Space in question is not taken
                if (pieceEnd == null) {

                    Position pStart = new Position(row, col);
                    Position pEnd = new Position(end.getRow(), end.getCol());
                    Move move = new Move(pStart, pEnd);

                    //Add move to list of valid moves
                    moves.add(move);
                }
            }
        }

        //Ensure that a move downwards can be made by the piece and won't be out
        //of bounds
        if (((row + 1) >= Board.size - 1 && color == Piece.Color.WHITE) ||
                ((row + 1) >= Board.size - 1 && piece.getType() == Piece.Type.KING)) {

            //Ensure that a move in the lower left direction won't be out of bounds
            if ((col - 1) >= 0) {

                Space end = gameBoard[row + 1][col - 1];
                Piece pieceEnd = end.getPiece();

                //Check that the Space in question is not taken
                if (pieceEnd == null) {

                    Position pStart = new Position(row, col);
                    Position pEnd = new Position(end.getRow(), end.getCol());
                    Move move = new Move(pStart, pEnd);

                    //Add move to list of valid moves
                    moves.add(move);
                }
            }

            //Ensure that a jump in the lower right direction won't be out of bounds
            if ((col + 1) <= Board.size - 1) {

                Space end = gameBoard[row + 1][col + 1];
                Piece pieceEnd = end.getPiece();

                //Check that the Space in question is not taken
                if (pieceEnd == null) {

                    Position pStart = new Position(row, col);
                    Position pEnd = new Position(end.getRow(), end.getCol());
                    Move move = new Move(pStart, pEnd);

                    //Add move to list of valid moves
                    moves.add(move);
                }
            }
        }
        return moves;
    }

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
        }

        //Ensure that a jump downwards can be made by the piece and won't be out
        //of bounds
        if (((row + 2) >= Board.size - 1 && color == Piece.Color.WHITE) ||
                ((row + 2) >= Board.size - 1 && piece.getType() == Piece.Type.KING)) {

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
        }
        return jumps;
    }

    private boolean opponentNearyby(Space space, Board board) {

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

}
