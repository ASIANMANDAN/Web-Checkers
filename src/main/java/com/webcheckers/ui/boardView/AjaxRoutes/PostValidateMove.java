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

public class PostValidateMove implements Route {

    private final Gson gson = new Gson();

    //Key in the session attribute map for the current user Player object
    static final String CURR_PLAYER = "currentPlayer";
    //Key in the session attribute map for the hash of current players in a game
    static final String CURRENTGAMES_KEY = "currentGames";
    //Key in the session attribute map for the most recent move
    static final String MOVE_KEY = "move";

    @Override
    public Object handle(Request request, Response response) {
        Session httpSession = request.session();
        CurrentGames currentGames = httpSession.attribute(CURRENTGAMES_KEY);
        Player currentPlayer = httpSession.attribute(CURR_PLAYER);

        Move move = gson.fromJson(request.body(), Move.class);
        httpSession.attribute(MOVE_KEY, move);

        String message = currentGames.validateMove(currentPlayer, move);

        if (message == null) {
            return gson.toJson(new Message("Move is valid", Message.Type.info));
        } else {
            return gson.toJson(new Message(message, Message.Type.error));
        }
    }
}
