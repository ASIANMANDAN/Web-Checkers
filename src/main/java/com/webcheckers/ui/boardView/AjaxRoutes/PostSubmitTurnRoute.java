package com.webcheckers.ui.boardView.AjaxRoutes;

import com.google.gson.Gson;
import com.webcheckers.appl.CurrentGames;
import com.webcheckers.ui.GetGameRoute;
import com.webcheckers.ui.GetHomeRoute;
import com.webcheckers.ui.WebServer;
import com.webcheckers.ui.boardView.Message;
import com.webcheckers.ui.boardView.Move;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

/**
 *  The Ajax route responsible for submitting a move to the model
 *  and ending a players turn.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class PostSubmitTurnRoute implements Route {

    private final Gson gson = new Gson();

    //Key in the session attribute map for the last move verified
    static final String MOVE_KEY = "move";
    //Key in the session attribute map for the hash of current players in a game
    static final String CURRENTGAMES_KEY = "currentGames";
    //Key in the session attribute map for the current user Player object
    static final String CURR_PLAYER = "currentPlayer";

    /**
     * Allows the player to submit their turn.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     *
     * @return GSON object which will be read by server
     */
    @Override
    public Object handle(Request request, Response response) {
        Session httpSession = request.session();
        CurrentGames currentGames = httpSession.attribute(CURRENTGAMES_KEY);
        Move move = httpSession.attribute(MOVE_KEY);

        //TODO: add winning move check to current games


        //Attempt to make move
        if (currentGames.makeMove(httpSession.attribute(CURR_PLAYER), move)) {

            if (currentGames.checkForWin(httpSession.attribute(CURR_PLAYER))){
                response.redirect(WebServer.HOME_URL);
            }

            //Text of Message is ignored
            return gson.toJson(new Message("",Message.Type.info));
        } else {
            //Text of Message is ignored
            return gson.toJson(new Message("",Message.Type.error));
        }


    }
}
