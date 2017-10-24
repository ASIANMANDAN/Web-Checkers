package com.webcheckers.ui.boardView;

/**
 *  A class which formats information to be passed through Ajax routes
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class Message {

    public enum Type {info, error}
    private String text;
    private Type type;

    /**
     * Creates a Message object.
     *
     * @param text the text to be placed in the message
     * @param type the type of the message
     */
    public Message(String text, Type type) {
        this.text = text;
        this.type = type;
    }

    /**
     * Get the text of a Message object.
     *
     * @return the message text
     */
    public String getText() {
        return text;
    }

    /**
     * Get the type of a Message object.
     *
     * @return the message type
     */
    public Type getType(){
        return type;
    }
}
