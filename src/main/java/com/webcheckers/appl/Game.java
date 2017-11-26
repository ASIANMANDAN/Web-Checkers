package com.webcheckers.appl;

import com.webcheckers.model.CheckForWin;
import com.webcheckers.model.Player;
import com.webcheckers.model.board.Board;
import com.webcheckers.model.board.Piece;
import com.webcheckers.model.board.Space;
import com.webcheckers.ui.boardView.Move;

/**
 * Application tier class which contains all needed information
 * for passing a game to the UI controller.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class Game {

    Player red;
    Player white;
    Board board;
    CheckForWin winChecker;

    /**
     * Constructor for a Game object.
     *
     * @param red the player who initiated the game, i.e. who selected
     *           an opponent and hit play
     * @param white the player who was chosen and gets redirected from home
     */
    public Game(Player red, Player white) throws Exception {
        this.red = red;
        this.white = white;

        //Create a new board and set it up for gameplay
        this.board = new Board();
        this.board.newGame();

        //Create the win checker for checking for a win after every move.
        this.winChecker = new CheckForWin();
    }

    /**
     * Gets the red player.
     *
     * @return the player object
     */
    public Player getRedPlayer() {
        return red;
    }

    /**
     * Gets the white player.
     *
     * @return the player object
     */
    public Player getWhitePlayer() {
        return white;
    }

    /**
     * Given a player in the game, find that players opponent.
     *
     * @param player the player whose opponent is to be found
     * @return the name of the given users opponent
     */
    public Player getOpponent(Player player) {
        if (player.equals(this.red)) {
            return this.white;
        }

        if (player.equals(this.white)) {
            return this.red;
        }
        return null;
    }

    /**
     * Sets a given player to null in the Game object.
     * This represents a player who has resigned.
     *
     * @param player the player to be removed
     */
    public void removePlayer(Player player) {
        if (player.equals(this.red)) {
            this.red = null;
        }

        if (player.equals(this.white)) {
            this.white = null;
        }
    }

    /**
     * Move a Piece from one Space to another on the Board model.
     *
     * @param move the move to make
     */
    protected void makeMove(Move move) {
        this.board.makeMove(move);
    }

    /**
     * Checks the Game to see if there is a player that won.
     *
     * @return true if victory present/false otherwise
     */
    protected boolean checkForWin() {
        boolean victoryPresent = this.winChecker.checkForWin(this.board.getNumOfWhitePieces(),
                this.board.getNumOfRedPieces(), this.board.getBoard(), this.board.currentTurn);

        if (victoryPresent) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Undo a previously made move.
     *
     * @param move the move that was made
     */
    protected void undoMove(Move move) {
        this.board.undoMove(move);
    }

    /**
     * Change the current turn so that it is the other players.
     */
    protected void toggleTurn() {
        this.board.toggleTurn();
    }

    /**
     * Gets the board model which tracks the game.
     *
     * @return the board model
     */
    protected Space[][] getBoard() {
        return board.getBoard();
    }

    /**
     * Gets the current players turn.
     *
     * @return the turn
     */
    protected Board.ActiveColor getTurn() {
        return board.currentTurn;
    }

    /**
     * Determines if a given player is in this game.
     *
     * @param player the player to check for
     * @return whether the player is in a game or not
     */
    public boolean playerInGame(Player player) {
        return player.equals(this.red) || player.equals(this.white);
    }
}
