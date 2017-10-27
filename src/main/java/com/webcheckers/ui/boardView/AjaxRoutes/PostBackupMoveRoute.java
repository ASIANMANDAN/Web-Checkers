package com.webcheckers.ui.boardView.AjaxRoutes;

import com.google.gson.Gson;
import com.webcheckers.ui.boardView.Message;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 *  The Ajax route responsible for undoing a players last validated move.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class PostBackupMoveRoute implements Route {
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
        return gson.toJson(new Message("true",Message.Type.info));
    }
}
