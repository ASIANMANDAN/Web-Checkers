package com.webcheckers.ui;

import java.util.*;
import java.util.logging.Logger;

import com.webcheckers.appl.CurrentGames;
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
  static final String PLAYERS_ONLINE_ATTR = "numPlayersOnline";
  static final String PLAYERS_LIST_ATTR = "allPlayers";
  static final String MESSAGE_ATTR = "message";
  static final String GAMES_IN_PROGRESS_ATTR = "allGames";

  //Key in the session attribute map for the playerLobby object
  static final String PLAYERLOBBY_KEY = "playerLobby";
  //Key in the session attribute map for the current user Player object
  static final String CURR_PLAYER = "currentPlayer";
  //Key in the session attribute map for the hash of current players in a game
  static final String CURRENTGAMES_KEY = "currentGames";
  //Key in the session attribute map for a String to be shown in case of error
  static final String MESSAGE_KEY = "message";

  private final TemplateEngine templateEngine;
  private final PlayerLobby playerLobby;
  private final CurrentGames currentGames;

  /**
   * Create the Spark Route (UI controller) for the
   * {@code GET /} HTTP request.
   *
   * @param templateEngine the HTML template rendering engine
   * @param playerLobby the application which shows a list of players that are signed in.
   */
  public GetHomeRoute(final TemplateEngine templateEngine,
					  final PlayerLobby playerLobby,
					  final CurrentGames currentGames) {
	// validation
	Objects.requireNonNull(templateEngine, "templateEngine must not be null");
	Objects.requireNonNull(playerLobby, "playerLobby must not be null");
	Objects.requireNonNull(currentGames, "currentGames must not be null");

	this.templateEngine = templateEngine;
	this.playerLobby = playerLobby;
	this.currentGames = currentGames;

	LOG.config("GetHomeRoute is initialized.");
  }

  /**
   * Render the WebCheckers Home page.
   *
   * @param request the HTTP request
   * @param response the HTTP response
   *
   * @return the rendered HTML for the Home page
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
	//Provide the current player to the view-model
	vm.put(CURR_PLAYER, httpSession.attribute(CURR_PLAYER));
	//Provide the playerlobby to the view-model
	vm.put(PLAYERS_LIST_ATTR, playerLobby.getUserList());
	//Provide the list of ongoing games to the view-model
	vm.put(GAMES_IN_PROGRESS_ATTR, currentGames.getGamesList());

	//Provide a message to the view-model if there has been an error
	vm.put(MESSAGE_ATTR, httpSession.attribute(MESSAGE_KEY));
	//Remove from session map so that errors are not redisplayed
	request.session().removeAttribute(MESSAGE_KEY);

	//Add the playerLobby object to session attribute map
	httpSession.attribute(PLAYERLOBBY_KEY, playerLobby);
	//Add currentGames object to session attribute map
	httpSession.attribute(CURRENTGAMES_KEY, currentGames);

	//Checks if the current user has been selected upon each refresh
	Player currentPlayer = httpSession.attribute(CURR_PLAYER);
	if (currentPlayer != null) {

		Player opponent = currentGames.getOpponent(currentPlayer);

		if (currentGames.playerInGame(currentPlayer) && opponent != null) {
			response.redirect("/game?opponent=" + opponent.getUsername());
		} else {
			currentGames.endGame(currentPlayer);
		}
	}

	return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
  }

}