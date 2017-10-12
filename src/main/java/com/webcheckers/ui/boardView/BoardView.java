package com.webcheckers.ui.boardView;

import com.webcheckers.model.board.Board;
import com.webcheckers.model.board.Piece;
import com.webcheckers.model.board.Space;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * UI tier class that represents the checkers board.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class BoardView implements Iterable {

    private ArrayList<Row> board;
    private static final int size = 8;

    public BoardView(Board gameBoard) throws Exception {
        this.board = new ArrayList<>();
        //Represents the model board
        Space[][] currentGameBoard = gameBoard.getBoard();

        for (int r = 0; r < size; r++){
            Row newRow = new Row(r);
            for (int c = 0; c < size; c++){
                //Space within the board model
                Space gameSpace = currentGameBoard[r][c];

                //Create copy to avoid overwrites in future
                Space displaySpace = new Space(r, c, gameSpace.getColor());
                displaySpace.setPiece(gameSpace.getPiece());
                newRow.addSpace(c, displaySpace);
            }
            this.board.add(r, newRow);
        }

    }

    public Iterator<Row> iterator() {
        return board.iterator();
    }
}
