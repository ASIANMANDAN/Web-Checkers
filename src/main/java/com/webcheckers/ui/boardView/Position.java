package com.webcheckers.ui.boardView;

/**
 * file: checkers-app
 * author: erl3193@rit.edu Emily Lederman
 * description:
 */
public class Position {
    private int row;
    private int cell;

    public Position(int r, int c){
        row = r;
        cell = c;
    }

    public int getRow(){
        return row;
    }

    public int getCell(){
        return cell;
    }
}
