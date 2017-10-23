package com.webcheckers.ui.boardView.AjaxRoutes;

import com.google.gson.Gson;
import com.webcheckers.appl.CurrentGames;
import com.webcheckers.model.Player;
import com.webcheckers.model.board.Board;
import com.webcheckers.ui.boardView.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

public class PostCheckTurnRoute implements Route{
    private final Gson gson = new Gson();

    //Key in the session attribute map for the current user Player object
    static final String CURR_PLAYER = "currentPlayer";
    //Key in the session attribute map for the hash of current players in a game
    static final String CURRENTGAMES_KEY = "currentGames";

    @Override
    public Object handle(Request request, Response response) {
        Session httpSession = request.session();
        CurrentGames currentGames = httpSession.attribute(CURRENTGAMES_KEY);
        Player currentPlayer = httpSession.attribute(CURR_PLAYER);

        Message message;

        //Values used for checks are created here to increase readability
        Player red = currentGames.getRedPlayer(currentPlayer);
        Player white = currentGames.getWhitePlayer(currentPlayer);
        Board.ActiveColor activeColor = currentGames.getTurn(currentPlayer);

        if (currentGames.getOpponent(currentPlayer) == null) {
            message = new Message("true", Message.Type.info);
        }

        else if (red.equals(currentPlayer) && activeColor == Board.ActiveColor.RED) {
            message = new Message("true", Message.Type.info);
        }

        else if (white.equals(currentPlayer) && activeColor == Board.ActiveColor.WHITE) {
            message = new Message("true", Message.Type.info);
        }

        else {
            message = new Message("false", Message.Type.info);
        }

        return gson.toJson(message);
    }
}
