package com.webcheckers.appl;

import com.webcheckers.model.Player;
import com.webcheckers.model.board.Board;
import com.webcheckers.model.board.Space;
import com.webcheckers.ui.boardView.Move;

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
    private static ArrayList<Game> currentGames;

    /**
     * Default constructor, creates an empty list of games.
     */
    public CurrentGames() {
        currentGames = new ArrayList<>();
    }

    /**
     * Allows a pre-defined list of games to be constructed.
     *
     * @param cg list of Game objects
     */
    public CurrentGames(ArrayList<Game> cg) {
        currentGames = cg;
    }

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
    public Player getOpponent(Player player) {
        Game game = getGame(player);
        if (game != null) {
            return game.getOpponent(player);
        }
        return null;
    }

    /**
     * Return the player who was assigned to red.
     *
     * @param player any player whose in the game
     * @return the red player
     */
    public Player getRedPlayer(Player player) {
        Game game = getGame(player);
        if (game != null) {
            return game.getRedPlayer();
        }
        return null;
    }

    /**
     * Return the player who was assigned to white.
     *
     * @param player any player whose in the game
     * @return the white player
     */
    public Player getWhitePlayer(Player player) {
        Game game = getGame(player);
        if (game != null) {
            return game.getWhitePlayer();
        }
        return null;
    }

    /**
     * Gets the board associated with a certain game.
     *
     * @param player any player whose in the game
     * @return the board used in that game
     */
    public Space[][] getBoard(Player player) {
        Game game = getGame(player);
        if (game != null) {
            return game.getBoard();
        }
        return null;
    }

    /**
     * Return the color whose turn it currently is.
     *
     * @param player any player whose in the game
     * @return the currently active color
     */
    public Board.ActiveColor getTurn(Player player) {
        Game game = getGame(player);
        if (game != null) {
            return game.getTurn();
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
        Game game = getGame(player);
        if (game != null) {
            game.removePlayer(player);
        }
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
     * Moves a Piece from one Space to another.
     *
     * @param player the player who made the move
     * @param move the move to make
     * @return whether or not the move was successfully made.
     */
    public boolean makeMove(Player player, Move move) {
        Game game = getGame(player);
        if (game != null) {
            game.makeMove(move);
            return true;
        }
        return false;
    }

    /**
     * Returns the game that a given player is a part of.
     *
     * @param player the player whose game to find
     * @return the game that player is a part of
     */
    private Game getGame(Player player) {
        for (Game game : currentGames) {
            if (player.equals(game.red) || player.equals(game.white)) {
                return game;
            }
        }
        return null;
    }
}
