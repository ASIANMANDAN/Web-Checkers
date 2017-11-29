package com.webcheckers.ui;

import spark.*;

/**
 * Created by nate on 11/29/17.
 */
public class GetReplayRoute implements Route {

    private final TemplateEngine templateEngine;

    public GetReplayRoute(TemplateEngine template){
        this.templateEngine = template;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        return null;
    }
}
