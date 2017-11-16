package com.webcheckers.ui;

import com.webcheckers.appl.CurrentGames;
import com.webcheckers.appl.Game;
import com.webcheckers.model.Player;
import com.webcheckers.model.board.Board;
import org.junit.Before;
import org.junit.Test;
import spark.*;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The unit test suite for the {@link GetGameRoute} component.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
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

        Game mockGame = new Game(player, player2);
        ArrayList<Game> gameList = new ArrayList<>();
        gameList.add(mockGame);

        currentGames = new CurrentGames(gameList);

        when(session.attribute(GetGameRoute.CURRENTGAMES_KEY)).thenReturn(currentGames);

        CuT = new GetGameRoute(engine);
    }

    /**
     * Test GetGameRoute will redirect to the home page when no opponent is selected
     */
    @Test
    public void test_no_opponent() throws Exception {
        Player player3 = new Player("player3");

        Response response = mock(Response.class);
        final MyModelAndView myModelView = new MyModelAndView();
        when(engine.render(any(ModelAndView.class))).thenAnswer(MyModelAndView.makeAnswer(myModelView));
        when(session.attribute(GetGameRoute.CURR_PLAYER)).thenReturn(player3);

        try{
            CuT.handle(request, response);
        }catch(HaltException e){
            assertTrue(e instanceof HaltException);
        }

        //Because of there being no opponent, the page redirects and doesn't fill a model
        final Object model = myModelView.model;
        assertNull(model);
    }

    /**
     * Test GetGameRoute will redirect to the home page when selected opponent is in game already.
     */
    @Test
    public void test_opponent_unavailable() throws Exception {
        Response response = mock(Response.class);
        Player player3 = new Player("p3");
        final MyModelAndView myModelView = new MyModelAndView();
        when(engine.render(any(ModelAndView.class))).thenAnswer(MyModelAndView.makeAnswer(myModelView));
        when(session.attribute(GetGameRoute.CURR_PLAYER)).thenReturn(player3);

        when(request.queryParams(GetGameRoute.OPPONENT_PARAM)).thenReturn("player2");

        try{
            CuT.handle(request, response);
        }catch(HaltException e){
            assertTrue(e instanceof HaltException);
        }

        //Because of there being no opponent, the page redirects and doesn't fill a model
        final Object model = myModelView.model;
        assertNull(model);
    }

    /**
     * Test game route generates correctly for the player who starts the game.
     */
    @Test
    public void test_game_starter() throws Exception {
        Response response = mock(Response.class);

        Player player3 = new Player("player3");
        Player player4 = new Player("player4");


        final MyModelAndView myModelView = new MyModelAndView();
        when(engine.render(any(ModelAndView.class))).thenAnswer(MyModelAndView.makeAnswer(myModelView));
        when(session.attribute(GetGameRoute.CURR_PLAYER)).thenReturn(player3);
        when(request.queryParams(GetGameRoute.OPPONENT_PARAM)).thenReturn("player4");
        //when(currentGames.getRedPlayer(player)).thenReturn(player);

        CuT.handle(request, response);

        final Object model = myModelView.model;
        assertNotNull(model);
        assertTrue(model instanceof Map);

        @SuppressWarnings("unchecked")
        final Map<String, Object> vm = (Map<String, Object>) model;
        assertEquals("Game", vm.get("title"));
        assertEquals(Board.ViewMode.PLAY, vm.get(GetGameRoute.MODE_ATTR));
        assertEquals(player3, vm.get(GetGameRoute.CURR_PLAYER));
        assertEquals(player3, vm.get(GetGameRoute.RED_PLAYER_ATTR));
        assertEquals(player4, vm.get(GetGameRoute.WHITE_PLAYER_ATTR));
        assertEquals(Board.ActiveColor.RED, vm.get(GetGameRoute.ACTIVE_ATTR));
        assertNotNull(vm.get(GetGameRoute.BOARD_ATTR));
    }

    /**
     * Test game route generates correctly when an opponent is redirected to the game
     */
    @Test
    public void test_opponent_redirect() throws Exception {
        Player player3 = new Player("player3");
        Player player4 = new Player("player4");
        currentGames.addGame(player3, player4);

        Response response = mock(Response.class);
        final MyModelAndView myModelView = new MyModelAndView();
        when(engine.render(any(ModelAndView.class))).thenAnswer(MyModelAndView.makeAnswer(myModelView));
        when(session.attribute(GetGameRoute.CURR_PLAYER)).thenReturn(player4);

        CuT.handle(request, response);

        final Object model = myModelView.model;
        assertNotNull(model);
        assertTrue(model instanceof Map);

        @SuppressWarnings("unchecked")
        final Map<String, Object> vm = (Map<String, Object>) model;
        assertEquals("Game", vm.get("title"));
        assertEquals(Board.ViewMode.PLAY, vm.get(GetGameRoute.MODE_ATTR));
        assertEquals(player4, vm.get(GetGameRoute.CURR_PLAYER));
        assertEquals(player3, vm.get(GetGameRoute.RED_PLAYER_ATTR));
        assertEquals(player4, vm.get(GetGameRoute.WHITE_PLAYER_ATTR));
        assertEquals(Board.ActiveColor.RED, vm.get(GetGameRoute.ACTIVE_ATTR));
        assertNotNull(vm.get(GetGameRoute.BOARD_ATTR));
    }

    /**
     * Test game route redirects to home when opponent resigns.
     */
    @Test
    public void test_resign() throws Exception {
        Response response = mock(Response.class);
        currentGames.removePlayer(player2);
        final MyModelAndView myModelView = new MyModelAndView();
        when(engine.render(any(ModelAndView.class))).thenAnswer(MyModelAndView.makeAnswer(myModelView));
        when(session.attribute(GetGameRoute.CURR_PLAYER)).thenReturn(player);
        when(session.attribute(GetGameRoute.MESSAGE_KEY)).thenReturn("player2 has resigned from the game.");

        try{
            CuT.handle(request, response);
        }catch(HaltException e){
            assertTrue(e instanceof HaltException);
        }

        //Because of there being no opponent, the page redirects and doesn't fill a model
        final Object model = myModelView.model;

        String message = "player2 has resigned from the game.";
        assertEquals(message, session.attribute(GetGameRoute.MESSAGE_KEY));
        assertNull(model);
    }
}
