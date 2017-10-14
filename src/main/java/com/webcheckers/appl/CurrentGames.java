package com.webcheckers.appl;

import com.webcheckers.ui.boardView.BoardView;

import java.util.ArrayList;

/**
 * Application Tier class which represents a list of users
 * that are in a game. Also handles the creation of URLs to
 * redirect people that have been selected for a game.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class CurrentGames {

    //Holds a list of all active games
    private static ArrayList<Game> currentGames = new ArrayList<>();

    /**
     * Determines if a player is already in a game.
     *
     * @param username the name of the player to check
     * @return whether that player is in a game already or not
     */
    public boolean playerInGame(String username) {
        //Iterate through each game in the collection
        for (Game game : currentGames) {
            if (game.playerInGame(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a Game object to list of all ongoing games
     *
     * @param game the game object to add
     */
    public void addGame(Game game) {
        currentGames.add(game);
    }

    /**
     * creates a URL to which the currentPlayer will be redirected to.
     *
     * @param currentPlayer the name of the Player which is used to find
     *                      the game they're associated with
     * @return the URL to be redirected to
     */
    public String createURL(String currentPlayer) {
        String opponent = getOpponent(currentPlayer);
        return "/game?opponent=" + opponent;
    }

    /**
     * Gets the player who started the game in a Game object.
     *
     * @param username the name of the user who was redirected to the game page
     * @return the name of the player who initiated the match
     */
    private String getOpponent(String username) {
        for (Game game : currentGames) {
            if (game.player2.equals(username)) {
                return game.player1;
            }
        }
        return null;
    }

    /**
     * Determines if two players are playing a game with one another.
     *
     * @param p1 name of the player which started the game
     * @param p2 name of the player who gets redirected to the game
     * @return whether or not the two players are in the same game
     */
    public boolean playingTogether(String p1, String p2) {
        for (Game game : currentGames) {
            if (game.player1.equals(p1) && game.player2.equals(p2)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the BoardView associated with a specific game.
     *
     * @param username the name of the player the BoardView is associated with
     * @return the BoardView for that player
     */
    public BoardView getView(String username) throws Exception {
        for (Game game : currentGames) {
            if (game.player2.equals(username)) {
                return new BoardView(game.board, true);
            }
        }
        return null;
    }
}
