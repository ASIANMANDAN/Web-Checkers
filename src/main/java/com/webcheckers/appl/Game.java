package com.webcheckers.appl;

import com.webcheckers.model.Player;
import com.webcheckers.model.board.Board;
import com.webcheckers.model.board.Piece;
import com.webcheckers.model.board.Space;
import com.webcheckers.ui.boardView.Move;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;

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

    Player red;
    Player white;
    Board board;
    ArrayList<Move> listOfMoves;

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
        this.listOfMoves = new ArrayList<>();
    }

    /**
     * Constructor for a Game object which accepts a board configuration for
     * ease of testing.
     *
     * @param red the player who initiated the game, i.e. who selected
     *           an opponent and hit play
     * @param white the player who was chosen and gets redirected from home
     * @param board a board configuration
     */
    public Game(Player red, Player white, Space[][] board) throws Exception {
        this.red = red;
        this.white = white;

        //Input the given board
        this.board = new Board(board, Board.ActiveColor.RED);
    }

    /**
     * Constructor for a Game object which accepts a board configuration for
     * ease of testing.
     *
     * @param red the player who initiated the game, i.e. who selected
     *           an opponent and hit play
     * @param white the player who was chosen and gets redirected from home
     * @param board a board configuration
     */
    public Game(Player red, Player white, Space[][] board, ArrayList<Move> moveList) throws Exception {
        this.red = red;
        this.white = white;

        //Input the given board
        this.board = new Board(board, Board.ActiveColor.RED);

        //Input the given list of moves
        this.listOfMoves = moveList;

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
        if (player.equals(this.red)) {
            return this.white;
        }

        if (player.equals(this.white)) {
            return this.red;
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
        if (player.equals(this.red)) {
            this.red = null;
        }

        if (player.equals(this.white)) {
            this.white = null;
        }
    }

    /**
     * Move a Piece from one Space to another on the Board model.
     *
     * @param move the move to make
     */
    protected void makeMove(Move move) {
        this.board.makeMove(move);
    }

    /**
     * Undo a previously made move.
     *
     * @param move the move that was made
     */
    protected void undoMove(Move move) {
        this.board.undoMove(move);

        //Remove last move
        int lastIndex = listOfMoves.size()-1;
        listOfMoves.remove(lastIndex);
    }

    /**
     * Return the piece color of a given player.
     *
     * @param player the player whose color to find
     * @return the players piece color
     */
    protected Piece.Color getPlayerColor(Player player) {
        if (player.equals(this.red)) {
            return Piece.Color.RED;
        }
        if (player.equals(this.white)) {
            return Piece.Color.WHITE;
        }
        return null;
    }

    /**
     * Change the current turn so that it is the other players.
     */
    protected void toggleTurn() {
        this.board.toggleTurn();
    }

    /**
     * Gets the board model which tracks the game.
     *
     * @return the board model
     */
    protected Space[][] getBoard() {
        return board.getBoard();
    }

    /**
     * Gets the current players turn.
     *
     * @return the turn
     */
    protected Board.ActiveColor getTurn() {
        return board.currentTurn;
    }

    /**
     * Determines if a given player is in this game.
     *
     * @param player the player to check for
     * @return whether the player is in a game or not
     */
    public boolean playerInGame(Player player) {
        return player.equals(this.red) || player.equals(this.white);
    }

    /**
     * Tells which players are playing against each other.
     *
     * @return red player name vs white player name
     */
    @Override
    public String toString(){
        if(getRedPlayer() == null || getWhitePlayer() == null){
            return null;
        }
        return getRedPlayer().getUsername() + " vs " + getWhitePlayer().getUsername();
    }

    /**
     * Adds move to list of total moves in game.
     * @param move move to be added.
     */
    public void addMove(Move move) {
        listOfMoves.add(move);
    }

    public ArrayList<Move> getListOfMoves(){
        return listOfMoves;
    }
}
