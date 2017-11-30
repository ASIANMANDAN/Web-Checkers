package com.webcheckers.ui;

import com.webcheckers.appl.CurrentGames;
import com.webcheckers.appl.Game;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.model.board.Board;
import com.webcheckers.model.board.Piece;
import com.webcheckers.model.board.Space;
import com.webcheckers.ui.boardView.BoardView;
import org.junit.Before;
import org.junit.Test;
import spark.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The unit test suite for the {@Link GetSpectateRoute} component.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class GetSpectateRouteTest {

    private GetSpectateRoute CuT;

    //Friendly objects
    private CurrentGames currentGames;
    private Player red;
    private Player white;
    private Player spectator;

    //Mock objects
    private Request request;
    private Session session;
    private TemplateEngine engine;
    private PlayerLobby lobby;

    @Before
    public void setup() throws Exception {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        lobby = mock(PlayerLobby.class);

        engine = mock(TemplateEngine.class);

        red = new Player("red");
        white = new Player("white");
        spectator = new Player("spectator");

        //Create a valid gameboard that represents an ongoing game
        Space[][] board = new Board().getBoard();
        board[0][1].setPiece(new Piece(Piece.Color.WHITE, Piece.Type.SINGLE));
        board[3][0].setPiece(new Piece(Piece.Color.RED, Piece.Type.SINGLE));

        Game mockGame = new Game(red, white, board);
        HashMap<Player, Game> gameList = new HashMap<>();
        gameList.put(red, mockGame);
        gameList.put(white, mockGame);

        currentGames = new CurrentGames(gameList);

        CuT = new GetSpectateRoute(engine);
    }

    /**
     * Test GetSpectateRoute will redirect to the home page when no game is selected
     */
    @Test
    public void test_no_game() throws Exception{
        Response response = mock(Response.class);
        final MyModelAndView myModelView = new MyModelAndView();
        when(engine.render(any(ModelAndView.class))).thenAnswer(MyModelAndView.makeAnswer(myModelView));

        //Setup test scenario.
        when(session.attribute(GetSpectateRoute.CURR_PLAYER)).thenReturn(spectator);
        when(session.attribute(GetSpectateRoute.CURRENTGAMES_KEY)).thenReturn(currentGames);
        when(request.queryParams(GetSpectateRoute.GAME_PARAM)).thenReturn(null);

        //Invoke test
        try{
            CuT.handle(request, response);
        }catch (HaltException e){
            assertTrue(e instanceof HaltException);
        }

        //Because no game was selected, the page redirects and doesn't fill a model.
        final Object model = myModelView.model;
        assertNull(model);
    }

    /**
     * Test GetSpectateRoute will redirect to the home page when the game has ended.
     */
    @Test
    public void test_game_end() throws Exception{
        Response response = mock(Response.class);
        final MyModelAndView myModelView = new MyModelAndView();
        when(engine.render(any(ModelAndView.class))).thenAnswer(MyModelAndView.makeAnswer(myModelView));
        CurrentGames cg = mock(CurrentGames.class);
        Player player1 = mock(Player.class);

        //Setup test scenario
        when(session.attribute(GetSpectateRoute.CURR_PLAYER)).thenReturn(spectator);
        when(session.attribute(GetSpectateRoute.CURRENTGAMES_KEY)).thenReturn(cg);
        when(session.attribute(GetSpectateRoute.PLAYERLOBBY_KEY)).thenReturn(lobby);
        when(request.queryParams(GetSpectateRoute.GAME_PARAM)).thenReturn("player1 vs player2");
        when(lobby.getWatching(spectator.getUsername())).thenReturn(Boolean.FALSE);
        when(cg.getRedPlayer(new Player(request.queryParams(GetSpectateRoute.GAME_PARAM)))).thenReturn(player1);
        when(cg.getOpponent(player1)).thenReturn(null);

        //Invoke the test.
        try{
            CuT.handle(request, response);
        }catch (HaltException e){
            assertTrue(e instanceof HaltException);
        }

        //Because the game has ended, the page redirects and doesn't fill a model.
        final Object model = myModelView.model;
        assertNull(model);
        //Player boolean flags updated
        assertTrue(spectator.getWatching());
        assertFalse(lobby.inLobby(spectator));
    }

    /**
     * Test spectate route generates correctly
     */
    @Test
    public void test_spectate() throws Exception {
        Response response = mock(Response.class);
        final MyModelAndView myModelView = new MyModelAndView();
        when(engine.render(any(ModelAndView.class))).thenAnswer(MyModelAndView.makeAnswer(myModelView));

        //Setup the test scenario.
        when(session.attribute(GetSpectateRoute.CURR_PLAYER)).thenReturn(spectator);
        when(session.attribute(GetSpectateRoute.CURRENTGAMES_KEY)).thenReturn(currentGames);
        when(session.attribute(GetSpectateRoute.PLAYERLOBBY_KEY)).thenReturn(lobby);
        when(lobby.getWatching(spectator.getUsername())).thenReturn(Boolean.FALSE);
        when(request.queryParams(GetSpectateRoute.GAME_PARAM)).thenReturn("red vs white");
        when(request.queryParams(GetSpectateRoute.GAME_PARAM)).thenReturn("red");

        //Invoke the test.
        CuT.handle(request, response);

        //Check the model is a non null Map
        final Object model = myModelView.model;
        assertNotNull(model);
        assertTrue(model instanceof Map);

        //Check all needed view model data is present
        final Map<String, Object> vm = (Map<String, Object>) model;
        assertEquals("Game", vm.get("title"));
        assertEquals(Board.ViewMode.SPECTATOR, vm.get(GetSpectateRoute.MODE_ATTR));
        assertEquals(spectator, vm.get(GetSpectateRoute.CURR_PLAYER));
        assertEquals(red, vm.get(GetSpectateRoute.RED_PLAYER_ATTR));
        assertEquals(white, vm.get(GetSpectateRoute.WHITE_PLAYER_ATTR));
        assertEquals(Board.ActiveColor.RED, vm.get(GetSpectateRoute.ACTIVE_ATTR));
        assertNotNull(vm.get(GetSpectateRoute.BOARD_ATTR));

        //Player boolean flags updated
        assertTrue(spectator.getWatching());
        assertFalse(lobby.inLobby(spectator));
    }

    /**
     * Test that a message pops up for the spectator when they have been challenged to a game.
     */
    @Test
    public void test_message() throws Exception {
        Response response = mock(Response.class);
        final MyModelAndView myModelView = new MyModelAndView();
        when(engine.render(any(ModelAndView.class))).thenAnswer(MyModelAndView.makeAnswer(myModelView));

        //Setup the test scenario.
        when(session.attribute(GetSpectateRoute.CURR_PLAYER)).thenReturn(spectator);
        when(session.attribute(GetSpectateRoute.CURRENTGAMES_KEY)).thenReturn(currentGames);
        when(session.attribute(GetSpectateRoute.PLAYERLOBBY_KEY)).thenReturn(lobby);
        when(lobby.getWatching(spectator.getUsername())).thenReturn(Boolean.FALSE);
        when(request.queryParams(GetSpectateRoute.GAME_PARAM)).thenReturn("red vs white");
        when(request.queryParams(GetSpectateRoute.GAME_PARAM)).thenReturn("red");
        when(lobby.getSelected(spectator.getUsername())).thenReturn(Boolean.TRUE);

        //Invoke the test.
        CuT.handle(request, response);

        //Check the model is a non null Map
        final Object model = myModelView.model;
        assertNotNull(model);
        assertTrue(model instanceof Map);

        //Check all needed view model data is present
        final Map<String, Object> vm = (Map<String, Object>) model;
        assertEquals("Game", vm.get("title"));
        assertEquals(Board.ViewMode.SPECTATOR, vm.get(GetSpectateRoute.MODE_ATTR));
        assertEquals(spectator, vm.get(GetSpectateRoute.CURR_PLAYER));
        assertEquals(red, vm.get(GetSpectateRoute.RED_PLAYER_ATTR));
        assertEquals(white, vm.get(GetSpectateRoute.WHITE_PLAYER_ATTR));
        assertEquals(Board.ActiveColor.RED, vm.get(GetSpectateRoute.ACTIVE_ATTR));
        assertNotNull(vm.get(GetSpectateRoute.BOARD_ATTR));
        assertEquals("Another player has challenged you to a game! Return to the Home Page if you wish" +
                " to connect.", vm.get(GetSpectateRoute.SELECTED_ATTR));

        //Player boolean flags updated
        assertTrue(spectator.getWatching());
        assertFalse(lobby.inLobby(spectator));
    }
}
