package com.webcheckers.model;

import com.webcheckers.model.board.Space;
import com.webcheckers.ui.boardView.Move;
import com.webcheckers.ui.boardView.Position;

public class Validate {

    public String isValid(Move move, Space[][] board) {
        return null;
    }

    public boolean isDiagonal(Move move, Space[][] board) {
        Position start = move.getStart();
        return false;
    }

}
