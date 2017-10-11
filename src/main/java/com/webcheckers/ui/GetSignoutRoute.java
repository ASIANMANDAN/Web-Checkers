package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * The UI controller to GET the sign-out page.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class GetSignoutRoute implements Route{

    //FTL file which is responsible for rendering the page
    private static final String VIEW_NAME = "home.ftl";
    //number of players in the lobby
    static final String PLAYERS_ONLINE_ATTR = "numPlayersOnline";

    //Key in the session attribute map for the current user Player object
    static final String CURR_PLAYER = "currentPlayer";

    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /} HTTP request.
     *
     * @param templateEngine the HTML template rendering engine
     * @param playerLobby the application which shows a list of players that are signed in.
     */
    public GetSignoutRoute(final TemplateEngine templateEngine,
                        final PlayerLobby playerLobby) {
        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");

        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
    }


    /**
     * Render the WebCheckers Home page after the player has signed out.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     *
     * @return the rendered HTML for the Home page
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        //Retrieve the HTTP session
        final Session httpSession = request.session();
        Player player = httpSession.attribute(CURR_PLAYER);

        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Player Sign-out");
        //updates number of players
        vm.put(PLAYERS_ONLINE_ATTR, playerLobby.getNumOfUsers());
        //signs out the current user
        playerLobby.signOut(player.getUsername());
        //Remove current player from session
        httpSession.removeAttribute(CURR_PLAYER);
        //Return the user to the home page
        response.redirect(WebServer.HOME_URL);

        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
}
