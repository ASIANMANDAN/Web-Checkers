package com.webcheckers.ui.boardView;

import com.webcheckers.model.board.Board;
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
    private static final int size = Board.size;

    /**
     * Creates a BoardView using a Board model which satisfies the conditions
     * needed in Game.ftl to display the board and pieces.
     *
     * @param gameBoard the Board model to base the BoardView off of
     * @param reverse true if the view is to be reversed, red on top, false
     *                to create a view with red on bottom
     * @throws Exception occurs in the Space class if the column index
     *                   used to create the space is greater than the
     */
    public BoardView(Board gameBoard, boolean reverse) throws Exception {

        //Construct the board that the red player will see
        if (!reverse) {
            this.board = new ArrayList<>();
            //Represents the model board
            Space[][] currentGameBoard = gameBoard.getBoard();

            for (int row = 0; row < size; row++) {
                Row newRow = new Row(row);
                for (int col = 0; col < size; col++) {
                    //Space within the board model
                    Space gameSpace = currentGameBoard[row][col];

                    //Create copy to avoid overwrites in future
                    Space displaySpace = new Space(row, col, gameSpace.getColor());
                    displaySpace.setPiece(gameSpace.getPiece());
                    newRow.addSpace(displaySpace);
                }
                this.board.add(row, newRow);
            }
        }

        //Construct the board that the white player will see
        else {
            this.board = new ArrayList<>();
            //Represents the model board
            Space[][] currentGameBoard = gameBoard.getBoard();

            //Create the opponents view
            for (int row = size-1; row >= 0; row--){
                Row newRow = new Row(row);
                for (int col = size-1; col >= 0; col--){
                    //Space within the board model
                    Space gameSpace = currentGameBoard[row][col];

                    //Create copy to avoid overwrites in future
                    Space displaySpace = new Space(row, col, gameSpace.getColor());
                    displaySpace.setPiece(gameSpace.getPiece());
                    newRow.addSpace(displaySpace);
                }
                this.board.add(newRow);
            }
        }
    }

    /**
     * Iterates over the rows of the board.
     *
     * @return the current row to be used in the row iterator
     */
    @Override
    public Iterator<Row> iterator() {
        return board.iterator();
    }
}
