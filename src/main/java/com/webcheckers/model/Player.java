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

    //boolean flag for determining when a player is
    //either spectating or replaying a game
    private boolean isWatching = false;

    //boolean flag for determining when another player
    //has chosen this player as an opponent
    private boolean isSelected = false;

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
     * Toggles the flag within a player object to
     * indicate that a given player is spectating or
     * replaying a game.
     */
    public void toggleWatching() {
        isWatching = !isWatching;
    }

    /**
     * Get the status of a players isWatching flag.
     *
     * @return the status of the isWatching flag
     */
    public boolean getWatching() {
        return isWatching;
    }

    /**
     * Toggle the flag within a player object to
     * indicate that another player has selected to
     * play a game with the current player.
     */
    public void toggleSelected() {
        isSelected = !isSelected;
    }

    /**
     * Get the status of a players isSelected flag.
     *
     * @return the status of the isSelected flag
     */
    public boolean getSelected() {
        return isSelected;
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
