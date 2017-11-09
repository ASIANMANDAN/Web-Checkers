package com.webcheckers.ui.boardView;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

/**
 * The unit test suite for the {@link Message} component.
 *
 * @author Dan Wang
 * @author Emily Lederman
 * @author Kevin Paradis
 * @author Nathan Farrell
 */
public class MessageTest {

    private Message CuT;

    private String text;
    private Message.Type type;

    @Before
    public void test_setUp() {
        text = "test";
        type =  Message.Type.info;

        CuT = new Message(text, type);
    }

    /**
     * Test the constructor with Type info.
     */
    @Test
    public void test_create_messageInfo() {
        new Message(text, type);
    }

    /**
     * Test the constructor with type error.
     */
    @Test
    public void test_create_messageError() {
        type = Message.Type.error;
        new Message(text, type);
    }

    /**
     * Test the getText method.
     */
    @Test
    public void test_getText() {
        assertEquals(text, CuT.getText());
    }

    /**
     * Test the getType method.
     */
    @Test
    public void test_getType() {
        assertEquals(type, CuT.getType());
    }

    /**
     * Test the enum values for Type.
     */
    @Test
    public void test_enum(){
        assertEquals(Message.Type.info, Message.Type.valueOf("info"));
        assertEquals(Message.Type.error, Message.Type.valueOf("error"));

    }
}
