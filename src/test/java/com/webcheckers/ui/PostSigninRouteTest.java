package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.Before;
import org.junit.Test;
import spark.*;

import java.util.Map;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The unit test suite for the {@link PostSigninRoute} component.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class PostSigninRouteTest {

    private PostSigninRoute CuT;

    //Objects to mock
    private Request request;
    private Session session;
    private TemplateEngine engine;
    private PlayerLobby playerLobby;

    @Before
    public void test_setUp(){
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        engine = mock(TemplateEngine.class);
        playerLobby = mock(PlayerLobby.class);
        CuT = new PostSigninRoute(engine);
    }

    /**
     * Test the sign in page will display the correct error message will display when the submitted username has ".
     */
    @Test
    public void test_error_sign_in(){
        //Create scenario for having " in username
        when(request.queryParams(PostSigninRoute.USERNAME_PARAM)).thenReturn("\"");
        final Response response = mock(Response.class);
        when(playerLobby.signIn("\"")).thenReturn(PlayerLobby.InputResult.INVALID);
        when(session.attribute(GetHomeRoute.PLAYERLOBBY_KEY)).thenReturn(playerLobby);

        final MyModelAndView myModelAndView = new MyModelAndView();
        when(engine.render(any(ModelAndView.class))).thenAnswer(MyModelAndView.makeAnswer(myModelAndView));

        //Invoke the test
        CuT.handle(request, response);

        //Check the model is a non-null Map.
        final Object model = myModelAndView.model;
        assertNotNull(model);
        assertTrue(model instanceof Map);

        //Check that the correct title and message displays
        final Map<String, Object> vm = (Map<String, Object>) model;
        assertEquals("Sign-in", vm.get("title"));
        assertEquals("The username you selected was invalid as it contains double quotation marks.",
                vm.get(PostSigninRoute.MESSAGE_ATTR));
    }

    /**
     * Test the sign in page will display the correct error message when the username is blank.
     */
    @Test
    public void test_empty_sign_in(){
        when(request.queryParams(PostSigninRoute.USERNAME_PARAM)).thenReturn("");
        final Response response = mock(Response.class);
        when(playerLobby.signIn("")).thenReturn(PlayerLobby.InputResult.EMPTY);
        when(session.attribute(GetHomeRoute.PLAYERLOBBY_KEY)).thenReturn(playerLobby);

        final MyModelAndView myModelAndView = new MyModelAndView();
        when(engine.render(any(ModelAndView.class))).thenAnswer(MyModelAndView.makeAnswer(myModelAndView));

        CuT.handle(request, response);

        final Object model = myModelAndView.model;
        assertNotNull(model);
        assertTrue(model instanceof Map);

        final Map<String, Object> vm = (Map<String, Object>) model;
        assertEquals("Sign-in", vm.get("title"));
        assertEquals("Your username cannot be blank.", vm.get(PostSigninRoute.MESSAGE_ATTR));
    }

    /**
     * Test the sign in page will display the correct error message when the username is already in use.
     */
    @Test
    public void test_taken_sign_in(){
        when(request.queryParams(PostSigninRoute.USERNAME_PARAM)).thenReturn("Dan");
        final Response response = mock(Response.class);
        when(playerLobby.signIn("Dan")).thenReturn(PlayerLobby.InputResult.TAKEN);
        when(session.attribute(GetHomeRoute.PLAYERLOBBY_KEY)).thenReturn(playerLobby);

        final MyModelAndView myModelAndView = new MyModelAndView();
        when(engine.render(any(ModelAndView.class))).thenAnswer(MyModelAndView.makeAnswer(myModelAndView));

        CuT.handle(request, response);

        final Object model = myModelAndView.model;
        assertNotNull(model);
        assertTrue(model instanceof Map);

        final Map<String, Object> vm = (Map<String, Object>) model;
        assertEquals("Sign-in", vm.get("title"));
        assertEquals("The username you selected is already in use.", vm.get(PostSigninRoute.MESSAGE_ATTR));
    }

    /**
     * Test the sign in page will redirect you to the home page upon a valid username.
     */
    @Test(expected = HaltException.class)
    public void test_valid_sign_in(){
        final Player player = mock(Player.class);
        when(request.queryParams(PostSigninRoute.USERNAME_PARAM)).thenReturn("Dan");
        final Response response = mock(Response.class);
        when(playerLobby.signIn("Dan")).thenReturn(PlayerLobby.InputResult.ACCEPTED);
        when(session.attribute(GetHomeRoute.PLAYERLOBBY_KEY)).thenReturn(playerLobby);

        final MyModelAndView myModelAndView = new MyModelAndView();
        when(engine.render(any(ModelAndView.class))).thenAnswer(MyModelAndView.makeAnswer(myModelAndView));

        CuT.handle(request, response);

        final Object model = myModelAndView.model;
        assertNotNull(model);
        assertTrue(model instanceof Map);

        final Map<String, Object> vm = (Map<String, Object>) model;
        assertEquals("Welcome!", vm.get("title"));
        assertEquals("0", vm.get(GetHomeRoute.PLAYERS_ONLINE_ATTR));
        assertEquals(player, vm.get(GetHomeRoute.CURR_PLAYER));
        assertEquals(null, vm.get(GetHomeRoute.PLAYERS_LIST_ATTR));
        assertEquals(null, vm.get(GetHomeRoute.MESSAGE_ATTR));
    }

    /**
     * Test the sign-in feature in the case where no username was given
     */
    @Test
    public void test_nullSignin(){
        try{
            playerLobby.signIn(null);
        }catch(NoSuchElementException e){
            assertEquals(e.getMessage(), "Invalid username received." );
        }

    }
}
