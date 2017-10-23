package com.webcheckers.ui.boardView;

/**
 * Created by nate on 10/19/17.
 */
public class Message {
    public enum Type {info, error}

    private String text;
    private Type type;


    public Message(String message, Type type){
        this.text = message;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public Type getType(){
        return type;
    }
}
