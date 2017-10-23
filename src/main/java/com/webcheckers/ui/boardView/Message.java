package com.webcheckers.ui.boardView;

public class Message {

    public enum Type {info, error}
    private String text;
    private Type type;

    public Message(String text, Type type) {
        this.text = text;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public Type getType(){
        return type;
    }
}
