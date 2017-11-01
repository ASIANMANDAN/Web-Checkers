package com.webcheckers.ui.boardView.AjaxRoutes;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import spark.Request;
import spark.Response;

import static org.mockito.Mockito.mock;

public class PostBackupMoveRouteTest {

    private PostBackupMoveRoute CuT;

    //Mocked objects
    private Request request;

    @Before
    public void test_setUp() {
        request = mock(Request.class);

        CuT = new PostBackupMoveRoute();
    }

    @Test
    public void test_message() {
        final Response response = mock(Response.class);

        // Invoke the test
        Object message = CuT.handle(request, response);

        assertEquals("{\"text\":\"Move Undone\",\"type\":\"info\"}", message);
    }
}
