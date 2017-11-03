package com.webcheckers.ui.boardView.AjaxRoutes;

import com.webcheckers.appl.CurrentGames;
import com.webcheckers.model.Player;
import org.junit.Before;
import org.junit.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by nate on 11/2/17.
 */
public class PostResignRouteTest {

    private PostResignRoute CuT;

    //Key in the session attribute map for the hash of current players in a game
    static final String CURRENTGAMES_KEY = "currentGames";
    //Key in the session attribute map for the current user Player object
    static final String CURR_PLAYER = "currentPlayer";
    //Key in the session attribute map for the current players opponent
    static final String OPPONENT_KEY = "opponent";

    //Mocked objects
    private Request request;
    private Session session;
    private Player currPlayer;
    private Player opponent;
    private CurrentGames currentGames;

    @Before
    public void test_setUp() throws Exception {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);

        currentGames = new CurrentGames();
        currPlayer = mock(Player.class);
        opponent = mock(Player.class);

        currentGames.addGame(currPlayer , opponent);

        when(session.attribute(CURRENTGAMES_KEY)).thenReturn(currentGames);
        when(session.attribute(CURR_PLAYER)).thenReturn(currPlayer);
        when(session.attribute(OPPONENT_KEY)).thenReturn(opponent);

        CuT = new PostResignRoute();

    }


    /**
     * Tests the {@link PostResignRoute#equals(Object)} handle method.
     */
    @Test
    public void test_resignPlayer(){
        //Mock Response
        Response response = mock(Response.class);

        //Obtain Message
        Object message = CuT.handle(request, response);

        //Test Message
        assertEquals("{\"text\":\"Player Resigned. Redirecting to home page\",\"type\":\"info\"}",message);
    }
}
