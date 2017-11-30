package com.webcheckers.ui;
import com.webcheckers.appl.Game;
import com.webcheckers.model.Player;
import com.webcheckers.model.board.Piece;
import spark.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by nate on 11/29/17.
 */
public class GetReplayRoute implements Route {
    //FTL file which is responsible for rendering the page
    static final String VIEW_NAME = "replay.ftl";

    static final String COMPLETED_GAME_PARAM = "replay";
    static final String CURR_PLAYER = "currentPlayer";
    static final String MOVES_MADE = "movesMade";
    static final String COMPLETED_GAMES = "completedGames";

    private final TemplateEngine templateEngine;


    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /} HTTP request.
     *
     * @param templateEngine the HTML template rendering engine
     */
    public GetReplayRoute (final TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;

        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        //Retrieve the HTTP session
        final Session httpSession = request.session();

        Player currentPlayer = httpSession.attribute(CURR_PLAYER);
        HashMap<String, Game> completedGames = httpSession.attribute(COMPLETED_GAMES);
        String completedGameName = request.queryParams(COMPLETED_GAME_PARAM);

        Game gameToReplay = completedGames.get(completedGameName);
        //Piece.Color = currentPlayer.ge


        Map<String, Object> vm = new HashMap<>();

        vm.put(CURR_PLAYER, currentPlayer);

        //TODO shifting between moves
        //TODO player selected

        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
}
