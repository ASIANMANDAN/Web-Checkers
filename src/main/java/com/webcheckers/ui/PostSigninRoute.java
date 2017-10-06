package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;


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
    private static final String MESSAGE_ATTR = "message";
    //private static final String MESSAGE_TYPE_ATTR = "messageType";
    private static final String USERNAME_PARAM = "username";

    private final TemplateEngine templateEngine;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /} HTTP request.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public PostSigninRoute(final TemplateEngine templateEngine) {
        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.templateEngine = templateEngine;
    }

    /**
     * Render the WebCheckers Sign-in page after a user has signed-in.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the rendered HTML for the Sign-in page
     */
    @Override
    public Object handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();

        // retrieve the PlayerLobby object
        final Session session = request.session();
        //Get the PlayerLobby object from attribute map
        final PlayerLobby playerLobby =
                session.attribute(GetHomeRoute.PLAYERLOBBY_KEY);

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
                mv = accepted(vm);
                break;

            default:
                // All the GuessResult values are in case statements so we should never get here.
                throw new NoSuchElementException("Invalid result of guess received.");
        }
        return templateEngine.render(mv);
    }

    private ModelAndView error(final Map<String, Object> vm) {
        String message = "The username you selected was invalid as it contains " +
                "double quotation marks.";
        vm.put(MESSAGE_ATTR, message);
        vm.put("title", "Sign-in");
        return new ModelAndView(vm, VIEW_NAME);
    }

    private ModelAndView empty(final Map<String, Object> vm) {
        String message = "Your username cannot be blank.";
        vm.put(MESSAGE_ATTR, message);
        vm.put("title", "Sign-in");
        return new ModelAndView(vm, VIEW_NAME);
    }

    private ModelAndView taken(final Map<String, Object> vm) {
        String message = "The username you selected is already in use.";
        vm.put(MESSAGE_ATTR, message);
        vm.put("title", "Sign-in");
        return new ModelAndView(vm, VIEW_NAME);
    }

    private ModelAndView accepted(final Map<String, Object> vm) {
        vm.put("title", "Welcome!");
        return new ModelAndView(vm, GetHomeRoute.VIEW_NAME);
    }
}
