package com.webcheckers.appl;

import com.webcheckers.model.Player;
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

    final Player red;
    final Player white;
    final Board board;

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
        if (this.red.equals(player)) {
            return white;
        }

        if (this.white.equals(player)) {
            return red;
        }
        return null;
    }

    /**
     * Gets the board model which tracks the game.
     *
     * @return the board model
     */
    protected Board getBoard() {
        return board;
    }

    /**
     * Determines if a given player is in this game.
     *
     * @param player the player to check for
     * @return whether the player is in a game or not
     */
    public boolean playerInGame(Player player) {
        return this.red.equals(player) || this.white.equals(player);
    }
}
