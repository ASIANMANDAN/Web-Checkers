package com.webcheckers.ui.boardView.AjaxRoutes;

import com.google.gson.Gson;
import com.webcheckers.appl.CurrentGames;
import com.webcheckers.model.Player;
import com.webcheckers.ui.WebServer;
import com.webcheckers.ui.boardView.Message;
import spark.*;

import java.util.logging.Logger;
/**
 * Created by nate on 10/18/17.
 */
public class PostResignRoute implements Route{
    private final Gson gson = new Gson();
    private static final Logger LOG = Logger.getLogger(PostResignRoute.class.getName());

    //Key in the session attribute map for the hash of current players in a game
    static final String CURRENTGAMES_KEY = "currentGames";

    //Key in the session attribute map for the current user Player object
    static final String CURR_PLAYER = "currentPlayer";

    @Override
    public Object handle(Request request, Response response) throws Exception {
        Session session = request.session();

        Player currPlayer = session.attribute(CURR_PLAYER);
        CurrentGames currentGames = session.attribute(CURRENTGAMES_KEY);

        currentGames.endGame(currPlayer);

        LOG.fine("Player Resigned. Redirecting to home page");
        Message message = new Message("Player Resigned. Redirecting to home page", Message.Type.info);


        return gson.toJson(message);
    }
}
