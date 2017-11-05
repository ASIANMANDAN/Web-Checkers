package com.webcheckers.ui.boardView.AjaxRoutes;


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
    private Request request;
    private Session session;
    private Move move;

    private static PostValidateMove CuT;

    @Before
    public void test_setup(){
        request = mock(Request.class);
        session = mock(Session.class);;
        move = mock(Move.class);

        when(request.session()).thenReturn(session);



        CuT = new PostValidateMove();
    }

    /**
     * Tests the handle method of the {@link PostValidateMove#equals(Object)} Route.
     */
    @Test
    public void test_handle(){
        //Mock Response
        Response response = mock(Response.class);

        //Obtain Message
        Object message = CuT.handle(request, response);

        //Test Message
        assertEquals("{\"text\":\"true\",\"type\":\"info\"}", message);
    }


}
