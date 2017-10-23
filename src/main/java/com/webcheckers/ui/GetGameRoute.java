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
import static spark.Spark.post;

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
    //Key in session attribute map which stores a selceted opponent
    //todo drop from attribute map after the game - sprint 2
    static final String OPPONENT_KEY = "opponent";

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
     * Recreates a ModelandView for when a player already in a game hits refresh
     *
     * @param currentPlayer the player to build the ModelandView for
     * @param httpSession that players session
     * @return the ModelandView to be displayed
     * @throws Exception can occur when building a BoardView which is
     *                   larger than the board model specifies
     */
    private ModelAndView handleRefresh(String currentPlayer, Session httpSession)
            throws Exception {

        CurrentGames currentGames = httpSession.attribute(CURRENTGAMES_KEY);
        Game game = currentGames.getGame(currentPlayer);

        //Username is not found in currentGames list
        if (game == null) {
            return null;
        }

        //Case for which the player in question is the one who started the game
        if (currentPlayer.equals(game.getPlayer1())) {

            //Start building the view-model
            Map<String, Object> vm = new HashMap<>();
            vm.put("title", "Game");

            //This is hardcoded in for now
            vm.put(MODE_ATTR, Board.ViewMode.PLAY);
            vm.put(CURR_PLAYER, httpSession.attribute(CURR_PLAYER));
            vm.put(RED_PLAYER_ATTR, httpSession.attribute(CURR_PLAYER));
            vm.put(WHITE_PLAYER_ATTR, httpSession.attribute(OPPONENT_KEY));
            vm.put(ACTIVE_ATTR, game.getBoard().currentTurn);
            vm.put(BOARD_ATTR, currentGames.getView(currentPlayer));

            return new ModelAndView(vm, VIEW_NAME);
        }

        //Case for which the player in question is the one who gets redirected
        if (currentPlayer.equals(game.getPlayer2())) {
            Player opponent = new Player(game.getPlayer1());

            //Start building the view-model
            Map<String, Object> vm = new HashMap<>();
            vm.put("title", "Game");

            //This is hardcoded in for now
            vm.put(MODE_ATTR, Board.ViewMode.PLAY);
            vm.put(CURR_PLAYER, httpSession.attribute(CURR_PLAYER));
            vm.put(RED_PLAYER_ATTR, opponent);
            vm.put(WHITE_PLAYER_ATTR, httpSession.attribute(CURR_PLAYER));
            vm.put(ACTIVE_ATTR, game.getBoard().currentTurn);
            vm.put(BOARD_ATTR, currentGames.getView(currentPlayer));

            return new ModelAndView(vm, VIEW_NAME);
        }
        return null;
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

        //Creates a view-model to be used if an ongoing game was refreshed
        ModelAndView mv = handleRefresh(currentPlayer.getUsername(), httpSession);

        if (mv != null) {
            return templateEngine.render(mv);
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
        CurrentGames currentGames = httpSession.attribute(CURRENTGAMES_KEY);

        //Case for when a selected opponent is redirected to a game
        if (currentGames.playingTogether(opponent.getUsername(),
                        currentPlayer.getUsername())) {

            //Add to session map in case user refreshes page
            httpSession.attribute(OPPONENT_KEY, opponent);

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
        //If they are, return the player to home with error message
        if (currentGames.playerInGame(opponent.getUsername())) {

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

            //Initialize board model which keeps track of state of game
            Board board = new Board();
            board.newGame();

            //Add the game to list of ongoing games
            Game game = new Game(currentPlayer.getUsername(),
                    opponent.getUsername(), board);
            currentGames.addGame(game);

            //Add to session map in case user refreshes page
            httpSession.attribute(OPPONENT_KEY, opponent);

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