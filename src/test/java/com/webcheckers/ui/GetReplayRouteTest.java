package com.webcheckers.ui;

import com.webcheckers.appl.Game;
import com.webcheckers.model.Player;
import com.webcheckers.model.board.Board;
import org.junit.Before;
import org.junit.Test;
import spark.*;

import java.util.ArrayList;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
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
    private Game game;
    private Board board;

    @Before
    public void setup() throws Exception {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        player = mock(Player.class);
        completedGames = mock(ArrayList.class);
        game = mock(Game.class);

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
    public void test_replay(){
        Response response = mock(Response.class);
        final MyModelAndView myModelView = new MyModelAndView();
        when(engine.render(any(ModelAndView.class))).thenAnswer(MyModelAndView.makeAnswer(myModelView));

        //Setup test scenario
        when(session.attribute(GetReplayRoute.CURR_PLAYER)).thenReturn(player);
        when(session.attribute(GetReplayRoute.COMPLETED_GAMES)).thenReturn(completedGames);
        when(request.queryParams(GetReplayRoute.COMPLETED_GAME_PARAM)).thenReturn("0");
        when(session.attribute(GetReplayRoute.REPLAYING_KEY)).thenReturn(game);
        when(completedGames.get(0)).thenReturn(game);
    }
}
