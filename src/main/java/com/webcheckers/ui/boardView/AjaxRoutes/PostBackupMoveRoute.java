package com.webcheckers.ui.boardView.AjaxRoutes;

import com.google.gson.Gson;
import com.webcheckers.appl.CurrentGames;
import com.webcheckers.model.Player;
import com.webcheckers.ui.boardView.Message;
import com.webcheckers.ui.boardView.Move;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

/**
 *  The Ajax route responsible for undoing a players last validated move.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class PostBackupMoveRoute implements Route {

    //Key in the session attribute map for the current user Player object
    static final String CURR_PLAYER = "currentPlayer";
    //Key in the session attribute map for the hash of current players in a game
    static final String CURRENTGAMES_KEY = "currentGames";
    //Key in the session attribute map for the most recent move
    static final String MOVE_KEY = "move";
    //Key in the session attribute map for if a move has been made
    static final String MOVE_MADE_KEY = "moveMade";

    private final Gson gson = new Gson();

    /**
     * Undoes the players last move.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     *
     * @return GSON object which will be read by server
     */
    @Override
    public Object handle(Request request, Response response) {
        Session httpSession = request.session();

        Player currentPlayer = httpSession.attribute(CURR_PLAYER);
        CurrentGames currentGames = httpSession.attribute(CURRENTGAMES_KEY);
        Move move = httpSession.attribute(MOVE_KEY);

        currentGames.undoMove(currentPlayer, move);

        //Swap the move made attribute
        httpSession.attribute(MOVE_MADE_KEY, false);

        return gson.toJson(new Message("Move Undone",Message.Type.info));
    }
}
