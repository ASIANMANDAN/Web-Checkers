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

    private final TemplateEngine templateEngine;

    private static final String USERNAME = "username";

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
        final String username = request.queryParams(USERNAME);

        ModelAndView mv;
        switch(playerLobby.signIn(username)) {
            case INVALID:
                mv = error();
                break;

            case EMPTY:
                mv = empty();
                break;

            case TAKEN:
                mv = taken();
                break;

            case ACCEPTED:
                mv = accepted();
                break;

            default:
                // All the GuessResult values are in case statements so we should never get here.
                throw new NoSuchElementException("Invalid result of guess received.");
        }
        return templateEngine.render(mv);
    }

    private ModelAndView error(final Map<String, Object> vm, final )
}
