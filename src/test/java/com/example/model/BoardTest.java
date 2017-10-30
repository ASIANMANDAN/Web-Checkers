package com.example.model;

import static org.junit.Assert.*;

import com.webcheckers.model.board.Board;
import com.webcheckers.model.board.Space;
import org.junit.Test;


/**
 * Created by nate on 10/30/17.
 */
public class BoardTest {


    /**
     * Tests the constructor with no args
     * @throws Exception
     */
    @Test
    public void ctor_noArg() throws Exception { new Board(); }

    /**
     * Tests the currentColor was initialized correctly/
     * @throws Exception
     */
    @Test
    public void test_currColor() throws Exception{
        Board CuT = new Board();
        assertEquals(Board.ActiveColor.RED, CuT.currentTurn);
    }

    /**
     * Tests to see if there is a valid empty board.
     * @throws Exception
     */
    @Test
    public void test_emptyBoard() throws Exception{
        Board CuT = new Board();
        int size = Board.size;
        Space[][] cutBoard = CuT.getBoard();

        for (int row = 0; row< size; row++){
            for (int col= 0; col< size; col++){
                if ((row%2 == 0 && col%2 == 1) || (row%2 == 1 && col%2 == 0)){
                    Space compareSpace = new Space(row, col, Space.Color.BLACK);
                    assertEquals(compareSpace, cutBoard[row][col]);
                }
                else{
                    Space compareSpace = new Space(row, col, Space.Color.WHITE);
                    assertEquals(compareSpace, cutBoard[row][col]);
                }

            }
        }

    }

    /**
     * Tests the newGame function to make sure the right pieces are in the right
     * places.
     * @throws Exception
     */
    @Test
    public void test_newGame() throws Exception{
        Board CuT = new Board();
        CuT.newGame();
        int size = Board.size;
        Space[][] cutBoard = CuT.getBoard();

        for(int row= 0; row< 3; row++){
            for (int col= 0; col< )
        }



    }


}
