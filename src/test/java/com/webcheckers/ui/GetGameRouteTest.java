package com.webcheckers.ui;

import com.webcheckers.appl.CurrentGames;
import com.webcheckers.model.Player;
import com.webcheckers.model.board.Board;
import org.junit.Before;
import org.junit.Test;
import spark.*;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The unit test suite for the {@link GetGameRoute} component.
 *
 * @author Dan Wang
 */
public class GetGameRouteTest {

    private GetGameRoute CuT;

    //Mock objects
    private Request request;
    private Session session;
    private TemplateEngine engine;
    private Player player;
    private Player player2;
    private CurrentGames currentGames;

    @Before
    public void setup() throws Exception {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);

        engine = mock(TemplateEngine.class);

        player = new Player("player");
        player2 = new Player("player2");

        currentGames = mock(CurrentGames.class);
        when(session.attribute(GetGameRoute.CURRENTGAMES_KEY)).thenReturn(currentGames);

        CuT = new GetGameRoute(engine);
    }

    /**
     * Test GetGameRoute will redirect to the home page when no opponent is selected
     */
    @Test(expected = HaltException.class)
    public void test_no_opponent() throws Exception {
        Response response = mock(Response.class);
        final MyModelAndView myModelView = new MyModelAndView();
        when(engine.render(any(ModelAndView.class))).thenAnswer(MyModelAndView.makeAnswer(myModelView));
        when(request.attribute(GetGameRoute.CURR_PLAYER)).thenReturn(player);

        CuT.handle(request, response);

        final Object model = myModelView.model;
        assertNotNull(model);
        assertTrue(model instanceof Map);

        final Map<String, Object> vm = (Map<String, Object>) model;
        assertEquals("Welcome!", vm.get("title"));
        assertEquals(player, vm.get(GetHomeRoute.CURR_PLAYER));
        assertEquals("Please select an opponent before hitting play.", vm.get(GetHomeRoute.MESSAGE_ATTR));
    }

    /**
     * Test GetGameRoute will redirect to the home page when selected opponent is in game already.
     */
    @Test(expected = HaltException.class)
    public void test_opponent_unavailable() throws Exception {
        Response response = mock(Response.class);
        Player player3 = new Player("p3");
        final MyModelAndView myModelView = new MyModelAndView();
        when(engine.render(any(ModelAndView.class))).thenAnswer(MyModelAndView.makeAnswer(myModelView));
        when(request.attribute(GetGameRoute.CURR_PLAYER)).thenReturn(player);
        currentGames.addGame(player2, player3);

        when(request.queryParams(GetGameRoute.OPPONENT_PARAM)).thenReturn("p3");
        when(currentGames.playerInGame(player3)).thenReturn(Boolean.TRUE);

        CuT.handle(request, response);

        final Object model = myModelView.model;
        assertNotNull(model);
        assertTrue(model instanceof Map);

        final Map<String, Object> vm = (Map<String, Object>) model;
        assertEquals("Welcome!", vm.get("title"));
        assertEquals(player, vm.get(GetHomeRoute.CURR_PLAYER));
        assertEquals("opponent is already in a game. Please select another player.",
                vm.get(GetHomeRoute.MESSAGE_ATTR));
    }

    /**
     * Test game route generates correctly for the player who starts the game.
     */
    @Test(expected = NullPointerException.class)
    public void test_game_starter() throws Exception {
        Response response = mock(Response.class);
        final MyModelAndView myModelView = new MyModelAndView();
        when(engine.render(any(ModelAndView.class))).thenAnswer(MyModelAndView.makeAnswer(myModelView));
        when(session.attribute(GetGameRoute.CURR_PLAYER)).thenReturn(player);
        when(request.queryParams(GetGameRoute.OPPONENT_PARAM)).thenReturn("player2");
        when(currentGames.getRedPlayer(player)).thenReturn(player);

        CuT.handle(request, response);

        final Object model = myModelView.model;
        assertNotNull(model);
        assertTrue(model instanceof Map);

        final Map<String, Object> vm = (Map<String, Object>) model;
        assertEquals("Game", vm.get("title"));
        assertEquals(Board.ViewMode.PLAY, vm.get(GetGameRoute.MODE_ATTR));
        assertEquals(player, vm.get(GetGameRoute.CURR_PLAYER));
        assertEquals(player, vm.get(GetGameRoute.RED_PLAYER_ATTR));
        assertEquals(player2, vm.get(GetGameRoute.WHITE_PLAYER_ATTR));
        assertEquals(player, vm.get(GetGameRoute.ACTIVE_ATTR));
        assertNotNull(vm.get(GetGameRoute.BOARD_ATTR));
    }

    /**
     * Test game route generates correctly when an opponent is redirected to the game
     */
    @Test(expected = NullPointerException.class)
    public void test_opponent_redirect() throws Exception {
        Response response = mock(Response.class);
        currentGames.addGame(player, player2);
        final MyModelAndView myModelView = new MyModelAndView();
        when(engine.render(any(ModelAndView.class))).thenAnswer(MyModelAndView.makeAnswer(myModelView));
        when(session.attribute(GetGameRoute.CURR_PLAYER)).thenReturn(player2);
        when(currentGames.playerInGame(player2)).thenReturn(Boolean.TRUE);
        when(currentGames.getOpponent(player2)).thenReturn(player);

        CuT.handle(request, response);

        final Object model = myModelView.model;
        assertNotNull(model);
        assertTrue(model instanceof Map);

        final Map<String, Object> vm = (Map<String, Object>) model;
        assertEquals("Game", vm.get("title"));
        assertEquals(Board.ViewMode.PLAY, vm.get(GetGameRoute.MODE_ATTR));
        assertEquals(player2, vm.get(GetGameRoute.CURR_PLAYER));
        assertEquals(player, vm.get(GetGameRoute.RED_PLAYER_ATTR));
        assertEquals(player2, vm.get(GetGameRoute.WHITE_PLAYER_ATTR));
        assertEquals(player, vm.get(GetGameRoute.ACTIVE_ATTR));
        assertNotNull(vm.get(GetGameRoute.BOARD_ATTR));
    }

    /**
     * Test game route redirects to home when opponent resigns.
     */
    @Test(expected = HaltException.class)
    public void test_resign() throws Exception {
        Response response = mock(Response.class);
        currentGames.addGame(player, player2);
        final MyModelAndView myModelView = new MyModelAndView();
        when(engine.render(any(ModelAndView.class))).thenAnswer(MyModelAndView.makeAnswer(myModelView));
        when(session.attribute(GetGameRoute.CURR_PLAYER)).thenReturn(player);
        when(currentGames.playerInGame(player)).thenReturn(Boolean.TRUE);
        when(session.attribute(GetGameRoute.OPPONENT_KEY)).thenReturn(player2);

        CuT.handle(request, response);

        final Object model = myModelView.model;
        assertNotNull(model);
        assertTrue(model instanceof Map);

        final Map<String, Object> vm = (Map<String, Object>) model;
        assertEquals("Welcome!", vm.get("title"));
        assertEquals(player, vm.get(GetHomeRoute.CURR_PLAYER));
        assertEquals("player2 has resigned from the game.", vm.get(GetHomeRoute.MESSAGE_ATTR));
    }
}
