package com.webcheckers.ui;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.webcheckers.appl.CurrentGames;
import com.webcheckers.appl.Game;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.Before;
import org.junit.Test;
import spark.*;

import java.util.Map;

/**
 * The unit test suite for the {@link GetHomeRoute} component.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class GetHomeRouteTest {

    private GetHomeRoute CuT;

    //Mocked objects
    private Request request;
    private Session session;
    private TemplateEngine engine;
    private PlayerLobby playerLobby;

    //Friendly
    private CurrentGames currentGames;

    @Before
    public void test_setUp() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        engine = mock(TemplateEngine.class);
        playerLobby = mock(PlayerLobby.class);
        when(playerLobby.getNumOfUsers()).thenReturn("0");
        when(playerLobby.getUserList()).thenReturn(null);

        currentGames = new CurrentGames();

        when(session.attribute(GetHomeRoute.PLAYERLOBBY_KEY)).thenReturn(playerLobby);


        CuT = new GetHomeRoute(engine, playerLobby, currentGames);
    }

    /**
     * Test the view for when a player has not been signed in.
     */
    @Test
    public void test_notSignedIn() {
        final Response response = mock(Response.class);

        final MyModelAndView myModelView = new MyModelAndView();
        when(engine.render(any(ModelAndView.class))).thenAnswer(MyModelAndView.makeAnswer(myModelView));

        // Invoke the test
        CuT.handle(request, response);

        // Analyze the results:
        //   * model is a non-null Map
        final Object model = myModelView.model;
        assertNotNull(model);
        assertTrue(model instanceof Map);
        //   * model contains all necessary View-Model data
        @SuppressWarnings("unchecked")
        final Map<String, Object> vm = (Map<String, Object>) model;
        assertEquals("Welcome!", vm.get("title"));
        assertEquals("0", vm.get(GetHomeRoute.PLAYERS_ONLINE_ATTR));
        assertEquals(null, vm.get(GetHomeRoute.CURR_PLAYER));
        assertEquals(null, vm.get(GetHomeRoute.PLAYERS_LIST_ATTR));
        assertEquals(null, vm.get(GetHomeRoute.MESSAGE_ATTR));
    }

    /**
     * Test the view for when a player has been signed in.
     */
    @Test
    public void test_signedIn() {
        Player player = mock(Player.class);
        when(session.attribute(GetHomeRoute.CURR_PLAYER)).thenReturn(player);
        final Response response = mock(Response.class);

        final MyModelAndView myModelView = new MyModelAndView();
        when(engine.render(any(ModelAndView.class))).thenAnswer(MyModelAndView.makeAnswer(myModelView));

        // Invoke the test
        CuT.handle(request, response);

        // Analyze the results:
        //   * model is a non-null Map
        final Object model = myModelView.model;
        assertNotNull(model);
        assertTrue(model instanceof Map);
        //   * model contains all necessary View-Model data
        @SuppressWarnings("unchecked")
        final Map<String, Object> vm = (Map<String, Object>) model;
        assertEquals("Welcome!", vm.get("title"));
        assertEquals("0", vm.get(GetHomeRoute.PLAYERS_ONLINE_ATTR));
        assertEquals(player, vm.get(GetHomeRoute.CURR_PLAYER));
        assertEquals(null, vm.get(GetHomeRoute.PLAYERS_LIST_ATTR));
        assertEquals(null, vm.get(GetHomeRoute.MESSAGE_ATTR));
    }

    /**
     * Test the view for when a player has been redirected back to home with an error.
     */
    @Test
    public void test_message() {
        Player player = mock(Player.class);
        when(session.attribute(GetHomeRoute.CURR_PLAYER)).thenReturn(player);
        String message = "Error";
        when(session.attribute(GetHomeRoute.MESSAGE_KEY)).thenReturn(message);
        final Response response = mock(Response.class);

        final MyModelAndView myModelView = new MyModelAndView();
        when(engine.render(any(ModelAndView.class))).thenAnswer(MyModelAndView.makeAnswer(myModelView));

        // Invoke the test
        CuT.handle(request, response);

        // Analyze the results:
        //   * model is a non-null Map
        final Object model = myModelView.model;
        assertNotNull(model);
        assertTrue(model instanceof Map);
        //   * model contains all necessary View-Model data
        @SuppressWarnings("unchecked")
        final Map<String, Object> vm = (Map<String, Object>) model;
        assertEquals("Welcome!", vm.get("title"));
        assertEquals("0", vm.get(GetHomeRoute.PLAYERS_ONLINE_ATTR));
        assertEquals(player, vm.get(GetHomeRoute.CURR_PLAYER));
        assertEquals(null, vm.get(GetHomeRoute.PLAYERS_LIST_ATTR));
        assertEquals(message, vm.get(GetHomeRoute.MESSAGE_ATTR));
    }
}
