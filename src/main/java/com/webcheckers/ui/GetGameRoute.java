package com.webcheckers.ui;

import com.webcheckers.appl.CurrentGames;
import com.webcheckers.appl.Game;
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

        //Query server for selected opponent
        //If the player did not choose an opponent, return them
        if (request.queryParams(OPPONENT_PARAM) == null) {
            response.redirect(WebServer.HOME_URL);
            halt();
            return null;
        }

        Player opponent = new Player(request.queryParams(OPPONENT_PARAM));
        Player currentPlayer = httpSession.attribute(CURR_PLAYER);

        //Checks that the selected opponent is not in a game already
        CurrentGames currentGames = httpSession.attribute(CURRENTGAMES_KEY);

        //Case for when a selected opponent is redirected to a game
        if (currentGames.playingTogether(opponent.getUsername(),
                        currentPlayer.getUsername())) {

            //Start building the view-model
            Map<String, Object> vm = new HashMap<>();
            vm.put("title", "Game");

            //This is hardcoded in for now
            vm.put(MODE_ATTR, Board.ViewMode.PLAY);
            vm.put(CURR_PLAYER, currentPlayer);
            vm.put(RED_PLAYER_ATTR, opponent);
            vm.put(WHITE_PLAYER_ATTR, currentPlayer);
            vm.put(ACTIVE_ATTR, Board.ActiveColor.RED);
            vm.put(BOARD_ATTR, currentGames.getView(currentPlayer.getUsername()));

            LOG.fine(currentPlayer.getUsername() + " has joined!");
            return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
        }

        //Determine if the selected opponent is already in a game
        if (currentGames.playerInGame(opponent.getUsername())) {

            //todo add message to home about invalid user
            response.redirect(WebServer.HOME_URL);
            halt();
            return null;
        }
        //Case for when a player initializes a game
        else {
            LOG.fine(currentPlayer.getUsername() + " has selected " +
                    opponent.getUsername() + " as an opponent!");

            //Initialize board model which keeps track of state of game
            Board board = new Board();
            board.newGame();

            //Add the game to list of ongoing games
            Game game = new Game(currentPlayer.getUsername(),
                    opponent.getUsername(), board);
            currentGames.addGame(game);

            //A copy of the board model used for displaying the game
            BoardView view = new BoardView(board, false);

            //Start building the view-model
            Map<String, Object> vm = new HashMap<>();
            vm.put("title", "Game");

            //This is hardcoded in for now
            vm.put(MODE_ATTR, Board.ViewMode.PLAY);
            vm.put(CURR_PLAYER, currentPlayer);
            vm.put(RED_PLAYER_ATTR, currentPlayer);
            vm.put(WHITE_PLAYER_ATTR, opponent);
            vm.put(ACTIVE_ATTR, Board.ActiveColor.RED);
            vm.put(BOARD_ATTR, view);

            LOG.fine("Waiting for " + opponent.getUsername() + " to join.");
            return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
        }
    }
}