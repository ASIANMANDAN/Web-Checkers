package com.webcheckers.ui;

import com.webcheckers.appl.CurrentGames;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.Before;
import org.junit.Test;
import spark.*;

import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * The unit test suite for the {@link GetSignoutRoute} component.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class GetSignoutRouteTest {

    /**
     * The component-under-test (CuT).
     */
    private GetSignoutRoute CuT;

    private Request request;
    private Session session;
    private TemplateEngine engine;
    private PlayerLobby playerLobby;
    private CurrentGames currGames;
    private Player player;

    /**
     * Setup new mock objects for each test.
     */
    @Before
    public void setup() {
        currGames = mock(CurrentGames.class);
        request = mock(Request.class);
        session = mock(Session.class);
        player = mock(Player.class);
        playerLobby = mock(PlayerLobby.class);

        when(request.session()).thenReturn(session);
        when(session.attribute(GetSignoutRoute.CURRENTGAMES_KEY)).thenReturn(currGames);
        when(session.attribute(GetSignoutRoute.CURR_PLAYER)).thenReturn(player);

        when(currGames.playerInGame(player)).thenReturn(true);

        engine = mock(TemplateEngine.class);
        // create a unique CuT for each test
        CuT = new GetSignoutRoute(engine, playerLobby);
    }

    /**
     * Test that the Game view will sign out of a game successfully.
     */
    @Test
    public void signout_Success() throws Exception {
        final Response response = mock(Response.class);

        final MyModelAndView myModelView = new MyModelAndView();
        when(engine.render(any(ModelAndView.class))).thenAnswer(MyModelAndView.makeAnswer(myModelView));

        // Invoke the test
        CuT.handle(request, response);

        final Object model = myModelView.model;
        assertNotNull(model);
        assertTrue(model instanceof Map);

        //check that model data is correct.
        @SuppressWarnings("unchecked")
        final Map<String, Object> vm = (Map<String, Object>) model;
        assertNull(vm.get(GetSignoutRoute.CURR_PLAYER));
    }

}
