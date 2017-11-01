package com.webcheckers.ui.boardView.AjaxRoutes;

import com.google.gson.Gson;
import com.webcheckers.ui.boardView.Message;
import com.webcheckers.ui.boardView.Move;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

public class PostValidateMove implements Route{
    private final Gson gson = new Gson();

    //Key in the session attribute map for the current user Player object
    static final String CURR_PLAYER = "currentPlayer";

    static final String MOVE_KEY = "move";

    @Override
    public Object handle(Request request, Response response) {
        Session httpSession = request.session();
        Move move = gson.fromJson(request.body(), Move.class);
        httpSession.attribute(MOVE_KEY, move);

        if (true) {
            return gson.toJson(new Message("true", Message.Type.info));
        } else {
            return gson.toJson(new Message("true", Message.Type.error));
        }


    }
}
