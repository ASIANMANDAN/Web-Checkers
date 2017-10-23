package com.webcheckers.appl;

import com.webcheckers.model.Player;
import com.webcheckers.model.board.Board;
import com.webcheckers.model.board.Space;

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
     * @param player the player to check
     * @return whether that player is in a game already or not
     */
    public boolean playerInGame(Player player) {
        //Iterate through each game in the collection
        for (Game game : currentGames) {
            if (game.playerInGame(player)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Creates a Game object and adds it to the list of
     * all ongoing games.
     *
     * @param red the player to be assigned red
     * @param white the player to be assigned white
     * @throws Exception occurs if the given column or row of a space
     * is greater or less than the bounds established by a standard
     * game board
     */
    public void addGame(Player red, Player white) throws Exception {
        currentGames.add(new Game(red, white));
    }

    /**
     * creates a URL to which the currentPlayer will be redirected to.
     *
     * @param player the player which is used to find
     *        the game they're associated with
     * @return the URL to be redirected to
     */
    public String createURL(Player player) {
        String opponent = getOpponent(player).getUsername();
        return "/game?opponent=" + opponent;
    }

    /**
     * Given a player in a game, return that players opponent.
     *
     * @param player the player whose opponent to find
     * @return the opponent of the given player
     */
    private Player getOpponent(Player player) {
        Game game = getGame(player);
        return game.getOpponent(player);
    }

    public Player getRedPlayer(Player player) {
        return getGame(player).getRedPlayer();
    }

    public Player getWhitePlayer(Player player) {
        return getGame(player).getWhitePlayer();
    }

    public Board.ActiveColor getActiveColor(Player player) {
        return getGame(player).getBoard().currentTurn;
    }

    public Space[][] getBoard(Player player) {
        return getGame(player).getBoard().getBoard();
    }

    /**
     * Given a player, find that users game and remove it from
     * the list of ongoing games.
     *
     * @param player name of any user in that game
     */
    public void endGame(Player player) {
        currentGames.remove(getGame(player));
    }

    /**
     * Returns the game that a given player is a part of.
     *
     * @param player the player whose game to find
     * @return the game that player is a part of
     */
    private Game getGame(Player player) {
        for (Game game : currentGames) {
            if (game.red.equals(player) || game.white.equals(player)) {
                return game;
            }
        }
        return null;
    }
}
