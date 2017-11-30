package com.webcheckers.ui;

import com.webcheckers.appl.CurrentGames;
import com.webcheckers.model.Player;
import com.webcheckers.model.board.Board;
import com.webcheckers.ui.boardView.BoardView;
import org.junit.Before;
import org.junit.Test;
import spark.*;

import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The unit test suite for the {@Link GetSpectateRoute} component.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class GetSpectateRouteTest {

    private GetSpectateRoute CuT;

    //Mock objects
    private Request request;
    private Session session;
    private TemplateEngine engine;
    private CurrentGames currentGames;
    private Player red;
    private Player white;
    private Player spectator;

    @Before
    public void setup(){
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);

        engine = mock(TemplateEngine.class);
        currentGames = mock(CurrentGames.class);
        red = mock(Player.class);
        white = mock(Player.class);
        spectator = mock(Player.class);

        CuT = new GetSpectateRoute(engine);
    }

    /**
     * Test GetSpectateRoute will redirect to the home page when no game is selected
     */
    @Test
    public void test_no_game() throws Exception{
        Response response = mock(Response.class);
        final MyModelAndView myModelView = new MyModelAndView();
        when(engine.render(any(ModelAndView.class))).thenAnswer(MyModelAndView.makeAnswer(myModelView));

        when(session.attribute(GetSpectateRoute.CURR_PLAYER)).thenReturn(red);
        when(session.attribute(GetSpectateRoute.CURRENTGAMES_KEY)).thenReturn(currentGames);
        when(request.queryParams(GetSpectateRoute.GAME_PARAM)).thenReturn(null);

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
     * Test GetSpectateRoute will redirect to the home page when the game has ended.
     */
    @Test
    public void test_game_end() throws Exception{
        Response response = mock(Response.class);
        final MyModelAndView myModelView = new MyModelAndView();
        when(engine.render(any(ModelAndView.class))).thenAnswer(MyModelAndView.makeAnswer(myModelView));

        when(session.attribute(GetSpectateRoute.CURR_PLAYER)).thenReturn(null);

        try{
            CuT.handle(request, response);
        }catch (HaltException e){
            assertTrue(e instanceof HaltException);
        }

        //Because the game has ended, the page redirects and doesn't fill a model.
        final Object model = myModelView.model;
        assertNull(model);
    }

    /**
     * Test spectate route generates correctly
     */
    @Test
    public void test_spectate() throws Exception {
        Response response = mock(Response.class);
        final MyModelAndView myModelView = new MyModelAndView();
        when(engine.render(any(ModelAndView.class))).thenAnswer(MyModelAndView.makeAnswer(myModelView));

        when(session.attribute(GetSpectateRoute.CURR_PLAYER)).thenReturn(spectator);
        when(session.attribute(GetSpectateRoute.CURRENTGAMES_KEY)).thenReturn(currentGames);

        when(request.queryParams(GetSpectateRoute.GAME_PARAM)).thenReturn("red vs white");

        when(currentGames.getRedPlayer(new Player(request.queryParams(GetSpectateRoute.GAME_PARAM)))).thenReturn(red);
        when(currentGames.getOpponent(red)).thenReturn(white);
        when(currentGames.getTurn(red)).thenReturn(Board.ActiveColor.RED);
        CuT.handle(request, response);

        final Object model = myModelView.model;
        assertNotNull(model);
        assertTrue(model instanceof Map);

        final Map<String, Object> vm = (Map<String, Object>) model;
        assertEquals("Game", vm.get("title"));
        assertEquals(Board.ViewMode.SPECTATOR, vm.get(GetSpectateRoute.MODE_ATTR));
        assertEquals(spectator, vm.get(GetSpectateRoute.CURR_PLAYER));
        assertEquals(red, vm.get(GetSpectateRoute.RED_PLAYER_ATTR));
        assertEquals(white, vm.get(GetSpectateRoute.WHITE_PLAYER_ATTR));
        assertEquals(Board.ActiveColor.RED, vm.get(GetSpectateRoute.ACTIVE_ATTR));
        assertNotNull(vm.get(GetSpectateRoute.BOARD_ATTR));
    }
}
