package com.webcheckers.model.board;

import com.webcheckers.ui.boardView.Move;
import com.webcheckers.ui.boardView.Position;

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

        if(end.getRow() == 0 || end.getRow() == size - 1){
            makeKing(this.board[end.getRow()][end.getCell()]);
        }

        if (this.currentTurn.equals(ActiveColor.RED)) {
            this.currentTurn = ActiveColor.WHITE;
        } else {
            this.currentTurn = ActiveColor.RED;
        }
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
