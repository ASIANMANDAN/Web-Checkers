package com.webcheckers.ui;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.webcheckers.appl.CurrentGames;
import com.webcheckers.model.Player;
import com.webcheckers.model.board.Board;
import com.webcheckers.model.board.Piece;
import com.webcheckers.model.board.Space;
import com.webcheckers.ui.boardView.BoardView;
import com.webcheckers.ui.boardView.Row;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *  Test for the UI representation of the board model.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class BoardViewTest {

    private Board board;
    private Space[][] arrayBoard;
    private Player redPlayer;
    private Player whitePlayer;
    private CurrentGames games;
    private int size;

    @Before
    public void test_setup() throws Exception {
        this.board = new Board();
        this.redPlayer = mock(Player.class);
        this.whitePlayer = mock(Player.class);
        this.games = mock(CurrentGames.class);
        this.size = Board.size;
        this.board.newGame();
        this.arrayBoard = board.getBoard();

        when(games.getRedPlayer(any(Player.class))).thenReturn(redPlayer);
        when(games.getWhitePlayer(any(Player.class))).thenReturn(whitePlayer);
        when(games.getBoard(any(Player.class))).thenReturn(arrayBoard);



    }


    /**
     * Tests to see if an iterable board is created that is the equivalent to a board
     * model for a red player.
     * @throws Exception
     */
    @Test
    public void test_constrBoardViewRed() throws Exception{
        BoardView boardView = new BoardView(redPlayer, this.games);
        Space[][] arrayBoard = board.getBoard();
        ArrayList<Row> itBoard = boardView.getBoard();

        for(int row = 0; row< size; row++){
            for (int col = 0; col< size; col++){
                assertEquals(arrayBoard[row][col], itBoard.get(row).getRowOfSpaces().get(col));
            }
        }

    }

    /**
     * Tests to see if an iterable board is created that is the equivalent to a board
     * model for a white player.
     * @throws Exception
     */
    @Test
    public void test_constrBoardViewWhite() throws Exception{
        BoardView boardView = new BoardView(whitePlayer, this.games);
        Space[][] arrayBoard = board.getBoard();
        ArrayList<Row> itBoard = boardView.getBoard();

        int itRow = 0;
        int itCol = 0;

        for(int row = size-1; row >= 0; row--){
            for (int col = size-1; col >= 0; col--){
                Space itSpace = itBoard.get(itRow).getRowOfSpaces().get(itCol);
                Space arraySpace = arrayBoard[row][col];
                assertEquals(itSpace, arraySpace);
                itCol++;
            }
            itRow++;
            itCol = 0;
        }

    }

    /**
     * Tests the orientation of the challengers board (red and on the bottom). Makes sure the
     * iterable version matches the correct orientation
     */
    @Test
    public void test_redBoard() throws Exception{
        BoardView boardView = new BoardView(redPlayer, this.games);
        ArrayList<Row> itBoard = boardView.getBoard();

        //Check White Pieces
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < size; col++) {
                if (row == 0 || row == 2) {
                    if (col == 1 || col == 3 || col == 5 || col == 7) {
                        Piece comparePiece = new Piece(Piece.Color.WHITE, Piece.Type.SINGLE);
                        Space currentSpace = itBoard.get(row).getRowOfSpaces().get(col);
                        Piece currentPiece = currentSpace.getPiece();
                        assertEquals(comparePiece, currentPiece);
                    }
                } else {
                    if (col == 0 || col == 2 || col == 4 || col == 6) {
                        Piece comparePiece = new Piece(Piece.Color.WHITE, Piece.Type.SINGLE);
                        Space currentSpace = itBoard.get(row).getRowOfSpaces().get(col);
                        Piece currentPiece = currentSpace.getPiece();
                        assertEquals(comparePiece, currentPiece);
                    }
                }
            }
        }

        //Check Red Pieces
        for (int row = 5; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (row == 5 || row == 7) {
                    if (col == 0 || col == 2 || col == 4 || col == 6) {
                        Piece comparePiece = new Piece(Piece.Color.RED, Piece.Type.SINGLE);
                        Space currentSpace = itBoard.get(row).getRowOfSpaces().get(col);
                        Piece currentPiece = currentSpace.getPiece();
                        assertEquals(comparePiece, currentPiece);
                    }
                } else {
                    if (col == 1 || col == 3 || col == 5 || col == 7) {
                        Piece comparePiece = new Piece(Piece.Color.RED, Piece.Type.SINGLE);
                        Space currentSpace = itBoard.get(row).getRowOfSpaces().get(col);
                        Piece currentPiece = currentSpace.getPiece();
                        assertEquals(comparePiece, currentPiece);
                    }
                }
            }
        }
    }

    /**
     * Tests the orientation of the challengers board (red and on the bottom). Makes sure the
     * iterable version matches the correct orientation
     */
    @Test
    public void test_whiteBoard() throws Exception{
        BoardView boardView = new BoardView(whitePlayer, this.games);
        ArrayList<Row> itBoard = boardView.getBoard();

        //Check Red Pieces
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < size; col++) {
                if (row == 0 || row == 2) {
                    if (col == 1 || col == 3 || col == 5 || col == 7) {
                        Piece comparePiece = new Piece(Piece.Color.RED, Piece.Type.SINGLE);
                        Space currentSpace = itBoard.get(row).getRowOfSpaces().get(col);
                        Piece currentPiece = currentSpace.getPiece();
                        assertEquals(comparePiece, currentPiece);
                    }
                } else {
                    if (col == 0 || col == 2 || col == 4 || col == 6) {
                        Piece comparePiece = new Piece(Piece.Color.RED, Piece.Type.SINGLE);
                        Space currentSpace = itBoard.get(row).getRowOfSpaces().get(col);
                        Piece currentPiece = currentSpace.getPiece();
                        assertEquals(comparePiece, currentPiece);
                    }
                }
            }
        }

        //Check White Pieces
        for (int row = 5; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (row == 5 || row == 7) {
                    if (col == 0 || col == 2 || col == 4 || col == 6) {
                        Piece comparePiece = new Piece(Piece.Color.WHITE, Piece.Type.SINGLE);
                        Space currentSpace = itBoard.get(row).getRowOfSpaces().get(col);
                        Piece currentPiece = currentSpace.getPiece();
                        assertEquals(comparePiece, currentPiece);
                    }
                } else {
                    if (col == 1 || col == 3 || col == 5 || col == 7) {
                        Piece comparePiece = new Piece(Piece.Color.WHITE, Piece.Type.SINGLE);
                        Space currentSpace = itBoard.get(row).getRowOfSpaces().get(col);
                        Piece currentPiece = currentSpace.getPiece();
                        assertEquals(comparePiece, currentPiece);
                    }
                }
            }
        }
    }

    /**
     * Tests the iterator. Go through the List and checks each element if it's a Row.
     * @throws Exception
     */
    @Test
    public void test_iterator() throws Exception{
        BoardView boardView = new BoardView(whitePlayer, this.games);
        Iterator<Row> itr = boardView.iterator();
        for (int row= 0; row< size; row++){
            Row rowOfSpaces = itr.next();
            assertTrue(rowOfSpaces instanceof Row);
        }

    }

}
