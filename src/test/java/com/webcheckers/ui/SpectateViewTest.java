package com.webcheckers.ui;

import com.webcheckers.appl.CurrentGames;
import com.webcheckers.model.Player;
import com.webcheckers.model.board.Board;
import com.webcheckers.ui.boardView.BoardView;
import org.junit.Test;
import spark.ModelAndView;
import spark.TemplateEngine;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * The unit test suite for the {@code spectate.ftl} component.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class SpectateViewTest {

    private static final String HOME_LINK_TAG = "<a href=\"/\">My Home</a>";

    private final TemplateEngine engine = new FreeMarkerEngine();

    /**
     * Test the game view renders correctly for spectators
     */
    @Test
    public void test_spectate() throws Exception {
        //Create all the objects needed for the view model.
        final String SIGNOUT_LINK_TAG = "<a href=\"/signout\">Sign out [spectator]</a>";
        final Map<String, Object> vm = new HashMap<>();
        final ModelAndView modelAndView = new ModelAndView(vm, GetSpectateRoute.VIEW_NAME);
        Player player1 = new Player("player1");
        Player player2 = new Player("player2");
        Player spectator = new Player("spectator");
        CurrentGames currentGames = new CurrentGames();
        currentGames.addGame(player1, player2);
        BoardView board = new BoardView(player1, currentGames);

        //Insert the objects into the view model
        vm.put("title", "Game");
        vm.put(GetSpectateRoute.MODE_ATTR, Board.ViewMode.SPECTATOR);
        vm.put(GetSpectateRoute.CURR_PLAYER, spectator);
        vm.put(GetSpectateRoute.RED_PLAYER_ATTR, player1);
        vm.put(GetSpectateRoute.WHITE_PLAYER_ATTR, player2);
        vm.put(GetSpectateRoute.ACTIVE_ATTR, Board.ActiveColor.RED);
        vm.put(GetSpectateRoute.BOARD_ATTR, board);

        //Render the view model
        final String viewHtml = engine.render(modelAndView);

        //Check to see if the home and sign out links exist in the view model.
        assertTrue("Sign out link exists", viewHtml.contains(SIGNOUT_LINK_TAG));
        assertTrue("My home link exists", viewHtml.contains(HOME_LINK_TAG));
    }
}
