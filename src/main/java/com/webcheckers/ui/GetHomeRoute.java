package com.webcheckers.ui;

import java.util.*;
import java.util.logging.Logger;

import com.webcheckers.appl.PlayerLobby;
import spark.*;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetHomeRoute implements Route {
  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

  //FTL file which is responsible for rendering the page
  static final String VIEW_NAME = "home.ftl";
  static final String PLAYERS_ONLINE_ATTR = "numPlayersOnline";
  static final String PLAYERS_LIST_ATTR = "allPlayers";

  //Key in the session attribute map for the playerLobby object
  static final String PLAYERLOBBY_KEY = "playerLobby";
  //Key in the session attribute map for the current user Player object
  static final String CURR_PLAYER = "currentPlayer";

  private final TemplateEngine templateEngine;
  private final PlayerLobby playerLobby;

  /**
   * Create the Spark Route (UI controller) for the
   * {@code GET /} HTTP request.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   *
   * @param playerLobby
   *   the application which shows a list of players that are signed in.
   */
  public GetHomeRoute(final TemplateEngine templateEngine,
                      final PlayerLobby playerLobby) {
    // validation
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");

    this.templateEngine = templateEngine;
    this.playerLobby = playerLobby;

    LOG.config("GetHomeRoute is initialized.");
  }

  /**
   * Render the WebCheckers Home page.
   *
   * @param request
   *   the HTTP request
   * @param response
   *   the HTTP response
   *
   * @return
   *   the rendered HTML for the Home page
   */
  @Override
  public Object handle(Request request, Response response) {
    LOG.finer("GetHomeRoute is invoked.");

    //Retrieve the HTTP session
    final Session httpSession = request.session();

    //Start building the view-model
    Map<String, Object> vm = new HashMap<>();
    vm.put("title", "Welcome!");
    vm.put(PLAYERS_ONLINE_ATTR, playerLobby.getNumOfUsers());
    //provide the current player to the view-model
    vm.put(CURR_PLAYER, httpSession.attribute(CURR_PLAYER));
    //provide the playerlobby to the view-model
    vm.put(PLAYERS_LIST_ATTR, playerLobby.getUserList());


    //Add the player lobby object to session attribute map
    httpSession.attribute(PLAYERLOBBY_KEY, playerLobby);

    return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
  }

}