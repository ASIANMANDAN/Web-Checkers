package com.webcheckers.ui.boardView.AjaxRoutes;

import com.webcheckers.appl.CurrentGames;
import com.webcheckers.model.Player;
import com.webcheckers.model.board.Board;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import spark.Request;
import spark.Response;
import spark.Session;

/**
 * The unit test suite for the {@link PostCheckTurnRoute} component.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class PostCheckTurnRouteTest {

    private PostCheckTurnRoute CuT;

    //Mocked objects
    private Request request;
    private Session session;
    private Player red;
    private Player white;
    private CurrentGames currentGames;

    @Before
    public void test_setUp() {
        request = mock(Request.class);
        session = mock(Session.class);
        red = mock(Player.class);
        white = mock(Player.class);
        currentGames = mock(CurrentGames.class);

        when(request.session()).thenReturn(session);
        when(session.attribute(PostCheckTurnRoute.CURRENTGAMES_KEY)).thenReturn(currentGames);

        //Cover all pairs of method calls given the current player.
        //Otherwise one of the players will return null and the route
        //will return true
        when(currentGames.getOpponent(red)).thenReturn(white);
        when(currentGames.getOpponent(white)).thenReturn(red);
        when(currentGames.getRedPlayer(red)).thenReturn(red);
        when(currentGames.getRedPlayer(white)).thenReturn(red);
        when(currentGames.getWhitePlayer(red)).thenReturn(white);
        when(currentGames.getWhitePlayer(white)).thenReturn(white);
        when(currentGames.getTurn(red)).thenReturn(Board.ActiveColor.RED);
        when(currentGames.getTurn(white)).thenReturn(Board.ActiveColor.RED);

        CuT = new PostCheckTurnRoute();
    }

    /**
     * Test the returned message for current player red and
     * active color red.
     */
    @Test
    public void test_checkTurn_0() {
        final Response response = mock(Response.class);

        when(session.attribute(PostCheckTurnRoute.CURR_PLAYER)).thenReturn(red);
        when(currentGames.getTurn(red)).thenReturn(Board.ActiveColor.RED);

        // Invoke the test
        Object message = CuT.handle(request, response);

        assertEquals("{\"text\":\"true\",\"type\":\"info\"}", message);
    }

    /**
     * Test the returned message for current player white and
     * active color white.
     */
    @Test
    public void test_checkTurn_1() {
        final Response response = mock(Response.class);

        when(session.attribute(PostCheckTurnRoute.CURR_PLAYER)).thenReturn(white);
        when(currentGames.getTurn(white)).thenReturn(Board.ActiveColor.WHITE);

        // Invoke the test
        Object message = CuT.handle(request, response);

        assertEquals("{\"text\":\"true\",\"type\":\"info\"}", message);
    }

    /**
     * Test the returned message for current player red and
     * active color white.
     */
    @Test
    public void test_checkTurn_2() {
        final Response response = mock(Response.class);

        when(session.attribute(PostCheckTurnRoute.CURR_PLAYER)).thenReturn(red);
        when(currentGames.getTurn(red)).thenReturn(Board.ActiveColor.WHITE);

        // Invoke the test
        Object message = CuT.handle(request, response);

        assertEquals("{\"text\":\"false\",\"type\":\"info\"}", message);
    }

    /**
     * Test the returned message for current player white and
     * active color red.
     */
    @Test
    public void test_checkTurn_3() {
        final Response response = mock(Response.class);

        when(session.attribute(PostCheckTurnRoute.CURR_PLAYER)).thenReturn(white);
        when(currentGames.getTurn(white)).thenReturn(Board.ActiveColor.RED);

        // Invoke the test
        Object message = CuT.handle(request, response);

        assertEquals("{\"text\":\"false\",\"type\":\"info\"}", message);
    }

    /**
     * Test the returned message for when an opponent resigns from the game.
     */
    @Test
    public void test_checkTurn_4() {
        final Response response = mock(Response.class);

        when(currentGames.getOpponent(white)).thenReturn(null);

        // Invoke the test
        Object message = CuT.handle(request, response);

        assertEquals("{\"text\":\"true\",\"type\":\"info\"}", message);
    }

}
