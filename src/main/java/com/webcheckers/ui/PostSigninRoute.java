package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

import static spark.Spark.halt;


/**
 * The UI controller to POST username to Sign-in page.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class PostSigninRoute implements Route{

    //FTL file which is responsible for rendering the page
    private static final String VIEW_NAME = "signin.ftl";
    static final String MESSAGE_ATTR = "message";
    private static final String USERNAME_PARAM = "username";

    //Key in the session attribute map for the current user Player object
    static final String CURR_PLAYER = "currentPlayer";

    private final TemplateEngine templateEngine;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code POST /} HTTP request.
     *
     * @param templateEngine the HTML template rendering engine
     */
    public PostSigninRoute(final TemplateEngine templateEngine) {
        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.templateEngine = templateEngine;
    }

    /**
     * Render the WebCheckers Sign-in page after a user has signed-in.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     *
     * @return the rendered HTML for the Sign-in page
     */
    @Override
    public Object handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();

        // retrieve the PlayerLobby object
        final Session httpSession = request.session();
        //Get the PlayerLobby object from attribute map
        final PlayerLobby playerLobby =
                httpSession.attribute(GetHomeRoute.PLAYERLOBBY_KEY);

        //Query server for username entered by client
        final String username = request.queryParams(USERNAME_PARAM);

        ModelAndView mv;
        switch(playerLobby.signIn(username)) {
            case INVALID:
                mv = error(vm);
                break;

            case EMPTY:
                mv = empty(vm);
                break;

            case TAKEN:
                mv = taken(vm);
                break;

            case ACCEPTED:
                Player player = new Player(username);
                httpSession.attribute(CURR_PLAYER, player);

                //Return the user to the home page
                response.redirect(WebServer.HOME_URL);
                halt();
                return null;

            default:
                // All the InputResult values are in case statements so we should never get here.
                throw new NoSuchElementException("Invalid result of guess received.");
        }
        return templateEngine.render(mv);
    }

    /**
     * Handles the view model in the case of an invalid username.
     *
     * @param vm ModelandView object which is rendered by the template
     * @return a modified ModelandView object which expresses an invalid username
     */
    private ModelAndView error(final Map<String, Object> vm) {
        String message = "The username you selected was invalid as it contains " +
                "double quotation marks.";
        vm.put(MESSAGE_ATTR, message);
        vm.put("title", "Sign-in");
        return new ModelAndView(vm, VIEW_NAME);
    }

    /**
     * Handles the view model in the case of an empty username.
     *
     * @param vm ModelandView object which is rendered by the template
     * @return a modified ModelandView object which expresses an empty username
     */
    private ModelAndView empty(final Map<String, Object> vm) {
        String message = "Your username cannot be blank.";
        vm.put(MESSAGE_ATTR, message);
        vm.put("title", "Sign-in");
        return new ModelAndView(vm, VIEW_NAME);
    }

    /**
     * Handles the view model in the case of an taken username.
     *
     * @param vm ModelandView object which is rendered by the template
     * @return a modified ModelandView object which expresses a taken username
     */
    private ModelAndView taken(final Map<String, Object> vm) {
        String message = "The username you selected is already in use.";
        vm.put(MESSAGE_ATTR, message);
        vm.put("title", "Sign-in");
        return new ModelAndView(vm, VIEW_NAME);
    }
}
