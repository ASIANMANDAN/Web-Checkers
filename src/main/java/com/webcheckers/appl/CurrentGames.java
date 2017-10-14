package com.webcheckers.appl;

import java.util.HashMap;

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

    //Holds a list of all players in a game.
    private static HashMap<String, String> inGame = new HashMap<>();

    /**
     * Determines if a player is already in a game.
     *
     * @param username the name of the player to check
     * @return whether that player is in a game already or not
     */
    public boolean playerInGame(String username) {
        return inGame.containsKey(username) || inGame.containsValue(username);
    }

    /**
     * Adds two players to the hashmap.
     *
     * @param currentPlayer the player who created the game originally,
     *                      is the key
     * @param opponent the player who gets redirected to the game, is the value
     */
    public void addPlayer(String currentPlayer, String opponent) {
        if (!playerInGame(opponent)) {
            inGame.put(currentPlayer, opponent);
        }
    }

    /**
     * creates a URL to which the currentPlayer will be redirected to.
     *
     * @param currentPlayer the name of the Player who will be listed as
     *                      an opponent in the URL
     * @return the URL to be redirected to
     */
    public String createURL(String currentPlayer) {
        return "/game?opponent=" + currentPlayer;
    }
}
