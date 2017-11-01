package com.webcheckers.ui.boardView.AjaxRoutes;

import com.google.gson.Gson;
import com.webcheckers.ui.boardView.Message;
import spark.Request;
import spark.Response;
import spark.Route;

public class PostValidateMove implements Route{
    private final Gson gson = new Gson();

    @Override
    public Object handle(Request request, Response response) {
        return gson.toJson(new Message("true", Message.Type.info));
    }
}
