package com.webcheckers.model.board;

import com.webcheckers.ui.boardView.Move;
import com.webcheckers.ui.boardView.Position;
import java.util.Stack;

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
    public enum ViewMode {PLAY, SPECTATOR, REPLAY}

    private Space[][] board;
    public ActiveColor currentTurn;

    //Number of checker pieces on the board per color
    private int numOfWhitePieces = 0;
    private int numOfRedPieces = 0;

    //A stack to remember what pieces were captured in a given turn
    private Stack<Piece> capturedPieces = new Stack<>();

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

        int red = 0;
        int white = 0;
        //Count the number of pieces on the passed in board
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Space space = this.board[i][j];
                if (space.getPiece() != null) {
                    if (space.getPiece().getColor() == Piece.Color.RED) {
                        red++;
                    }
                    if (space.getPiece().getColor() == Piece.Color.WHITE) {
                        white++;
                    }
                }

            }
        }
        this.numOfRedPieces = red;
        this.numOfWhitePieces = white;
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
        this.numOfRedPieces = 12;
        this.numOfWhitePieces = 12;
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

            //Update the piece count
            if(middle.getPiece().getColor() == Piece.Color.RED){
                numOfRedPieces--;
            }else{
                numOfWhitePieces--;
            }

            this.capturedPieces.push(middle.getPiece());

            board[middle.getRow()][middle.getCol()].removePiece();
        }

        if(end.getRow() == 0 || end.getRow() == size - 1){
            Space space = this.board[end.getRow()][end.getCell()];
            if(space.getPiece().getType() != Piece.Type.KING){
                makeKing(this.board[end.getRow()][end.getCell()]);
            }
        }
    }

    /**
     * Undo a previously made move on the game board.
     *
     * @param move the start and end coordinates of the move that was just made
     */
    public void undoMove(Move move) {
        //Reverse the move
        Position start = move.getEnd();
        Position end = move.getStart();

        Piece piece = this.board[start.getRow()][start.getCell()].getPiece();
        this.board[start.getRow()][start.getCell()].removePiece();
        this.board[end.getRow()][end.getCell()].setPiece(piece);

        //Determine if the move made was a jump and store the
        //middle Space here so the piece can be "captured"
        Space middle = getMiddle(move);

        //"Capture" an opponents Piece if a jump occurred
        if (middle != null) {

            //Update the piece count
            if(middle.getPiece().getColor() == Piece.Color.RED){
                numOfRedPieces++;
            }else{
                numOfWhitePieces++;
            }

            //Replace a piece if the middle space is null. This is in case
            //a player chooses to undo a jump
            if (middle.getPiece() == null && !this.capturedPieces.empty()) {
                board[middle.getRow()][middle.getCol()].setPiece(this.capturedPieces.pop());
            }
        }

        if(start.getRow() == 0 || start.getRow() == size - 1){
            Space space = this.board[end.getRow()][end.getCell()];
            if(space.getPiece().getType() == Piece.Type.KING){
                Piece.Color color = space.getPiece().getColor();
                space.removePiece();
                space.setPiece(new Piece(color, Piece.Type.SINGLE));
            }
        }
    }

    /**
     * Change the current turn so that it is the other players.
     */
    public void toggleTurn() {
        if (this.currentTurn.equals(ActiveColor.RED)) {
            this.currentTurn = ActiveColor.WHITE;
        } else {
            this.currentTurn = ActiveColor.RED;
        }
        //Create a new Stack so pieces captured in a previous
        //turn are not remembered
        this.capturedPieces = new Stack<>();
    }

    /**
     * Finds the first instance of a players piece on the board.
     *
     * @param player the desired players piece color
     * @return the coordinates for that piece as a Space
     */
    public Space findPiece(Piece.Color player) {

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                if (player == Piece.Color.RED) {
                    Space space = board[i][j];

                    if (space.getPiece() != null &&
                            space.getPiece().getColor() == Piece.Color.RED) {
                        return space;
                    }
                } else {
                    Space space = board[i][j];

                    if (space.getPiece() != null &&
                            space.getPiece().getColor() == Piece.Color.WHITE) {
                        return space;
                    }
                }
            }
        }
        return null;
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

    /**
     * Turn a single piece into a king
     *
     * @param space - the space that has the piece to upgrade
     * @return if the piece was successfully upgraded.
     */
    public boolean makeKing(Space space){
        Piece piece = space.getPiece();
        boolean result = false;

        //Check if piece is a single
        if(piece.getType() == Piece.Type.SINGLE){
            //Check piece color
            if(piece.getColor() == Piece.Color.RED){
                //Check if piece is in the correct row for promotion
                if(space.getRow() == 0){
                    space.removePiece();
                    space.setPiece(new Piece(piece.getColor(), Piece.Type.KING));
                    result = true;
                }
            }
            else if(piece.getColor() == Piece.Color.WHITE){
                if(space.getRow() == size-1){
                    space.removePiece();
                    space.setPiece(new Piece(piece.getColor(), Piece.Type.KING));
                    result = true;
                }
            }
        }
        return result;
    }
}
