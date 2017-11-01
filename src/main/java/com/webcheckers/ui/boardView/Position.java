package com.webcheckers.ui.boardView;

public class Position {

    private int row;
    private int cell;

    public Position(int row, int col) {
        this.row = row;
        this.cell = col;
    }

    public int getCell() {
        return cell;
    }

    public int getRow() {
        return row;
    }
}
