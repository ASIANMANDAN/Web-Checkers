package com.webcheckers.ui.boardView.AjaxRoutes;

import static org.junit.Assert.*;

import com.webcheckers.ui.boardView.Message;
import org.junit.Test;
import org.junit.Before;

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

    @Test
    public void test_create_messageInfo() {
        new Message(text, type);
    }

    @Test
    public void test_create_messageError() {
        type = Message.Type.error;
        new Message(text, type);
    }

    @Test
    public void test_getText() {
        assertEquals(text, CuT.getText());
    }

    @Test
    public void test_getType() {
        assertEquals(type, CuT.getType());
    }
}
