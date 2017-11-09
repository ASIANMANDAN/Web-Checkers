package com.webcheckers.ui.boardView;

import com.webcheckers.appl.CurrentGames;
import com.webcheckers.model.Player;
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
     * Creates a boardView using a Board model which satisfies the conditions
     * needed in Game.ftl to display the board and pieces.
     *
     * @param player the player whose view is to be created
     * @param currentGames a list of current games to get the board model from
     * @throws Exception occurs in the Space class if the column index
     *                   used to create the space is greater than the
     */
    public BoardView(Player player, CurrentGames currentGames) throws Exception {

        //Constructs view for the red player
        if (currentGames.getRedPlayer(player).equals(player)) {
            this.board = new ArrayList<>();
            //Represents the model board
            Space[][] currentGameBoard = currentGames.getBoard(player);

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

        //Constructs view for the white player
        else if (currentGames.getWhitePlayer(player).equals(player)){
            this.board = new ArrayList<>();
            //Represents the model board
            Space[][] currentGameBoard = currentGames.getBoard(player);

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
     * Retruns the iterable boardView.
     * @return the iterable version of the board model.
     */
    public ArrayList<Row> getBoard(){
        return board;
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
