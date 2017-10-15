package com.webcheckers.model.board;

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
    public final ActiveColor currentTurn;

    //Used to determine the size of the board
    public static final int size = 8;

    /**
     * Creates a board used for keeping track of the state
     * of the game.
     *
     * @throws Exception occurs if the given cellIdx is greater or less than
     * the bounds established by a standard game board
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
     * Retrieves the 2d array of Spaces representing the game board.
     *
     * @return 2d array of Spaces.
     */
    public Space[][] getBoard() {
        return this.board;
    }
}
