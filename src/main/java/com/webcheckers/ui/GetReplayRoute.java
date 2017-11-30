package com.webcheckers.ui;
import com.webcheckers.appl.CurrentGames;
import com.webcheckers.appl.Game;
import com.webcheckers.model.Player;
import com.webcheckers.model.board.Board;
import com.webcheckers.ui.boardView.BoardView;
import spark.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static spark.Spark.halt;

/**
 * The UI controller to get the Game page when replaying a
 * previously played game.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class GetReplayRoute implements Route {
    //FTL file which is responsible for rendering the page
    static final String VIEW_NAME = "replay.ftl";

    static final String COMPLETED_GAME_PARAM = "replay";
    static final String MOVE_INDEX_PARAM = "index";

    static final String CURR_PLAYER = "currentPlayer";
    static final String COMPLETED_GAMES = "completedGames";
    static final String BOARD_ATTR = "board";
    static final String MODE_ATTR = "viewMode";
    static final String RED_PLAYER_ATTR = "redPlayer";
    static final String WHITE_PLAYER_ATTR = "whitePlayer";
    static final String ACTIVE_ATTR = "activeColor";
    static final String MOVEINDEX_ATTR = "moveIndex";
    static final String MOVELISTSIZE_ATTR = "moveListSize";

    //Key in the session attribute map for a String to be shown in case of error
    static final String MESSAGE_KEY = "message";
    //Key in the session attribute map for the current game being replayed
    static final String REPLAYING_KEY = "replaying";
    //Key in the session attribute map for the
    static final String PREVIOUSINDEX_KEY = "previousIndex";

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
        ArrayList<Game> completedGames = httpSession.attribute(COMPLETED_GAMES);
        String gameIndex = request.queryParams(COMPLETED_GAME_PARAM);

        Game gameToReplay = httpSession.attribute(REPLAYING_KEY);

        //Case for if a player doesn't select any game to replay
        if (gameIndex == null && gameToReplay == null) {
            String msg = "Please select a game to replay before hitting Replay.";
            httpSession.attribute(MESSAGE_KEY, msg);
            response.redirect(WebServer.HOME_URL);
            halt();
            return null;
        }

        //Case for when user has chosen a game to replay
        if (gameIndex != null) {
            Game game = completedGames.get(Integer.parseInt(gameIndex));

            //Setup a new board to use for replaying
            Board board = new Board();
            board.newGame();

            //Construct a copy of the selected game
            gameToReplay = new Game(game.getRedPlayer(), game.getWhitePlayer(),
                    board.getBoard(), game.getListOfMoves());

            //Add to attribute map so that player doesn't get redirected to home
            //while replaying the game
            httpSession.attribute(REPLAYING_KEY, gameToReplay);
        }

        String index = request.queryParams(MOVE_INDEX_PARAM);
        int currentIndex = 0;
        if (index != null) {
            currentIndex = Integer.parseInt(index);
        }

        Integer previousIndex = httpSession.attribute(PREVIOUSINDEX_KEY);
        if (previousIndex == null) {
            httpSession.attribute(PREVIOUSINDEX_KEY, currentIndex);
        }
        else if (currentIndex > previousIndex) {
            gameToReplay.makeMoves(currentIndex);
            httpSession.attribute(PREVIOUSINDEX_KEY, currentIndex);
        }

        Map<String, Object> vm = new HashMap<>();

        vm.put("title", "Replay");
        vm.put(MODE_ATTR, Board.ViewMode.REPLAY);
        vm.put(MOVELISTSIZE_ATTR, (gameToReplay.getListOfMoves().size()-1));
        vm.put(MOVEINDEX_ATTR, currentIndex);
        vm.put(CURR_PLAYER, currentPlayer);
        vm.put(RED_PLAYER_ATTR, gameToReplay.getRedPlayer().getUsername());
        vm.put(WHITE_PLAYER_ATTR, gameToReplay.getWhitePlayer().getUsername());
        vm.put(ACTIVE_ATTR, gameToReplay.getTurn());
        vm.put(COMPLETED_GAMES, completedGames);
        vm.put(BOARD_ATTR, new BoardView(currentPlayer, gameToReplay));

        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
}
