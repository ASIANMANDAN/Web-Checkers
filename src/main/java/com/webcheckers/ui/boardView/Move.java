package com.webcheckers.ui.boardView;

/**
 * file: checkers-app
 * author: erl3193@rit.edu Emily Lederman
 * description: represents a Move Ajax route
 */
public class Move {
    private Position start;
    private Position end;

    //constructor for a Move
    public Move(Position startPos, Position endPos){
        start = startPos;
        end = endPos;
    }

    public Position getStart(){return start;}

    public Position getEnd(){
        return end;
    }
}
