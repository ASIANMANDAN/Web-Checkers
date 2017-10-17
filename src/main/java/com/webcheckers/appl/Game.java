package com.webcheckers.appl;

import com.webcheckers.model.board.Board;
import com.webcheckers.ui.boardView.BoardView;

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

    final String player1;
    final String player2;
    final Board board;

    /**
     * Constructor for a Game object.
     *
     * @param p1 name of the player who initiated the game, i.e. who selected
     *           an opponent and hit play
     * @param p2 name of the player who was chosen and gets redirected from home
     * @param board board model to base UI BoardView on
     */
    public Game(String p1, String p2, Board board) {
        this.player1 = p1;
        this.player2 = p2;
        this.board = board;
    }

    /**
     * Gets the name of the player who started the game.
     *
     * @return the name of the player
     */
    public String getPlayer1() {
        return player1;
    }

    /**
     * Given a player in the game, find that players opponent.
     *
     * @param username the user whose opponent is to be found
     * @return the name of the given users opponent
     */
    public String getOtherPlayer(String username) {
        if (this.player1.equals(username)) {
            return player2;
        }

        if (this.player2.equals(username)) {
            return player1;
        }
        return null;
    }

    /**
     * Returns an integer based off of whether a given player
     * is the one who first created the game or if they joined
     * afterwards.
     *
     * @param username the players index to find
     * @return an integer representing the order in which
     *         the given player joined the game
     */
    public int getPlayerIndex(String username) {
        if (this.player1.equals(username)) {
            return 1;
        }
        if (this.player2.equals(username)) {
            return 2;
        }
        return 0;
    }

    /**
     * Creates the BoardView associated with a specific game and player.
     *
     * @param username the name of the player the BoardView is associated with
     * @return the BoardView for that player
     */
    public BoardView createView(String username) throws Exception {
        if (this.player1.equals(username)) {
            return new BoardView(this.board, false);
        }
        if (this.player2.equals(username)) {
            return new BoardView(this.board, true);
        }
        return null;
    }

    /**
     * Gets the name of the player who joined the game.
     *
     * @return the name of the player
     */
    public String getPlayer2() {
        return player2;
    }

    /**
     * Gets the board model which tracks the game.
     *
     * @return the board model
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Determines if a given username is contained within the object.
     *
     * @param username name of player to check for
     * @return whether the player is in a game or not
     */
    boolean playerInGame(String username) {
        return this.player1.equals(username) || this.player2.equals(username);
    }
}
