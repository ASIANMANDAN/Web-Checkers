package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
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
  static final String PLAYERS_ONLINE = "numPlayersOnline";
  //Key in the session attribute map for the list of players in the lobby.
  static final String PLAYERLOBBY_KEY = "playerLobby";
  //Key for the current player
  static final String PLAYER_CURR = "currentPlayer";


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
    vm.put(PLAYERS_ONLINE, playerLobby.getNumOfUsers());
    //provide the current player to the view-model
    vm.put(PLAYER_CURR, playerLobby.getCurrentPlayer());
    //provide the playerlist to the view-model
    //TODO test/utilize
    vm.put(PLAYERLOBBY_KEY, playerLobby.getUserList());


    //Add the player lobby object to session attribute map
    httpSession.attribute(PLAYERLOBBY_KEY, playerLobby);

    return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
  }

}