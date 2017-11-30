package com.webcheckers.ui;

import com.webcheckers.appl.CurrentGames;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.model.board.Board;
import com.webcheckers.ui.boardView.BoardView;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static spark.Spark.halt;

/**
 * The UI controller to get the Game page when spectating.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class GetSpectateRoute implements Route{

    //FTL file which is responsible for rendering the page
    static final String VIEW_NAME = "spectate.ftl";
    static final String MODE_ATTR = "viewMode";
    static final String RED_PLAYER_ATTR = "redPlayer";
    static final String WHITE_PLAYER_ATTR = "whitePlayer";
    static final String ACTIVE_ATTR = "activeColor";
    static final String BOARD_ATTR = "board";
    static final String SELECTED_ATTR = "selected";
    static final String GAME_PARAM = "spectate";

    //Key in the session attribute map for the playerLobby object
    static final String PLAYERLOBBY_KEY = "playerLobby";
    //Key in the session attribute map for the current user Player object
    static final String CURR_PLAYER = "currentPlayer";
    //Key in the session attribute map for the hash of current players in a game
    static final String CURRENTGAMES_KEY = "currentGames";
    //Key in the session attribute map for a String to be shown in case of error
    static final String MESSAGE_KEY = "message";

    private final TemplateEngine templateEngine;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /} HTTP request.
     *
     * @param templateEngine the HTML template rendering engine
     */
    public GetSpectateRoute(final TemplateEngine templateEngine) {

        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.templateEngine = templateEngine;
    }

    /**
     * Render the Web checkers page for a spectator
     *
     * @param request the HTTP request
     * @param response the HTTP response
     *
     * @return the rendered HTML for the Game page
     */
    @Override
    public Object handle(Request request, Response response) throws Exception{
        final Session httpSession = request.session();
        CurrentGames currentGames = httpSession.attribute(CURRENTGAMES_KEY);
        PlayerLobby playerLobby = httpSession.attribute(PLAYERLOBBY_KEY);
        Player currentPlayer = httpSession.attribute(CURR_PLAYER);

        //Case for when no game was selected to spectate
        //Return to home with error message
        if(request.queryParams(GAME_PARAM) == null){
            String msg = "Please select an ongoing game before hitting spectate.";
            httpSession.attribute(MESSAGE_KEY, msg);
            response.redirect(WebServer.HOME_URL);
            halt();
            return null;
        }

        //Set the isWatching to true so others know that the
        //current play is spectating
        if (!playerLobby.getWatching(currentPlayer.getUsername())) {
            currentPlayer.toggleWatching();
            playerLobby.updateLobby(currentPlayer);
        }

        Player red = currentGames.getRedPlayer(new Player(request.queryParams(GAME_PARAM)));
        Player white = currentGames.getOpponent(red);
        Board.ActiveColor turn = currentGames.getTurn(red);

        //Case for when a game has ended
        //Spectators should be redirected to the home page and be told the game ended.
        if(red == null || white == null){
            String msg = "The game has ended.";
            httpSession.attribute(MESSAGE_KEY, msg);
            response.redirect(WebServer.HOME_URL);
            halt();
            return null;
        }

        String selectedMessage = null;
        if (playerLobby.getSelected(currentPlayer.getUsername())) {
            selectedMessage = "Another player has challenged you to a game! " +
                    "Return to the Home Page if you wish to connect.";
            //Change selected to false as the current player would have
            //already been given the message
            currentPlayer.toggleSelected();
            playerLobby.updateLobby(currentPlayer);
        }

        //Start building the view-model
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Game");

        vm.put(MODE_ATTR, Board.ViewMode.SPECTATOR);
        vm.put(CURR_PLAYER, currentPlayer);
        vm.put(RED_PLAYER_ATTR, red);
        vm.put(WHITE_PLAYER_ATTR, white);
        vm.put(ACTIVE_ATTR, turn);
        vm.put(BOARD_ATTR, new BoardView(red, currentGames));
        vm.put(SELECTED_ATTR, selectedMessage);

        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
}
