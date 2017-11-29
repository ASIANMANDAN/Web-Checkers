package com.webcheckers.ui;

import com.webcheckers.appl.CurrentGames;
import com.webcheckers.appl.Game;
import com.webcheckers.model.Player;
import com.webcheckers.model.board.Board;
import com.webcheckers.model.board.Piece;
import com.webcheckers.model.board.Space;
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
 * The unit test suite for the {@link GetGameRoute} component.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class GetGameRouteTest {

    private final String VICTORY = "Congratulations! You just beat player4 " +
            " in a game of checkers.";
    private final String DEFEAT = "player3 has just won the game. Better " +
            "luck next time.";

    private GetGameRoute CuT;

    //Mock objects
    private Request request;
    private Session session;
    private TemplateEngine engine;
    private Player player1;
    private Player player2;
    private CurrentGames currentGames;

    @Before
    public void setup() throws Exception {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);

        engine = mock(TemplateEngine.class);

        player1 = new Player("player");
        player2 = new Player("player2");

        //Create a valid gameboard that represents an ongoing game
        Space[][] board = new Board().getBoard();
        board[0][1].setPiece(new Piece(Piece.Color.WHITE, Piece.Type.SINGLE));
        board[3][0].setPiece(new Piece(Piece.Color.RED, Piece.Type.SINGLE));

        Game mockGame = new Game(player1, player2, board);
        HashMap<Player, Game> gameList = new HashMap<>();
        gameList.put(player1, mockGame);
        gameList.put(player2, mockGame);

        currentGames = new CurrentGames(gameList);

        when(session.attribute(GetGameRoute.CURRENTGAMES_KEY)).thenReturn(currentGames);

        CuT = new GetGameRoute(engine);
    }

    /**
     * Test GetGameRoute will redirect to the home page when no opponent is selected
     */
    @Test
    public void test_no_opponent() throws Exception {
        Player player3 = new Player("player3");

        Response response = mock(Response.class);
        final MyModelAndView myModelView = new MyModelAndView();
        when(engine.render(any(ModelAndView.class))).thenAnswer(MyModelAndView.makeAnswer(myModelView));
        when(session.attribute(GetGameRoute.CURR_PLAYER)).thenReturn(player3);

        try{
            CuT.handle(request, response);
        }catch(HaltException e){
            assertTrue(e instanceof HaltException);
        }

        //Because of there being no opponent, the page redirects and doesn't fill a model
        final Object model = myModelView.model;
        assertNull(model);
    }

    /**
     * Test GetGameRoute will redirect to the home page when selected opponent is in game already.
     */
    @Test
    public void test_opponent_unavailable() throws Exception {
        Response response = mock(Response.class);
        Player player3 = new Player("p3");
        final MyModelAndView myModelView = new MyModelAndView();
        when(engine.render(any(ModelAndView.class))).thenAnswer(MyModelAndView.makeAnswer(myModelView));
        when(session.attribute(GetGameRoute.CURR_PLAYER)).thenReturn(player3);

        when(request.queryParams(GetGameRoute.OPPONENT_PARAM)).thenReturn("player2");

        try{
            CuT.handle(request, response);
        }catch(HaltException e){
            assertTrue(e instanceof HaltException);
        }

        //Because of there being no opponent, the page redirects and doesn't fill a model
        final Object model = myModelView.model;
        assertNull(model);
    }

    /**
     * Test game route generates correctly for the player who starts the game.
     */
    @Test
    public void test_game_starter() throws Exception {
        Response response = mock(Response.class);

        Player player3 = new Player("player3");
        Player player4 = new Player("player4");


        final MyModelAndView myModelView = new MyModelAndView();
        when(engine.render(any(ModelAndView.class))).thenAnswer(MyModelAndView.makeAnswer(myModelView));
        when(session.attribute(GetGameRoute.CURR_PLAYER)).thenReturn(player3);
        when(request.queryParams(GetGameRoute.OPPONENT_PARAM)).thenReturn("player4");
        //when(currentGames.getRedPlayer(player)).thenReturn(player);

        CuT.handle(request, response);

        final Object model = myModelView.model;
        assertNotNull(model);
        assertTrue(model instanceof Map);

        @SuppressWarnings("unchecked")
        final Map<String, Object> vm = (Map<String, Object>) model;
        assertEquals("Game", vm.get("title"));
        assertEquals(Board.ViewMode.PLAY, vm.get(GetGameRoute.MODE_ATTR));
        assertEquals(player3, vm.get(GetGameRoute.CURR_PLAYER));
        assertEquals(player3, vm.get(GetGameRoute.RED_PLAYER_ATTR));
        assertEquals(player4, vm.get(GetGameRoute.WHITE_PLAYER_ATTR));
        assertEquals(Board.ActiveColor.RED, vm.get(GetGameRoute.ACTIVE_ATTR));
        assertNotNull(vm.get(GetGameRoute.BOARD_ATTR));
    }

    /**
     * Test game route generates correctly when an opponent is redirected to the game
     */
    @Test
    public void test_opponent_redirect() throws Exception {
        //Create a valid gameboard that represents an ongoing game
        Space[][] board = new Board().getBoard();
        board[0][1].setPiece(new Piece(Piece.Color.WHITE, Piece.Type.SINGLE));
        board[3][0].setPiece(new Piece(Piece.Color.RED, Piece.Type.SINGLE));
        Player player3 = new Player("player3");
        Player player4 = new Player("player4");
        currentGames.addGame(player3, player4, board);

        Response response = mock(Response.class);
        final MyModelAndView myModelView = new MyModelAndView();
        when(engine.render(any(ModelAndView.class))).thenAnswer(MyModelAndView.makeAnswer(myModelView));
        when(session.attribute(GetGameRoute.CURR_PLAYER)).thenReturn(player4);

        CuT.handle(request, response);

        final Object model = myModelView.model;
        assertNotNull(model);
        assertTrue(model instanceof Map);

        @SuppressWarnings("unchecked")
        final Map<String, Object> vm = (Map<String, Object>) model;
        assertEquals("Game", vm.get("title"));
        assertEquals(Board.ViewMode.PLAY, vm.get(GetGameRoute.MODE_ATTR));
        assertEquals(player4, vm.get(GetGameRoute.CURR_PLAYER));
        assertEquals(player3, vm.get(GetGameRoute.RED_PLAYER_ATTR));
        assertEquals(player4, vm.get(GetGameRoute.WHITE_PLAYER_ATTR));
        assertEquals(Board.ActiveColor.RED, vm.get(GetGameRoute.ACTIVE_ATTR));
        assertNotNull(vm.get(GetGameRoute.BOARD_ATTR));
    }

    /**
     * Test game route redirects to home when opponent resigns.
     */
    @Test
    public void test_resign() throws Exception {
        Response response = mock(Response.class);
        currentGames.removePlayer(player2, player1);
        final MyModelAndView myModelView = new MyModelAndView();
        when(engine.render(any(ModelAndView.class))).thenAnswer(MyModelAndView.makeAnswer(myModelView));
        when(session.attribute(GetGameRoute.CURR_PLAYER)).thenReturn(player1);
        when(session.attribute(GetGameRoute.MESSAGE_KEY)).thenReturn("player2 has resigned from the game.");

        try{
            CuT.handle(request, response);
        }catch(HaltException e){
            assertTrue(e instanceof HaltException);
        }

        //Because of there being no opponent, the page redirects and doesn't fill a model
        final Object model = myModelView.model;

        String message = "player2 has resigned from the game.";
        assertEquals(message, session.attribute(GetGameRoute.MESSAGE_KEY));
        assertNull(model);
    }

    /**
     * Test that the proper view is created when the current player wins.
     *
     * @throws Exception occurs if the given column or row of a space
     * is greater or less than the bounds established by a standard
     * game board
     */
    @Test
    public void test_victory() throws Exception {
        //Create a gameboard where player3 has won
        Space[][] board = new Board().getBoard();
        board[3][0].setPiece(new Piece(Piece.Color.RED, Piece.Type.SINGLE));
        Player player3 = new Player("player3");
        Player player4 = new Player("player4");
        currentGames.addGame(player3, player4, board);

        Response response = mock(Response.class);
        final MyModelAndView myModelView = new MyModelAndView();
        when(engine.render(any(ModelAndView.class))).thenAnswer(MyModelAndView.makeAnswer(myModelView));
        when(session.attribute(GetGameRoute.CURR_PLAYER)).thenReturn(player3);
        when(session.attribute(GetGameRoute.OPPONENT_KEY)).thenReturn(player4);
        when(session.attribute(GetGameRoute.MESSAGE_KEY)).thenReturn(VICTORY);

        try{
            CuT.handle(request, response);
        }catch(HaltException e){
            assertTrue(e instanceof HaltException);
        }

        //Because of there being no opponent, the page redirects and doesn't fill a model
        final Object model = myModelView.model;

        assertEquals(VICTORY, session.attribute(GetGameRoute.MESSAGE_KEY));
        assertNull(model);
    }

    /**
     * Test that the proper view is created when the current player loses.
     *
     * @throws Exception occurs if the given column or row of a space
     * is greater or less than the bounds established by a standard
     * game board
     */
    @Test
    public void test_defeat() throws Exception {
        //Create a gameboard where player3 has won
        Space[][] board = new Board().getBoard();
        board[3][0].setPiece(new Piece(Piece.Color.RED, Piece.Type.SINGLE));
        Player player3 = new Player("player3");
        Player player4 = new Player("player4");
        currentGames.addGame(player3, player4, board);

        Response response = mock(Response.class);
        final MyModelAndView myModelView = new MyModelAndView();
        when(engine.render(any(ModelAndView.class))).thenAnswer(MyModelAndView.makeAnswer(myModelView));
        when(session.attribute(GetGameRoute.CURR_PLAYER)).thenReturn(player4);
        when(session.attribute(GetGameRoute.OPPONENT_KEY)).thenReturn(player3);
        when(session.attribute(GetGameRoute.MESSAGE_KEY)).thenReturn(DEFEAT);

        try{
            CuT.handle(request, response);
        }catch(HaltException e){
            assertTrue(e instanceof HaltException);
        }

        //Because of there being no opponent, the page redirects and doesn't fill a model
        final Object model = myModelView.model;

        assertEquals(DEFEAT, session.attribute(GetGameRoute.MESSAGE_KEY));
        assertNull(model);
    }
}
