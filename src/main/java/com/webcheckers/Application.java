package com.webcheckers;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.webcheckers.appl.CurrentGames;
import com.webcheckers.appl.Game;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.model.board.Board;
import com.webcheckers.model.board.Piece;
import com.webcheckers.model.board.Space;
import com.webcheckers.ui.WebServer;

import spark.TemplateEngine;
import spark.template.freemarker.FreeMarkerEngine;


/**
 * The entry point for the WebCheckers web application.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public final class Application {
  private static final Logger LOG = Logger.getLogger(Application.class.getName());

  //
  // Application Launch method
  //

  /**
   * Entry point for the WebCheckers web application.
   *
   * <p>
   * It wires the application components together.  This is an example
   * of <a href='https://en.wikipedia.org/wiki/Dependency_injection'>Dependency Injection</a>
   * </p>
   *
   * @param args
   *    Command line arguments; none expected.
   */
  public static void main(String[] args) throws Exception {
    // initialize Logging
    try {
      ClassLoader classLoader = Application.class.getClassLoader();
      final InputStream logConfig = classLoader.getResourceAsStream("log.properties");
      LogManager.getLogManager().readConfiguration(logConfig);
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("Could not initialize log manager because: " + e.getMessage());
    }

    // The application uses FreeMarker templates to generate the HTML
    // responses sent back to the client. This will be the engine processing
    // the templates and associated data.
    final TemplateEngine templateEngine = new FreeMarkerEngine();

    // The application uses Gson to generate JSON representations of Java objects.
    // This should be used by your Ajax Routes to generate JSON for the HTTP
    // response to Ajax requests.
    final Gson gson = new Gson();

    //Application which tracks the players which are signed in.
    final PlayerLobby playerLobby = new PlayerLobby();

    Player bob = new Player("bob");
    Player pancakes = new Player("Pancakes");
    Player checkers = new Player("checkers");
    Player buddah = new Player("buddah");
    Player ilove261 = new Player("ILove261");
    Player scrummaster = new Player("ScrumMaster");

    Game game1 = new Game (bob, pancakes);
    HashMap<Player, Game> cg = new HashMap<>();

    Space[][] board2 = new Board().getBoard();

    board2[3][0].setPiece(new Piece(Piece.Color.RED, Piece.Type.SINGLE));
    board2[3][4].setPiece(new Piece(Piece.Color.WHITE, Piece.Type.SINGLE));
    board2[6][1].setPiece(new Piece(Piece.Color.RED, Piece.Type.SINGLE));
    board2[5][2].setPiece(new Piece(Piece.Color.WHITE, Piece.Type.SINGLE));
    board2[3][2].setPiece(new Piece(Piece.Color.WHITE, Piece.Type.SINGLE));
    board2[1][4].setPiece(new Piece(Piece.Color.WHITE, Piece.Type.SINGLE));
    board2[0][1].setPiece(new Piece(Piece.Color.WHITE, Piece.Type.SINGLE));

    Game game2 = new Game(checkers, buddah, board2);

    Space[][] board3 = new Board().getBoard();

    board3[0][7].setPiece(new Piece(Piece.Color.WHITE, Piece.Type.SINGLE));
    board3[2][7].setPiece(new Piece(Piece.Color.RED, Piece.Type.SINGLE));
    board3[2][5].setPiece(new Piece(Piece.Color.RED, Piece.Type.SINGLE));

    Game game3 = new Game(ilove261, scrummaster, board3);

    cg.put(bob, game1);
    cg.put(pancakes, game1);
    cg.put(checkers, game2);
    cg.put(buddah, game2);
    cg.put(ilove261, game3);
    cg.put(scrummaster, game3);

    final CurrentGames currentGames = new CurrentGames(cg);

    // inject the game center and freemarker engine into web server
    final WebServer webServer = new WebServer(templateEngine, gson, playerLobby,
            currentGames);

    // inject web server into application
    final Application app = new Application(webServer);

    // start the application up
    app.initialize();
  }

  //
  // Attributes
  //

  private final WebServer webServer;

  //
  // Constructor
  //

  private Application(final WebServer webServer) {
    // validation
    Objects.requireNonNull(webServer, "webServer must not be null");
    //
    this.webServer = webServer;
  }

  //
  // Private methods
  //

  private void initialize() {
    LOG.config("WebCheckers is initializing.");

    // configure Spark and startup the Jetty web server
    webServer.initialize();

    // other applications might have additional services to configure

    LOG.config("WebCheckers initialization complete.");
  }

}