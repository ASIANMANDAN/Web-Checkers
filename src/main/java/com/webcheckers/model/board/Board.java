package com.webcheckers.model.board;

import com.sun.tools.javac.util.Pair;
import com.webcheckers.ui.boardView.Move;
import com.webcheckers.ui.boardView.Position;

import java.util.HashMap;
import java.util.Map;

/**
 * Model tier class that represents the checkers board.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class Board {

    //The color of the player whose turn it currently is
    public enum ActiveColor {RED, WHITE}

    //The mode of the Game View
    public enum ViewMode {PLAY, SPECTATE, REPLAY}

    private Space[][] board;
    public ActiveColor currentTurn;

    //Number of checker pieces on the board per color
    public int numOfWhitePieces = 12;
    public int numOfRedPieces = 12;

    //Used to determine the size of the board
    public static final int size = 8;

    /**
     * Creates a board used for keeping track of the state
     * of the game.
     *
     * @throws Exception occurs if the given column or row of a space
     * is greater or less than the bounds established by a standard
     * game board
     */
    public Board() throws Exception {
        this.board = new Space[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                //Black spaces
                if ((i%2 == 0 && j%2 == 1) || (i%2 == 1 && j%2 == 0)) {
                    this.board[i][j] = new Space(i, j, Space.Color.BLACK);
                }
                //White spaces
                else {
                    this.board[i][j] = new Space(i, j, Space.Color.WHITE);
                }
            }
        }

        this.currentTurn = ActiveColor.RED;
    }

    /**
     * Creates a Board object based off a given game board and current turn.
     *
     * @param board the game board to crate the Board after
     * @param turn the current turn
     * @throws Exception occurs if the given column or row of a space
     * is greater or less than the bounds established by a standard
     * game board
     */
    public Board(Space[][] board, Board.ActiveColor turn) throws Exception {
        this.board = board;
        this.currentTurn = turn;
    }

    /**
     * Populates the board with pieces in the starting game formation.
     */
    public void newGame() {
        //Places white pieces at the top of the board
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < size; j++) {

                Space space = this.board[i][j];
                if (space.isValid()) {
                    space.setPiece(new Piece(Piece.Color.WHITE,
                            Piece.Type.SINGLE));
                }
            }
        }

        //Places red pieces at the bottom of the board
        for (int i = size - 3; i < size; i++) {
            for (int j = 0; j < size; j++) {

                Space space = this.board[i][j];
                if (space.isValid()) {
                    space.setPiece(new Piece(Piece.Color.RED,
                            Piece.Type.SINGLE));
                }
            }
        }
    }

    /**
     * Move a piece on the game board.
     *
     * @param move the start and end coordinates of the piece to move
     */
    public void makeMove(Move move) {
        Position start = move.getStart();
        Position end = move.getEnd();

        Piece piece = this.board[start.getRow()][start.getCell()].getPiece();
        this.board[start.getRow()][start.getCell()].removePiece();
        this.board[end.getRow()][end.getCell()].setPiece(piece);

        //Determine if the move made was a jump and store the
        //middle Space here so the piece can be "captured"
        Space middle = getMiddle(move);

        //"Capture" an opponents Piece if a jump occurred
        if (middle != null) {
            Piece jumpedPiece = board[middle.getRow()][middle.getCol()].getPiece();
            if(jumpedPiece.getColor() == Piece.Color.RED){
                numOfRedPieces--;
            }else{
                numOfWhitePieces--;
            }
            board[middle.getRow()][middle.getCol()].removePiece();
        }

        if (this.currentTurn.equals(ActiveColor.RED)) {
            this.currentTurn = ActiveColor.WHITE;
        } else {
            this.currentTurn = ActiveColor.RED;
        }
    }

    /**
     * Checks the board to see if there is a player that won.
     *
     * @return true if victory present/false otherwise
     */
    public boolean checkForVictory(){
        ActiveColor opponentColor;
        if(this.currentTurn != ActiveColor.RED){
            opponentColor = ActiveColor.WHITE;
        }else{
            opponentColor = ActiveColor.RED;
        }

        if (numOfWhitePieces == 0 || numOfRedPieces == 0){
            return true;
        }

        boolean cantMove = true;
        //Check if opponents pieces can move
        for (int row = 0; row < this.size; row++){
            for (int col = 0; col < this.size; col++){

                //Check opponents color
                if(opponentColor == ActiveColor.WHITE){
                    Space space = this.board[row][col];
                    Piece piece = space.getPiece();
                    Piece.Color color = piece.getColor();

                    if (piece != null && color == Piece.Color.WHITE){
                        Space upperLeft = this.board[row-1][col-1];
                        Space upperRight = this.board[row-1][col+1];
                        Space bottomLeft = this.board[row+1][col-1];
                        Space bottomRight = this.board[row+1][col-1];

                    }

                }else{
                    Space space = this.board[row][col];

                }
            }
        }


        return false;
    }

    /**
     * Get the Space which a piece is jumping over.
     *
     * @param move the Move being made
     * @return the Space that was jumped over (if a jump occurred)
     */
    private Space getMiddle(Move move) {
        Position start = move.getStart();
        Position end = move.getEnd();

        //The row and column location of the middle Space
        int rowMiddle = 0;
        int colMiddle = 0;

        //Indicates the Piece is moving "upwards"
        if (start.getRow() > end.getRow()) {

            int distance = start.getRow() - end.getRow();

            //Indicates a jump did not occur
            if (distance < 2) {
                return null;
            }

            rowMiddle = start.getRow() - 1;

            //Indicates a leftwards move
            if (start.getCell() < end.getCell()) {
                colMiddle = start.getCell() + 1;
            }
            //Move occurred to the right of the start Position
            else {
                colMiddle = start.getCell() - 1;
            }
        }

        //Indicates the Piece is moving "downwards"
        if (start.getRow() < end.getRow()) {

            int distance = end.getRow() - start.getRow();

            //Indicates a jump did not occur
            if (distance < 2) {
                return null;
            }

            rowMiddle = start.getRow() + 1;

            //Indicates a leftwards move
            if (start.getCell() < end.getCell()) {
                colMiddle = start.getCell() + 1;
            }
            //Move occurred to the right of the start Position
            else {
                colMiddle = start.getCell() - 1;
            }
        }
        return board[rowMiddle][colMiddle];
    }

    /**
     * Retrieves num of white pieces.
     *
     * @return # of white pieces
     */
    public int getNumOfWhitePieces(){
        return numOfWhitePieces;
    }

    /**
     * Retrieves num of red pieces
     *
     * @return # of red pieces
     */
    public int getNumOfRedPieces(){
        return numOfRedPieces;
    }

    /**
     * Retrieves the 2d array of Spaces representing the game board.
     *
     * @return 2d array of Spaces.
     */
    public Space[][] getBoard() {
        return this.board;
    }
}
