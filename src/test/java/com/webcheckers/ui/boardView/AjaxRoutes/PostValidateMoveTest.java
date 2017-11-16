package com.webcheckers.ui.boardView.AjaxRoutes;


import com.webcheckers.appl.CurrentGames;
import com.webcheckers.model.Player;
import com.webcheckers.ui.boardView.Move;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The unit test suite for the {@link PostCheckTurnRoute} component.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class PostValidateMoveTest {

    //Mock Objects
    private Request request;
    private Session session;
    private CurrentGames currentGames;
    private Move move;
    private Player player;

    private static PostValidateMove CuT;

    @Before
    public void test_setup(){
        request = mock(Request.class);
        session = mock(Session.class);
        currentGames = mock(CurrentGames.class);
        move = mock(Move.class);
        player = mock(Player.class);

        when(request.session()).thenReturn(session);
        when(session.attribute(PostValidateMove.CURRENTGAMES_KEY)).thenReturn(currentGames);
        when(session.attribute(PostValidateMove.CURR_PLAYER)).thenReturn(player);

        when(currentGames.validateMove(player, move)).thenReturn(null);

        CuT = new PostValidateMove();
    }

    /**
     * Tests the handle method of the {@link PostValidateMove#equals(Object)}
     * given a valid move.
     */
    @Test
    public void test_handle_0(){
        //Mock Response
        Response response = mock(Response.class);

        //Obtain Message
        Object message = CuT.handle(request, response);

        //Test Message
        assertEquals("{\"text\":\"Move is valid\",\"type\":\"info\"}", message);
    }
}
