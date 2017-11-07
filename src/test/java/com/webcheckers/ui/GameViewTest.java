package com.webcheckers.ui;

import com.webcheckers.model.Player;
import com.webcheckers.model.board.Board;
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

    private static final String SIGNOUT_LINK_TAG = "<a href=\"/signout\">Sign out [player]</a>";
    private static final String HOME_LINK_TAG = "<a href=\"/\">My Home</a>";

    private final TemplateEngine engine = new FreeMarkerEngine();

    /**
     * Test the
     */
    @Test
    public void test_red(){
        final Map<String, Object> vm = new HashMap<>();
        final ModelAndView modelAndView = new ModelAndView(vm, GetGameRoute.VIEW_NAME);
        Player currentPlayer = new Player("player");
        Player opponent = new Player("opponent");
        Board board = mock(Board.class);

        vm.put("title", "Game");

        vm.put(GetGameRoute.MODE_ATTR, Board.ViewMode.PLAY);
        vm.put(GetGameRoute.CURR_PLAYER, currentPlayer);
        vm.put(GetGameRoute.RED_PLAYER_ATTR, currentPlayer);
        vm.put(GetGameRoute.WHITE_PLAYER_ATTR, opponent);
        vm.put(GetGameRoute.ACTIVE_ATTR, Board.ActiveColor.RED);
        vm.put(GetGameRoute.BOARD_ATTR, board);

        final String viewHtml = engine.render(modelAndView);

        assertTrue("Sign out button exists", viewHtml.contains(SIGNOUT_LINK_TAG));
    }
}
