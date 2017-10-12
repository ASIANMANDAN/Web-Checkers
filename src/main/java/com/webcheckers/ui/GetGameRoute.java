package com.webcheckers.ui;

import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

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

    //Key in the session attribute map for the current user Player object
    static final String CURR_PLAYER = "currentPlayer";

    //key for session attribute map board
    static final String BOARD = "board";
    //
    static final String PIECE = "piece";

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
    public Object handle(Request request, Response response) {
        final Session httpSession = request.session();
        Player currentPlayer = httpSession.attribute(CURR_PLAYER);

        //Query server for selected opponent
        Player opponent = new Player(request.queryParams(OPPONENT_PARAM));

        LOG.finer(currentPlayer.getUsername() + " has selected " +
                opponent.getUsername() + " as an opponent!");

        //Start building the view-model
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Game");

        //This is hardcoded in for now //todo add this enumeration
        vm.put(MODE_ATTR, "PLAY");
        vm.put(CURR_PLAYER, httpSession.attribute(CURR_PLAYER));
        vm.put(RED_PLAYER_ATTR, httpSession.attribute(CURR_PLAYER));
        vm.put(WHITE_PLAYER_ATTR, opponent);
        //This is hardcoded in for now //todo add this enumeration
        vm.put(ACTIVE_ATTR, "RED");

        LOG.finer("The game has started!");
        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
}
