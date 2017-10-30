package com.webcheckers.ui.boardView.AjaxRoutes;

import com.google.gson.Gson;
import com.webcheckers.appl.CurrentGames;
import com.webcheckers.model.Player;
import com.webcheckers.ui.WebServer;
import com.webcheckers.ui.boardView.Message;
import spark.*;

import java.util.logging.Logger;

/**
 *  The Ajax route responsible for handling a player resignation.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class PostResignRoute implements Route{
    private final Gson gson = new Gson();
    private static final Logger LOG = Logger.getLogger(PostResignRoute.class.getName());

    //Key in the session attribute map for the hash of current players in a game
    static final String CURRENTGAMES_KEY = "currentGames";
    //Key in the session attribute map for the current user Player object
    static final String CURR_PLAYER = "currentPlayer";
    //Key in the session attribute map for the current players opponent
    static final String OPPONENT_KEY = "opponent";

    /**
     * Allows a player to resign from the game.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     *
     * @return GSON object which will be read by server
     */
    @Override
    public Object handle(Request request, Response response) {
        Session session = request.session();

        Player currPlayer = session.attribute(CURR_PLAYER);
        CurrentGames currentGames = session.attribute(CURRENTGAMES_KEY);
        session.removeAttribute(OPPONENT_KEY);
        currentGames.removePlayer(currPlayer);

        LOG.fine(currPlayer.getUsername() + " has resigned. Redirecting to home page");
        Message message = new Message("Player Resigned. Redirecting to home page", Message.Type.info);

        return gson.toJson(message);
    }
}
