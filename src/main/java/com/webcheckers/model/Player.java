package com.webcheckers.model;

import com.webcheckers.appl.Game;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * PLayer instance. Contains username for the instance of that Player.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class Player {

    private final String username;
    private final ArrayList<Game> gamesPlayed;

    /**
     * Instantiates Player with a username.
     *
     * @param newUserName new user name
     */
    public Player(String newUserName){
        this.username = newUserName;
        this.gamesPlayed = new ArrayList<>();
    }

    /**
     * Gets username.
     *
     * @return name of user
     */
    public String getUsername(){
        return this.username;
    }

    /**
     * .equals method for a Player object. Determines if a Player object is
     * equal based off of a Player's username.
     * @param obj other Player to check
     * @return boolean for itf the two objects are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Player)) return false;
        final Player that = (Player) obj;
        return this.username.equals(that.getUsername());
    }

    /**
     * Override of default hash method to force the system
     * to hash players by their username and nothing else;
     *
     * @return the objects hash code
     */
    @Override
    public int hashCode() {
        return username.hashCode();
    }

    /**
     * Once the Player is done with a game, it is added to the list
     * of completed games.
     * @param game completed game to add to gamesPlayed
     */
    public void addGame(Game game){
        //Games with no moves made should not be replayable
        if (game.getListOfMoves().size() > 0) {
            this.gamesPlayed.add(game);
        }
    }

    /**
     * Returns list of completed games.
     * @return completed games
     */
    public ArrayList<Game> getGamesPlayed(){
        return this.gamesPlayed;
    }
}
