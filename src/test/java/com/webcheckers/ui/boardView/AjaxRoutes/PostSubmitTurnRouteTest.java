package com.webcheckers.ui.boardView.AjaxRoutes;

import com.webcheckers.appl.CurrentGames;
import com.webcheckers.model.Player;
import com.webcheckers.ui.boardView.Move;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import spark.Request;
import spark.Response;
import spark.Session;

/**
 * The unit test suite for the {@link PostSubmitTurnRoute} component.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class PostSubmitTurnRouteTest {

    private PostSubmitTurnRoute CuT;

    //Mocked objects
    private Move move;
    private Session session;
    private Request request;
    private CurrentGames currentGames;
    private Player player;

    @Before
    public void test_setUp() {
        session = mock(Session.class);
        request = mock(Request.class);
        currentGames = mock(CurrentGames.class);
        move = mock(Move.class);
        player = mock(Player.class);

        when(request.session()).thenReturn(session);
        when(session.attribute(PostSubmitTurnRoute.CURRENTGAMES_KEY)).thenReturn(currentGames);
        when(session.attribute(PostSubmitTurnRoute.CURR_PLAYER)).thenReturn(player);
        when(session.attribute(PostSubmitTurnRoute.MOVE_KEY)).thenReturn(move);

        CuT = new PostSubmitTurnRoute();
    }

    /**
     * Test that the Message returned is correct when a move is made
     * with no errors.
     */
    @Test
    public void test_submitTurn_0() {
        final Response response = mock(Response.class);

        when(currentGames.makeMove(player, move)).thenReturn(true);

        // Invoke the test
        Object message = CuT.handle(request, response);

        assertEquals("{\"text\":\"\",\"type\":\"info\"}", message);
    }

    /**
     * Test that the Message returned is correct when a move cannot be made.
     */
    @Test
    public void test_submitTurn_1() {
        final Response response = mock(Response.class);

        when(currentGames.makeMove(player, move)).thenReturn(false);

        // Invoke the test
        Object message = CuT.handle(request, response);

        assertEquals("{\"text\":\"\",\"type\":\"error\"}", message);
    }
}
