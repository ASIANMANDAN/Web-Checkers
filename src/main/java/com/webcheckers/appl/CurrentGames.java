package com.webcheckers.appl;

import com.webcheckers.model.Player;
import com.webcheckers.model.Validate;
import com.webcheckers.model.board.Board;
import com.webcheckers.model.board.Space;
import com.webcheckers.ui.boardView.Move;

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

    //Holds a list of all active games
    //private static ArrayList<Game> currentGames;
    private static HashMap<Player, Game> currentGames;

    /**
     * Default constructor, creates an empty list of games.
     */
    public CurrentGames() {
        currentGames = new HashMap<>();
    }

    /**
     * Allows a pre-defined list of games to be constructed.
     *
     * @param cg list of Game objects
     */
    public CurrentGames(HashMap<Player, Game> cg) {
        currentGames = cg;
    }

    /**
     * Determines if a player is already in a game.
     *
     * @param player the player to check
     * @return whether that player is in a game already or not
     */
    public boolean playerInGame(Player player) {
        Game game = getGame(player);

        if (game != null) {
            return game.playerInGame(player);
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
        Game game = new Game(red, white);
        currentGames.put(red, game);
        currentGames.put(white, game);
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
            //Remove this player from the map to represent their resignation
            currentGames.remove(player, game);
        }
    }

    /**
     * Determine if a given move is valid given the current
     * board configuration.
     *
     * @param player the player who made the move
     * @param move the proposed move to make
     * @return a message stating either that a move is valid or why
     *         one isn't
     * @throws Exception occurs if the given column or row of a space
     * is greater or less than the bounds established by a standard
     * game board
     */
    public String validateMove(Player player, Move move) throws Exception {

        //Copy the board to pass to validator to avoid it making changes
        Board board = new Board(getBoard(player), getTurn(player));
        return Validate.isValid(move, board);
    }

    /**
     *  Determine if a given move is valid win the case of multiple jumps.
     *
     * @param player the player who made the move
     * @param move the proposed move to make
     * @return a message stating either that a move is valid or why
     *         one isn't
     * @throws Exception occurs if the given column or row of a space
     * is greater or less than the bounds established by a standard
     * game board
     */
    public String continueJump(Player player, Move move) throws Exception {

        //Copy the board to pass to validator to avoid it making changes
        Board board = new Board(getBoard(player), getTurn(player));

        return Validate.continueJump(move, board);
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
     * Undoes a previously made move.
     *
     * @param player the player who made the move
     * @param move the move that was just made
     * @return whether or not the move was successfully undone.
     */
    public boolean undoMove(Player player, Move move) {
        Game game = getGame(player);
        if (game != null) {
            game.undoMove(move);
            return true;
        }
        return false;
    }

    /**
     * Toggle the state of the turn so that it is the other players.
     *
     * @param player the player who is ending their turn
     */
    public void toggleTurn(Player player) {
        Game game = getGame(player);
        if (game != null) {
            game.toggleTurn();
        }
    }

    /**
     * Returns the game that a given player is a part of.
     *
     * @param player the player whose game to find
     * @return the game that player is a part of
     */
    private Game getGame(Player player) {
        return currentGames.get(player);
    }
}
