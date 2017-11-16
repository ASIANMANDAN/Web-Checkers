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
 * The unit test suite for the {@link PostBackupMoveRoute} component.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class PostBackupMoveRouteTest {

    private PostBackupMoveRoute CuT;

    //Mocked objects
    private Request request;
    private Session session;
    private CurrentGames currentGames;
    private Move move;
    private Player player;

    @Before
    public void test_setUp() {
        request = mock(Request.class);
        session = mock(Session.class);
        currentGames = mock(CurrentGames.class);
        move = mock(Move.class);
        player = mock(Player.class);

        when(request.session()).thenReturn(session);
        when(request.session().attribute(PostValidateMove.CURR_PLAYER)).thenReturn(player);
        when(request.session().attribute(PostValidateMove.CURRENTGAMES_KEY)).thenReturn(currentGames);
        when(request.session().attribute(PostValidateMove.MOVE_KEY)).thenReturn(move);

        CuT = new PostBackupMoveRoute();
    }

    /**
     * Test that the Message returned by the route states that the move
     * has been undone and is of type info.
     */
    @Test
    public void test_message() {
        final Response response = mock(Response.class);

        // Invoke the test
        Object message = CuT.handle(request, response);

        assertEquals("{\"text\":\"Move Undone\",\"type\":\"info\"}", message);
    }
}
