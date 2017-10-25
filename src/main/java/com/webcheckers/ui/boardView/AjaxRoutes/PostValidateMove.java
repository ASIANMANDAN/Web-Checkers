package com.webcheckers.ui.boardView.AjaxRoutes;

/**
 * file: checkers-app
 * language: java
 * author: erl3193@rit.edu Emily Lederman
 * description:
 */
import com.google.gson.Gson;
import com.webcheckers.appl.CurrentGames;
import com.webcheckers.model.Player;
import com.webcheckers.ui.WebServer;
import com.webcheckers.ui.boardView.Message;
import com.webcheckers.ui.boardView.Move;
import spark.*;

import java.util.logging.Logger;

public class PostValidateMove implements Route{
    private final Gson gson = new Gson();
    private static final Logger LOG = Logger.getLogger(PostValidateMove.class.getName());

    //Key in the session attribute map for the hash of current players in a game
    private static final String CURRENTGAMES_KEY = "currentGames";

    //Key in the session attribute map for the current user Player object
    private static final String CURR_PLAYER = "currentPlayer";

    private static final String MOVE = "move";

    @Override
    public Object handle(Request request, Response response) throws Exception {
        Session session = request.session();

        Player currPlayer = session.attribute(CURR_PLAYER);
        CurrentGames currGame = session.attribute(CURRENTGAMES_KEY);
        Move moveMade = session.attribute(MOVE);


        if(moveMade.getEnd().getRow() >= moveMade.getStart().getRow()) {
            return gson.toJson(new Message("Invalid move", Message.Type.error));
        }

        return gson.toJson(new Message("Player Resigned. Redirecting to home page", Message.Type.info));
    }
}

