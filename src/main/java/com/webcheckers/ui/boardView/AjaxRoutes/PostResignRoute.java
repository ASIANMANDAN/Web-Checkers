package com.webcheckers.ui.boardView.AjaxRoutes;

import com.webcheckers.ui.WebServer;
import com.webcheckers.ui.boardView.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
/**
 * Created by nate on 10/18/17.
 */
public class PostResignRoute implements Route{
    private static final Logger LOG = Logger.getLogger(PostResignRoute.class.getName());

    @Override
    public Object handle(Request request, Response response) throws Exception {
        LOG.fine("In PostResignRoute.");
        Message message = new Message("Player Resigned. Redirecting to home page", Message.Type.info);


        return message;
    }
}
