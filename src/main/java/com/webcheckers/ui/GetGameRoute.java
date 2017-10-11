package com.webcheckers.ui;

import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class GetGameRoute implements Route{
    private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

    //FTL file which is responsible for rendering the page
    static final String VIEW_NAME = "game.ftl";
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
        LOG.finer("A game has been started!"); //todo add names of users who started game

        final Session httpSession = request.session();

        //Query server for selected opponent
        Player opponent = new Player(request.queryParams(OPPONENT_PARAM));

        //For test purposes
        LOG.finer(httpSession.attribute(CURR_PLAYER) + " chose " + opponent.getUsername() +
                "as an opponent!");

        //Start building the view-model
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Game");



        //This is hardcoded in for now //todo add this enumeration
        vm.put("viewMode", "PLAY");
        vm.put("board", httpSession.attribute(BOARD));
        vm.put("currentPlayer", httpSession.attribute(CURR_PLAYER));
        vm.put("redPlayer", httpSession.attribute(CURR_PLAYER));
        vm.put("whitePlayer", opponent);
        //This is hardcoded in for now //todo add this enumeration
        vm.put("activeColor", "RED");

        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
}
