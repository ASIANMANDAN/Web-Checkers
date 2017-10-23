package com.webcheckers.ui;

import com.webcheckers.appl.CurrentGames;
import com.webcheckers.model.Player;
import com.webcheckers.model.board.Board;
import com.webcheckers.ui.boardView.BoardView;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static spark.Spark.halt;

/**
 * The UI controller to get the Game page.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class GetGameRoute implements Route{
    private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

    //FTL file which is responsible for rendering the page
    static final String VIEW_NAME = "game.ftl";
    static final String MODE_ATTR = "viewMode";
    static final String RED_PLAYER_ATTR = "redPlayer";
    static final String WHITE_PLAYER_ATTR = "whitePlayer";
    static final String ACTIVE_ATTR = "activeColor";
    static final String OPPONENT_PARAM = "opponent";
    static final String BOARD_ATTR = "board";

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
    public GetGameRoute(final TemplateEngine templateEngine) {
        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.templateEngine = templateEngine;
    }

    /**
     * Render the WebCheckers Game page after the user has selected an opponent.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     *
     * @return the rendered HTML for the Game page
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Session httpSession = request.session();
        Player currentPlayer = httpSession.attribute(CURR_PLAYER);
        CurrentGames currentGames = httpSession.attribute(CURRENTGAMES_KEY);

        //Case for when a selected opponent is redirected to a game
        //or any player in game hits refresh
        if (currentGames.playerInGame(currentPlayer)) {

            //Start building the view-model
            Map<String, Object> vm = new HashMap<>();
            vm.put("title", "Game");

            //This is hardcoded in for now
            vm.put(MODE_ATTR, Board.ViewMode.PLAY);
            vm.put(CURR_PLAYER, currentPlayer);
            vm.put(RED_PLAYER_ATTR, currentGames.getRedPlayer(currentPlayer));
            vm.put(WHITE_PLAYER_ATTR, currentGames.getWhitePlayer(currentPlayer));
            vm.put(ACTIVE_ATTR, currentGames.getActiveColor(currentPlayer));
            vm.put(BOARD_ATTR, new BoardView(currentPlayer, currentGames));

            return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
        }

        //Query server for selected opponent
        //If the player did not choose an opponent, or they are not in
        //a game with one, return them to home with an error message
        if (request.queryParams(OPPONENT_PARAM) == null) {

            String msg = "Please select an opponent before hitting play.";
            httpSession.attribute(MESSAGE_KEY, msg);
            response.redirect(WebServer.HOME_URL);
            halt();
            return null;
        }

        Player opponent = new Player(request.queryParams(OPPONENT_PARAM));

        //Determine if the selected opponent is already in a game
        //If they are, return the player to home with error message
        if (currentGames.playerInGame(opponent)) {

            String msg = opponent.getUsername() + " is already in a game." +
                    " Please select another player.";
            httpSession.attribute(MESSAGE_KEY, msg);
            response.redirect(WebServer.HOME_URL);
            halt();
            return null;
        }

        //Case for when a player successfully initializes a game
        else {
            LOG.fine(currentPlayer.getUsername() + " has selected " +
                    opponent.getUsername() + " as an opponent!");

            //Construct a new game and add it to the list of ongoing games
            currentGames.addGame(currentPlayer, opponent);

            //Start building the view-model
            Map<String, Object> vm = new HashMap<>();
            vm.put("title", "Game");

            //This is hardcoded in for now
            vm.put(MODE_ATTR, Board.ViewMode.PLAY);
            vm.put(CURR_PLAYER, currentPlayer);
            vm.put(RED_PLAYER_ATTR, currentGames.getRedPlayer(currentPlayer));
            vm.put(WHITE_PLAYER_ATTR, currentGames.getWhitePlayer(currentPlayer));
            vm.put(ACTIVE_ATTR, currentGames.getActiveColor(currentPlayer));
            vm.put(BOARD_ATTR, new BoardView(currentPlayer, currentGames));

            return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
        }
    }
}