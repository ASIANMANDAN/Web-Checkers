package com.webcheckers.ui.boardView.AjaxRoutes;

import com.google.gson.Gson;
import com.webcheckers.ui.boardView.Message;
import spark.Request;
import spark.Response;
import spark.Route;

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

    /**
     * Allows the player to submit their turn.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     *
     * @return GSON object which will be read by server
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        return gson.toJson(new Message("true",Message.Type.info));
    }
}
