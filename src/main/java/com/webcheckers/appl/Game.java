package com.webcheckers.appl;

import com.webcheckers.model.board.Board;

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
     * Determines if a given username is contained within the object.
     *
     * @param username name of player to check for
     * @return whether the player is in a game or not
     */
    boolean playerInGame(String username) {
        return this.player1.equals(username) || this.player2.equals(username);
    }
}
