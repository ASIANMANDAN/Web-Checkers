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
    //Key in the session attribute map for if a jump has been made
    static final String MOVE_MADE_KEY = "moveMade";

    @Override
    public Object handle(Request request, Response response) throws Exception {
        Session httpSession = request.session();

        CurrentGames currentGames = httpSession.attribute(CURRENTGAMES_KEY);
        Player currentPlayer = httpSession.attribute(CURR_PLAYER);

        Move move = gson.fromJson(request.body(), Move.class);

        //If the player in question has made a move within their current turn
        Boolean moveMade = httpSession.attribute(MOVE_MADE_KEY);

        String message;

        //Determine if a jump has been made already. If it has, no other
        //move can be made unless the same piece that jumped has another
        //jump available.
        if(moveMade) {
            //Use validation for multiple jumps
            message = currentGames.continueJump(currentPlayer, move);
        } else {
            //Default to normal validation
            message = currentGames.validateMove(currentPlayer, move);
        }

        if (message == null) {
            httpSession.attribute(MOVE_MADE_KEY, true);
            currentGames.makeMove(currentPlayer, move);

            //Add move to the Game's list of moves for Replay Mode
            currentGames.addMove(currentPlayer, move);

            //Save the valid move in case player chooses to undo the move
            httpSession.attribute(MOVE_KEY, move);
          
            return gson.toJson(new Message("Move is valid", Message.Type.info));
        } else {
            return gson.toJson(new Message(message, Message.Type.error));
        }
    }
}
