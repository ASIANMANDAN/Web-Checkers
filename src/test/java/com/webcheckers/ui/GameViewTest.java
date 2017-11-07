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
import static org.mockito.Mockito.mock;

/**
 * The unit test suite for the {@code game.ftl} component.
 *
 * @author Dan Wang
 */
public class GameViewTest {

    private static final String HOME_LINK_TAG = "<a href=\"/\">My Home</a>";

    private final TemplateEngine engine = new FreeMarkerEngine();

    /**
     * Test the game view renders correctly for the red player.
     */
    @Test
    public void test_red() throws Exception {
        final String SIGNOUT_LINK_TAG = "<a href=\"/signout\">Sign out [player1]</a>";
        final Map<String, Object> vm = new HashMap<>();
        final ModelAndView modelAndView = new ModelAndView(vm, GetGameRoute.VIEW_NAME);
        Player player1 = new Player("player1");
        Player player2 = new Player("player2");
        CurrentGames currentGames = new CurrentGames();
        currentGames.addGame(player1, player2);
        BoardView board = new BoardView(player1, currentGames);

        vm.put("title", "Game");

        vm.put(GetGameRoute.MODE_ATTR, Board.ViewMode.PLAY);
        vm.put(GetGameRoute.CURR_PLAYER, player1);
        vm.put(GetGameRoute.RED_PLAYER_ATTR, player1);
        vm.put(GetGameRoute.WHITE_PLAYER_ATTR, player2);
        vm.put(GetGameRoute.ACTIVE_ATTR, Board.ActiveColor.RED);
        vm.put(GetGameRoute.BOARD_ATTR, board);

        final String viewHtml = engine.render(modelAndView);

        assertTrue("Sign out link exists", viewHtml.contains(SIGNOUT_LINK_TAG));
        assertTrue("My home link exists", viewHtml.contains(HOME_LINK_TAG));
    }

    /**
     * Test the game view renders correctly for the white player.
     */
    @Test
    public void test_white() throws Exception {
        final String SIGNOUT_LINK_TAG = "<a href=\"/signout\">Sign out [player2]</a>";
        final Map<String, Object> vm = new HashMap<>();
        final ModelAndView modelAndView = new ModelAndView(vm, GetGameRoute.VIEW_NAME);
        Player player1 = new Player("player1");
        Player player2 = new Player("player2");
        CurrentGames currentGames = new CurrentGames();
        currentGames.addGame(player1, player2);
        BoardView board = new BoardView(player2, currentGames);

        vm.put("title", "Game");

        vm.put(GetGameRoute.MODE_ATTR, Board.ViewMode.PLAY);
        vm.put(GetGameRoute.CURR_PLAYER, player2);
        vm.put(GetGameRoute.RED_PLAYER_ATTR, player1);
        vm.put(GetGameRoute.WHITE_PLAYER_ATTR, player2);
        vm.put(GetGameRoute.ACTIVE_ATTR, Board.ActiveColor.RED);
        vm.put(GetGameRoute.BOARD_ATTR, board);

        final String viewHtml = engine.render(modelAndView);

        assertTrue("Sign out link exists", viewHtml.contains(SIGNOUT_LINK_TAG));
        assertTrue("My home link exists", viewHtml.contains(HOME_LINK_TAG));
    }
}
