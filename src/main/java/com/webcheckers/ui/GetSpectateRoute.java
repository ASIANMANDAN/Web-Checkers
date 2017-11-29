package com.webcheckers.ui;

import com.webcheckers.appl.CurrentGames;
import com.webcheckers.appl.Game;
import com.webcheckers.model.Player;
import com.webcheckers.model.board.Board;
import com.webcheckers.model.board.Space;
import com.webcheckers.ui.boardView.BoardView;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

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
    private static final Logger LOG = Logger.getLogger(GetSpectateRoute.class.getName());

    //FTL file which is responsible for rendering the page
    static final String VIEW_NAME = "spectate.ftl";
    static final String MODE_ATTR = "viewMode";
    static final String RED_PLAYER_ATTR = "redPlayer";
    static final String WHITE_PLAYER_ATTR = "whitePlayer";
    static final String ACTIVE_ATTR = "activeColor";
    static final String BOARD_ATTR = "board";
    static final String GAME_PARAM = "spectate";

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
        Player currentPlayer = httpSession.attribute(CURR_PLAYER);
        CurrentGames currentGames = httpSession.attribute(CURRENTGAMES_KEY);

        //Case for when no game was selected to spectate
        //Return to home with error message
        if(request.queryParams(GAME_PARAM) == null){
            String msg = "Please select an ongoing game before hitting spectate.";
            httpSession.attribute(MESSAGE_KEY, msg);
            response.redirect(WebServer.HOME_URL);
            halt();
            return null;
        }

        Player red = currentGames.getRedPlayer(new Player(request.queryParams(GAME_PARAM)));
        Player white = currentGames.getOpponent(red);
        Board.ActiveColor turn = currentGames.getTurn(red);

        //Start building the view-model
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Game");

        vm.put(MODE_ATTR, Board.ViewMode.SPECTATOR);
        vm.put(CURR_PLAYER, currentPlayer);
        vm.put(RED_PLAYER_ATTR, red);
        vm.put(WHITE_PLAYER_ATTR, white);
        vm.put(ACTIVE_ATTR, turn);
        vm.put(BOARD_ATTR, new BoardView(red, currentGames));

        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
}
