package com.webcheckers.ui;

import com.webcheckers.appl.Game;
import com.webcheckers.appl.PlayerLobby;
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
 * The unit test suite for the {@Link GetReplayRoute} component.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class GetReplayRouteTest {

    private GetReplayRoute CuT;

    //Mock objects
    private Request request;
    private Session session;
    private TemplateEngine engine;
    private Player player;
    private ArrayList completedGames;
    private PlayerLobby lobby;

    @Before
    public void setup() throws Exception {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        player = mock(Player.class);
        completedGames = mock(ArrayList.class);
        lobby = mock(PlayerLobby.class);

        engine = mock(TemplateEngine.class);

        CuT = new GetReplayRoute(engine);
    }

    /**
     * Test you are redirected to homw when no game selected
     */
    @Test
    public void test_no_game() throws Exception {
        Response response = mock(Response.class);
        final MyModelAndView myModelView = new MyModelAndView();
        when(engine.render(any(ModelAndView.class))).thenAnswer(MyModelAndView.makeAnswer(myModelView));

        //Setup test scenario
        when(session.attribute(GetReplayRoute.CURR_PLAYER)).thenReturn(player);
        when(session.attribute(GetReplayRoute.COMPLETED_GAMES)).thenReturn(completedGames);
        when(request.queryParams(GetReplayRoute.COMPLETED_GAME_PARAM)).thenReturn(null);
        when(session.attribute(GetReplayRoute.REPLAYING_KEY)).thenReturn(null);

        //Invoke test
        try{
            CuT.handle(request, response);
        }catch (HaltException e){
            assertTrue(e instanceof HaltException);
        }

        //Because no game was selected, the page redirects and doesn't fill a model.
        final Object model = myModelView.model;
        assertNull(model);
    }

    /**
     * Test successfully replaying a game.
     */
    @Test
    public void test_replay() throws Exception {
        Response response = mock(Response.class);
        final MyModelAndView myModelView = new MyModelAndView();
        when(engine.render(any(ModelAndView.class))).thenAnswer(MyModelAndView.makeAnswer(myModelView));
        when(session.attribute(GetReplayRoute.PLAYERLOBBY_KEY)).thenReturn(lobby);
        Player red = new Player("red");
        Player white = new Player("white");
        Game g = new Game(red, white);
        String selectedMessage = "Another player has challenged you to a game! Return to the Home Page if you wish " +
                "to connect.";

        //Setup test scenario
        when(session.attribute(GetReplayRoute.CURR_PLAYER)).thenReturn(player);
        when(session.attribute(GetReplayRoute.COMPLETED_GAMES)).thenReturn(completedGames);
        when(request.queryParams(GetReplayRoute.COMPLETED_GAME_PARAM)).thenReturn("0");
        when(session.attribute(GetReplayRoute.REPLAYING_KEY)).thenReturn(g);
        when(completedGames.get(0)).thenReturn(g);
        when(request.queryParams(GetReplayRoute.MOVE_INDEX_PARAM)).thenReturn("0");
        when(session.attribute(GetReplayRoute.PREVIOUSINDEX_KEY)).thenReturn(null);
        when(lobby.getWatching(player.getUsername())).thenReturn(Boolean.FALSE);
        when(lobby.getSelected(player.getUsername())).thenReturn(Boolean.TRUE);

        //Invoke the test
        CuT.handle(request, response);

        //Check the model is a non null Map
        final Object model = myModelView.model;
        assertNotNull(model);
        assertTrue(model instanceof Map);

        //Check all needed view model data is present
        final Map<String, Object> vm = (Map<String, Object>) model;
        assertEquals("Replay", vm.get("title"));
        assertEquals(Board.ViewMode.REPLAY, vm.get(GetReplayRoute.MODE_ATTR));
        assertEquals(0, vm.get(GetReplayRoute.MOVELISTSIZE_ATTR));
        assertEquals(0, vm.get(GetReplayRoute.MOVEINDEX_ATTR));
        assertEquals(player, vm.get(GetReplayRoute.CURR_PLAYER));
        assertEquals("red", vm.get(GetReplayRoute.RED_PLAYER_ATTR));
        assertEquals("white", vm.get(GetReplayRoute.WHITE_PLAYER_ATTR));
        assertEquals(Board.ActiveColor.RED, vm.get(GetReplayRoute.ACTIVE_ATTR));
        assertEquals(completedGames, vm.get(GetReplayRoute.COMPLETED_GAMES));
        assertEquals(selectedMessage, vm.get(GetReplayRoute.SELECTED_ATTR));
        assertNotNull(vm.get(GetReplayRoute.BOARD_ATTR));

        //Player boolean flags updated
        assertFalse(player.getWatching());
        assertFalse(lobby.inLobby(player));
    }
}
