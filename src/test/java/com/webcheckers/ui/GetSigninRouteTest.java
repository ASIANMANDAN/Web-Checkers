package com.webcheckers.ui;

import org.junit.Before;
import org.junit.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;
import spark.ModelAndView;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The unit test suite for the {@link GetSigninRoute} component.
 *
 * @author Dan Wang
 */
public class GetSigninRouteTest {

    private GetSigninRoute CuT;

    //mock objects
    private Request request;
    private Session session;
    private TemplateEngine engine;

    /**
     * Setup mock objects for testing.
     */
    @Before
    public void setup(){
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        engine = mock(TemplateEngine.class);

        CuT = new GetSigninRoute(engine);
    }

    /**
     * Test that the normal Sign in page will display when the sign in button is clicked.
     */
    @Test
    public void normal_sign_in(){
        //Setup normal sign in scenario
        final Response response = mock(Response.class);
        final MyModelAndView myModelView = new MyModelAndView();
        when(engine.render(any(ModelAndView.class))).thenAnswer(MyModelAndView.makeAnswer(myModelView));

        //Invoke test
        CuT.handle(request, response);

        //Check that the model is a non-null Map.
        final Object model = myModelView.model;
        assertNotNull(model);
        assertTrue(model instanceof Map);

        //Make sure correct information is displayed.
        final Map<String, Object> vm = (Map<String, Object>) model;
        assertEquals("Player Sign-in", vm.get("title"));
        assertEquals(GetSigninRoute.VIEW_NAME, myModelView.viewName);
    }

}
